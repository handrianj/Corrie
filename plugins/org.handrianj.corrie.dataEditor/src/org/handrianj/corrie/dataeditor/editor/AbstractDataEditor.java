package org.handrianj.corrie.dataeditor.editor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.handrianj.corrie.dataeditor.Activator;
import org.handrianj.corrie.dataeditor.editor.dialog.SearchTableDialog;
import org.handrianj.corrie.dataeditor.editor.input.IDataEditorInput;
import org.handrianj.corrie.dataeditor.lang.IDataEditorLang;
import org.handrianj.corrie.dblink.impl.CoreTableData;
import org.handrianj.corrie.dblink.model.ITableStructure;
import org.handrianj.corrie.editors.util.editors.AbstractCorrieEditor;
import org.handrianj.corrie.editors.util.editors.ICorrieEditorInput;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.handrianj.corrie.utilsui.TableBuilerHelper;

/**
 * Abstract UI class to edit an IUser. This class will provide a search bar as
 * well as a user editor
 *
 * @author Heri Andrianjafy
 *
 */
public abstract class AbstractDataEditor extends AbstractCorrieEditor<List<ITableStructure>> {
	private TableViewer tableViewer;
	private Composite compositeWidget;
	private ScrolledComposite scrolledComposite;

	private Map<String, Control> colNameToWidget = new HashMap<>();
	private Text currentTable;
	private ITableStructure currentTableSelected;
	private ILanguageManagerService languageManagerService;
	private String formatErrorMessage;

	public AbstractDataEditor() {
	}

	@Override
	public void languageChanged() {
		// nothing to do for now

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IDataEditorInput) {
			setSite(site);
			setInput(input);
		} else {
			throw new PartInitException("Invalid Input: Must be IDataEditorInput"); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("serial")
	@Override
	public void createPartControl(Composite parent) {

		languageManagerService = ServiceRegistry.getLanguageManagerService();
		formatErrorMessage = languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.WRONG_FORMAT,
				RWT.getUISession());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		setPartName(languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.EDITOR_TITLE,
				RWT.getUISession()));
		Composite compositeSelector = new Composite(composite, SWT.NONE);
		compositeSelector.setLayout(getZeroMarginLayout(2, false));
		compositeSelector.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button lblSelectTable = new Button(compositeSelector, SWT.NONE);
		lblSelectTable.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSelectTable.setText(languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.SELECT_TABLE,
				RWT.getUISession()));
		lblSelectTable.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ICorrieEditorInput<List<ITableStructure>> input = getInput();
				SearchTableDialog dialog = new SearchTableDialog(getSite());
				if (dialog.open(input.getData()) == org.eclipse.jface.dialogs.Dialog.OK) {
					ITableStructure selection = dialog.getSelection();

					if (selection != null) {
						updateWidgetStructure(selection);
						currentTable.setText(selection.getTableName());
						currentTableSelected = selection;
					}

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do

			}
		});

		currentTable = new Text(compositeSelector, SWT.READ_ONLY);
		currentTable.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		Composite compositeItems = new Composite(composite, SWT.NONE);
		compositeItems.setLayout(getZeroMarginLayout(2, true));
		compositeItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		scrolledComposite = new ScrolledComposite(compositeItems, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		compositeWidget = new Composite(scrolledComposite, SWT.NONE);
		compositeWidget.setLayout(getZeroMarginLayout(1, false));
		scrolledComposite.setContent(compositeWidget);
		scrolledComposite.setMinSize(compositeWidget.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite compositeTable = new Composite(compositeItems, SWT.NONE);
		compositeTable.setLayout(getZeroMarginLayout(1, false));
		compositeTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));

		tableViewer = TableBuilerHelper.buildTableViewer(compositeTable);

		Composite compositeButton = new Composite(compositeItems, SWT.NONE);
		compositeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		compositeButton.setLayout(getZeroMarginLayout(3, false));

		Button addValue = new Button(compositeButton, SWT.NONE);
		addValue.addSelectionListener(createAddListener());

		addValue.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		addValue.setText(
				languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.ADD_BTN, RWT.getUISession()));

		Button btnUpdate = new Button(compositeButton, SWT.NONE);
		btnUpdate.addSelectionListener(createUpdateListener());
		btnUpdate.setText(
				languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.UPDATE_BTN, RWT.getUISession()));
		btnUpdate.setEnabled(false);

		Button btnRemove = new Button(compositeButton, SWT.NONE);
		btnRemove.addSelectionListener(createDeleteListener());
		btnRemove.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText(
				languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.REMOVE_BTN, RWT.getUISession()));

		tableViewer.setContentProvider(createTableValuesContentProvider());
		tableViewer.addSelectionChangedListener(createTableSelectionListener(btnUpdate));

		setPartName(languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.EDITOR_TITLE,
				RWT.getUISession()));
	}

	private SelectionAdapter createUpdateListener() {
		return new SelectionAdapter() {
			/**
			 *
			 */
			private static final long serialVersionUID = 711663414648716093L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				Map<String, String> allValues = getAllValues();

				if (allValues != null) {
					update(currentTableSelected.getTableName(), allValues);
					tableViewer.refresh();
				}
			}
		};
	}

	private SelectionAdapter createDeleteListener() {
		return new SelectionAdapter() {
			/**
			 *
			 */
			private static final long serialVersionUID = -3111929800987900018L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				delete(currentTableSelected.getTableName(),
						(CoreTableData) tableViewer.getStructuredSelection().getFirstElement());
				tableViewer.refresh();
			}
		};
	}

	private SelectionAdapter createAddListener() {
		return new SelectionAdapter() {
			/**
			 *
			 */
			private static final long serialVersionUID = -7401820872529594405L;

			@Override
			public void widgetSelected(SelectionEvent e) {

				Map<String, String> allValues = getAllValues();

				if (getAllValues() != null) {
					add(currentTableSelected, allValues);
					tableViewer.refresh();
				}
			}
		};
	}

	@SuppressWarnings("serial")
	private void updateWidgetStructure(ITableStructure tableStructure) {
		Collection<String> columnsName = tableStructure.getColumnsName();

		for (Control childrendCompo : compositeWidget.getChildren()) {
			childrendCompo.dispose();
		}

		for (String colName : columnsName) {
			Class<?> typeForColumn = tableStructure.getTypeForColumn(colName);

			boolean columnMandatory = tableStructure.isColumnMandatory(colName);

			Label lblString = new Label(compositeWidget, SWT.NONE);
			if (columnMandatory) {
				lblString.setText(colName + " * ");
			} else {
				lblString.setText(colName);
			}

			Control control = null;
			if (typeForColumn.isAssignableFrom(String.class) || typeForColumn.isAssignableFrom(Long.class)) {
				control = new Text(compositeWidget, SWT.BORDER);
				control.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			}

			if (typeForColumn.isAssignableFrom(Integer.class)) {
				control = new Spinner(compositeWidget, SWT.BORDER);
				control.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, true, false, 1, 1));
			}

			if (typeForColumn.isAssignableFrom(Date.class)) {
				control = new DateTime(compositeWidget, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				control.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			}

			colNameToWidget.put(colName, control);

			TableColumn[] columns = tableViewer.getTable().getColumns();

			for (TableColumn tableColumn : columns) {
				tableColumn.dispose();
			}

			for (String currentColName : columnsName) {
				TableBuilerHelper.addColumn(currentColName, 0, TableBuilerHelper.DEFAULT_COLUMN_SIZE,
						new ColumnLabelProvider() {
							@Override
							public String getText(Object element) {
								if (element instanceof CoreTableData) {
									Object value = ((CoreTableData) element).getValue(currentColName);

									if (value != null) {
										return value.toString();
									}
								}

								return "";
							}
						}, tableViewer);
			}

		}

		compositeWidget.layout();

		scrolledComposite.setMinSize(compositeWidget.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.layout(true);

		tableViewer.setInput(tableStructure);
		tableViewer.refresh();

	}

	private ISelectionChangedListener createTableSelectionListener(Button btnUpdate) {
		return new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				IStructuredSelection selection = (IStructuredSelection) event.getSelection();

				btnUpdate.setEnabled(true);
				Calendar calendar = new GregorianCalendar();

				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof CoreTableData) {

					CoreTableData coreTableData = (CoreTableData) firstElement;

					for (Entry<String, Control> entry : colNameToWidget.entrySet()) {

						Control control = entry.getValue();

						String columnName = entry.getKey();

						Object value = coreTableData.getValue(columnName);

						if (value instanceof String) {
							((Text) control).setText((String) value);
						}

						if (value instanceof Long) {
							((Text) control).setText(Long.toString((long) value));
						}

						if (value instanceof Integer) {
							((Spinner) control).setValues((int) value, 0, 1000, 0, 1, 1);
						}

						if (value instanceof Timestamp) {

							calendar.setTimeInMillis(((Timestamp) value).getTime());
							((DateTime) control).setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
									calendar.get(Calendar.DAY_OF_MONTH));
							((DateTime) control).redraw();
						}

					}

				}

			}
		};
	}

	/**
	 * Returns all the values stored
	 *
	 * @return
	 */
	private Map<String, String> getAllValues() {
		Map<String, String> map = new HashMap<>();
		Calendar calendar = new GregorianCalendar();

		for (Entry<String, Control> currentEntry : colNameToWidget.entrySet()) {

			String colName = currentEntry.getKey();

			Control value = currentEntry.getValue();

			Class<?> typeForColumn = currentTableSelected.getTypeForColumn(colName);

			if (value instanceof Text) {
				Text textValue = (Text) value;
				UISession uiSession = RWT.getUISession();
				if (textValue.getText().isEmpty() && currentTableSelected.isColumnMandatory(colName)) {
					MessageDialog.openError(Display.getCurrent().getActiveShell(),
							languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.ERROR, uiSession),
							languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.MANDATORY, uiSession)
									+ colName);
					return null;
				}

				if (typeForColumn.isAssignableFrom(Long.class)) {

					try {
						Long.parseLong(textValue.getText());
					} catch (NumberFormatException cast) {

						MessageDialog.openError(Display.getCurrent().getActiveShell(),
								languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.ERROR,
										uiSession),
								formatErrorMessage + colName
										+ languageManagerService.getMessage(Activator.PLUGIN_ID,
												IDataEditorLang.EXPECTED, uiSession)
										+ languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.BIG,
												uiSession));

						return null;
					}

				}

				if (typeForColumn.isAssignableFrom(Integer.class)) {

					try {
						Integer.parseInt(textValue.getText());
					} catch (NumberFormatException cast) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(),
								languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.ERROR,
										uiSession),
								formatErrorMessage + colName
										+ languageManagerService.getMessage(Activator.PLUGIN_ID,
												IDataEditorLang.EXPECTED, uiSession)
										+ languageManagerService.getMessage(Activator.PLUGIN_ID,
												IDataEditorLang.INTEGER, uiSession));

						return null;
					}

				}

				if (typeForColumn.isAssignableFrom(Short.class)) {

					try {
						Short.parseShort(textValue.getText());
					} catch (NumberFormatException cast) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(),
								languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.ERROR,
										uiSession),
								formatErrorMessage + colName
										+ languageManagerService.getMessage(Activator.PLUGIN_ID,
												IDataEditorLang.EXPECTED, uiSession)
										+ languageManagerService.getMessage(Activator.PLUGIN_ID, IDataEditorLang.SHORT,
												uiSession));

						return null;
					}

				}

				map.put(colName, textValue.getText());
			}

			if (value instanceof Spinner) {

				map.put(colName, String.valueOf(((Spinner) value).getSelection()));
			}

			if (value instanceof DateTime) {
				DateTime dateTime = ((DateTime) value);
				calendar.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
				Timestamp stamp = new Timestamp(calendar.getTimeInMillis());
				map.put(colName, stamp.toString());
			}

		}

		return map;

	}

	@Override
	public void setFocus() {
		scrolledComposite.setFocus();
	}

	protected Control getWidgetFor(String colName) {
		return colNameToWidget.get(colName);
	}

	/**
	 * Returns the content provider to be used by the combo
	 *
	 * @return
	 */
	protected abstract IStructuredContentProvider createAllTablesComboContentProvider();

	/**
	 * Returns the content provider to be used by the tableViewer.
	 *
	 * @return
	 */
	protected abstract IStructuredContentProvider createTableValuesContentProvider();

	/**
	 * Used to add data into db
	 *
	 * @param values
	 * @param currentTableSelected2
	 */
	protected abstract void add(ITableStructure currentTableSelected, Map<String, String> values);

	/**
	 * Used to remove data into db
	 *
	 * @param tableName
	 *
	 * @param values
	 * @param currentTableSelected
	 */
	protected abstract void delete(String tableName, CoreTableData selectedRow);

	/**
	 * Updates data in db
	 */
	protected abstract void update(String tableName, Map<String, String> values);

}
