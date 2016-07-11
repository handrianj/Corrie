package com.handrianj.corrie.graphics.charts.items.impl.internal;

import java.util.Collection;
import java.util.List;

import org.eclipse.rap.chartjs.ChartPointData;
import org.eclipse.rap.chartjs.ChartRowData;
import org.eclipse.rap.chartjs.ChartStyle;
import org.eclipse.swt.graphics.Color;

import com.handrianj.corrie.graphics.charts.IChartItem;
import com.handrianj.corrie.graphics.charts.IMultipleValueItem;
import com.handrianj.corrie.graphics.charts.ISingleValueItem;

/**
 * Helper class used for to convert from IChartITem to JS format
 *
 * @author Heri Andrianjafy
 *
 */
public class ChartJSHelper {

	public static ChartRowData convertToRowData(IChartItem<Float> chartItem) {

		if (chartItem instanceof IMultipleValueItem) {
			Collection<String> rows = chartItem.getRows();
			List<String> titles = ((IMultipleValueItem<?>) chartItem).getAttributeTitles();
			ChartRowData data = new ChartRowData(titles.toArray(new String[titles.size()]));

			for (String rowTitle : rows) {

				int cpt = 0;

				Color color = chartItem.getColor(rowTitle);

				// create a chartstyle for colors
				ChartStyle style = new ChartStyle(color.getRed(), color.getGreen(), color.getBlue(),
						color.getAlpha() / 255.0f);

				// get the data (color and numbers)
				Float[] dataForRow = ((IMultipleValueItem<Float>) chartItem).getValues(rowTitle);

				float[] format = new float[dataForRow.length];

				for (Float value : dataForRow) {
					format[cpt++] = value;
				}

				data.addRow(format, style);

			}

			return data;
		} else {
			throw new IllegalArgumentException(
					("ChartItem must be an instance of " + IMultipleValueItem.class.getCanonicalName()));
		}

	}

	public static ChartPointData convertToPointData(IChartItem<Float> chartItem) {

		if (chartItem instanceof ISingleValueItem) {
			ChartPointData chartPointData = new ChartPointData();

			// For all the rows
			Collection<String> rows = chartItem.getRows();
			for (String rowName : rows) {

				// get the data (color and numbers)
				Float value = ((ISingleValueItem<Float>) chartItem).getValue(rowName);
				Color color = chartItem.getColor(rowName);

				// create a chartstyle for colors
				ChartStyle style = new ChartStyle(color.getRed(), color.getGreen(), color.getBlue(),
						color.getAlpha() / 255.0f);

				// Add all the points to the pie
				chartPointData.addPoint(value, style);

			}
			return chartPointData;
		} else {
			throw new IllegalArgumentException(
					"Chart Item must be an instance of " + ISingleValueItem.class.getCanonicalName());
		}
	}

}
