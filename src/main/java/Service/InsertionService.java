package Service;

import Model.Aeroporto;
import Model.Estado;
import Model.Pais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import Utils.SqlUtils;
import static Log.Log.LOG_COLOR_RESET;
import static Log.Log.LOG_COLOR_GREEN;

import java.util.List;

public class InsertionService {
    private static final Logger logger = LoggerFactory.getLogger(InsertionService.class);
    private final JdbcTemplate jdbcTemplate;

    public InsertionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPaises(List<Pais> paises) {
        String sql = SqlUtils.ConstruirInsertPais(paises);
        if (sql.isEmpty()) {
            logger.warn("Nenhum país para inserir.");
            return;
        }
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} países com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }

    public void insertEstados(List<Estado> estados) {
        String sql = SqlUtils.ConstruirInsertEstado(estados);
        if (sql.isEmpty()) {
            logger.warn("Nenhum estado para inserir.");
            return;
        }
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} estados com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }

    public void insertAeroportos(List<Aeroporto> aeroportos) {
        String sql = SqlUtils.ConstruirInsertAeroporto(aeroportos);
        if (sql.isEmpty()) {
            logger.warn("Nenhum aeroporto para inserir.");
            return;
        }
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} aeroportos com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }
}