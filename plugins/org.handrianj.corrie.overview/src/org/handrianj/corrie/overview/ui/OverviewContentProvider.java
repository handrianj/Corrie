package org.handrianj.corrie.overview.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.handrianj.corrie.datamodel.structure.impl.TreeStructure;

public class OverviewContentProvider implements ITreeContentProvider {

	/**
	 *
	 */
	private static final long serialVersionUID = -5602190785965865452L;

	@Override
	public void dispose() {
		// nothing to do

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do

	}

	@Override
	public Object[] getElements(Object inputElement) {

		if (inputElement instanceof TreeStructure) {
			Object value = ((TreeStructure) inputElement).getValue();

			if (value != null) {
				return new Object[] { value };
			} else {
				return ((TreeStructure) inputElement).getChildren().toArray();
			}
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof TreeStructure) {
			return ((TreeStructure) parentElement).getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof TreeStructure) {
			return ((TreeStructure) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof TreeStructure) {
			return !((TreeStructure) element).getChildren().isEmpty();
		}
		return false;
	}

}
