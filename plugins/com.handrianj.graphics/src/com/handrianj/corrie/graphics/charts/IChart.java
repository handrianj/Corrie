package com.handrianj.corrie.graphics.charts;

/**
 * Interface used to define a chart component with a legend
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public interface IChart<N extends Number, I extends IChartItem<N>> {

	/**
	 * Updates the data of the chart, this will automatically triggers a redraw
	 * of the chart
	 *
	 * @param data
	 *            ChartItem to be drawed
	 */
	public void setData(I data);

	/**
	 * Updates the chart to show the legend
	 *
	 * @param show
	 *            True if you want to show the legend, false otherwise
	 */
	public void showLegend(boolean show);

}
