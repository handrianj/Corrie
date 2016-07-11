package com.handrianj.corrie.overview.ui;

import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.rap.rwt.RWT;

import com.handrianj.corrie.datamodel.structure.impl.TreeStructure;
import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.overview.internal.ActionData;
import com.handrianj.corrie.overview.internal.EditorData;
import com.handrianj.corrie.overview.internal.MenuData;
import com.handrianj.corrie.serviceregistry.ServiceRegistry;

public class OverviewFilter extends ViewerFilter {

	/**
	 *
	 */
	private static final long serialVersionUID = 177001252127552986L;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if (element instanceof TreeStructure) {
			TreeStructure tree = (TreeStructure) element;

			Object data = ((TreeStructure) element).getValue();

			// If editor data
			if (data instanceof EditorData) {

				EditorData editorData = (EditorData) data;

				return isEditorValid(editorData);

			}

			// If MenuData, filter if no children
			if (data instanceof MenuData) {
				MenuData menuData = (MenuData) data;

				Set<TreeStructure> list = ((TreeStructure) element).getChildren();

				return checkChildrens(list);
			}

			if (data instanceof ActionData) {
				return true;
			}

		}

		return false;
	}

	private boolean isEditorValid(EditorData editorData) {
		List<String> roleID = editorData.getRoleID();
		IUser user = ServiceRegistry.getSessionManager().getUser(RWT.getUISession());

		if (roleID.isEmpty()) {
			return true;
		}

		// Check the role id first
		if (!roleID.isEmpty()) {

			for (String string : roleID) {
				for (int currentId : user.getRoles()) {
					if (currentId == Integer.valueOf(string)) {
						return true;
					}
				}

			}
		}
		return false;
	}

	private boolean checkChildrens(Set<TreeStructure> currentLevel) {
		boolean hasValidEditors = false;
		for (TreeStructure treeStructure : currentLevel) {
			if (treeStructure.getValue() instanceof EditorData) {

				hasValidEditors |= isEditorValid((EditorData) treeStructure.getValue());

			} else if (treeStructure.getValue() instanceof ActionData) {
				hasValidEditors = true;
			} else {
				hasValidEditors |= checkChildrens(treeStructure.getChildren());
			}

			if (hasValidEditors) {
				return hasValidEditors;
			}
		}

		return hasValidEditors;
	}

}
