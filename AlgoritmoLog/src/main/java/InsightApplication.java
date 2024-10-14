import ETL.Leitor;
import ETL.S3Provider;
import Objetos.VooExterior;

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
//        S3Client s3 = S3Provider.getS3Client();
        String bucketName = System.getenv("NOME_BUCKET");

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

//        Baixando arquivos
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

            int numeroDeVoosParaExibir = Math.min(10, voosExtraidos.size());

            System.out.println("Exibindo os " + numeroDeVoosParaExibir + " primeiros voos:\n");

            for (int i = 0; i < numeroDeVoosParaExibir; i++) {
                VooExterior voo = voosExtraidos.get(i);
                System.out.println(voo.toString());
                System.out.println("----------------------------");
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }


    }
}