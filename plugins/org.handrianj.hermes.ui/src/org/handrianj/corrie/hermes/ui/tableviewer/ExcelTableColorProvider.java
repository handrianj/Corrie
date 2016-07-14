package org.handrianj.corrie.hermes.ui.tableviewer;

import org.eclipse.swt.graphics.Color;

/**
 * Class used to provide colors to the excell table.
 *
 * @author Heri Andrianjafy
 *
 */
public abstract class ExcelTableColorProvider {

	/**
	 * Returns the color of a cell, Row is the map containing the columnNames in
	 * keys and String in values
	 * 
	 * @param columnName
	 * @param row
	 * @return
	 */
	public abstract Color getColor(String columnName, Object row);

}
