//package Service;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import io.github.cdimascio.dotenv.Dotenv;
//
//public class DatabaseService {
//    private final JdbcTemplate jdbcTemplate;
//
//    public DatabaseService() {
//        Dotenv dotenv = Dotenv.load();
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(dotenv.get("DB_DRIVER"));
//        dataSource.setUrl(dotenv.get("DB_URL"));
//        dataSource.setUsername(dotenv.get("DB_USER"));
//        dataSource.setPassword(dotenv.get("DB_PASSWORD"));
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    public JdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }
//}