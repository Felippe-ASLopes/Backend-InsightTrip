package Service;

import Provider.DataBaseProvider;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataBaseService {
    private DataBaseProvider dataBaseProvider;
    private JdbcTemplate jdbcTemplate;

    public DataBaseService() {
        this.dataBaseProvider = new DataBaseProvider();
        this.jdbcTemplate = dataBaseProvider.getConnection();
    }

    public Integer getQtdViagens() {
        String sql = "SELECT count(idPassagem) FROM Viagem;";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            System.out.println("Erro ao executar a consulta para UFs: " + e);
            return 0;
        }
    }
}