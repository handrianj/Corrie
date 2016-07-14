package org.handrianj.corrie.overview.ui;

import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.handrianj.corrie.datamodel.structure.impl.TreeStructure;
import org.handrianj.corrie.overview.internal.EditorData;
import org.handrianj.corrie.overview.internal.MenuData;

public class OverviewComparator extends ViewerComparator {

	/**
	 *
	 */
	private static final long serialVersionUID = 9157582312631746350L;

	public OverviewComparator() {
		super(new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {

				return arg0.compareTo(arg1);
			};
		});
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int result = 1;
		if ((e1 instanceof TreeStructure) && (e2 instanceof TreeStructure)) {

			TreeStructure a = (TreeStructure) e1;
			TreeStructure b = (TreeStructure) e2;

			Object aTreeValue = a.getValue();
			Object bTreeValue = b.getValue();
			if ((aTreeValue instanceof MenuData) && (bTreeValue instanceof MenuData)) {
				MenuData aValue = (MenuData) aTreeValue;
				MenuData bValue = (MenuData) bTreeValue;

				return (aValue.getOrder() - bValue.getOrder());

			}

			if ((aTreeValue instanceof EditorData) && (bTreeValue instanceof EditorData)) {
				EditorData aValue = (EditorData) aTreeValue;
				EditorData bValue = (EditorData) bTreeValue;

				return (aValue.getOrder() - bValue.getOrder());

			}

		}

		return result;
	}

}
