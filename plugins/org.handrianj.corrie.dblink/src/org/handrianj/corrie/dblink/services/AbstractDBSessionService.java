package org.handrianj.corrie.dblink.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.handrianj.corrie.datamodel.entities.IUser;
import org.handrianj.corrie.dblink.Activator;
import org.handrianj.corrie.sessionmanager.service.ISessionManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDBSessionService<U extends IUser> implements IDBSessionService<U> {

	private static Logger logger = LoggerFactory.getLogger(AbstractDBSessionService.class);

	private Map<U, IDAOProvider> userToSession = new ConcurrentHashMap<>();
	private IDAOProvider defaultService;
	private ISessionManager sessionManager;

	@Override
	public IDAOProvider getServiceForUser(U user) {

		IDAOProvider service;

		if (user != null) {
			if (userToSession.containsKey(user)) {

				if (logger.isDebugEnabled()) {
					logger.debug("Retrieving the service for " + user.getUserId());
				}

				service = userToSession.get(user);
			} else {

				if (logger.isDebugEnabled()) {
					logger.debug("Creating the service for " + user.getUserId());
				}

				service = createNewService(user);
				userToSession.put(user, service);
			}
		} else {
			if (defaultService == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Creating the default service");
				}

				defaultService = createNewService(null);
			}

			return defaultService;
		}

		return service;
	}

	@Override
	public void initUser(U user) {

		ISessionManager sessionManager2 = getSessionManager();
		sessionManager2.addSession(user);
	}

	private ISessionManager getSessionManager() {
		if (sessionManager == null) {
			BundleContext bundleContext = Activator.getContext();
			ServiceReference<?> serviceReference = bundleContext.getServiceReference(ISessionManager.class);
			if (serviceReference != null) {
				sessionManager = (ISessionManager) bundleContext.getService(serviceReference);
			}
		}
		return sessionManager;
	}

	@Override
	public void closeDBsession(U user) {

		if (logger.isDebugEnabled()) {
			logger.debug("Closing DB session for " + user.getUserId());
		}

		IDAOProvider service = userToSession.get(user);

		if (service != null) {
			service.closeSession();
		}
		userToSession.remove(user);
	}

	public void clearData() {
		userToSession.clear();
		userToSession = null;
	}

	/**
	 * This method creates and returns a new IDAOProvider.
	 *
	 * @param user
	 *
	 * @return
	 */
	protected abstract IDAOProvider createNewService(U user);

}
