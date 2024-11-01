import Provider.DataBaseProvider;
import Service.*;
import Model.Aeroporto;
import Model.Estado;
import Model.Pais;
import Model.VooAnac;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.s3.model.S3Object;
import static Log.Log.LOG_COLOR_RESET;
import static Log.Log.LOG_COLOR_GREEN;

import java.nio.file.Path;
import java.util.List;

public class InsightApplication {
    private static final Logger logger = LoggerFactory.getLogger(InsightApplication.class);

    public static void main(String[] args) {
        S3Service s3Service = new S3Service();
        DataBaseProvider cnp = new DataBaseProvider();
        JdbcTemplate connection = cnp.getConnection();

        ExcelService excelService = new ExcelService();
        TransformationService transformationService = new TransformationService();
        InsertionService insertionService = new InsertionService(connection);

        boolean acessarBucket = false;

        if (acessarBucket) {
            // Listar e Baixar Arquivos do Bucket
            try {
                List<S3Object> objetos = s3Service.ListarObjetos();
                logger.info("Objetos no bucket:");
                objetos.forEach(obj -> logger.info("- {}", obj.key()));
                s3Service.BaixarArquivos(objetos);
            } catch (Exception e) {
                logger.error("Erro ao processar S3: {}", e.getMessage(), e);
            }
        }

        // Processamento do Arquivo Excel
        String nomeArquivo = "resumo_anual_2024.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try {
            List<VooAnac> voos = excelService.ExtrairVoos(nomeArquivo, caminho);

            // Transformação dos Dados
            List<Pais> paises = transformationService.TransformarPaises(voos);
            List<Estado> estados = transformationService.TransformarEstados(voos);
            List<Aeroporto> aeroportos = transformationService.TransformarAeroportos(voos, paises, estados);

            // Inserção no Banco de Dados
            logger.info("Inserindo dados no banco");
            insertionService.insertPaises(paises);
            insertionService.insertEstados(estados);
            insertionService.insertAeroportos(aeroportos);
            insertionService.insertViagens(voos, aeroportos);

            logger.info("{}Base de dados {} inseridas no banco com sucesso! {}",LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);
        } catch (Exception e) {
            logger.error("Erro durante o processamento: {}", e.getMessage(), e);
        }
    }
}