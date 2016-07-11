package com.handrianj.corrie.usermanager.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.editors.util.editors.AbstractCorrieEditor;
import com.handrianj.corrie.editors.util.editors.AbstractCorrieEditorInput;
import com.handrianj.corrie.editors.util.editors.ICorrieEditorInput;
import com.handrianj.corrie.editors.util.factories.IEditorInputFactory;
import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.serviceregistry.ServiceRegistry;
import com.handrianj.corrie.usermanager.Lang.IUserManagerLang;
import com.handrianj.corrie.utilsui.TableBuilerHelper;

public abstract class UserManagerEditor<U extends IUser> extends AbstractCorrieEditor<List<U>> {

	public UserManagerEditor() {
	}

	public static final String ID = "com.capsa.leads.leadsusermanager.editor.LeadsUserManagerEditor";

	private Text txtUserCode;
	private Text txtUserName;
	private Text txtUserNameUpdate;
	private Text txtPassWordUpdate;
	private Label lblUserName;
	private Label lblUserCode;
	private Label lblUserNameUpdate;
	private Label lblPassWordUpdate;
	private Button btnSearch;
	private Button btnUserInsert;
	private Button btnUserUpdate;
	private Button btnUserDelete;
	private TableViewer tableViewer;
	private ILanguageManagerService languageManagerService;
	private Composite buttonsComposite;
	private Button btnCheckUserActive;
	private Button btnCheckButton;

	private Map<Integer, Button> rolesCheckboxes = new HashMap<>();
	private List<Button> searchCheckBoxes = new ArrayList<>();
	private Composite additionalComponents;
	private Composite compositeAdditionalSearch;
	private ScrolledComposite scrolledComposite;

	public static String getId() {
		return ID;
	}

	public ILanguageManagerService getLanguageManagerService() {
		return languageManagerService;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void languageChanged() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IUserManagerEditorInput) {
			setSite(site);
			setInput(input);
		}

	}

	public String getUserCode() {
		return txtUserCode.getText();
	}

	public String getUserName() {
		return txtUserName.getText();
	}

	public String getUserNameUpdate() {
		return txtUserNameUpdate.getText();
	}

	public void setUserNameUpdate(String userName) {
		txtUserNameUpdate.setText(userName);
	}

	public String getPassWordUpdate() {
		return txtPassWordUpdate.getText();
	}

	public void setPassWordUpdate(String passWord) {
		txtPassWordUpdate.setText(passWord);
	}

	public void setbtnSearchEnable(boolean a) {
		btnSearch.setEnabled(a);
	}

	public void setbtnUserInsertEnable(boolean b) {
		btnUserInsert.setEnabled(b);
	}

	public void setbtnUserUpdateEnable(boolean c) {
		btnUserUpdate.setEnabled(c);
	}

	public void setbtnUserDeleteEnable(boolean d) {
		btnUserDelete.setEnabled(d);
	}

	public void setbtnUserFreezeSelection(boolean f) {
		btnCheckUserActive.setSelection(f);
	}

	public void settxtPassWordUpdate(String password) {
		txtPassWordUpdate.setText(password);
	}

	public boolean getbtnUserFreezeSelection() {
		return btnCheckUserActive.getSelection();
	}

	public ISelection getTableViewerSelection() {
		ISelection selection = tableViewer.getSelection();
		return selection;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		UserListComparator userListComparator = createComparator();

		languageManagerService = ServiceRegistry.getLanguageManagerService();
		setPartName(
				languageManagerService.getMessage(getPluginId(), IUserManagerLang.USER_MANAGER, RWT.getUISession()));

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = layout.marginWidth = 0;
		composite.setLayout(layout);

		Group searchComposite = new Group(composite, SWT.NONE);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_searchComposite = new GridLayout(1, false);
		gl_searchComposite.marginHeight = gl_searchComposite.marginWidth = 0;
		searchComposite.setLayout(gl_searchComposite);
		searchComposite.setText(getLanguageText(IUserManagerLang.SEARCH));
		Composite composite_contentSearch = new Composite(searchComposite, SWT.NONE);
		composite_contentSearch.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
		GridLayout layout3 = new GridLayout(4, false);
		layout3.marginHeight = layout3.marginWidth = 0;
		composite_contentSearch.setLayout(layout3);
		lblUserCode = new Label(composite_contentSearch, SWT.NONE);
		lblUserCode.setText(getLanguageText(IUserManagerLang.USER_CODE));

		txtUserCode = new Text(composite_contentSearch, SWT.BORDER);
		txtUserCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblUserName = new Label(composite_contentSearch, SWT.NONE);
		lblUserName.setText(getLanguageText(IUserManagerLang.USER_NAME));

		txtUserName = new Text(composite_contentSearch, SWT.BORDER);
		txtUserName.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

		compositeAdditionalSearch = new Composite(composite_contentSearch, SWT.NONE);
		GridLayout layout9 = new GridLayout(4, false);
		layout9.marginHeight = layout9.marginWidth = 0;
		compositeAdditionalSearch.setLayout(layout9);
		compositeAdditionalSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		createAdditionalSearchComponents(compositeAdditionalSearch);

		Group compositeRoles = new Group(composite_contentSearch, SWT.NONE);
		compositeRoles.setLayout(new RowLayout(SWT.HORIZONTAL));
		compositeRoles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
		compositeRoles.setText(getLanguageText(IUserManagerLang.USER_ROLE));

		// Dynamically create the roles checkbox
		IRoleItem[] rolesName = getRoleItems();

		for (IRoleItem currentRole : rolesName) {
			Button currentRoleSearchButton = new Button(compositeRoles, SWT.CHECK);
			currentRoleSearchButton.setText(currentRole.getName());
			currentRoleSearchButton.setData(currentRole);
			searchCheckBoxes.add(currentRoleSearchButton);
		}

		Composite composite_SearchExecution = new Composite(searchComposite, SWT.NONE);
		composite_SearchExecution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout layout4 = new GridLayout(1, false);
		layout4.marginHeight = layout4.marginWidth = 0;
		composite_SearchExecution.setLayout(layout4);

		btnSearch = new Button(composite_SearchExecution, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText(getLanguageText(IUserManagerLang.SEARCH));
		btnSearch.addSelectionListener(createSearchListener());

		scrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite editionComposite = new Composite(scrolledComposite, SWT.NONE);
		editionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		GridLayout layout5 = new GridLayout(2, false);
		layout5.marginHeight = layout5.marginWidth = 10;
		editionComposite.setLayout(layout5);

		lblUserNameUpdate = new Label(editionComposite, SWT.NONE);
		lblUserNameUpdate.setText(getLanguageText(IUserManagerLang.USER_NAME));

		txtUserNameUpdate = new Text(editionComposite, SWT.BORDER);
		txtUserNameUpdate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblPassWordUpdate = new Label(editionComposite, SWT.NONE);
		lblPassWordUpdate.setText(getLanguageText(IUserManagerLang.PASSWORD));

		txtPassWordUpdate = new Text(editionComposite, SWT.BORDER);
		txtPassWordUpdate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		GridLayout layoutAdditional = new GridLayout(2, false);
		layoutAdditional.marginHeight = layoutAdditional.marginWidth = 0;
		additionalComponents = new Composite(editionComposite, SWT.NONE);
		additionalComponents.setLayout(layoutAdditional);
		additionalComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		createAdditionalEditComponents(additionalComponents);

		btnCheckUserActive = new Button(editionComposite, SWT.CHECK);
		btnCheckUserActive.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnCheckUserActive.setText(getLanguageText(IUserManagerLang.ACTIVE));
		btnCheckUserActive.setSelection(true);

		Group rolesComposite = new Group(editionComposite, SWT.NONE);
		GridLayout rolesLayout = new GridLayout(3, false);
		rolesLayout.marginHeight = rolesLayout.marginWidth = 0;
		rolesComposite.setLayout(rolesLayout);
		rolesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		rolesComposite.setText(getLanguageText(IUserManagerLang.ROLE_SELECTION));

		for (IRoleItem item : rolesName) {
			btnCheckButton = new Button(rolesComposite, SWT.CHECK);
			btnCheckButton.setText(item.getName());
			btnCheckButton.setData(item.getId());
			rolesCheckboxes.put(item.getId(), btnCheckButton);
		}
		new Label(editionComposite, SWT.NONE);
		new Label(editionComposite, SWT.NONE);

		buttonsComposite = new Composite(editionComposite, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		GridLayout layout6 = new GridLayout(3, false);
		layout6.marginHeight = layout6.marginWidth = 0;
		buttonsComposite.setLayout(layout6);

		btnUserInsert = new Button(buttonsComposite, SWT.NONE);
		btnUserInsert.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnUserInsert.setText(getLanguageText(IUserManagerLang.INSERT));
		btnUserInsert.setEnabled(true);
		btnUserInsert.addSelectionListener(createInsertListener());

		btnUserUpdate = new Button(buttonsComposite, SWT.NONE);
		btnUserUpdate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnUserUpdate.setText(getLanguageText(IUserManagerLang.UPDATE));
		btnUserUpdate.setEnabled(false);
		btnUserUpdate.addSelectionListener(createUpdateListener());

		btnUserDelete = new Button(buttonsComposite, SWT.NONE);
		btnUserDelete.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnUserDelete.setText(getLanguageText(IUserManagerLang.DELETE));
		btnUserDelete.setEnabled(false);
		btnUserDelete.addSelectionListener(createDeleteListener());

		scrolledComposite.setContent(editionComposite);
		scrolledComposite.setMinSize(editionComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite table_composite = new Composite(composite, SWT.NONE);
		GridLayout layout7 = new GridLayout(1, false);
		layout7.marginHeight = layout7.marginWidth = 0;
		table_composite.setLayout(layout7);
		table_composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tableViewer = TableBuilerHelper.buildTableViewer(table_composite);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		TableBuilerHelper.addColumn(getLanguageText(IUserManagerLang.USER_CODE), 0, 150, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IUser) {
					U user = (U) element;
					return user.getUserId();
				}
				return super.getText(element);
			}
		}, tableViewer, userListComparator);

		TableBuilerHelper.addColumn(getLanguageText(IUserManagerLang.USER_NAME), 1, 100, new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				if (element instanceof IUser) {
					U user = (U) element;
					return user.getUserName();
				}
				return super.getText(element);
			}
		}, tableViewer, userListComparator);

		TableBuilerHelper.addColumn(getLanguageText(IUserManagerLang.USER_ROLE), 2, 100, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IUser) {
					return getRoleName((U) element);
				}
				return super.getText(element);
			}
		}, tableViewer, userListComparator);

		TableBuilerHelper.addColumn(getLanguageText(IUserManagerLang.ACTIVE), 3, 100, new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IUser) {
					U user = (U) element;
					return getUserActive((U) element);
				}
				return super.getText(element);
			}
		}, tableViewer, userListComparator);

		addTableColumns(tableViewer, userListComparator);
		tableViewer.setContentProvider(getTableContentProvider());
		tableViewer.setInput(getInput());
		tableViewer.setComparator(userListComparator);
		tableViewer.addSelectionChangedListener(createSelectionListener());

	}

	public String getLanguageText(String key) {
		return languageManagerService.getMessage(getPluginId(), key, RWT.getUISession());
	}

	@Override
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Override
	public void updateInput(ICorrieEditorInput<List<U>> input) {
		setInput(input);
		tableViewer.setInput(input);
		tableViewer.refresh();

	}

	/**
	 * Returns a default comparator subclasses should override this method with
	 * their own comparator
	 *
	 * @return
	 */
	protected UserListComparator<U> createComparator() {
		return new UserListComparator<U>();
	};

	/**
	 * Returns the list of the selected roles for edition
	 *
	 * @return
	 */
	protected Integer[] getSelectedRolesId() {

		List<Integer> selection = new ArrayList<>();

		for (Button currentButton : rolesCheckboxes.values()) {
			if (currentButton.getSelection()) {

				selection.add((Integer) currentButton.getData());

			}
		}

		return selection.toArray(new Integer[selection.size()]);

	}

	/**
	 * check if role checkbox is empty
	 *
	 * @return
	 */
	protected boolean validateRoleCheckBox() {
		boolean validCheckBox = false;
		if (rolesCheckboxes.isEmpty()) {
			validCheckBox = false;
		}
		for (Button currentButton : rolesCheckboxes.values()) {
			if (currentButton.getSelection()) {
				validCheckBox = true;
			}
		}
		return validCheckBox;
	}

	/**
	 * Returns the list of selected role for search
	 *
	 * @return
	 */
	protected Integer[] getSearchRolesID() {
		List<Integer> selection = new ArrayList<>();

		for (Button currentBox : searchCheckBoxes) {

			if (currentBox.getSelection()) {

				selection.add(((IRoleItem) currentBox.getData()).getId());
			}

		}

		return selection.toArray(new Integer[selection.size()]);

	}

	protected void setSelectedRolesId(Integer[] allRoles) {

		for (Button value : rolesCheckboxes.values()) {
			value.setSelection(false);
		}

		for (int i : allRoles) {
			rolesCheckboxes.get(i).setSelection(true);
		}

	}

	/**
	 * Returns the content provider of the table
	 *
	 * @return
	 */
	protected abstract IContentProvider getTableContentProvider();

	/**
	 * Creates the search listener of the editor
	 *
	 * @param comboViewer
	 *            The role combo viewer
	 * @return
	 */
	protected abstract SelectionListener createSearchListener();

	/**
	 * Creates the update listener for the editor
	 *
	 * @return
	 */
	protected abstract SelectionListener createUpdateListener();

	/**
	 * Creates the insert listener for the editor
	 *
	 * @return
	 */
	protected abstract SelectionListener createInsertListener();

	/**
	 * Creates the selection listener for the table viewer
	 *
	 * @return
	 */
	public abstract ISelectionChangedListener createSelectionListener();

	/**
	 * Creates the delete listener
	 *
	 * @return
	 */
	protected abstract SelectionListener createDeleteListener();

	/**
	 * Returns the content provider of the combobox containing the roles
	 *
	 * @return
	 */
	protected abstract IStructuredContentProvider getContentProvider();

	/**
	 * Returns the label provider for the combobox containing the roles
	 *
	 * @return
	 */
	protected abstract IBaseLabelProvider getLabelProvider();

	/**
	 * Returns the editor input
	 *
	 * @return
	 */
	public abstract AbstractCorrieEditorInput<U> getUserEditorInput();

	/**
	 * Returns the input factory of the editor
	 *
	 * @return
	 */
	public abstract IEditorInputFactory<U> getUserEditorInputFactory();

	/**
	 * Returns the rolename of the user
	 *
	 * @param user
	 * @return
	 */
	public abstract String getRoleName(U user);

	/**
	 * Returns the Role status of the user
	 *
	 * @param user
	 * @return
	 */
	public abstract String getUserActive(U user);

	/**
	 * Returns the plugin ID
	 *
	 * @return
	 */
	public abstract String getPluginId();

	/**
	 * Checks the box depending on the user status
	 *
	 * @param user
	 * @return
	 */
	public abstract boolean checkBoxSelection(U user);

	/**
	 * Adding columns to the end of the tableviewer
	 *
	 * @param tableViewer
	 * @param userListComparator
	 */
	protected abstract void addTableColumns(TableViewer tableViewer, UserListComparator<U> userListComparator);

	/**
	 * Returs the roles as an array of IRoleItem
	 *
	 * @return
	 */
	public abstract IRoleItem[] getRoleItems();

	/**
	 * Creates additional components needed by the subclasses (if you have a
	 * user that has more attributes than the standard IUser you can create the
	 * missing components here)
	 *
	 * @param additionalComponents
	 */
	protected abstract void createAdditionalEditComponents(Composite additionalComponents);

	/**
	 * Creates additional components needed for the search by the subclasses (if
	 * you have a user that has more attributes than the standard IUser you can
	 * create the missing search components here)
	 *
	 * @param compositeAdditionalSearch
	 */
	protected abstract void createAdditionalSearchComponents(Composite compositeAdditionalSearch);

}
