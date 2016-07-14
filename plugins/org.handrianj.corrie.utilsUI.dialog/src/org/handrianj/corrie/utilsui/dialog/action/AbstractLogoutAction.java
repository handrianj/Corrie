package org.handrianj.corrie.utilsui.dialog.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.ui.IWorkbenchWindow;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.handrianj.corrie.utilsui.dialog.Activator;
import org.handrianj.corrie.utilsui.dialog.lang.IDialogLang;

public class AbstractLogoutAction extends Action {

	private final String REFRESH_DELAY = "1000"; // refresh after logout (in
	// milliseconds)

	private String logoutMessage;

	private String logoutTitle;

	private IWorkbenchWindow window;

	public AbstractLogoutAction(IWorkbenchWindow window) {
		super();
		this.window = window;
		setupData();
	}

	public AbstractLogoutAction(String text, ImageDescriptor image, IWorkbenchWindow window) {
		super(text, image);
		this.window = window;
		setupData();
	}

	public AbstractLogoutAction(String text, int style, IWorkbenchWindow window) {
		super(text, style);
		this.window = window;
		setupData();
	}

	public AbstractLogoutAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		setupData();
	}

	private void setupData() {
		ILanguageManagerService languageManager = ServiceRegistry.getLanguageManagerService();
		logoutMessage = languageManager.getMessage(Activator.PLUGIN_ID, IDialogLang.LOGOUT_INFO_2, RWT.getUISession());
		logoutTitle = languageManager.getMessage(Activator.PLUGIN_ID, IDialogLang.LOGOUT_INFO_1, RWT.getUISession());
	}

	@Override
	public void run() {

		if (MessageDialog.openConfirm(window.getShell(), logoutTitle, logoutMessage)) {

			// Restart the ui (the way is dirty but works since
			// workbench.restart() doesn't work....)
			// The logout will be handled by the listener of the session
			JavaScriptExecutor executor = RWT.getClient().getService(JavaScriptExecutor.class);
			executor.execute("setTimeout(function(){window.location.reload(true);}," + REFRESH_DELAY + ");"); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}

}
