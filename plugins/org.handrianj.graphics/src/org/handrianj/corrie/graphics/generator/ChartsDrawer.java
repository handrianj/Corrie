package org.handrianj.corrie.graphics.generator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.handrianj.corrie.graphics.charts.IChart;
import org.handrianj.corrie.graphics.charts.IChartItem;
import org.handrianj.corrie.graphics.charts.IMultipleValueItem;
import org.handrianj.corrie.graphics.charts.ISingleValueItem;
import org.handrianj.corrie.graphics.charts.impl.CorrieChartOptions;
import org.handrianj.corrie.graphics.charts.impl.FunnelChart;
import org.handrianj.corrie.graphics.charts.items.impl.internal.ChartJSEmbedded;

/**
 * Class used to render different types of charts. Please see the differents
 * constant values for more details on each chart
 *
 * @author Heri Andrianjafy
 *
 */
public class ChartsDrawer {

	/**
	 * Draws a line chart, uses a {@link IMultipleValueItem} for rendering
	 */
	public static final int LINE_CHART = 0;

	/**
	 * Draws a pie chart, uses a {@link ISingleValueItem} for rendering
	 */
	public static final int PIE_CHART = 1;

	/**
	 * Draws a bar chart, uses a {@link IMultipleValueItem} for rendering
	 */
	public static final int BAR_CHART = 2;

	/**
	 * Draws a radar char, uses a {@link IMultipleValueItem} for rendering
	 */
	public static final int RADAR_CHART = 3;

	/**
	 * Draws a polar area chart, uses a {@link ISingleValueItem} for rendering
	 */
	public static final int POLAR_AREA_CHART = 4;

	/**
	 * Draws a doughnut chart, uses a {@link ISingleValueItem} for rendering
	 */
	public static final int DOUGHNUT_CHART = 5;

	/**
	 * Draws a funnel chart, uses a {@link ISingleValueItem} for rendering
	 */
	public static final int FUNNEL_CHART = 6;

	private CorrieChartOptions defaultOptions = new CorrieChartOptions();

	public ChartsDrawer() {
		defaultOptions.setShowFill(false);
		defaultOptions.setBezierCurve(false);
	}

	public IChart<?, ?> drawChart(Composite parent, IChartItem<? extends Number> chartItem, int chartKey) {
		return drawChart(parent, chartItem, chartKey, defaultOptions);
	}

	/**
	 * Draws the chart in the composite given into parameter
	 *
	 * @param parent
	 * @param chartItem
	 * @param chartKey
	 */
	@SuppressWarnings("unchecked")
	public IChart<?, ?> drawChart(Composite parent, IChartItem<? extends Number> chartItem, int chartKey,
			CorrieChartOptions options) {
		IChart<?, ?> chart;
		if (chartKey == FUNNEL_CHART) {

			// if (chartItem.isFormatOk(Float.class)) {

			if (chartItem instanceof ISingleValueItem) {
				FunnelChart funnel = new FunnelChart(parent, options, SWT.NONE);
				funnel.showLegend(options.getShowLegend());
				funnel.setData((ISingleValueItem<Float>) chartItem);
				chart = funnel;

			} else {
				throw new IllegalArgumentException("Chart item must be " + ISingleValueItem.class.getCanonicalName());
			}

		} else {

			@SuppressWarnings("rawtypes")
			ChartJSEmbedded embedded = new ChartJSEmbedded<>(parent, chartKey, options);
			embedded.setData(chartItem);
			chart = embedded;
		}

		parent.layout();

		return chart;
	}

}
