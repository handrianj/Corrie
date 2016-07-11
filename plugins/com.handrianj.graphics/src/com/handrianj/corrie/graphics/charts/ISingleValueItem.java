package com.handrianj.corrie.graphics.charts;

/**
 * Item used for chart with only one value per item such as pie charts, funnels.
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public interface ISingleValueItem<T extends Number> extends IChartItem<T> {

	/**
	 * Sets the value for the given row
	 *
	 * @param name
	 *            Name of the row
	 * @param value
	 *            Value of the row
	 */
	public void setValue(String name, T value);

	/**
	 * Returns the value for the given row
	 *
	 * @param name
	 *            Name of the row
	 * @return Value of the row
	 */
	public T getValue(String name);
}
