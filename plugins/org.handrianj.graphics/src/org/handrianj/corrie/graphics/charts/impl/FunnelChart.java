package org.handrianj.corrie.graphics.charts.impl;

import java.text.NumberFormat;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.handrianj.corrie.graphics.charts.ISingleValueItem;

/**
 * Default implementation of the funnel chart, the chart will use a ChartItem
 * with N items. Each item will define a separation between two levels of the
 * chart
 *
 * @author Heri Andrianjafy
 *
 */
public class FunnelChart extends AbstractChart<Float, ISingleValueItem<Float>> {
	private NumberFormat format = NumberFormat.getInstance();

	public FunnelChart(Composite parent, CorrieChartOptions options, int style) {
		super(parent, style, options);
	}

	@Override
	protected void paintEventHandler(PaintEvent event) {
		// Nothing to do
	}

	@Override
	protected void addCompositeToCanvas(Canvas canvas) {

		ISingleValueItem<Float> data = getData();

		if (data == null) {
			return;
		}

		List<String> titles = data.getRows();

		boolean firstItem = true;

		float previousValue = 0;

		if (titles.isEmpty()) {
			return;
		}
		float firstValue = data.getValue(titles.get(0));

		// Let's check all the lines
		for (int i = 0; i < (titles.size() - 1); i++) {

			// Get the values for the current line
			float testValue = data.getValue(titles.get(i + 1));
			float ratio = testValue / data.getValue(titles.get(i));
			float currentValue = testValue / firstValue;
			format.setMaximumFractionDigits(2);

			Canvas funnelItem = new Canvas(canvas, SWT.NONE);
			funnelItem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			funnelItem.addPaintListener(
					createMiniItem(data, firstItem, previousValue, titles.get(i), currentValue, funnelItem, ratio));

			funnelItem.setToolTipText(titles.get(i) + " " + format.format(ratio * 100) + "%");

			previousValue = currentValue;

			firstItem = false;

		}

	}

	@SuppressWarnings("serial")
	private PaintListener createMiniItem(ISingleValueItem<Float> data, boolean firstItem, float previousValue,
			String currentTitle, Float storedValue, Canvas funnelItem, float ratio) {
		return new PaintListener() {

			@Override
			public void paintControl(PaintEvent event) {

				Point size = funnelItem.getSize();

				GC gc = event.gc;

				int xSize = size.x;
				int ySize = size.y;

				Float currentValueScaled = storedValue / 2;
				Float previousValueScaled = previousValue / 2;

				Color color = data.getColor(currentTitle);
				gc.setBackground(color);

				// Check if total size is not superior to the canvas (happens
				// when an idiot gives bigger values at the begining than at the
				// end)
				int computedXSize = (int) (xSize * currentValueScaled);
				if (computedXSize > (xSize / 2)) {
					computedXSize = xSize / 2;
				}

				/**
				 * Calculating quad points Horizontal positions are calculated
				 * fron the center of the picture (makes calculations easier)
				 * Vertical positions are calculated in steps (divide total
				 * height in number of lies and increment gradually)
				 */
				if (firstItem) {

					gc.fillPolygon(new int[] { 0, 0, xSize, 0, (xSize / 2) + computedXSize, ySize,
							(xSize / 2) - computedXSize, ySize });

				} else {

					gc.fillPolygon(new int[] { (xSize / 2) - (int) (xSize * previousValueScaled), 0,
							(xSize / 2) + (int) (xSize * previousValueScaled), 0, (xSize / 2) + computedXSize, ySize,
							(xSize / 2) - computedXSize, ySize });

				}

				int green = color.getGreen();
				int red = color.getRed();
				int blue = color.getBlue();
				if (((red + green + blue) / 3) < 122) {
					gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				} else {
					gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
				}
				format.setMaximumFractionDigits(2);
				String formattedValue = format.format(ratio * 100);

				String string = formattedValue + "%";
				gc.drawText(string, (xSize / 2) - (string.length() * 4), (int) (ySize * 0.45), true);

			}
		};
	}

}
