package com.handrianj.corrie.usermanager.editor;

import org.eclipse.jface.viewers.Viewer;

import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.utilsui.AbstractTableViewerComparator;

public class UserListComparator<U extends IUser> extends AbstractTableViewerComparator {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected int compareObjects(Viewer viewer, Object e1, Object e2) {

		U a = (U) e1;
		U b = (U) e2;

		int columnIndex = getColumnIndex();
		int result = 0;

		switch (columnIndex) {
		case 0:
			result = a.getUserId().compareTo(b.getUserId());
			break;

		case 1:
			result = a.getUserName().compareTo(b.getUserName());
			break;

		case 2:
			result = Integer.compare(a.getRoles().iterator().next(), b.getRoles().iterator().next());

			break;

		case 3:
			result = Boolean.compare(a.isActive(), b.isActive());

		default:
			result = 0;
		}

		return -result;

	}
}
