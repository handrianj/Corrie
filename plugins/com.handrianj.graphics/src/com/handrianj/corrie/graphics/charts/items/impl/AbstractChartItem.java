package com.handrianj.corrie.graphics.charts.items.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.handrianj.corrie.graphics.charts.IChartItem;

/**
 * Abstract implementation of chart item for color management
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public abstract class AbstractChartItem<T extends Number> implements IChartItem<T> {

	private Map<String, Color> itemsToColor = new LinkedHashMap<>();

	@Override
	public boolean isFormatOk(Class<? extends Number> clazzToCheck) {

		if (getClassType().isAssignableFrom(clazzToCheck)) {
			return true;
		}

		return false;
	}

	@Override
	public Color getColor(String name) {

		Color color = itemsToColor.get(name);

		if (color == null) {
			color = new Color(Display.getDefault(), (int) (Math.random() * 255), (int) (Math.random() * 255),
					(int) (Math.random() * 255), 124);

			itemsToColor.put(name, color);
		}
		return color;
	}

	@Override
	public void setColor(String name, Color color) {
		itemsToColor.put(name, color);
	}

	@Override
	public void setColor(String name, int r, int g, int b, int a) {
		itemsToColor.put(name, new Color(Display.getCurrent(), r, g, b, a));

	}

	/**
	 * Method used to retrieve the current number class stored in the chartitem
	 *
	 * @return
	 */
	protected abstract Class<?> getClassType();
}
