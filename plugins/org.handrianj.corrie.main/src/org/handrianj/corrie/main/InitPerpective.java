/**
 *
 */
package org.handrianj.corrie.main;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Default perspective for all project
 *
 * @author Heri Andrianjafy
 *
 */
public class InitPerpective implements IPerspectiveFactory {
	public static final String ID = "org.handrianj.corrie.main.InitPerpective";
	private static final String ACTION_MENU_ID = "org.handrianj.corrie.actions";

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
	}

}
