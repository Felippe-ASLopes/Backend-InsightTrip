import ETL.*;
import Conexão.*;
import Objetos.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static Utils.SqlUtils.ConstruirSqlInsertEstado;
import static Utils.SqlUtils.ConstruirSqlInsertPais;
import static Log.Log.LOG_COLOR_RESET;
import static Log.Log.LOG_COLOR_GREEN;
import static Log.Log.LOG_COLOR_YELLOW;
import static Log.Log.LOG_COLOR_RED;

public class InsightApplication {

    private static final Logger logger = LoggerFactory.getLogger(InsightApplication.class);

    public static void main(String[] args) {
        // Conexão S3
        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = System.getenv("NOME_BUCKET");
        Boolean acessarBucket = false;

        // Conexão BD
        DataBaseProvider cnp = new DataBaseProvider();
        JdbcTemplate connection = cnp.getConnection();

        if (acessarBucket) {
            // Listando objetos no bucket
            try {
                logger.info("{} Iniciando conexão com o bucket {} {}", LOG_COLOR_GREEN, bucketName, LOG_COLOR_RESET);

                ListObjectsRequest listObjects = ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .build();

                List<S3Object> objects = s3Client.listObjects(listObjects).contents();
                logger.info("Objetos no bucket {}:", bucketName);
                for (S3Object object : objects) {
                    logger.info("- {}", object.key());
                }
            } catch (S3Exception e) {
                logger.error("Erro ao listar objetos no bucket: {}", e.getMessage(), e);
            }

            // Baixando arquivos do Bucket
            try {
                List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();
                for (S3Object object : objects) {
                    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(object.key())
                            .build();

                    InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                    Files.copy(inputStream, new File(object.key()).toPath());
                    logger.info("{} Arquivo baixado: {} {}",LOG_COLOR_GREEN, object.key(), LOG_COLOR_RESET);
                }
            } catch (IOException | S3Exception e) {
                logger.error("Erro ao fazer download dos arquivos: {}", e.getMessage(), e);
            }
        }

        // Extraindo dados do .xlsx
        String nomeArquivo = "resumo_anual_2024.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try (InputStream arquivo = Files.newInputStream(caminho)) {

            List<VooAnac> viagensExtraidas = LeitorVoos.ExtrairViagem(nomeArquivo, arquivo);

            Map<String, String> paisesEContinentesUnicos = viagensExtraidas.stream()
                    .filter(voo -> voo.getPaisOrigem() != null && !voo.getPaisOrigem().isEmpty())
                    .collect(Collectors.toMap(
                            VooAnac::getPaisOrigem,
                            VooAnac::getContinentePaisOrigem,
                            (continenteExistente, novoContinente) -> continenteExistente
                    ));

            String sqlInsertPais = ConstruirSqlInsertPais(paisesEContinentesUnicos);

            Map<String, Estado> EstadosRegioes = viagensExtraidas.stream()
                    .filter(voo -> voo.getUfAeroportoOrigem() != null && !voo.getUfAeroportoOrigem().isEmpty())
                    .collect(Collectors.toMap(
                            VooAnac::getUfAeroportoOrigem,
                            voo -> new Estado(
                                    voo.getUfAeroportoOrigem(),
                                    voo.getRegiaoAeroportoOrigem()
                            ),
                            (estadoExistente, estadoNovo) -> estadoExistente
                    ));

            String sqlInsertEstados = ConstruirSqlInsertEstado(EstadosRegioes);

            // Enviando ao Banco
            try {
                logger.info("Inserindo países: {}", sqlInsertPais);
                int rowsPais = connection.update(sqlInsertPais);
                logger.info("{} Inseridos {} países com sucesso. {}", LOG_COLOR_GREEN, rowsPais, LOG_COLOR_RESET);

                logger.info("Inserindo estados: {}", sqlInsertEstados);
                int rowsEstados = connection.update(sqlInsertEstados);
                logger.info("{} Inseridos {} estados com sucesso. {}", LOG_COLOR_GREEN, rowsEstados, LOG_COLOR_RESET);

                logger.info("{} Base de dados {} inseridas no banco com sucesso! {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);

            } catch (Exception e) {
                logger.error("Erro ao executar queries de inserção.", e);
            }
        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Ocorreu um erro inesperado: {}", e.getMessage(), e);
        }
    }
}