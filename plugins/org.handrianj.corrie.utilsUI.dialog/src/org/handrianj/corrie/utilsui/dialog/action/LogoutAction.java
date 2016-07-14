package org.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.utilsui.dialog.Activator;
import org.handrianj.corrie.utilsui.dialog.lang.IDialogLang;

@SuppressWarnings("serial")
public class LogoutAction extends AbstractLogoutAction {

	public LogoutAction(IWorkbenchWindow window, ILanguageManagerService languageManager) {

		super(languageManager.getMessage(Activator.PLUGIN_ID, IDialogLang.SESSION_MENU_2, RWT.getUISession()), window);
		setId(this.getClass().getName());

	}

}
