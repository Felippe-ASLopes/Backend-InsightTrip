import ETL.Leitor;
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

public class InsightApplication {
    public static void main(String[] args) {
//        Conexão S3
        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = System.getenv("NOME_BUCKET");

//        Conexão BD
        DataBaseProvider cnp = new DataBaseProvider();
        JdbcTemplate connection = cnp.getConnection();

//        Listando objetos no bucket
        try {
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjects).contents();
            System.out.println("Objetos no bucket " + bucketName + ":");
            for (S3Object object : objects) {
                System.out.println("- " + object.key());
            }
        } catch (S3Exception e) {
            System.err.println("Erro ao listar objetos no bucket: " + e.getMessage());
        }

//        Baixando arquivos Bucket
        try {
            List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();
            for (S3Object object : objects) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(object.key())
                        .build();

                InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                Files.copy(inputStream, new File(object.key()).toPath());
                System.out.println("Arquivo baixado: " + object.key());
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }

//    Extraindo dados do .xlsx
        String nomeArquivo = "chegadas_2023.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try (InputStream arquivo = Files.newInputStream(caminho)) {

            List<VooExterior> voosExtraidos = Leitor.ExtrairVoo(nomeArquivo, arquivo);

//            Enviando para o banco
            for (VooExterior vooExtraido : voosExtraidos) {

                String dataViagem = String.format("%04d-%02d-%s", vooExtraido.getAno(), vooExtraido.getMes(), "01");
                String sqlInsertDatas = "INSERT INTO Passagem (NomePassagem, Natureza, Origem, Destino, dtViagem, fkAgencia) \n" +
                        "VALUES (?, ?, ?, ?, ?, ?);\n";

                try {
                    System.out.println("Inserindo viagem: " + vooExtraido.toString());
                    connection.update(sqlInsertDatas, vooExtraido.createName(), "Internacional", vooExtraido.converterToStringPaisOrigem(),
                            vooExtraido.converterToStringEstado(), dataViagem, null);
                } catch (Exception e) {
                    System.out.println("Erro ao executar query: " + sqlInsertDatas);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Viagens inseridas no banco!");

//        Exibindo primeiras 10 passagens do banco
        String sqlStart = "SELECT * FROM Passagem LIMIT 10;";
        SqlRowSet rowSet = connection.queryForRowSet(sqlStart);

        while (rowSet.next()) {
            System.out.println("Passagem: " + rowSet.getString(1));
            System.out.println("Natureza: " + rowSet.getString(2));
            System.out.println("Origem: " + rowSet.getString(3));
            System.out.println("Destino: " + rowSet.getString(4));
            System.out.println("Data Viagem: " + rowSet.getString(5));
            System.out.println("FK Agencia: " + rowSet.getString(6));
            System.out.println();
        }
    }
}