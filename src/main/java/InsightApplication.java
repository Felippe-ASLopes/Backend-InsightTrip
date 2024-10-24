import ETL.*;
import Conexão.*;
import Objetos.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static Utils.SqlUtils.ConstruirSqlInsertPais;

public class InsightApplication {

    private static final Logger logger = Logger.getLogger(InsightApplication.class.getName());

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
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();
            logger.info("Objetos no bucket " + bucketName + ":");
            for (S3Object object : objects) {
                logger.info("- " + object.key());
            }
        } catch (S3Exception e) {
            logger.log(Level.SEVERE, "Erro ao listar objetos no bucket: " + e.getMessage(), e);
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
                    logger.info("Arquivo baixado: " + object.key());
                }
            } catch (IOException | S3Exception e) {
                logger.log(Level.SEVERE, "Erro ao fazer download dos arquivos: " + e.getMessage(), e);
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

//            Map<String, String> EstadosRegioes = viagensExtraidas.stream()
//                    .filter(voo -> voo.getUfAeroportoOrigem() != null && !voo.getUfAeroportoOrigem().isEmpty())
//                    .collect(Collectors.toMap(
//                            VooAnac::getUfAeroportoOrigem,
//                            VooAnac::getRegiaoAeroportoOrigem,
//                            ::
//                            (continenteExistente, novoContinente) -> continenteExistente
//                    ));
//
//            String sqlInsertEstados =

            try {
                logger.info("Inserindo países: " + sqlInsertPais);
                connection.update(sqlInsertPais);
                logger.info("Viagens inseridas no banco!");

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao executar query: " + sqlInsertPais, e);
            }

//            for (VooAnac viagemExtraida : viagensExtraidas) {
//                String dataViagem = String.format("%04d-%02d-%s", VooAnac.getAno(), VooAnac.getMes(), "01");
//                String sqlInsertDatas = "INSERT INTO Passagem (NomePassagem, Natureza, Origem, Destino, dtViagem, fkAgencia) VALUES (?, ?, ?, ?, ?, ?);";
//
//                try {
//                    logger.info("Inserindo viagem: " + viagemExtraida.toString());
//                    connection.update(sqlInsertDatas, viagemExtraida.createName(), "Internacional", viagemExtraida.converterToStringPaisOrigem(),
//                            viagemExtraida.converterToStringEstado(), dataViagem, null);
//                } catch (Exception e) {
//                    logger.log(Level.SEVERE, "Erro ao executar query: " + sqlInsertDatas, e);
//                }
//            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao ler o arquivo: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocorreu um erro inesperado: " + e.getMessage(), e);
        }
    }
}
