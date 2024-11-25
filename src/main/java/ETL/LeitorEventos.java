package ETL;

import Model.Estado;
import Model.Evento;
import Model.VooAnac;
import Utils.CellUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static Log.Log.LOG_COLOR_GREEN;
import static Log.Log.LOG_COLOR_RESET;
import static Utils.FormaterUtils.formatarNomes;

public class LeitorEventos {

    private static final Logger logger = LoggerFactory.getLogger(LeitorVoos.class);

    //    Definição das colunas
    private static final int COL_NOME = 0;               // A
    private static final int COL_DT_INICIO = 1;              // B
    private static final int COL_DT_FIM = 2;    // C
    private static final int COL_ESTADO = 3;    // D

    public static List<Evento> ExtrairEvento(String nomeArquivo, InputStream arquivo) {
        try {
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<Evento> eventosExtraidas = new ArrayList<>();

            for (Row row : sheet) {

                if (row.getRowNum() == 0) {

                    int[] colIndices = {COL_NOME, COL_DT_INICIO, COL_DT_FIM, COL_ESTADO};

                    for (int i = 0; i < colIndices.length; i++) {
                        Cell cell = row.getCell(colIndices[i]);
                        String coluna = (cell != null) ? cell.getStringCellValue() : "N/A";
                        logger.info("Coluna {}: {}", i, coluna);
                    }
                    continue;
                }

                logger.info("Lendo linha {}", row.getRowNum());

                try {
                    String nome = row.getCell(COL_NOME).getStringCellValue();
                    String dtInicio = row.getCell(COL_DT_INICIO).getStringCellValue();
                    String dtFim = row.getCell(COL_DT_FIM).getStringCellValue();
                    Integer estado = Estado.ConverterCodigoIBGE(row.getCell(COL_ESTADO).getStringCellValue());


                    Evento evento = new Evento(nome, LocalDate.parse(dtInicio), LocalDate.parse(dtFim), estado);

                    eventosExtraidas.add(evento);
                } catch (NullPointerException | IllegalStateException e) {
                    logger.error("Erro ao processar a linha {}: {}", row.getRowNum(), e.getMessage());
                }
            }

            workbook.close();

            logger.info("{} Leitura do arquivo finalizada {}", LOG_COLOR_GREEN, LOG_COLOR_RESET);

            return eventosExtraidas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }

    private LocalDate converterDate(java.util.Date data) {
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
