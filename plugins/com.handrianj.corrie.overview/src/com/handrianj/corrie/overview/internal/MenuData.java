package com.handrianj.corrie.overview.internal;

import java.util.Map;

public class MenuData extends AbstractMenuItemData {

	public MenuData(String itemId, int order, Map<Integer, String> langMap) {
		super(itemId, order, langMap);
	}

	@Override
	public void elementSelected(Object pilotObject) {
		// nothing to select on menu element

	}

}
