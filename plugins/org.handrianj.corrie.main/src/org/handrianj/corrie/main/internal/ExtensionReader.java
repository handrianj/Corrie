package org.handrianj.corrie.main.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.handrianj.corrie.applicationmanagerservice.IApplicationConfig;

public class ExtensionReader {

	private static final String EXTENSION_POINT = "org.handrianj.corrie.main.defaultLaunch";

	private static final String ATTRIB_ID = "EditorID";

	private static final String ATTRIB_PERSPECTIVE_ID = "PerspectiveID";

	private static final String ATTRIB_PERSPECTIVE_NAME = "ApplicationName";

	/**
	 * Returns map containing item name / EditorClass
	 *
	 * @return
	 */
	public static Map<String, IApplicationConfig> readExtensionPoint() {

		Map<String, IApplicationConfig> nameToItem = new HashMap<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (final IConfigurationElement element : iExtension.getConfigurationElements()) {

					String editorID = element.getAttribute(ATTRIB_ID);
					String perspectiveID = element.getAttribute(ATTRIB_PERSPECTIVE_ID);
					String applicationName = element.getAttribute(ATTRIB_PERSPECTIVE_NAME);

					nameToItem.put(applicationName, new ApplicationItem(editorID, perspectiveID));
				}
			}
		}

		return nameToItem;

	}

}
