package org.handrianj.corrie.serviceregistry;

import org.eclipse.rap.rwt.service.UISession;
import org.handrianj.corrie.applicationmanagerservice.IApplicationManagerService;
import org.handrianj.corrie.colorregistry.service.IColorRegistry;
import org.handrianj.corrie.datamodel.entities.IUser;
import org.handrianj.corrie.dblink.services.IConstantService;
import org.handrianj.corrie.dblink.services.IDAOProvider;
import org.handrianj.corrie.dblink.services.IDBSessionService;
import org.handrianj.corrie.editors.util.editors.IEditorPilotService;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.picregistry.service.IPictureRegistryService;
import org.handrianj.corrie.sessionmanager.service.ISessionManager;
import org.handrianj.corrie.utilsui.IFileDownloadService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceRegistry {

	private static Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

	private static IPictureRegistryService pictureRegistryService;
	private static ILanguageManagerService languageManagerService;
	private static IDBSessionService<IUser> dbservice;
	private static IEditorPilotService pilotService;
	private static ISessionManager sessionManager;
	private static IConstantService<?, ?> constantService;
	private static IApplicationManagerService applicationService;
	private static IFileDownloadService fileRegistry;
	private static IColorRegistry colorRegistry;

	public static IColorRegistry getColorRegistry() {

		if (logger.isDebugEnabled()) {
			logger.debug("Initializing the ColorRegistry");
		}
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext.getServiceReference(IColorRegistry.class);
		if (serviceReference != null) {

			if (logger.isDebugEnabled()) {
				logger.debug("ColorRegistry initialized");
			}

			colorRegistry = (IColorRegistry) bundleContext.getService(serviceReference);

		} else {
			logger.warn("ColorRegistry cannot be initialized");
		}

		return colorRegistry;

	}

	@SuppressWarnings("unchecked")
	public static IDBSessionService<IUser> getDbservice() {

		if (dbservice == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IDBSessionService");
			}
			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IDBSessionService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IDBSessionService initialized");
				}

				dbservice = (IDBSessionService<IUser>) bundleContext.getService(serviceReference);
			} else {
				logger.warn("IDBSessionService cannot be initialized");
			}
		}

		return dbservice;
	}

	public static IFileDownloadService getFileRegistryService() {

		if (fileRegistry == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IFileDownloadService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IFileDownloadService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IFileDownloadService initialized");
				}

				fileRegistry = (IFileDownloadService) bundleContext.getService(serviceReference);
			} else {
				logger.warn("IFileDownloadService cannot be initialized");
			}
		}

		return fileRegistry;
	}

	public static ILanguageManagerService getLanguageManagerService() {

		if (languageManagerService == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the ILanguageManagerService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(ILanguageManagerService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("ILanguageManagerService initialized");
				}

				languageManagerService = (ILanguageManagerService) bundleContext.getService(serviceReference);
				languageManagerService.init();
			} else {
				logger.warn("ILanguageManagerService cannot be initialized");
			}
		}
		return languageManagerService;
	}

	public static IPictureRegistryService getPictureRegistryService() {
		if (pictureRegistryService == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IPictureRegistryService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IPictureRegistryService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IPictureRegistryService initialized");
				}

				pictureRegistryService = (IPictureRegistryService) bundleContext.getService(serviceReference);
				pictureRegistryService.preloadPictures();
			} else {
				logger.warn("IPictureRegistryService cannot be initialized");
			}
		}
		return pictureRegistryService;
	}

	public static IEditorPilotService getPilotService() {
		if (pilotService == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IEditorPilotService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IEditorPilotService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IEditorPilotService initalized");
				}

				pilotService = (IEditorPilotService) bundleContext.getService(serviceReference);
			} else {
				logger.warn("IEditorPilotService cannot be initialized");
			}
		}
		return pilotService;
	}

	public static ISessionManager getSessionManager() {
		if (sessionManager == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the ISessionManager");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(ISessionManager.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("ISessionManager initialized");
				}

				sessionManager = (ISessionManager) bundleContext.getService(serviceReference);
			} else {
				logger.warn("ISessionManager cannot be initialized");
			}
		}
		return sessionManager;
	}

	public static IConstantService<?, ?> getConstantService() {
		if (constantService == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IConstantService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IConstantService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IConstantService initialized");
				}

				constantService = (IConstantService<?, ?>) bundleContext.getService(serviceReference);
			} else if (logger.isErrorEnabled()) {
				logger.error("IConstantService cannot be initialized");
			}
		}

		return constantService;
	}

	public static IDAOProvider getServiceProviderForSession(UISession session) {
		ISessionManager sessionManager = getSessionManager();
		IUser user = sessionManager.getUser(session);

		IDBSessionService<IUser> dbservice = getDbservice();
		return dbservice.getServiceForUser(user);

	}

	public static IApplicationManagerService getApplicationService() {
		if (applicationService == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing the IApplicationManagerService");
			}

			BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(IApplicationManagerService.class);
			if (serviceReference != null) {

				if (logger.isDebugEnabled()) {
					logger.debug("IApplicationManagerService initialized");
				}

				applicationService = (IApplicationManagerService) bundleContext.getService(serviceReference);
			} else {
				logger.warn("IApplicationManagerService cannot be initialized");
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
