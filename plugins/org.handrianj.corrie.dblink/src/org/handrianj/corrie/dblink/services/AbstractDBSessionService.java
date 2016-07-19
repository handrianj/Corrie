package org.handrianj.corrie.dblink.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.handrianj.corrie.datamodel.entities.IUser;
import org.handrianj.corrie.dblink.Activator;
import org.handrianj.corrie.sessionmanager.service.ISessionManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public abstract class AbstractDBSessionService<U extends IUser> implements IDBSessionService<U> {

	private Map<U, IDAOProvider> userToSession = new ConcurrentHashMap<>();
	private IDAOProvider defaultService;
	private ISessionManager sessionManager;

	@Override
	public IDAOProvider getServiceForUser(U user) {

		IDAOProvider service;

		if (user != null) {
			if (userToSession.containsKey(user)) {

				service = userToSession.get(user);
			} else {

				service = createNewService(user);
				userToSession.put(user, service);
			}
		} else {
			if (defaultService == null) {
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
		IDAOProvider service = userToSession.get(user);
		service.closeSession();
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
