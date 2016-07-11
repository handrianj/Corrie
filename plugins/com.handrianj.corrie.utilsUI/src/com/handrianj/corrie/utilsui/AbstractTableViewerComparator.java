/**
 *
 */
package com.handrianj.corrie.utilsui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

@SuppressWarnings("serial")
public abstract class AbstractTableViewerComparator extends ViewerComparator implements ITableViewerComparator {

	private int propertyIndex = 0;
	public static final int DESCENDING = 1;
	private int direction = DESCENDING;

	@Override
	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	@Override
	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction

			if (direction == 0) {
				direction = DESCENDING;
			} else {
				direction = 1 - DESCENDING;
			}
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (direction == DESCENDING) {
			return -compareObjects(viewer, e1, e2);
		}
		return compareObjects(viewer, e1, e2);
	}

	protected int getColumnIndex() {
		return propertyIndex;
	}

	protected abstract int compareObjects(Viewer viewer, Object e1, Object e2);
}
