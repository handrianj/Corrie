package com.handrianj.corrie.overview.internal;

import java.util.Map;

import org.eclipse.jface.action.Action;

public class ActionData extends AbstractMenuItemData {

	private Action action;

	public ActionData(Action action, String id, int order, Map<Integer, String> langMap) {
		super(id, order, langMap);
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public void elementSelected(Object pilotObject) {
		action.run();

	}

}
