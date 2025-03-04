import Log.ConecaoSlack;
import Model.*;
import Provider.DataBaseProvider;
import Service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.xml.crypto.Data;

import static Log.Log.LOG_COLOR_RESET;
import static Log.Log.LOG_COLOR_GREEN;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InsightApplication {
    private static final Logger logger = LoggerFactory.getLogger(InsightApplication.class);

    public static void main(String[] args) {
        S3Service s3Service = new S3Service();
        DataBaseProvider cnp = new DataBaseProvider();
        JdbcTemplate connection = cnp.getConnection();

        DataBaseService dataBaseService = new DataBaseService();
        ExcelService excelService = new ExcelService();
        TransformationService transformationService = new TransformationService();
        InsertionService insertionService = new InsertionService(connection);

        // Listar e Baixar Arquivos do Bucket
        try {
            List<S3Object> objetos = s3Service.listarObjetos();
            logger.info("Objetos no bucket:");

            for (S3Object objeto : objetos) {
                logger.info("Verificando arquivo: {}", objeto.key());
                Path caminhoLocal = Path.of(objeto.key());
                if (!Files.exists(caminhoLocal)) {
                    logger.info("Arquivo faltante encontrado. Baixando: {}", objeto.key());
                    s3Service.baixarArquivo(objeto);
                } else {
                    logger.info("Arquivo já existe localmente: {}", objeto.key());
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao processar S3: {}", e.getMessage(), e);
        }


        // Processamento do Arquivo Excel
        String nomeArquivo = "resumo_anual_2023.xlsx";
        Path caminho = Path.of(nomeArquivo);

        List<Estado> estados;
        Integer qtdViagens = dataBaseService.getQtdViagens();

        logger.info("Quantidade de Viagens: {}", qtdViagens);

        if (qtdViagens == 0) {

            try {
                List<VooAnac> voos = excelService.ExtrairVoos(nomeArquivo, caminho);

                // Transformação dos Dados
                List<Pais> paises = transformationService.TransformarPaises(voos);
                estados = transformationService.TransformarEstados(voos);
                List<Aeroporto> aeroportos = transformationService.TransformarAeroportos(voos, paises, estados);

                // Inserção no Banco de Dados
                logger.info("Inserindo dados no banco");
                insertionService.insertPaises(paises);
                insertionService.insertEstados(estados);
                insertionService.insertAeroportos(aeroportos);
                insertionService.insertViagens(voos, aeroportos);

                nomeArquivo = "EventosSazonais.xlsx";
                caminho = Path.of(nomeArquivo);

                List<Evento> eventos = excelService.ExtrairEventos(nomeArquivo, caminho);

                logger.info("Inserindo Eventos");
                insertionService.insertEventos(eventos);

                logger.info("Inserindo EventoshasEstados");
                insertionService.insertEventosEstados(eventos, estados);

                nomeArquivo = "indicadoressegurancapublicauf.xlsx";
                caminho = Path.of(nomeArquivo);

                List<Crime> crimes = excelService.ExtrairCrimes(nomeArquivo, caminho);

                logger.info("Inserindo Crimes");
                insertionService.insertCrimes(crimes);

                logger.info("{}Base de dados {} inseridas no banco com sucesso! {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);

                ConecaoSlack.enviarMensagemSlack();
            } catch (Exception e) {
                logger.error("Erro durante o processamento: {}", e.getMessage(), e);
            }
        }
    }
}