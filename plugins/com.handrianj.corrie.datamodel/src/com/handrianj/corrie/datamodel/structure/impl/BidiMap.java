package com.handrianj.corrie.datamodel.structure.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bidirectional map, this map is used to quickly retrieves the key of a value
 * without using EntrySet
 *
 * @author Heri Andrianjafy
 *
 */
public class BidiMap<T, U> {

	private Map<T, U> mapA = new LinkedHashMap<>();

	private Map<U, T> mapB = new LinkedHashMap<>();

	public void put(T key, U value) {
		mapA.put(key, value);
		mapB.put(value, key);
	}

	public U getValue(T key) {
		return mapA.get(key);
	}

	public T getKey(U value) {
		return mapB.get(value);
	}

	public void clear() {
		mapA.clear();
		mapB.clear();

	}

	public void remove(T key) {
		U u = mapA.remove(key);
		mapB.remove(u);

	}

	public Set<T> getKeySet() {
		return mapA.keySet();
	}

}
