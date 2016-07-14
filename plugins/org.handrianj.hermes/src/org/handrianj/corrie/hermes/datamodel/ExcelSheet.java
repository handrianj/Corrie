package org.handrianj.corrie.hermes.datamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent an excell sheet composted of Map<String,String> A map
 * represents one row with the mapping, <Column Title, Column Value)
 *
 * @author Heri Andrianjafy
 *
 */
public class ExcelSheet {

	public static final String DEFAULT_NAME = "Untitled";

	private Map<String, Class<?>> titleToType = new LinkedHashMap<>();

	private List<LinkedHashMap<String, CellData>> allRows = new ArrayList<>();

	private String title = DEFAULT_NAME;

	public void addRow(LinkedHashMap<String, CellData> newRow) {
		allRows.add(newRow);
	}

	public List<LinkedHashMap<String, CellData>> getRows() {
		return Collections.unmodifiableList(allRows);
	}

	public void addTitle(String title, Class<?> type) {
		titleToType.put(title, type);
	}

	public Collection<String> getTitles() {
		return Collections.unmodifiableSet(titleToType.keySet());
	}

	public void setTitles(List<String> titles) {
		for (String string : titles) {
			this.titleToType.put(string, String.class);
		}
	}

	public void setTitleType(String title, Class<?> type) {
		titleToType.put(title, type);
	}

	public Class<?> getType(String title) {
		return titleToType.get(title);
	}

	public void setPageTitle(String name) {
		this.title = name;

	}

	public String getPageTitle() {
		return title;
	}

}
