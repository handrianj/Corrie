package com.handrianj.corrie.graphics.charts.impl;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.handrianj.corrie.graphics.charts.IChart;
import com.handrianj.corrie.graphics.charts.IChartItem;

/**
 * Abstract that contains the composite and the canvas to draw the chart.
 *
 * @author Heri Andrianjafy
 *
 * @param <N>
 * @param <I>
 */
public abstract class AbstractChart<N extends Number, I extends IChartItem<N>> implements IChart<N, I> {

	private Canvas canvas;

	private Composite legendComposite;

	private I data;

	private RowLayout legendLayout;

	private GridData legendGridData;

	private Composite chartCompo;

	@SuppressWarnings("serial")
	public AbstractChart(Composite parent, int style, CorrieChartOptions options) {

		chartCompo = new Composite(parent, SWT.NONE);
		GridLayout chartLayout = new GridLayout(1, false);
		chartLayout.marginWidth = 0;
		chartCompo.setLayout(chartLayout);
		chartCompo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		legendComposite = new Composite(chartCompo, SWT.NONE);
		legendGridData = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		legendComposite.setLayoutData(legendGridData);
		legendLayout = new RowLayout(SWT.HORIZONTAL);
		legendLayout.marginWidth = 0;
		legendComposite.setLayout(legendLayout);

		canvas = new Canvas(chartCompo, style);
		canvas.setLayout(chartLayout);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent event) {
				paintEventHandler(event);

			}
		});
		addCompositeToCanvas(canvas);
	}

	protected Canvas getCanvas() {
		return canvas;
	}

	protected Composite getLegendComposite() {
		return legendComposite;
	}

	@Override
	public void setData(I data) {
		this.data = data;

		Collection<String> rows = data.getRows();

		Control[] children = legendComposite.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		for (String title : rows) {

			Label colorLabel = new Label(legendComposite, SWT.BORDER);
			colorLabel.setLayoutData(new RowData(20, 20));
			colorLabel.setBackground(data.getColor(title));

			Label rowLabel = new Label(legendComposite, SWT.NONE);
			rowLabel.setText(title);

		}

		Control[] canvasChildren = canvas.getChildren();
		for (Control control : canvasChildren) {
			control.dispose();
		}

		addCompositeToCanvas(canvas);

		legendComposite.layout(true, true);
		legendComposite.pack();

		canvas.layout(true, true);
		canvas.redraw();

	}

	@Override
	public void showLegend(boolean show) {
		legendGridData.exclude = !show;
		legendComposite.setVisible(show);
		chartCompo.layout(true);

	}

	public I getData() {
		return data;
	}

	/**
	 * All the chart rendering is done in this method. Please be carefull as
	 * this method is called at each rendering event (which means, this method
	 * is called very often)
	 *
	 * @param event
	 *
	 * @return
	 */
	protected abstract void paintEventHandler(PaintEvent event);

	/**
	 * Method used to create other canvas inside this composite.
	 *
	 * @param canvas
	 */
	protected abstract void addCompositeToCanvas(Canvas canvas);

}
