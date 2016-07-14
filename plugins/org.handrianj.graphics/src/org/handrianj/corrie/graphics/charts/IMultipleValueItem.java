package org.handrianj.corrie.graphics.charts;

import java.util.List;

/**
 * Item used for chart with multiple values per item such as Line charts, Bar
 * charts.
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public interface IMultipleValueItem<T extends Number> extends IChartItem<T> {

	/**
	 * Adds a new row to the current chart item
	 *
	 * @param string
	 *            Row name
	 * @param values
	 *            All values for this row
	 */
	@SuppressWarnings("unchecked")
	public void setValues(String string, T... values);

	/**
	 * Returns an array with all the numeric datas for the given row
	 *
	 * @param name
	 *            Row name
	 * @return all the values for this row
	 */
	public T[] getValues(String name);

	/**
	 * Adds a title attribute in the item
	 *
	 * @param title
	 *            Name of the attribute
	 */
	public void addAttributeTitle(String title);

	/**
	 * Gets all the attributes title
	 *
	 * @return List of attribute titles
	 */
	public List<String> getAttributeTitles();

}
