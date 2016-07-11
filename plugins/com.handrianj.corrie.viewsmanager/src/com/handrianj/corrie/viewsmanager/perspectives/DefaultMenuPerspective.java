package com.handrianj.corrie.viewsmanager.perspectives;

import com.handrianj.corrie.viewsmanager.ui.AbstractCorriePerspectiveFactory;

/**
 * Perspective for app that will use a menu
 *
 * @author Heri Andrianjafy
 *
 */
public class DefaultMenuPerspective extends AbstractCorriePerspectiveFactory {

	public static final String ID = "com.capsa.corrie.viewsmanager.perspectives.DefaultMenuPerspective";

	@Override
	protected String getID() {
		return ID;
	}

}
