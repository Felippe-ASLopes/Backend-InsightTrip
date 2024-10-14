package Conexão;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataBaseProvider {

    private JdbcTemplate connection;

    public DataBaseProvider() {
//        Configure as variáveis de ambiente
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        connection = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConnection() {
        return connection;
    }
}
