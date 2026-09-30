package mx.org.inegi.sistemacaptura;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.metamodel.EntityType;
import mx.org.inegi.sistemacaptura.config.DatabaseTables;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/** Explicit preflight only. Does not migrate, import or write production records. */
public class DeploymentReadOnlyCheck {
    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        try (FileInputStream in = new FileInputStream(args[0])) { config.load(in); }
        List<String> failures = new ArrayList<>();
        try (BasicDataSource ds = new BasicDataSource()) {
            ds.setDriverClassName(config.getProperty("db.driver"));
            ds.setUrl(config.getProperty("db.url"));
            ds.setUsername(config.getProperty("db.username"));
            ds.setPassword(config.getProperty("db.password"));
            ds.setDefaultReadOnly(true);
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(ds);
            factory.setPackagesToScan("mx.org.inegi.sistemacaptura");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            Properties h = new Properties();
            h.setProperty("hibernate.hbm2ddl.auto", "none");
            h.setProperty("hibernate.show_sql", "false");
            h.setProperty("hibernate.dialect", config.getProperty("hibernate.dialect"));
            factory.setJpaProperties(h);
            factory.afterPropertiesSet();
            try {
                for (EntityType<?> type : factory.getObject().getMetamodel().getEntities()) {
                    Table table = type.getJavaType().getAnnotation(Table.class);
                    String name = table.schema() + "." + table.name();
                    EntityManager em = factory.getObject().createEntityManager();
                    try {
                        em.getTransaction().begin();
                        em.createNativeQuery("SET TRANSACTION READ ONLY").executeUpdate();
                        em.createQuery("SELECT e FROM " + type.getName() + " e").setMaxResults(1).getResultList();
                        System.out.println("PASS entity " + name);
                    } catch (Exception ex) {
                        Throwable root = ex;
                        while (root.getCause() != null) root = root.getCause();
                        failures.add(name + ": " + root.getMessage());
                    } finally {
                        if (em.getTransaction().isActive()) em.getTransaction().rollback();
                        em.close();
                    }
                }
                try (Connection connection = ds.getConnection()) {
                    connection.setAutoCommit(false);
                    try (Statement st = connection.createStatement()) { st.execute("SET TRANSACTION READ ONLY"); }
                    for (String table : Arrays.asList(DatabaseTables.CLASIFICADORES_A_SQL, DatabaseTables.TICKETS_A_SQL)) {
                        String[] parts = table.split("\\.");
                        DatabaseMetaData md = connection.getMetaData();
                        int keys = 0;
                        try (ResultSet rs = md.getPrimaryKeys(null, parts[0], parts[1])) {
                            while (rs.next()) { keys++; System.out.println("PK " + table + ": " + rs.getString("COLUMN_NAME")); }
                        }
                        if (keys == 0) failures.add("Missing primary key: " + table);
                        boolean variableFk = false;
                        try (ResultSet rs = md.getImportedKeys(null, parts[0], parts[1])) {
                            while (rs.next()) {
                                String target = rs.getString("PKTABLE_SCHEM") + "." + rs.getString("PKTABLE_NAME");
                                System.out.println("FK " + table + "." + rs.getString("FKCOLUMN_NAME") + " -> " + target);
                                if ("id_a".equals(rs.getString("FKCOLUMN_NAME")) && target.equals(DatabaseTables.VARIABLES_A_SQL)) variableFk = true;
                            }
                        }
                        if (!variableFk) failures.add("Missing variable FK to " + DatabaseTables.VARIABLES_A_SQL + ": " + table);
                        if (keys == 0) continue;
                        try (PreparedStatement st = connection.prepareStatement("SELECT pg_get_serial_sequence(?, ?)")) {
                            st.setString(1, table);
                            st.setString(2, table.equals(DatabaseTables.TICKETS_A_SQL) ? "id_ticket" : "id_unique");
                            try (ResultSet rs = st.executeQuery()) {
                                if (rs.next()) {
                                    System.out.println("SEQUENCE " + table + ": " + rs.getString(1));
                                    if (rs.getString(1) == null) failures.add("Missing identity/serial sequence: " + table);
                                }
                            }
                        }
                    }
                    connection.rollback();
                }
            } finally { factory.destroy(); }
        }
        for (String failure : failures) System.out.println("FAIL " + failure);
        if (!failures.isEmpty()) throw new AssertionError("Preflight " + DatabaseTables.AMBIENTE + ": " + failures.size() + " incompatibilities");
        System.out.println("PASS preflight " + DatabaseTables.AMBIENTE);
    }
}
