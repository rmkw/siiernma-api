package mx.org.inegi.sistemacaptura;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import mx.org.inegi.sistemacaptura.config.PersistenceConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;

/** Explicit read-only check. Accelerates idle intervals only in this test pool. */
public class PoolIdleCheck {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(args[0])) { properties.load(in); }
        StandardEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addFirst(new PropertiesPropertySource("test", properties));
        PersistenceConfig config = new PersistenceConfig();
        config.setEnvironment(environment);
        try (BasicDataSource pool = (BasicDataSource) config.dataSource()) {
            check(pool.getMinIdle() == 0, "Pool must be allowed to empty");
            check(pool.getMinEvictableIdleTimeMillis() == 300000L, "Five-minute idle threshold");
            check(pool.getTimeBetweenEvictionRunsMillis() == 60000L, "One-minute cleanup interval");
            check(pool.getNumTestsPerEvictionRun() == -1, "Inspect all idle connections");
            pool.setDefaultReadOnly(true);
            pool.addConnectionProperty("ApplicationName", "SIIERNMA-pool-idle-check");
            pool.setMinEvictableIdleTimeMillis(200L);
            pool.setTimeBetweenEvictionRunsMillis(100L);
            try (Connection borrowed = pool.getConnection()) {
                try (Connection idle = pool.getConnection()) { selectOne(idle); }
                long deadline = System.nanoTime() + 5000000000L;
                while (pool.getNumIdle() != 0 && System.nanoTime() < deadline) Thread.sleep(50L);
                check(pool.getNumIdle() == 0, "Periodic cleanup must close returned connection");
                check(pool.getNumActive() == 1, "Borrowed connection must survive cleanup");
                selectOne(borrowed);
                try (Connection fresh = pool.getConnection()) { selectOne(fresh); }
            }
            long deadline = System.nanoTime() + 5000000000L;
            while (pool.getNumIdle() != 0 && System.nanoTime() < deadline) Thread.sleep(50L);
            check(pool.getNumIdle() == 0 && pool.getNumActive() == 0, "Inactive pool must empty");
        }
        System.out.println("PASS periodic eviction, borrowed connection preserved, reconnection and zero idle; no database writes");
    }

    private static void selectOne(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery("SELECT 1")) {
            check(result.next() && result.getInt(1) == 1, "Connection must remain usable");
        }
    }

    private static void check(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }
}
