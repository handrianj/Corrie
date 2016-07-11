package com.handrianj.corrie.utilsui.dialog.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.utilsui.dialog.Activator;
import com.handrianj.corrie.utilsui.dialog.lang.IDialogLang;

@SuppressWarnings("serial")
public class UpdatePasswordDialog extends TitleAreaDialog {
	private static final int MINIMUM_PWD_SIZE = 6;

	private ILanguageManagerService languageService;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private boolean force;
	private Composite area;
	private Composite container;
	private Label lblCurrentPassword;
	private Label lblNewPassword;
	private Label lblConfirmPassword;
	private Text txtCurrentpassword;
	private Text txtNewpassword;
	private Text txtConfirmpassword;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 * @param service
	 * @param force
	 */
	public UpdatePasswordDialog(Shell parentShell, ILanguageManagerService service, boolean force) {
		super(parentShell);
		this.languageService = service;
		this.force = force;
		if (force) {
			setShellStyle(getShellStyle() & ~SWT.CLOSE);
		}
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		UISession uiSession = RWT.getUISession();

		setTitle(languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.CHANGE_PASSWORD, uiSession));
		setMessage(languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.INPUTPASSWORDS, uiSession));
		area = (Composite) super.createDialogArea(parent);
		container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		lblCurrentPassword = new Label(container, SWT.NONE);
		lblCurrentPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCurrentPassword
				.setText(languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.CURRENT_PASSWORD, uiSession));

		txtCurrentpassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtCurrentpassword.setText("");
		txtCurrentpassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblNewPassword = new Label(container, SWT.NONE);
		lblNewPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewPassword.setText(languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.NEW_PASSWORD, uiSession));

		txtNewpassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtNewpassword.setText("");
		txtNewpassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblConfirmPassword = new Label(container, SWT.NONE);
		lblConfirmPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblConfirmPassword
				.setText(languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.CONFIRM_PASSWORD, uiSession));

		txtConfirmpassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtConfirmpassword.setText("");
		txtConfirmpassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID,
				languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.OK, RWT.getUISession()), true);

		if (!force) {
			createButton(parent, IDialogConstants.CANCEL_ID,
					languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.CANCEL, RWT.getUISession()), false);
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	public boolean close() {

		oldPassword = txtCurrentpassword.getText();
		newPassword = txtNewpassword.getText();

		confirmPassword = txtConfirmpassword.getText();

		lblCurrentPassword.dispose();
		lblCurrentPassword = null;

		lblNewPassword.dispose();
		lblNewPassword = null;

		lblConfirmPassword.dispose();
		lblConfirmPassword = null;

		txtConfirmpassword.dispose();
		txtConfirmpassword = null;

		txtNewpassword.dispose();
		txtNewpassword = null;

		txtCurrentpassword.dispose();
		txtCurrentpassword = null;

		area.dispose();
		area = null;
		container.dispose();
		container = null;

		return super.close();
	}

	public boolean isNewPasswordOkay() {

		if (newPassword.length() < MINIMUM_PWD_SIZE) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_ERRO, RWT.getUISession()),
					languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.TOO_SHORT, RWT.getUISession()));
			return false;
		}

		if (newPassword.compareTo(confirmPassword) != 0) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_ERRO, RWT.getUISession()),
					languageService.getMessage(Activator.PLUGIN_ID, IDialogLang.PASSWORD_NOT_MATCH,
							RWT.getUISession()));
			return false;
		}

		return true;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

}
