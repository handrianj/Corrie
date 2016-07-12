package com.handrianj.corrie.editors.util.factories;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Factory used to dynamically read and load the differents editors inputs
 *
 * @author Heri Andrianjafy
 *
 */
public class EditorInputFactory {

	private static Map<String, IEditorInputFactory<?>> idToFacto = new HashMap<>();

	private static final String EXTENSION_POINT = "com.handrianj.corrie.editors.util.inputs";

	private static final String ATTRIB_EDITORID = "EditorID";

	private static final String ATTRIB_FACTO_CLASS = "InputFactory";

	/**
	 * Reads the input extension point to store the different classes
	 */
	public static void readExtensionPoint() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (IConfigurationElement element : iExtension.getConfigurationElements()) {

					String attributeID = element.getAttribute(ATTRIB_EDITORID);
					IEditorInputFactory<?> factoryClass = null;

					try {
						factoryClass = (IEditorInputFactory<?>) element.createExecutableExtension(ATTRIB_FACTO_CLASS);

						idToFacto.put(attributeID, factoryClass);
					} catch (CoreException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		}
	}

	/**
	 * Returns the object factory associated with this ID
	 *
	 * @param editorId
	 * @return
	 */
	public static IEditorInputFactory<?> getFactory(String editorId) {

		return idToFacto.get(editorId);
	}

	public static void clearData() {
		idToFacto.clear();
		idToFacto = null;
	}
}
