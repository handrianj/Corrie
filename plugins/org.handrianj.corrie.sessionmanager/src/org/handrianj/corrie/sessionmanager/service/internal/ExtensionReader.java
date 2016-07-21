package org.handrianj.corrie.sessionmanager.service.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.handrianj.corrie.sessionmanager.service.ISessionManagerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensionReader {

	private static Logger logger = LoggerFactory.getLogger(ExtensionReader.class);

	private static final String EXTENSION_POINT = "org.handrianj.corrie.sessionmanager.sessiondelegate";

	private static final String ATTRIB_DELEGATE_CLASS = "Delegate";

	/**
	 * Returns list containing all the DB sessionManagers
	 *
	 * @return
	 */
	public static List<ISessionManagerDelegate> readExtensionPoint() {

		ArrayList<ISessionManagerDelegate> allDelegatesList = new ArrayList<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (final IConfigurationElement element : iExtension.getConfigurationElements()) {

					ISessionManagerDelegate delegateInstance = null;

					try {
						delegateInstance = (ISessionManagerDelegate) element
								.createExecutableExtension(ATTRIB_DELEGATE_CLASS);
					} catch (CoreException e) {
						logger.error(e.getMessage(), e);
					}

					if (delegateInstance != null) {
						allDelegatesList.add(delegateInstance);
					}
				}
			}
		}

		return allDelegatesList;

	}

}
