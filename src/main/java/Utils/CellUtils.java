package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class CellUtils {
    //    Classe auxiliar para tratamento de celulas vazias
    public static String getStringCellValueOrNull(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue().trim();
            return value.isEmpty() ? null : value;
        }

        return cell.toString().trim().isEmpty() ? null : cell.toString().trim();
    }
}
