package com.visp.gate_command.util;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@UtilityClass
public class FileProcessor {

  public static List<String[]> processCSV(InputStream inputStream) throws Exception {
    try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
      return reader.readAll();
    }
  }

  public static List<String[]> processXLSX(InputStream inputStream) throws Exception {
    List<String[]> data = new ArrayList<>();
    try (Workbook workbook = new XSSFWorkbook(inputStream)) {
      Sheet sheet = workbook.getSheetAt(0);

      for (Row row : sheet) {
        int numberOfColumns = row.getLastCellNum();
        String[] rowData = new String[numberOfColumns];
        for (int i = 0; i < numberOfColumns; i++) {
          Cell cell = row.getCell(i);
          rowData[i] = getCellValue(cell);
        }
        data.add(rowData);
      }
    }
    return data;
  }

  private static String getCellValue(Cell cell) {
    if (cell == null) return "";
    return switch (cell.getCellType()) {
      case STRING -> cell.getStringCellValue();
      case NUMERIC -> String.valueOf(cell.getNumericCellValue());
      case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
      default -> "";
    };
  }
}
