package ETL;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import Objetos.Estado;
import Objetos.VooAnac;
import Utils.CellUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Log.Log.LOG_COLOR_RESET;
import static Log.Log.LOG_COLOR_GREEN;
import static Log.Log.LOG_COLOR_YELLOW;
import static Log.Log.LOG_COLOR_RED;
import static Utils.FormaterUtils.formatarNomes;

public class LeitorVoos {

    private static final Logger logger = LoggerFactory.getLogger(LeitorVoos.class);

    //    Definição das colunas
    private static final int COL_ANO = 3;               // D
    private static final int COL_MES = 4;              // E
    private static final int COL_NOME_AEROPORTO_ORIGEM = 6;    // G
    private static final int COL_UF_AEROPORTO_ORIGEM = 7;    // H
    private static final int COL_REGIAO_AEROPORTO_ORIGEM = 8;    // I
    private static final int COL_NOME_PAIS_ORIGEM = 9;    // J
    private static final int COL_CONTINENTE_PAIS_ORIGEM = 10;    // K
    private static final int COL_NOME_AEROPORTO_DESTINO = 12;    // M
    private static final int COL_UF_AEROPORTO_DESTINO = 13;    // N
    private static final int COL_REGIAO_AEROPORTO_DESTINO = 14;    // O
    private static final int COL_NOME_PAIS_DESTINO = 15;    // P
    private static final int COL_QTD_PASSENGEIROS_PAGOS = 19;    // T
    private static final int COL_QTD_PASSENGEIROS_GRATIS = 20;    // U

    public static List<VooAnac> ExtrairViagem(String nomeArquivo, InputStream arquivo) {
        try {
            logger.info("{} Iniciando leitura do arquivo {} {}", LOG_COLOR_GREEN, nomeArquivo, LOG_COLOR_RESET);

            Workbook workbook;
            if (nomeArquivo.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(arquivo);
            } else {
                workbook = new HSSFWorkbook(arquivo);
            }

            Sheet sheet = workbook.getSheetAt(0);

            List<VooAnac> viagensExtraidas = new ArrayList<>();

            for (Row row : sheet) {

                if (row.getRowNum() == 0) {

                    int[] colIndices = {COL_ANO, COL_MES,
                            COL_NOME_AEROPORTO_ORIGEM, COL_UF_AEROPORTO_ORIGEM, COL_REGIAO_AEROPORTO_ORIGEM, COL_NOME_PAIS_ORIGEM, COL_CONTINENTE_PAIS_ORIGEM,
                            COL_NOME_AEROPORTO_DESTINO, COL_UF_AEROPORTO_DESTINO, COL_REGIAO_AEROPORTO_DESTINO, COL_NOME_PAIS_DESTINO,
                            COL_QTD_PASSENGEIROS_PAGOS, COL_QTD_PASSENGEIROS_GRATIS};

                    for (int i = 0; i < colIndices.length; i++) {
                        Cell cell = row.getCell(colIndices[i]);
                        String coluna = (cell != null) ? cell.getStringCellValue() : "N/A";
                        logger.info("Coluna {}: {}", i, coluna);
                    }
                    continue;
                }

                logger.info("Lendo linha {}", row.getRowNum());

                try {
//                    Dados obrigatórios
                    Integer ano = getIntegerCellValue(row.getCell(COL_ANO));
                    Integer mes = getIntegerCellValue(row.getCell(COL_MES));
                    String nomeAeroportoOrigem = row.getCell(COL_NOME_AEROPORTO_ORIGEM).getStringCellValue();
                    String nomePaisOrigem = row.getCell(COL_NOME_PAIS_ORIGEM).getStringCellValue();
                    String nomeContinenteOrigem = row.getCell(COL_CONTINENTE_PAIS_ORIGEM).getStringCellValue();
                    String nomeAeroportoDestino = row.getCell(COL_NOME_AEROPORTO_DESTINO).getStringCellValue();
                    String nomePaisDestino = row.getCell(COL_NOME_PAIS_DESTINO).getStringCellValue();
                    Integer passageirosPagos = getIntegerCellValue(row.getCell(COL_QTD_PASSENGEIROS_PAGOS));
                    Integer passageirosGratuitos = getIntegerCellValue(row.getCell(COL_QTD_PASSENGEIROS_GRATIS));

                    // Dados opcionais (pode haver celulas vazias)
                    String ufAeroportoOrigem = CellUtils.getStringCellValueOrNull(row, COL_UF_AEROPORTO_ORIGEM);
                    String regiaoAeroportoOrigem = CellUtils.getStringCellValueOrNull(row, COL_REGIAO_AEROPORTO_ORIGEM);
                    String ufAeroportoDestino = CellUtils.getStringCellValueOrNull(row, COL_UF_AEROPORTO_DESTINO);
                    String regiaoAeroportoDestino = CellUtils.getStringCellValueOrNull(row, COL_REGIAO_AEROPORTO_DESTINO);


                    if (nomePaisDestino.equals("BRASIL")) {

                        if (nomePaisOrigem.equals("ESTADOS UNIDOS DA AMÉRICA")) {
                            nomePaisOrigem = "Estados Unidos";
                        }

                        if (nomePaisOrigem.equals("REINO UNIDO")) {
                            nomePaisOrigem = "Inglaterra";
                        }

                        if (nomePaisOrigem.equals("EMIRADOS ÁRABES UNIDOS")) {
                            nomePaisOrigem = "Emirados Árabes";
                        }

                        if (nomePaisOrigem.equals("REPÚBLICA DEMOCRÁTICA DO CONGO")) {
                            nomePaisOrigem = "República Do Congo";
                        }

                        if (nomePaisOrigem.equals("HONG KONG")) {
                            nomePaisOrigem = "China";
                        }

                        if (ufAeroportoOrigem != null) {
                            ufAeroportoOrigem = Estado.ConverterNomeEstado(ufAeroportoOrigem);
                        }

                        if (regiaoAeroportoOrigem != null) {
                            regiaoAeroportoOrigem = formatarNomes(regiaoAeroportoOrigem);
                        }

                        nomeAeroportoOrigem = formatarNomes(nomeAeroportoOrigem);
                        nomeAeroportoDestino = formatarNomes(nomeAeroportoDestino);
                        nomePaisOrigem = formatarNomes(nomePaisOrigem);
                        nomeContinenteOrigem = formatarNomes(nomeContinenteOrigem);

                        VooAnac viagem = new VooAnac(ano, mes,
                                nomeAeroportoOrigem, ufAeroportoOrigem, regiaoAeroportoOrigem, nomePaisOrigem, nomeContinenteOrigem,
                                nomeAeroportoDestino, ufAeroportoDestino, regiaoAeroportoDestino, nomePaisDestino,
                                passageirosPagos, passageirosGratuitos);

                        viagensExtraidas.add(viagem);
                    }
                } catch (NullPointerException | IllegalStateException e) {
                    logger.error("Erro ao processar a linha {}: {}", row.getRowNum(), e.getMessage());
                }
            }

            workbook.close();

            logger.info("{} Leitura do arquivo finalizada {}", LOG_COLOR_GREEN, LOG_COLOR_RESET);

            return viagensExtraidas;
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
