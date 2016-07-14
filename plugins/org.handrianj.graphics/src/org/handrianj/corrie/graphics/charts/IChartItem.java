package org.handrianj.corrie.graphics.charts;

import java.util.List;

import org.eclipse.swt.graphics.Color;

/**
 * Interface used to define the data of a chart, use is mandatory for all charts
 * items
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public interface IChartItem<T extends Number> {

	/**
	 * Returns all the row titles of the chart
	 *
	 * @return List of all the chart rows
	 */
	public List<String> getRows();

	/**
	 * Returns the color that the chart will use for the row in parameter
	 *
	 * @param name
	 *            Name of the row
	 * @return Color to be used by this row
	 */
	public Color getColor(String name);

	/**
	 * Sets the color that the chart will use for the row in parameter
	 *
	 * @param name
	 *            Row name
	 * @param color
	 *            Color to be used by this row
	 */
	void setColor(String name, Color color);

	/**
	 * Sets the color code for the row
	 *
	 * @param name
	 * @param r
	 *            Red from 0 to 255
	 * @param g
	 *            Green from 0 to 255
	 * @param b
	 *            Blue from 0 to 255
	 * @param a
	 *            Alpha from 0 to 255
	 */
	void setColor(String name, int r, int g, int b, int a);

	/**
	 * Checks if the class in parameter is okay with the current item format
	 *
	 * @param clazzToCheck
	 *            Class to be checked
	 */
	boolean isFormatOk(Class<? extends Number> clazzToCheck);

}
