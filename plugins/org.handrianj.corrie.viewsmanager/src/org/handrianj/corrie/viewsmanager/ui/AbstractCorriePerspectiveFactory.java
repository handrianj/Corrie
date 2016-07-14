package org.handrianj.corrie.viewsmanager.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractCorriePerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);

		// Close all the perspectives
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		IViewReference[] viewReferences = activePage.getViewReferences();

		for (IViewReference viewReference : viewReferences) {
			activePage.hideView(viewReference);
		}

	}

	protected abstract String getID();

}
