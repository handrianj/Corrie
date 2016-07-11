package com.handrianj.corrie.dblink.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.handrianj.corrie.dblink.model.ITableStructure;

/**
 * Item for the table structure
 *
 * @author Heri Andrianjafy
 *
 */
public class TableStructure implements ITableStructure {

	private String name;

	private Map<String, Class<?>> columnToType = new HashMap<>();

	private Map<String, Boolean> columnToNull = new HashMap<>();

	public TableStructure(String name) {
		this.name = name;
	}

	public void addData(String colName, Class<?> colClass, boolean canBeNull) {
		columnToType.put(colName, colClass);
		columnToNull.put(colName, canBeNull);
	}

	@Override
	public String getTableName() {
		return name;
	}

	@Override
	public Collection<String> getColumnsName() {
		return columnToType.keySet();
	}

	@Override
	public Class<?> getTypeForColumn(String colName) {
		return columnToType.get(colName);
	}

	@Override
	public boolean isColumnMandatory(String colName) {
		return columnToNull.get(colName);
	}

}
