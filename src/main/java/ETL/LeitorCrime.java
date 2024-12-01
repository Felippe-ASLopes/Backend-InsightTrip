package ETL;

import Model.Crime;
import Model.Estado;
import Model.Evento;
import Model.VooAnac;
import Service.TransformationService;
import Utils.CellUtils;
import Utils.FormaterDay;
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

public class LeitorCrime {

    private static final Logger logger = LoggerFactory.getLogger(LeitorVoos.class);

    //    Definição das colunas
    private static final int COL_ESTADO = 0;    // A
    private static final int COL_NOME = 1;               // B
    private static final int COL_ANO = 2;              // C
    private static final int COL_MES = 3;              // D
    private static final int COL_QTDOCORRENCIAS = 4;              // E

    public static List<Crime> ExtrairCrime(String nomeArquivo, InputStream arquivo) {
        try {
            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<Crime> crimesExtraidos = new ArrayList<>();

            for (Row row : sheet) {

                if (row.getRowNum() == 0) {

                    int[] colIndices = {COL_ESTADO, COL_NOME, COL_ANO, COL_MES, COL_QTDOCORRENCIAS};

                    for (int i = 0; i < colIndices.length; i++) {
                        Cell cell = row.getCell(colIndices[i]);
                        String coluna = (cell != null) ? cell.getStringCellValue() : "N/A";
                        logger.info("Coluna {}: {}", i, coluna);
                    }
                    continue;
                }

                logger.info("Lendo linha {}", row.getRowNum());

                try {
                    Integer estado = Estado.ConverterCodigoIBGE(row.getCell(COL_ESTADO).getStringCellValue());
                    String nome = row.getCell(COL_NOME).getStringCellValue();
                    Integer ano = 2023;
                    Integer mes = TransformationService.converterMes(row.getCell(COL_MES).getStringCellValue());
                    Integer qtdOcorrencia = getIntegerCellValue(row.getCell(COL_QTDOCORRENCIAS));

                    LocalDate dataCrime = FormaterDay.formatarDia(ano, mes, true);

                    Crime crime = new Crime(nome, dataCrime, estado, qtdOcorrencia);

                    crimesExtraidos.add(crime);
                } catch (NullPointerException | IllegalStateException e) {
                    logger.error("Erro ao processar a linha {}: {}", row.getRowNum(), e.getMessage());
                }
            }

            workbook.close();

            logger.info("{} Leitura do arquivo finalizada {}", LOG_COLOR_GREEN, LOG_COLOR_RESET);

            return crimesExtraidos;
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
