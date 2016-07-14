package org.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.ui.PlatformUI;

@SuppressWarnings("serial")
public class LogoutRunner extends AbstractLogoutAction {

	public LogoutRunner() {

		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow());

	}

}
