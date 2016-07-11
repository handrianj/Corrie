package com.handrianj.corrie.viewsmanager;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.handrianj.corrie.viewsmanager.manager.internal.ViewData;
import com.handrianj.corrie.viewsmanager.ui.IViewsManagerService;

/**
 * Class used to read the extension points for the view contributions
 *
 * @author Heri Andrianjafy
 *
 */
public final class ViewsExtensionReader {

	private static final String EXTENSION_POINT = "com.capsa.corrie.viewsManager.capsaView";

	private static final String ATTRIB_ID = "ViewID";

	private static final String ATTRIB_PERSPECTIVE = "PerspectiveID";

	public static void readExtensionPoint() {
		BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = context.getServiceReference(IViewsManagerService.class.getName());
		IViewsManagerService service = (IViewsManagerService) context.getService(serviceReference);

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (final IConfigurationElement element : iExtension.getConfigurationElements()) {

					String attributeID = element.getAttribute(ATTRIB_ID);
					if (attributeID != null) {
						String perspectiveAttribute = element.getAttribute(ATTRIB_PERSPECTIVE);

						String idAttribute = element.getAttribute(ATTRIB_ID);

						service.addView(new ViewData(idAttribute), perspectiveAttribute);
					}
				}
			}
		}

	}

}
