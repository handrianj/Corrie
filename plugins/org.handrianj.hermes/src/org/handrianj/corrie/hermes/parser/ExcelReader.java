package org.handrianj.corrie.hermes.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.handrianj.corrie.hermes.datamodel.CellData;
import org.handrianj.corrie.hermes.datamodel.ExcelDocument;
import org.handrianj.corrie.hermes.datamodel.ExcelSheet;
import org.handrianj.corrie.hermes.datamodel.IFormula;

/**
 * Class used to read the excell file
 *
 * @author Heri Andrianjafy
 *
 */
public class ExcelReader {

	private final static String XSLX = "XLSX";
	private final static String XSL = "XLS";
	private final static String CSV = "CSV";

	public static ExcelDocument parseFile(String fileName) {
		FileInputStream stream;
		ExcelDocument doc = new ExcelDocument();

		try {

			File file = new File(fileName);
			stream = new FileInputStream(file);

			String extension = "";

			int i = fileName.lastIndexOf('.');
			int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

			if (i > p) {
				extension = fileName.substring(i + 1);
			}

			if (extension.toUpperCase().compareTo(XSLX) == 0) {
				parseExcelFile(doc, new XSSFWorkbook(stream));
			} else if (extension.toUpperCase().compareTo(XSL) == 0) {
				parseExcelFile(doc, new HSSFWorkbook(stream));
			} else if (extension.toUpperCase().compareTo(CSV) == 0) {
				parseCSVFile(doc, fileName);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	private static void parseCSVFile(ExcelDocument doc, String fileName) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		boolean init = true;

		String titles[] = new String[0];
		ExcelSheet newSheet = new ExcelSheet();
		try {

			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				LinkedHashMap<String, CellData> row = new LinkedHashMap<>();

				// use comma as separator
				String[] datas = line.split(cvsSplitBy);

				if (init) {
					titles = line.split(cvsSplitBy);
					init = false;
					newSheet.setTitles(Arrays.asList(titles));
				} else {

					// If no init read all the values and associate it with
					// title
					for (int i = 0; i < titles.length; i++) {
						row.put(titles[i], new CellData(datas[i]));

					}
					// Add the row to the sheet
					newSheet.addRow(row);
				}

			}

			doc.addPage(ExcelSheet.DEFAULT_NAME, newSheet);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void parseExcelFile(ExcelDocument doc, Workbook workbook) {

		int numberOfSheets = workbook.getNumberOfSheets();

		for (int i = 0; i < numberOfSheets; i++) {

			ExcelSheet data = new ExcelSheet();

			// Get first sheet from the workbook
			Sheet sheet = workbook.getSheetAt(i);

			// Get iterator to all the rows in current sheet
			Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();

			// Used to initialize the columns

			int colIndex = 0;

			int nbElements = 0;

			List<String> titleRow = new ArrayList<>();

			boolean init = true;
			// Take a row
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				short lastCellNum = row.getLastCellNum();
				boolean emptyRow = true;

				LinkedHashMap<String, CellData> newRow = new LinkedHashMap<>();

				// For every cell of the row get the value
				while ((init && (colIndex < lastCellNum)) || (colIndex < nbElements)) {

					String colValue = "";

					CellData cellData = null;

					Cell currentCell = row.getCell(colIndex);

					// Use this variable to store the type of the column
					Class<?> colClass = String.class;

					if (currentCell != null) {
						// Evaluate the value
						switch (currentCell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							colValue = currentCell.getStringCellValue();
							break;

						case Cell.CELL_TYPE_NUMERIC:
							colValue = Double.toString(currentCell.getNumericCellValue());
							colClass = Double.class;
							break;

						case Cell.CELL_TYPE_BOOLEAN:
							colValue = Boolean.toString(currentCell.getBooleanCellValue());
							colClass = Boolean.class;
							break;

						case Cell.CELL_TYPE_FORMULA:
							colValue = currentCell.getCellFormula();
							colClass = IFormula.class;
							break;

						case Cell.CELL_TYPE_BLANK:
							// Nothing to do since empty value
							break;

						case Cell.CELL_TYPE_ERROR:
							// FIXME : I don't know how to handle this
							break;

						// Default is considered date
						default:
							TimeZone defaultTimeZone = SimpleTimeZone.getDefault();

							GregorianCalendar calendar = new GregorianCalendar(defaultTimeZone);
							calendar.setTime(currentCell.getDateCellValue());
							colValue = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
									+ calendar.get(Calendar.DAY_OF_MONTH);
							colClass = Date.class;
							break;
						}

						cellData = new CellData(colValue, currentCell.getCellStyle().getFillBackgroundColor(),
								currentCell.getCellStyle().getFillForegroundColor(),
								currentCell.getCellStyle().getFillPattern());
					}

					// If initialization create the columns
					if (init) {
						if (!colValue.isEmpty()) {
							titleRow.add(colValue);
						}

					}
					// Else put in the row data
					else if (colIndex < titleRow.size()) {

						if (!colValue.isEmpty()) {
							emptyRow = false;
							String currentTitle = titleRow.get(colIndex);
							newRow.put(currentTitle, cellData);
							data.setTitleType(currentTitle, colClass);
						}

					}

					colIndex++;

				}

				if (!init && !emptyRow) {
					data.addRow(newRow);
				} else if (init) {
					nbElements = titleRow.size();
					data.setTitles(titleRow);
					init = false;
				}

				doc.addPage(ExcelSheet.DEFAULT_NAME + " " + i, data);

				colIndex = 0;
			}
		}
	}

}
