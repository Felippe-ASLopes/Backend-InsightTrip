package Service;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import Provider.S3Provider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;
    private final String bucketName;

    public S3Service() {
        Dotenv dotenv = Dotenv.load();
        S3Provider s3Provider = new S3Provider();
        this.s3Client = s3Provider.getS3Client();
        this.bucketName = dotenv.get("NOME_BUCKET");
    }

    public List<S3Object> ListarObjetos() {
        ListObjectsRequest listaObjetos = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        return s3Client.listObjects(listaObjetos).contents();
    }

    public void BaixarArquivos(List<S3Object> objects) {
        for (S3Object object : objects) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(object.key())
                    .build();
            try (InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream())) {
                Files.copy(inputStream, new File(object.key()).toPath());
                logger.info("Arquivo baixado: {}", object.key());
            } catch (IOException | S3Exception e) {
                logger.error("Erro ao baixar arquivo {}: {}", object.key(), e.getMessage(), e);
            }
        }
    }
}