package org.handrianj.corrie.graphics.charts.items.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.handrianj.corrie.graphics.charts.IMultipleValueItem;

/**
 * Chart item used for X,Y values
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public class MultipleValueChartItem<T extends Number> extends AbstractChartItem<T> implements IMultipleValueItem<T> {

	private List<String> titles = new ArrayList<>();

	private Map<String, T[]> rowToValue = new LinkedHashMap<>();

	public List<String> getTitles() {
		return Collections.unmodifiableList(titles);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValues(String string, T... values) {
		rowToValue.put(string, values);

	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] getValues(String name) {
		T[] fs = rowToValue.get(name);

		if (fs == null) {

			fs = (T[]) new Number[0];
		}

		return fs;
	}

	@Override
	public void addAttributeTitle(String title) {
		titles.add(title);

	}

	@Override
	public List<String> getAttributeTitles() {
		return Collections.unmodifiableList(titles);
	}

	@Override
	public List<String> getRows() {
		return new ArrayList<>(rowToValue.keySet());
	}

	@Override
	protected Class<?> getClassType() {

		if (rowToValue.values().isEmpty()) {
			return Object.class;
		}
		T[] next = rowToValue.values().iterator().next();
		if (next.length > 0) {
			return next[0].getClass();
		}
		return Object.class;
	}

}
