package org.handrianj.corrie.overview.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.handrianj.corrie.datamodel.structure.impl.TreeStructure;

/**
 * Class used by the extension point to read items added to the menu
 *
 * @author Heri Andrianjafy
 *
 */
public class ExtensionReader {

	private static final String ATTRIB_MENU_ID = "menuID";

	private static final String ATTRIB_ACTION_ID = "actionID";

	private static final String ATTRIB_ACTION_CLASS = "Class";

	private static final String EXTENSION_POINT = "org.handrianj.corrie.overview.menuItem";

	private static final String ATTRIB_ID = "editorID";

	private static final String ATTRIB_LANG_NAMES = "Name";

	private static final String ATTRIB_LANG_ID = "LangID";

	private static final String ATTRIB_ITEM_ORDER = "order";

	private static final String ELEMENT_ITEM_ROLE_LIST = "Role";

	private static final String ATTRIB_ITEM_ROLE = "id";

	private static boolean init = false;

	private static TreeStructure<Object> root;

	private static Map<String, TreeStructure<Object>> idToMenu = new HashMap<>();

	/**
	 * Returns map containing item name / EditorClass
	 *
	 * @return
	 */
	public static TreeStructure<Object> readExtensionPoint() {

		if (!init) {

			init = true;

			root = new TreeStructure<Object>(null);

			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
			if (point != null) {

				IExtension[] extensions = point.getExtensions();
				for (IExtension extension : extensions) {

					for (final IConfigurationElement element : extension.getConfigurationElements()) {
						readExtension(element, root, null);
					}
				}
			}
		}
		return root;

	}

	private static void readExtension(IConfigurationElement element, TreeStructure<Object> currentNode,
			String currentMenuID) {

		String editorID = element.getAttribute(ATTRIB_ID);
		String menuID = element.getAttribute(ATTRIB_MENU_ID);
		String actionID = element.getAttribute(ATTRIB_ACTION_ID);

		HashMap<Integer, String> langMap = new HashMap<>();
		IConfigurationElement[] child = element.getChildren();

		for (IConfigurationElement childElement : child) {

			String langID = childElement.getAttribute(ATTRIB_LANG_ID);
			String name = childElement.getAttribute(ATTRIB_LANG_NAMES);

			if (langID != null) {
				langMap.put(Integer.parseInt(langID), name);
			}

		}

		String order = element.getAttribute(ATTRIB_ITEM_ORDER);
		Integer orderNumber = -1;

		if (order != null) {
			orderNumber = Integer.parseInt(order);
		}

		TreeStructure<Object> newNode = null;

		// Case editor or action
		if ((editorID != null) || (actionID != null)) {

			// if editor
			if (editorID != null) {

				IConfigurationElement[] roleIDs = element.getChildren(ELEMENT_ITEM_ROLE_LIST);
				List<String> ids = new ArrayList<String>();
				for (IConfigurationElement id : roleIDs) {
					if (id.getAttribute(ATTRIB_ITEM_ROLE) != null) {
						String role_id = id.getAttribute(ATTRIB_ITEM_ROLE);
						ids.add(role_id);
					}
				}

				newNode = new TreeStructure<Object>(new EditorData(editorID, orderNumber, langMap, ids));
			}
			// else is action
			else {
				try {
					Action actionInstance = (Action) element.createExecutableExtension(ATTRIB_ACTION_CLASS);
					newNode = new TreeStructure<Object>(new ActionData(actionInstance, actionID, orderNumber, langMap));
				} catch (CoreException e) {

					e.printStackTrace();
				}
			}

			if ((newNode != null)) {

				// if the currentMenu is not null and id not empty then get the
				// node
				// corresponding to the menu ID and add the editor to the
				// childrens
				if ((currentMenuID != null) && !currentMenuID.isEmpty()) {

					TreeStructure<Object> treeStructure = idToMenu.get(currentMenuID);
					treeStructure.addChildren(newNode);
				}
				// Else we are very probably at the root
				else {
					currentNode.addChildren(newNode);
				}
			}

		} // Else we have a Menu Item
		else {

			MenuData menuData = new MenuData(menuID, orderNumber, langMap);
			IConfigurationElement[] children = element.getChildren();
			newNode = new TreeStructure<Object>(menuData);

			// If the menu is not already registered, put it in the map
			if (!idToMenu.containsKey(menuID)) {

				idToMenu.put(menuID, newNode);
				currentNode.addChildren(newNode);
			}

			for (IConfigurationElement childElement : children) {
				readExtension(childElement, newNode, menuID);
			}
		}

	}

	public static void clearData() {
		root.purgeTree();
	}
}
