package com.handrianj.corrie.viewsmanager;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.handrianj.corrie.viewsmanager.manager.internal.ViewsManagerServiceImpl;
import com.handrianj.corrie.viewsmanager.ui.IViewsManagerService;

public class Activator extends AbstractUIPlugin {

	private static BundleContext context;

	// The shared instance
	private static Activator plugin;

	private ViewsManagerServiceImpl service;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		service = new ViewsManagerServiceImpl();
		context.registerService(IViewsManagerService.class.getName(), service, null);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		service.clearData();
		Activator.context = null;
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
