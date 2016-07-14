package org.handrianj.corrie.dataeditor.editor.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredList;
import org.handrianj.corrie.dataeditor.Activator;
import org.handrianj.corrie.dataeditor.lang.IDataEditorLang;
import org.handrianj.corrie.dblink.model.ITableStructure;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;

@SuppressWarnings("serial")
public class SearchTableDialog extends Dialog {

	private Text text;
	private List<ITableStructure> data;
	private FilteredList filtered;
	private Object lastSelection;

	public SearchTableDialog(IShellProvider parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite createDialogArea = (Composite) super.createDialogArea(parent);

		createDialogArea.setLayout(new GridLayout(1, false));

		text = new Text(createDialogArea, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		filtered = new FilteredList(createDialogArea, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, new ListLabelProvider(),
				true, false, true);
		filtered.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		filtered.setElements(data.toArray());
		if (!data.isEmpty()) {
			filtered.setSelection(new Object[] { data.get(0) });
		}
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent event) {
				filtered.setFilter(text.getText());

			}
		});

		filtered.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object[] selection = filtered.getSelection();
				if (selection.length > 0) {
					lastSelection = selection[0];
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do

			}
		});
		return createDialogArea;
	}

	public int open(java.util.List<ITableStructure> data) {
		this.data = data;

		return super.open();

	}

	// overriding this methods allows you to set the
	// title of the custom dialog
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(ServiceRegistry.getLanguageManagerService().getMessage(Activator.PLUGIN_ID,
				IDataEditorLang.SEARCH_DIALOG, RWT.getUISession()));
	}

	public ITableStructure getSelection() {
		return (ITableStructure) lastSelection;
	}
}
