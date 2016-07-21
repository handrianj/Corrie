package org.handrianj.corrie.sessionmanager.service.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.handrianj.corrie.datamodel.entities.IUser;
import org.handrianj.corrie.sessionmanager.service.ISessionManager;
import org.handrianj.corrie.sessionmanager.service.ISessionManagerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager implements ISessionManager {

	private static Logger logger = LoggerFactory.getLogger(SessionManager.class);

	// User session map
	private Map<UISession, IUser> sessionToUserMap = new ConcurrentHashMap<>();

	private List<ISessionManagerDelegate> sessionDelegates = new ArrayList<ISessionManagerDelegate>();

	public SessionManager() {
		sessionDelegates = ExtensionReader.readExtensionPoint();
	}

	@Override
	public void addSession(IUser activeUser) {
		sessionToUserMap.put(RWT.getUISession(), activeUser);
	}

	@Override
	public void closeSession(UISession uiSession) {

		IUser user = sessionToUserMap.get(uiSession);

		if (logger.isDebugEnabled()) {
			logger.debug("Closing session for " + user.getUserId());
		}

		closeDelegatesSession(user);
		sessionToUserMap.remove(uiSession);

		if (logger.isDebugEnabled()) {
			logger.debug("Session closed");
		}
	}

	private void closeDelegatesSession(IUser user) {
		for (ISessionManagerDelegate delegate : sessionDelegates) {

			if (logger.isDebugEnabled()) {
				logger.debug("Closing delegate for " + delegate.getClass());
			}

			delegate.closeSession(user);
		}
	}

	@Override
	public IUser getUser(UISession session) {
		return sessionToUserMap.get(session);
	}

	@Override
	public boolean isUserOnline(IUser user) {
		return sessionToUserMap.containsValue(user);
	}

	@Override
	public void logoutUser(IUser user) {

		if (logger.isDebugEnabled()) {
			logger.debug("User logout");
		}

		closeDelegatesSession(user);

		Set<Entry<UISession, IUser>> entrySet = sessionToUserMap.entrySet();

		for (Entry<UISession, IUser> entry : entrySet) {

			if (entry.getValue().equals(user)) {
				sessionToUserMap.remove(entry.getKey());

				if (logger.isDebugEnabled()) {
					logger.debug("User " + user.getUserId() + " disconected ");
				}
				break;
			}
		}
	}

	public void clearData() {

		if (logger.isDebugEnabled()) {
			logger.debug("DATA PURGE");
		}

		sessionToUserMap.clear();
		for (ISessionManagerDelegate delegate : sessionDelegates) {
			delegate.clearData();
		}
	}

	@Override
	public ISessionManagerDelegate getDelegateForClass(Class<?> clazz) {
		for (ISessionManagerDelegate delegate : sessionDelegates) {
			if (delegate.getClass().isAssignableFrom(clazz)) {
				return delegate;
			}
		}
		return null;
	}

}
