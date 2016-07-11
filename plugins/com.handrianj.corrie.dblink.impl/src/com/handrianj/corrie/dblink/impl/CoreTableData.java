package com.handrianj.corrie.dblink.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.handrianj.corrie.dblink.model.ITableData;

/**
 * Standard implementation of ITable Data
 * 
 * @author Heri Andrianjafy
 *
 */
public class CoreTableData implements ITableData {

	private Map<String, Object> columnToValue = new HashMap<>();

	public CoreTableData(Map<String, ?> originaDatas) {

		for (Entry<String, ?> entry : originaDatas.entrySet()) {

			columnToValue.put(entry.getKey(), entry.getValue());

		}
	}

	@Override
	public void addValue(String columnName, Object colValue) {
		columnToValue.put(columnName, colValue);
	}

	@Override
	public Object getValue(String currentColName) {
		return columnToValue.get(currentColName);
	}
}
