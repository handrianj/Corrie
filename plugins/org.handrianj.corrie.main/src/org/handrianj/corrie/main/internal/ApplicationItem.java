package org.handrianj.corrie.main.internal;

import org.handrianj.corrie.applicationmanagerservice.IApplicationConfig;

public class ApplicationItem implements IApplicationConfig {

	private String editorID;
	private String perspectiveID;

	public ApplicationItem(String editorID, String perspectiveID) {
		super();
		this.editorID = editorID;
		this.perspectiveID = perspectiveID;
	}

	@Override
	public String getInitialEditorID() {
		return editorID;
	}

	@Override
	public String getInitialPerspectiveID() {
		return perspectiveID;
	}

}
