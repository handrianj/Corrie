package org.handrianj.corrie.hermes.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.handrianj.corrie.hermes.datamodel.CellData;
import org.handrianj.corrie.hermes.datamodel.ExcelDocument;
import org.handrianj.corrie.hermes.datamodel.ExcelSheet;
import org.handrianj.corrie.hermes.datamodel.IFormula;

/**
 * Class used to write an excel file from an ExcelSheet, will use the .xlsx
 * format only
 *
 * @author Heri Andrianjafy
 *
 */
public class ExcelWriter {

	public static byte[] getByteFormat(ExcelDocument doc) {

		Workbook workBook = new XSSFWorkbook();

		for (ExcelSheet currentSheet : doc.getAllPages()) {

			Sheet newSheet = workBook.createSheet(currentSheet.getPageTitle());

			Collection<String> titles = currentSheet.getTitles();
			Row headerRow = newSheet.createRow(0);

			int currentTitleIndex = 0;
			for (String currentTitle : titles) {
				Cell titleCell = headerRow.createCell(currentTitleIndex++);
				titleCell.setCellValue(currentTitle);
			}

			int currentRowIndex = 1;

			List<LinkedHashMap<String, CellData>> rows = currentSheet.getRows();
			for (LinkedHashMap<String, CellData> currentRow : rows) {

				Row dataRow = newSheet.createRow(currentRowIndex++);

				int currentCellIndex = 0;
				for (String title : titles) {

					Cell dataCell = dataRow.createCell(currentCellIndex++);
					CellData cellData = currentRow.get(title);
					String value = cellData.getValue();
					Class<?> type = currentSheet.getType(title);

					// Type the value according to the sheet
					if (type.isAssignableFrom(Double.class)) {
						dataCell.setCellValue(Double.parseDouble(value));
					} else if (type.isAssignableFrom(Boolean.class)) {
						dataCell.setCellValue(Boolean.parseBoolean(value));
					} else if (type.isAssignableFrom(IFormula.class)) {
						dataCell.setCellFormula(value);
					} else if (type.isAssignableFrom(Date.class)) {
						dataCell.setCellValue(Date.valueOf(value));
					} else {
						dataCell.setCellValue(value);
					}

					CellStyle style = workBook.createCellStyle();

					style.setFillBackgroundColor(cellData.getBackgroundIndex());
					style.setFillForegroundColor(cellData.getForegroundIndex());
					style.setFillPattern(cellData.getPattern());
					dataCell.setCellStyle(style);
				}
			}

		}

		try {

			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			workBook.write(byteOutput);
			byteOutput.close();
			workBook.close();
			return byteOutput.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return new byte[0];
	}
}