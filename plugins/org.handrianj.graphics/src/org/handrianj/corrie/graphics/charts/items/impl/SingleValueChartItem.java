package org.handrianj.corrie.graphics.charts.items.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.handrianj.corrie.graphics.charts.ISingleValueItem;

/***
 * Chart item with non dependant values (X values only)
 *
 * @author Heri Andrianjafy
 *
 * @param <C>
 */
public class SingleValueChartItem<C extends Number> extends AbstractChartItem<C> implements ISingleValueItem<C> {

	private Map<String, C> rowToValue = new LinkedHashMap<>();

	@Override
	public List<String> getRows() {
		return new ArrayList<>(rowToValue.keySet());

	}

	@Override
	public void setValue(String name, C value) {
		rowToValue.put(name, value);

	}

	@Override
	public C getValue(String name) {
		C c = rowToValue.get(name);
		if (c != null) {
			return c;
		}
		return null;
	}

	@Override
	protected Class<?> getClassType() {
		return rowToValue.values().iterator().next().getClass();
	}

}
