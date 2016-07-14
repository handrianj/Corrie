package org.handrianj.corrie.utilsui;

/**
 *
 * Viewer comparator used to sort the TableViewers used in UNIC. All
 * implementations must extends the ViewerComparator class
 *
 * @author Heri Andrianjafy
 *
 */
public interface ITableViewerComparator {

	/**
	 * Sets the column to be compared
	 * 
	 * @param colIndex
	 *            column index
	 */
	void setColumn(int colIndex);

	/**
	 * Returns the direction of the sorting
	 * 
	 * @return -1 for ascending, 1 for descendings
	 */
	int getDirection();

}
