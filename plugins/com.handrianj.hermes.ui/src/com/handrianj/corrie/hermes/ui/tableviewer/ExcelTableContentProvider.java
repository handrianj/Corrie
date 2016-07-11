package com.handrianj.corrie.hermes.ui.tableviewer;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.handrianj.corrie.hermes.datamodel.ExcelSheet;

@SuppressWarnings("serial")
public class ExcelTableContentProvider implements IStructuredContentProvider {

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
		if (inputElement instanceof ExcelSheet) {
			return ((ExcelSheet) inputElement).getRows().toArray();
		} else {
			return new Object[0];
		}

	}

}
