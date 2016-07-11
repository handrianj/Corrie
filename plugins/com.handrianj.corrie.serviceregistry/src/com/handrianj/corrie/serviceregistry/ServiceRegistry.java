package com.handrianj.corrie.serviceregistry;

import org.eclipse.rap.rwt.service.UISession;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.handrianj.corrie.applicationmanagerservice.IApplicationManagerService;
import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.dblink.services.IConstantService;
import com.handrianj.corrie.dblink.services.IDBSessionService;
import com.handrianj.corrie.dblink.services.IServiceDBProvider;
import com.handrianj.corrie.editors.util.editors.IEditorPilotService;
import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.picregistry.service.IPictureRegistryService;
import com.handrianj.corrie.sessionmanager.service.ISessionManager;
import com.handrianj.corrie.utilsui.IFileDownloadService;

public class ServiceRegistry {

	private static IPictureRegistryService pictureRegistryService;
	private static ILanguageManagerService languageManagerService;
	private static IDBSessionService<IUser> dbservice;
	private static IEditorPilotService pilotService;
	private static ISessionManager sessionManager;
	private static IConstantService<?, ?> constantService;
	private static IApplicationManagerService applicationService;
	private static IFileDownloadService fileRegistry;

	@SuppressWarnings("unchecked")
	public static IDBSessionService<IUser> getDbservice() {

		if (dbservice == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IDBSessionService.class.getName());
			if (serviceReference != null) {
				dbservice = (IDBSessionService<IUser>) bundleContext.getService(serviceReference);
			}
		}

		return dbservice;
	}

	public static IFileDownloadService getFileRegistryService() {

		if (fileRegistry == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IFileDownloadService.class);
			if (serviceReference != null) {
				fileRegistry = (IFileDownloadService) bundleContext.getService(serviceReference);
			}
		}

		return fileRegistry;
	}

	public static ILanguageManagerService getLanguageManagerService() {

		if (languageManagerService == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext
					.getServiceReference(ILanguageManagerService.class.getName());
			if (serviceReference != null) {
				languageManagerService = (ILanguageManagerService) bundleContext.getService(serviceReference);
			}
		}
		return languageManagerService;
	}

	public static IPictureRegistryService getPictureRegistryService() {
		if (pictureRegistryService == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext
					.getServiceReference(IPictureRegistryService.class.getName());
			if (serviceReference != null) {
				pictureRegistryService = (IPictureRegistryService) bundleContext.getService(serviceReference);
			}
		}
		return pictureRegistryService;
	}

	public static IEditorPilotService getPilotService() {
		if (pilotService == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext
					.getServiceReference(IEditorPilotService.class.getName());
			if (serviceReference != null) {
				pilotService = (IEditorPilotService) bundleContext.getService(serviceReference);
			}
		}
		return pilotService;
	}

	public static ISessionManager getSessionManager() {
		if (sessionManager == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(ISessionManager.class);
			if (serviceReference != null) {
				sessionManager = (ISessionManager) bundleContext.getService(serviceReference);
			}
		}
		return sessionManager;
	}

	public static IConstantService<?, ?> getConstantService() {
		if (constantService == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IConstantService.class);
			if (serviceReference != null) {
				constantService = (IConstantService<?, ?>) bundleContext.getService(serviceReference);
			}
		}

		return constantService;
	}

	public static IServiceDBProvider getServiceProviderForSession(UISession session) {
		ISessionManager sessionManager = getSessionManager();
		IUser user = sessionManager.getUser(session);

		IDBSessionService<IUser> dbservice = getDbservice();
		return dbservice.getServiceForUser(user);

	}

	public static IApplicationManagerService getApplicationService() {
		if (applicationService == null) {
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext
					.getServiceReference(IApplicationManagerService.class.getName());
			if (serviceReference != null) {
				applicationService = (IApplicationManagerService) bundleContext.getService(serviceReference);
			}
		}

		return applicationService;
	}

	static void clearData() {
		pictureRegistryService = null;
		languageManagerService = null;
		dbservice = null;
		pilotService = null;
		sessionManager = null;
		constantService = null;
		applicationService = null;
	}

}
