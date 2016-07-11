package com.handrianj.corrie.overview.internal;

import java.util.HashMap;
import java.util.Map;

import com.handrianj.corrie.languagemanager.service.ILang;

public abstract class AbstractMenuItemData {
	private String itemId;
	private int order = -1;
	private Map<Integer, String> langMap = new HashMap<>();

	public AbstractMenuItemData(String itemId, int order, Map<Integer, String> langMap) {
		super();
		this.itemId = itemId;
		this.order = order;
		this.langMap = langMap;
	}

	public String getId() {
		return itemId;
	}

	public int getOrder() {
		return order;
	}

	public String getText(ILang key) {
		return langMap.get(key.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractMenuItemData other = (AbstractMenuItemData) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		return true;
	}

	/**
	 * Executes the action in case of element selection
	 */
	public abstract void elementSelected(Object pilotObject);

}
