/**
 *
 */
package com.handrianj.corrie.main;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchWindow;

import com.handrianj.corrie.main.lang.IMainLang;
import com.handrianj.corrie.serviceregistry.ServiceRegistry;

/**
 * Default perspective for all project
 *
 * @author Heri Andrianjafy
 *
 */
public class InitPerpective implements IPerspectiveFactory {
	public static final String ID = "com.handrianj.corrie.main.InitPerpective";
	private static final String ACTION_MENU_ID = "com.handrianj.unic.actions";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.
	 * IPageLayout)
	 */
	@SuppressWarnings("restriction")
	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);

		IWorkbenchWindow window = Workbench.getInstance().getActiveWorkbenchWindow();

		if (window instanceof WorkbenchWindow) {
			MenuManager menuManager = ((WorkbenchWindow) window).getMenuManager();

			IContributionItem item = menuManager.find(ACTION_MENU_ID);

			if ((item != null) && (item instanceof MenuManager)) {

				MenuManager actionItem = (MenuManager) item;
				actionItem.setMenuText(ServiceRegistry.getLanguageManagerService().getMessage(Activator.PLUGIN_ID,
						IMainLang.ACTION_MENU, RWT.getUISession()));
			}

		}
	}

}
