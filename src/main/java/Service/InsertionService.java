package Service;

import Model.*;
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
        logger.info(sql);
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} países com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }

    public void insertEstados(List<Estado> estados) {
        String sql = SqlUtils.ConstruirInsertEstado(estados);
        if (sql.isEmpty()) {
            logger.warn("Nenhum estado para inserir.");
            return;
        }
        logger.info(sql);
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} estados com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }

    public void insertAeroportos(List<Aeroporto> aeroportos) {
        String sql = SqlUtils.ConstruirInsertAeroporto(aeroportos);
        if (sql.isEmpty()) {
            logger.warn("Nenhum aeroporto para inserir.");
            return;
        }
//        logger.info(sql);
        int rows = jdbcTemplate.update(sql);
        logger.info("{}Inseridos {} aeroportos com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
    }

    public void insertViagens(List<VooAnac> voos, List<Aeroporto> aeroportos) {
        String sql = SqlUtils.ConstruirInsertViagem(voos, aeroportos);

        if (sql.isEmpty()) {
            logger.warn("Nenhuma viagem para inserir.");
            return;
        }
//        logger.info(sql);

        try {
            int rows = jdbcTemplate.update(sql);
            logger.info("{}Inseridas {} viagens com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
        } catch (Exception e) {
            logger.error("Erro ao inserir viagens: {}", e.getMessage(), e);
        }
    }

    public void insertEventos(List<Evento> eventos) {
        String sql = SqlUtils.ConstruirInsertEventos(eventos);

        if (sql.isEmpty()) {
            logger.warn("Nenhum evento para inserir.");
            return;
        }
        logger.info(sql);

        try {
            int rows = jdbcTemplate.update(sql);
            logger.info("{}Inseridos {} eventos com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
        } catch (Exception e) {
            logger.error("Erro ao inserir eventos: {}", e.getMessage(), e);
        }
    }

    public void insertEventosEstados(List<Evento> eventos, List<Estado> estados) {
        String sql = SqlUtils.ConstruirInsertEventosEstados(eventos, estados);

        if (sql.isEmpty()) {
            logger.warn("Nenhum evento estado para inserir.");
            return;
        }
        logger.info(sql);

        try {
            int rows = jdbcTemplate.update(sql);
            logger.info("{}Inseridos {} eventos estados com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
        } catch (Exception e) {
            logger.error("Erro ao inserir eventos estados: {}", e.getMessage(), e);
        }
    }

    public void insertCrimes(List<Crime> crimes) {
        String sql = SqlUtils.ConstruirInsertCrimes(crimes);

        if (sql.isEmpty()) {
            logger.warn("Nenhum crime para inserir.");
            return;
        }
//        logger.info(sql);

        try {
            int rows = jdbcTemplate.update(sql);
            logger.info("{}Inseridos {} crimes com sucesso.{}",LOG_COLOR_GREEN, rows, LOG_COLOR_RESET);
        } catch (Exception e) {
            logger.error("Erro ao inserir crimes: {}", e.getMessage(), e);
        }
    }
}