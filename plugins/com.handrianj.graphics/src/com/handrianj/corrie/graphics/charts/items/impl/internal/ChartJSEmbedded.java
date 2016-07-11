package com.handrianj.corrie.graphics.charts.items.impl.internal;

import org.eclipse.rap.chartjs.Chart;
import org.eclipse.rap.chartjs.ChartOptions;
import org.eclipse.rap.chartjs.ChartPointData;
import org.eclipse.rap.chartjs.ChartRowData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.handrianj.corrie.graphics.charts.IChart;
import com.handrianj.corrie.graphics.charts.IChartItem;
import com.handrianj.corrie.graphics.charts.impl.CorrieChartOptions;
import com.handrianj.corrie.graphics.generator.ChartsDrawer;

public class ChartJSEmbedded<N extends Number, I extends IChartItem<N>> implements IChart<N, I> {

	private Chart chart;
	private int chartType;

	private ChartOptions options;

	public ChartJSEmbedded(Composite parent, int chartType, CorrieChartOptions options) {
		chart = new Chart(parent, SWT.NONE);
		chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.chartType = chartType;
		this.options = options;
	}

	public ChartJSEmbedded(Composite parent, int chartType) {
		this(parent, chartType, generateDefaultOptions());
	}

	private static CorrieChartOptions generateDefaultOptions() {
		CorrieChartOptions chartOptions = new CorrieChartOptions();
		chartOptions.setShowFill(false);
		chartOptions.setBezierCurve(false);
		return chartOptions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setData(I chartItem) {
		switch (chartType) {
		case ChartsDrawer.FUNNEL_CHART:

			break;

		case ChartsDrawer.PIE_CHART:

			if (chartItem.isFormatOk(Float.class)) {

				ChartPointData chartPointData = ChartJSHelper.convertToPointData((IChartItem<Float>) chartItem);

				// Finally draw the chart
				chart.drawPieChart(chartPointData, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}
			break;

		case ChartsDrawer.BAR_CHART:
			if (chartItem.isFormatOk(Float.class)) {

				ChartRowData data = ChartJSHelper.convertToRowData((IChartItem<Float>) chartItem);

				chart.drawBarChart(data, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}

			break;

		case ChartsDrawer.DOUGHNUT_CHART:
			if (chartItem.isFormatOk(Float.class)) {

				ChartPointData data = ChartJSHelper.convertToPointData((IChartItem<Float>) chartItem);

				chart.drawDoughnutChart(data, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}

			break;

		case ChartsDrawer.LINE_CHART:
			if (chartItem.isFormatOk(Float.class)) {

				ChartRowData data = ChartJSHelper.convertToRowData((IChartItem<Float>) chartItem);

				chart.drawLineChart(data, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}

			break;

		case ChartsDrawer.RADAR_CHART:
			if (chartItem.isFormatOk(Float.class)) {

				ChartRowData data = ChartJSHelper.convertToRowData((IChartItem<Float>) chartItem);

				chart.drawRadarChart(data, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}
			break;

		case ChartsDrawer.POLAR_AREA_CHART:
			if (chartItem.isFormatOk(Float.class)) {

				ChartPointData data = ChartJSHelper.convertToPointData((IChartItem<Float>) chartItem);

				chart.drawPolarAreaChart(data, options);

			} else {
				throw new IllegalArgumentException("Format of numbrs must be " + Float.class.getCanonicalName());
			}
			break;

		default:
			break;
		}
		chart.layout();
		chart.redraw();
	}

	@Override
	public void showLegend(boolean show) {
		options.setShowToolTips(false);
	}

}
