package com.handrianj.corrie.dblink.model;

import java.util.Collection;

/**
 * Interface used to represent a DB table structure
 *
 * @author Heri Andrianjafy
 *
 */
public interface ITableStructure {

	/**
	 * Returns the table name
	 *
	 * @return The name of the DB table
	 */
	public String getTableName();

	/**
	 * Returns all the columns name
	 *
	 * @return Name of all the columns
	 */
	public Collection<String> getColumnsName();

	/**
	 * Returns the class type expected for the column
	 *
	 * @param colName
	 *            Name of the column
	 * @return The class used by the column
	 */
	public Class<?> getTypeForColumn(String colName);

	/**
	 * Returns a boolean to say if the value can be null
	 *
	 * @param colName
	 * @return True if the value cannot be null, false otherwise
	 */
	public boolean isColumnMandatory(String colName);

}
