package com.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.ui.IWorkbenchWindow;

import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.utilsui.dialog.Activator;
import com.handrianj.corrie.utilsui.dialog.lang.IDialogLang;

@SuppressWarnings("serial")
public class UpdatePasswordAction extends AbstractUpdatePasswordAction {

	private IWorkbenchWindow window;
	private boolean force;

	public UpdatePasswordAction(IWorkbenchWindow window, ILanguageManagerService languageManager, boolean force) {
		super(languageManager.getMessage(Activator.PLUGIN_ID, IDialogLang.UPDATE_TITLE, RWT.getUISession()));
		setId(this.getClass().getName());
		this.window = window;
		this.force = force;

	}

	@Override
	public void run() {
		openUpdateDialog(window, force);
	}

}
