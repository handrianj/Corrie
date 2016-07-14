package org.handrianj.corrie.graphics.charts.impl;

import org.eclipse.rap.chartjs.ChartOptions;

/**
 * Options specific to corrie charts
 *
 * @author Heri Andrianjafy
 *
 */
public class CorrieChartOptions extends ChartOptions {

	private boolean showLegend = false;

	public CorrieChartOptions() {
		super();
	}

	public boolean getShowLegend() {
		return showLegend;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

}
