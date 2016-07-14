package org.handrianj.corrie.hermes.ui.tableviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.handrianj.corrie.hermes.datamodel.CellData;
import org.handrianj.corrie.utilsui.AbstractTableViewerComparator;

@SuppressWarnings("serial")
public class DefaultExcelComparator extends AbstractTableViewerComparator {

	private List<String> allTitles = new ArrayList<>();
	private boolean isActive = true;

	public DefaultExcelComparator(List<String> allTitles) {
		this.allTitles = new ArrayList<>(allTitles);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected int compareObjects(Viewer viewer, Object e1, Object e2) {

		if (!isActive) {
			return 0;
		}

		if ((e1 instanceof Map) && (e2 instanceof Map)) {
			Map a = (Map) e1;
			Map b = (Map) e2;

			int columnIndex = getColumnIndex();

			if (columnIndex >= allTitles.size()) {
				return 0;
			}

			String columnName = allTitles.get(columnIndex);

			Object object = a.get(columnName);
			Object object2 = b.get(columnName);

			String aValue = null;

			if (object != null) {
				aValue = ((CellData) object).getValue();
			}
			String bValue = null;

			if (object2 != null) {
				bValue = ((CellData) object2).getValue();
			}

			// If a is null
			if (aValue == null) {

				// If b is null then equality
				if (bValue == null) {
					return 0;
				}
				// if b not null then b is bigger
				else {
					return -1;
				}
			}
			// if a not null
			else {

				// if b null then a is bigger
				if (bValue == null) {
					return 1;
				}
			}

			// If no null value just compare normally
			return aValue.compareTo(bValue);
		}
		return 0;

	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
