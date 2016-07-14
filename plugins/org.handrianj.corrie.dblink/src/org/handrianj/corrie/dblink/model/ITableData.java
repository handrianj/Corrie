package org.handrianj.corrie.dblink.model;

/**
 * Interface used to represent the datas of a DB tables
 *
 * @author Heri Andrianjafy
 *
 */
public interface ITableData {

	/**
	 * Adds a value to the column
	 * 
	 * @param columnName
	 *            Name of the column
	 * @param colValue
	 *            Value to be inserted
	 */
	void addValue(String columnName, Object colValue);

	/**
	 * Returns the value of a column
	 * 
	 * @param currentColName
	 *            Name of the column
	 * @return Value of the column
	 */
	Object getValue(String currentColName);

}
