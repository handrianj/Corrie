package com.handrianj.corrie.utilsui;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Helper for bulding a TableViewer using code
 *
 * @author Heri Andrianjafy
 *
 */
public class TableBuilerHelper {

	public static final int DEFAULT_COLUMN_SIZE = 150;

	/**
	 * Builds a table viewer inside the parent composite The parent composite
	 * should have a gridlayout
	 *
	 * @param parent
	 *            Parent composite
	 * @return the created table Viewer
	 */
	public static TableViewer buildTableViewer(Composite parent) {
		Composite tableComposite = new Composite(parent, SWT.NONE);
		tableComposite.setLayout(new GridLayout(1, false));
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewer tableViewer = new DynamicTableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		return tableViewer;
	}

	/**
	 * Adds a new TableViewerColumn to the provided table viewer
	 *
	 * @param name
	 *            Column name
	 * @param colIndex
	 *            Column index
	 * @param initialSize
	 *            Initial size of the column
	 * @param layout
	 *            TableColumnLayout used by this column
	 * @param labelProvider
	 *            LabelProvider used by this column
	 * @param tableViewer
	 *            Tableviewer using the column
	 * @param comparator
	 *            comparator used for this column, can be null
	 */
	public static TableViewerColumn addColumn(String name, int colIndex, int initialSize,
			ColumnLabelProvider labelProvider, TableViewer tableViewer, ITableViewerComparator comparator) {

		TableViewerColumn newColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn col = newColumn.getColumn();
		col.setWidth(DEFAULT_COLUMN_SIZE);
		col.setResizable(true);

		col.setText(name);
		newColumn.setLabelProvider(labelProvider);

		if (comparator != null) {
			col.addSelectionListener(createSelectionAdapter(tableViewer, comparator, col, colIndex));
		}

		return newColumn;
	}

	public static TableViewerColumn addColumn(String name, int colIndex, int initialSize,
			ColumnLabelProvider labelProvider, TableViewer tableViewer) {
		return addColumn(name, colIndex, initialSize, labelProvider, tableViewer, null);
	}

	@SuppressWarnings("serial")
	private static SelectionAdapter createSelectionAdapter(final TableViewer viewer, final ITableViewerComparator comp,
			final TableColumn col, final int colIndex) {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				comp.setColumn(colIndex);
				int dir = comp.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(col);
				viewer.refresh();
			}
		};
	}

}
