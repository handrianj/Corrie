package org.handrianj.corrie.picregistry;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.handrianj.corrie.picregistry.service.IPictureRegistryService;
import org.handrianj.corrie.picregistry.service.impl.internal.PicturesRegistryService;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.handrianj.corrie.picregistry"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private PicturesRegistryService pictureService;

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

		pictureService = new PicturesRegistryService();
		context.registerService(IPictureRegistryService.class.getName(), pictureService, null);

		plugin = this;
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
		pictureService.clearData();
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

}
