package org.handrianj.corrie.viewsmanager.manager.internal;

import org.handrianj.corrie.viewsmanager.ui.IViewData;

public class ViewData implements IViewData {

	private String id;

	public ViewData(String iD) {
		super();
		id = iD;
	}

	@Override
	public String getID() {
		return id;
	}

}
