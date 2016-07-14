package org.handrianj.corrie.utilsui.dialog.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.handrianj.corrie.languagemanager.service.ILang;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerListener;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.handrianj.corrie.utilsui.dialog.Activator;
import org.handrianj.corrie.utilsui.dialog.lang.IDialogLang;

@SuppressWarnings("serial")
public class LogInDialog extends TitleAreaDialog implements ILanguageManagerListener {
	private Text login;
	private Text password;

	private ILanguageManagerService languageManagerService;
	private Label lblLogin;
	private Label lblPassword;
	private String text = "";
	private String psd = "";
	private Composite area;
	private Button button;
	private ComboViewer comboViewer;

	/**
	 * Create the dialog.
	 *
	 * @param parentShell
	 */
	public LogInDialog(Shell parentShell, ILanguageManagerService languageManagerService) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(getShellStyle() & ~SWT.CLOSE);
		this.languageManagerService = languageManagerService;

		languageManagerService.addLanguageManagerListener(this, RWT.getUISession());
	}

	/**
	 * Create contents of the dialog.
	 *
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		final UISession uiSession = RWT.getUISession();
		setDialogMessage(uiSession);
		area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		lblLogin = new Label(container, SWT.NONE);
		lblLogin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		lblLogin.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, "LogInDialog_2", uiSession));

		login = new Text(container, SWT.BORDER);
		login.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		lblPassword = new Label(container, SWT.NONE);
		GridData gd_lblPassword = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblPassword.widthHint = 70;
		lblPassword.setLayoutData(gd_lblPassword);

		lblPassword.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, "LogInDialog_3", uiSession));

		password = new Text(container, SWT.BORDER | SWT.PASSWORD);
		password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		new Label(container, SWT.NONE);

		comboViewer = new ComboViewer(container, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));

		comboViewer.setContentProvider(new IStructuredContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// nothing to do

			}

			@Override
			public void dispose() {
				// nothing to do

			}

			@Override
			public Object[] getElements(Object inputElement) {

				return ServiceRegistry.getLanguageManagerService().getAvailableLanguages().toArray();
			}
		});

		comboViewer.setLabelProvider(new ILabelProvider() {

			@Override
			public void removeListener(ILabelProviderListener listener) {
				// nothing to do

			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				// nothing to do
				return false;
			}

			@Override
			public void dispose() {
				// nothing to do

			}

			@Override
			public void addListener(ILabelProviderListener listener) {
				// nothing to do

			}

			@Override
			public String getText(Object element) {

				if (element instanceof ILang) {
					ILang currentKey = (ILang) element;
					return currentKey.getName();
				}
				return null;
			}

			@Override
			public Image getImage(Object element) {

				return null;
			}
		});

		comboViewer.setInput(new Object());
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) comboViewer.getSelection();
				Object firstElement = selection.getFirstElement();

				languageManagerService.setCurrentLanguage((ILang) firstElement, uiSession);

			}
		});
		ILang firstLanguage = ServiceRegistry.getLanguageManagerService().getAvailableLanguages().get(0);
		comboViewer.setSelection(new StructuredSelection(firstLanguage));

		return area;
	}

	private void setDialogMessage(UISession uiSession) {
		String applicationName = ServiceRegistry.getApplicationService().getAllAvailableApplications().get(0);
		setTitle(applicationName
				+ languageManagerService.getMessage(Activator.PLUGIN_ID, IDialogLang.LOG_IN_DIALOG_0, uiSession));
		String message = languageManagerService.getMessage(Activator.PLUGIN_ID, IDialogLang.LOG_IN_DIALOG_1, uiSession)
				+ applicationName
				+ languageManagerService.getMessage(Activator.PLUGIN_ID, IDialogLang.LOG_IN_DIALOG_2, uiSession);
		setMessage(message);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 350);
	}

	@Override
	public void languageChanged() {

		UISession uiSession = RWT.getUISession();
		setDialogMessage(uiSession);

		if (lblLogin != null) {
			lblLogin.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, "LogInDialog_2", uiSession));
		}

		if (lblPassword != null) {
			lblPassword.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, "LogInDialog_3", uiSession));
		}
		if (button != null) {
			button.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, IDialogLang.LOGIN, uiSession));
		}
		if (area != null) {
			area.layout(true);
			// area.layout(true);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		UISession uiSession = RWT.getUISession();
		button = createButton(parent, IDialogConstants.OK_ID,
				languageManagerService.getMessage(Activator.PLUGIN_ID, IDialogLang.LOGIN, uiSession), true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

	}

	public String getLogin() {
		return text;
	}

	public String getPassword() {
		return psd;
	}

	@Override
	public boolean close() {
		text = login.getText();
		psd = password.getText();

		lblLogin.dispose();
		lblLogin = null;
		lblPassword.dispose();
		lblPassword = null;

		login.dispose();
		login = null;
		password.dispose();
		password = null;

		button.dispose();
		button = null;

		// comboViewer.getControl().dispose();
		comboViewer = null;

		area.dispose();
		area = null;

		languageManagerService.removeLanguageManagerListener(this, RWT.getUISession());
		languageManagerService = null;
		return super.close();
	}

}
