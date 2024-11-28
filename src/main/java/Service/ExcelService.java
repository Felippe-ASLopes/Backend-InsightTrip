package Service;

import ETL.LeitorCrime;
import ETL.LeitorEventos;
import ETL.LeitorVoos;
import Model.Crime;
import Model.Evento;
import Model.VooAnac;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Log.Log.LOG_COLOR_GREEN;
import static Log.Log.LOG_COLOR_RESET;

public class ExcelService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    public List<VooAnac> ExtrairVoos(String nomeArquivo, Path caminho) {
        try (InputStream fileStream = Files.newInputStream(caminho)) {
            logger.info("{} Iniciando leitura do arquivo {} {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);
            return LeitorVoos.ExtrairViagem(nomeArquivo, fileStream);
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Evento> ExtrairEventos(String nomeArquivo, Path caminho) {
        try (InputStream fileStream = Files.newInputStream(caminho)) {
            logger.info("{} Iniciando leitura do arquivo {} {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);
            return LeitorEventos.ExtrairEvento(nomeArquivo, fileStream);
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Crime> ExtrairCrimes(String nomeArquivo, Path caminho) {
        try (InputStream fileStream = Files.newInputStream(caminho)) {
            logger.info("{} Iniciando leitura do arquivo {} {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);
            return LeitorCrime.ExtrairCrime(nomeArquivo, fileStream);
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}