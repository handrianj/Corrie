package org.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.handrianj.corrie.dblink.services.IDBSessionService;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.handrianj.corrie.sessionmanager.service.ISessionManager;
import org.handrianj.corrie.utilsui.dialog.Activator;
import org.handrianj.corrie.utilsui.dialog.lang.IDialogLang;
import org.handrianj.corrie.utilsui.dialog.ui.UpdatePasswordDialog;

public abstract class AbstractUpdatePasswordAction extends Action {

	private ILanguageManagerService languageService;
	private ISessionManager sessionManager;

	public AbstractUpdatePasswordAction() {
		super();
		languageService = ServiceRegistry.getLanguageManagerService();
		sessionManager = ServiceRegistry.getSessionManager();
	}

	public AbstractUpdatePasswordAction(String text, ImageDescriptor image) {
		super(text, image);
		languageService = ServiceRegistry.getLanguageManagerService();
		sessionManager = ServiceRegistry.getSessionManager();
	}

	public AbstractUpdatePasswordAction(String text, int style) {
		super(text, style);
		languageService = ServiceRegistry.getLanguageManagerService();
		sessionManager = ServiceRegistry.getSessionManager();
	}

	public AbstractUpdatePasswordAction(String text) {
		super(text);
		languageService = ServiceRegistry.getLanguageManagerService();
		sessionManager = ServiceRegistry.getSessionManager();
	}

	@SuppressWarnings("unchecked")
	protected void openUpdateDialog(IWorkbenchWindow window, boolean force) {
		UpdatePasswordDialog dialog = new UpdatePasswordDialog(Display.getCurrent().getActiveShell(), languageService,
				force);

		if (dialog.open() == 0) {

			if (dialog.isNewPasswordOkay()) {
				@SuppressWarnings("rawtypes")
				IDBSessionService dbservice = ServiceRegistry.getDbservice();
				UISession uiSession = RWT.getUISession();

				if (dbservice.isPasswordCorrect(sessionManager.getUser(uiSession), dialog.getOldPassword())) {
					dbservice.updatePassword(sessionManager.getUser(uiSession), dialog.getNewPassword().trim());

					if (window != null) {
						MessageDialog.openInformation(window.getShell(),
								languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_CHANGE, uiSession),
								languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_CHANGE_SUCCESS,
										uiSession));
					}
				} else {
					if (window != null) {
						MessageDialog.openError(window.getShell(),
								languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_ERRO, uiSession),
								languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.OLDPASSWORD_ERRO,
										uiSession));
					}
					openUpdateDialog(window, force);
				}
			} else {
				openUpdateDialog(window, force);
			}

		}
	}

}
