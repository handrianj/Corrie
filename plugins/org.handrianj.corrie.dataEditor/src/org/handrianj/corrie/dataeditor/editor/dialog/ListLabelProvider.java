package org.handrianj.corrie.dataeditor.editor.dialog;

import org.eclipse.jface.viewers.LabelProvider;
import org.handrianj.corrie.dblink.model.ITableStructure;

@SuppressWarnings("serial")
public class ListLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof ITableStructure) {
			return ((ITableStructure) element).getTableName();
		}
		return "";
	}
}
