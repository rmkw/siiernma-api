package mx.org.inegi.sistemacaptura.armonizacion.visor;

import mx.org.inegi.sistemacaptura.config.DatabaseTables;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import javax.persistence.*;
import mx.org.inegi.sistemacaptura.armonizacion.repository.visor.visor_variables_repo;
import mx.org.inegi.sistemacaptura.armonizacion.service.visor.visor_variables_service_impl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import mx.org.inegi.sistemacaptura.armonizacion.service.variables.variables_armo_service_impl;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_detalle_armo_dto;

/** Run explicitly with the local properties path; all database access is read-only. */
public class VisorReadOnlyCheck {
    static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    static Map<String, String> params(String... values) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i += 2) result.put(values[i], values[i + 1]);
        return result;
    }

    static void inject(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    static long count(visor_variables_service_impl service, Map<String, String> filters) {
        return ((Number) service.listar(filters).get("totalElements")).longValue();
    }

    static Object bean(Class<?> type, EntityManager em, Map<Class<?>, Object> cache) throws Exception {
        if (cache.containsKey(type)) return cache.get(type);
        Object value;
        if (Repository.class.isAssignableFrom(type)) {
            value = new JpaRepositoryFactory(em).getRepository(type);
        } else {
            Class<?> implementation = type.isInterface() ? Class.forName(type.getName() + "_impl") : type;
            value = implementation.getDeclaredConstructor().newInstance();
            cache.put(type, value);
            for (Field field : implementation.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(value, bean(field.getType(), em, cache));
                }
            }
        }
        cache.put(type, value);
        return value;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(args[0])) { properties.load(input); }
        try (BasicDataSource ds = new BasicDataSource()) {
            ds.setDriverClassName(properties.getProperty("db.driver"));
            ds.setUrl(properties.getProperty("db.url"));
            ds.setUsername(properties.getProperty("db.username"));
            ds.setPassword(properties.getProperty("db.password"));
            ds.setDefaultReadOnly(true);
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(ds);
            factory.setPackagesToScan("mx.org.inegi.sistemacaptura");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            Properties hibernate = new Properties();
            hibernate.setProperty("hibernate.hbm2ddl.auto", "none");
            hibernate.setProperty("hibernate.show_sql", "false");
            hibernate.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
            factory.setJpaProperties(hibernate);
            factory.afterPropertiesSet();
            EntityManager em = factory.getObject().createEntityManager();
            try {
                em.getTransaction().begin();
                em.createNativeQuery("SET TRANSACTION READ ONLY").executeUpdate();
                visor_variables_repo repo = new visor_variables_repo();
                inject(repo, "em", em);
                visor_variables_service_impl service = new visor_variables_service_impl();
                inject(service, "repo", repo);
                long start = System.nanoTime();
                Map<String, Object> page = service.listar(params());
                long total = ((Number) page.get("totalElements")).longValue();
                long dbTotal = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM " + DatabaseTables.VARIABLES_A_SQL + "").getSingleResult()).longValue();
                check(total == dbTotal, "Total must match database");
                List<Map<String, Object>> rows = (List<Map<String, Object>>) page.get("content");
                check(rows.size() == Math.min(20L, total), "Page size");
                for (int size : Arrays.asList(20, 50, 100)) {
                    List<Map<String, Object>> sized = (List<Map<String, Object>>) service.listar(params("size", String.valueOf(size))).get("content");
                    List<?> ordered = em.createNativeQuery("SELECT id_a FROM " + DatabaseTables.VARIABLES_A_SQL + " ORDER BY id_a")
                            .setMaxResults(size).getResultList();
                    check(sized.size() == ordered.size(), "Size " + size);
                    for (int i = 0; i < sized.size(); i++) check(sized.get(i).get("idA").equals(ordered.get(i)), "Stable order");
                }
                List<Map<String, Object>> next = (List<Map<String, Object>>) service.listar(params("page", "1")).get("content");
                Set<Object> ids = new HashSet<>();
                for (Map<String, Object> row : rows) {
                    String id = (String) row.get("idA");
                    check(ids.add(id), "Duplicate row");
                }
                for (Map<String, Object> row : next) check(!ids.contains(row.get("idA")), "Page overlap");
                check(count(service, params("variableA", "zzzz_visor_no_existe_987654")) == 0, "Empty search");
                for (String flag : Arrays.asList("clasificacion", "tabulados", "datosabiertos", "mdea", "ods", "validada", "microdatos")) {
                    long yes = count(service, params(flag, "true"));
                    long no = count(service, params(flag, "false"));
                    long nulls = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM " + DatabaseTables.VARIABLES_A_SQL + " WHERE " + flag + " IS NULL").getSingleResult()).longValue();
                    check(yes + no + nulls == total, "Tri-state: " + flag);
                }
                for (String[] invalid : new String[][] {
                    {"page", "-1"}, {"size", "21"}, {"page", "abc"}, {"anioReferencia", "abc"}, {"mdea", "null"}
                }) {
                    try {
                        service.listar(params(invalid));
                        throw new AssertionError("Expected 400: " + invalid[0]);
                    } catch (ResponseStatusException ex) { check(ex.getStatus().value() == 400, "HTTP status"); }
                }
                Map<String, Object> options = service.filtros(params());
                check(options.containsKey("unidades") && options.containsKey("subtema2"), "Filter options");
                check(((List<?>) options.get("fuentes")).isEmpty(), "Sources disabled without process");
                List<String> units = (List<String>) options.get("unidades");
                if (!units.isEmpty()) {
                    long expected = ((Number) em.createNativeQuery("SELECT COUNT(*) FROM " + DatabaseTables.VARIABLES_A_SQL + " v"
                            + " WHERE EXISTS (SELECT 1 FROM " + DatabaseTables.PROCESOS_S_SQL + " p WHERE p.acronimo=v.acronimo AND p.unidad=:unidad)")
                            .setParameter("unidad", units.get(0)).getSingleResult()).longValue();
                    check(count(service, params("unidad", units.get(0))) == expected, "Unit filter");
                }
                if (!rows.isEmpty()) {
                    Map<String, Object> row = rows.get(0);
                    String acronimo = (String) row.get("acronimo");
                    String fuente = (String) row.get("idFuente");
                    Map<String, String> scoped = params("acronimo", acronimo, "idFuente", fuente);
                    Map<String, Object> scopedOptions = service.filtros(scoped);
                    check(!((List<?>) scopedOptions.get("fuentes")).isEmpty(), "Scoped sources");
                    for (Map<String, Object> item : (List<Map<String, Object>>) service.listar(scoped).get("content")) {
                        check(acronimo.equals(item.get("acronimo")) && fuente.equals(item.get("idFuente")), "AND filtering");
                    }
                    String name = (String) row.get("variableA");
                    if (name != null && !name.trim().isEmpty()) {
                        check(count(service, params("variableA", name.toUpperCase(Locale.ROOT)))
                                == count(service, params("variableA", name.toLowerCase(Locale.ROOT))), "Case insensitive");
                    }
                    for (String campo : Arrays.asList("tematica", "tema1", "subtema1", "tema2", "subtema2")) {
                        List<String> values = (List<String>) scopedOptions.get(campo);
                        if (!values.isEmpty()) {
                            scoped.put(campo, values.get(0));
                            service.listar(scoped);
                            scoped.remove(campo);
                        }
                    }
                    List<Integer> years = (List<Integer>) scopedOptions.get("anios");
                    if (!years.isEmpty()) {
                        scoped.put("anioReferencia", years.get(0).toString());
                        for (Map<String, Object> item : (List<Map<String, Object>>) service.listar(scoped).get("content")) {
                            check(years.get(0).equals(item.get("anioReferencia")), "Exact year");
                        }
                    }
                }
                System.out.println("PASS read-only checks; rows=" + total
                        + "; elapsed_ms=" + (System.nanoTime() - start) / 1000000);
                if (!rows.isEmpty()) {
                    variables_armo_service_impl detailService = (variables_armo_service_impl)
                            bean(variables_armo_service_impl.class, em, new HashMap<>());
                    String id = (String) rows.get(0).get("idA");
                    variables_detalle_armo_dto detail = detailService.obtenerDetallePorIdA(id);
                    check(detail.getVariable().getIdA().equals(id), "Detail ID");
                    check(detail.getClasificadores() != null && detail.getClasificaciones() != null
                            && detail.getMicrodatos() != null && detail.getDatosAbiertos() != null
                            && detail.getTabulados() != null && detail.getMdeas() != null
                            && detail.getOdsList() != null, "Existing and new detail relationships");
                    System.out.println("PASS detail relationships");
                }
                for (String sql : Arrays.asList(
                        "SELECT v.id_a, v.variable_a, v.acronimo, v.id_fuente, f.fuente, f.edicion, v.anio_referencia, v.validada"
                                + " FROM " + DatabaseTables.VARIABLES_A_SQL + " v LEFT JOIN " + DatabaseTables.FUENTES_A_SQL + " f ON f.id_fuente=v.id_fuente ORDER BY v.id_a LIMIT 20",
                        "SELECT COUNT(*) FROM " + DatabaseTables.VARIABLES_A_SQL + "",
                        "SELECT id_a FROM " + DatabaseTables.VARIABLES_A_SQL + " WHERE LOWER(variable_a) LIKE '%accidente%' ORDER BY id_a LIMIT 20")) {
                    List<?> plan = em.createNativeQuery("EXPLAIN (ANALYZE, BUFFERS) " + sql).getResultList();
                    System.out.println("PLAN: " + sql);
                    for (Object line : plan) System.out.println(line);
                }
                em.getTransaction().rollback();
            } finally {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                factory.destroy();
            }
        }
    }
}
