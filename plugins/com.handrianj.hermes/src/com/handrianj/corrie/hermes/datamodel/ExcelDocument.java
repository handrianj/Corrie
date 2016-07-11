package com.handrianj.corrie.hermes.datamodel;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representation of an excell document
 * 
 * @author Heri Andrianjafy
 *
 */
public class ExcelDocument {

	private Map<String, ExcelSheet> nameToPage = new LinkedHashMap<>();

	public ExcelDocument(ExcelSheet... pages) {
		for (ExcelSheet excelSheet : pages) {
			nameToPage.put(excelSheet.getPageTitle(), excelSheet);
		}
	}

	public void addPage(String name, ExcelSheet page) {
		nameToPage.put(name, page);
	}

	public void removePage(String name) {
		nameToPage.remove(name);
	}

	public Collection<ExcelSheet> getAllPages() {
		return nameToPage.values();
	}

	public Collection<String> getAllSheetsName() {
		return nameToPage.keySet();
	}

	public ExcelSheet getFirstPage() {
		for (ExcelSheet currentPage : nameToPage.values()) {

			return currentPage;
		}
		return null;
	}
}
