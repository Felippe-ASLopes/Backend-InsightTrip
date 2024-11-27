package Service;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import Provider.S3Provider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;
    private final String bucketName;

    public S3Service() {
        S3Provider s3Provider = new S3Provider();
        this.s3Client = s3Provider.getS3Client();
        Dotenv dotenv = Dotenv.load(); // Garantir que Dotenv está carregado aqui
        this.bucketName = dotenv.get("NOME_BUCKET");
    }

    public List<S3Object> listarObjetos() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsV2Request);
        return response.contents();
    }

    public void baixarArquivos(List<S3Object> objects) {
        for (S3Object object : objects) {
            baixarArquivo(object);
        }
    }

    public void baixarArquivo(S3Object objeto) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objeto.key())
                .build();
        try (InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream())) {
            Path path = Path.of(objeto.key());
            // Cria diretórios pai se não existirem
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            // Sobrescrever se existir para evitar erros
            Files.copy(inputStream, path);
            logger.info("Arquivo baixado: {}", objeto.key());
        } catch (IOException | S3Exception e) {
            logger.error("Erro ao baixar arquivo {}: {}", objeto.key(), e.getMessage(), e);
        }
    }
}