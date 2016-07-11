package com.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.ui.PlatformUI;

@SuppressWarnings("serial")
public class UpdatePasswordRunner extends AbstractUpdatePasswordAction {

	public UpdatePasswordRunner() {
		super();
	}

	@Override
	public void run() {
		openUpdateDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), false);

	}

}
