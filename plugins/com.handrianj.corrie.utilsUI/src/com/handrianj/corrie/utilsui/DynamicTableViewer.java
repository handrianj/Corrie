package com.handrianj.corrie.utilsui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Table viewer with columns resizing depending on the content
 *
 * @author Heri Andrianjafy
 *
 */
@SuppressWarnings("serial")
public class DynamicTableViewer extends TableViewer {

	public DynamicTableViewer(Composite parent, int style) {
		super(parent, style);
	}

	public DynamicTableViewer(Composite parent) {
		super(parent);
	}

	public DynamicTableViewer(Table table) {
		super(table);
	}

	@Override
	public void refresh() {

		super.refresh();

		for (TableColumn column : getTable().getColumns()) {

			column.pack();
		}
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);

		for (TableColumn column : getTable().getColumns()) {

			column.pack();
		}
	}
}
