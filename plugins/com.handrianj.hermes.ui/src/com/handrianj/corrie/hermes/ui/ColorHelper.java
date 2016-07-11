package com.handrianj.corrie.hermes.ui;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.eclipse.swt.SWT;

import com.handrianj.corrie.datamodel.structure.impl.BidiMap;

/**
 * POI to SWT colors mapper
 *
 * @author Heri Andrianjafy
 *
 */
public class ColorHelper {

	private static BidiMap<IndexedColors, Integer> excelColorsToSWT = new BidiMap<IndexedColors, Integer>();

	static void init() {
		excelColorsToSWT.put(IndexedColors.BLACK, SWT.COLOR_BLACK);
		excelColorsToSWT.put(IndexedColors.BLUE, SWT.COLOR_BLUE);
		excelColorsToSWT.put(IndexedColors.DARK_BLUE, SWT.COLOR_DARK_BLUE);
		excelColorsToSWT.put(IndexedColors.DARK_GREEN, SWT.COLOR_DARK_GREEN);
		excelColorsToSWT.put(IndexedColors.DARK_RED, SWT.COLOR_DARK_RED);
		excelColorsToSWT.put(IndexedColors.DARK_YELLOW, SWT.COLOR_DARK_YELLOW);
		excelColorsToSWT.put(IndexedColors.GREEN, SWT.COLOR_GREEN);
		excelColorsToSWT.put(IndexedColors.GREY_25_PERCENT, SWT.COLOR_DARK_GRAY);
		excelColorsToSWT.put(IndexedColors.GREY_40_PERCENT, SWT.COLOR_DARK_GRAY);
		excelColorsToSWT.put(IndexedColors.GREY_50_PERCENT, SWT.COLOR_DARK_GRAY);
		excelColorsToSWT.put(IndexedColors.GREY_80_PERCENT, SWT.COLOR_GRAY);
		excelColorsToSWT.put(IndexedColors.RED, SWT.COLOR_RED);
		excelColorsToSWT.put(IndexedColors.WHITE, SWT.COLOR_WHITE);
		excelColorsToSWT.put(IndexedColors.YELLOW, SWT.COLOR_YELLOW);

	}

	public static Integer getSWTColor(IndexedColors color) {

		Integer value = excelColorsToSWT.getValue(color);
		if (value == null) {
			value = -1;
		}
		return value;

	}

	public static Integer getSWTColor(short color) {
		Integer value;
		try {
			value = excelColorsToSWT.getValue(IndexedColors.values()[color]);
		} catch (ArrayIndexOutOfBoundsException e) {
			value = -1;
		}

		if (value == null) {
			value = -1;
		}
		return value;

	}

	public static IndexedColors getExcelColor(Integer colorIndex) {
		return excelColorsToSWT.getKey(colorIndex);
	}

}
