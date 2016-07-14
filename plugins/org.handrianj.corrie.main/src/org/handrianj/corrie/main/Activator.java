package org.handrianj.corrie.main;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.handrianj.corrie.applicationmanagerservice.IApplicationManagerService;
import org.handrianj.corrie.editors.util.factories.EditorInputFactory;
import org.handrianj.corrie.main.internal.CorrieApplicationManagerServiceImpl;
import org.handrianj.corrie.main.internal.ExtensionReader;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.osgi.framework.BundleContext;

import org.handrianj.corrie.viewsmanager.ViewsExtensionReader;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.handrianj.corrie.main"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		EditorInputFactory.readExtensionPoint();
		ExtensionReader.readExtensionPoint();
		CorrieApplicationManagerServiceImpl applicationManagerService = new CorrieApplicationManagerServiceImpl();
		applicationManagerService.init();
		context.registerService(IApplicationManagerService.class, applicationManagerService, null);

		ServiceRegistry.getLanguageManagerService().init();
		ViewsExtensionReader.readExtensionPoint();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}