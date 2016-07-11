package com.handrianj.corrie.sessionmanager.service;

import org.eclipse.rap.rwt.service.UISession;

import com.handrianj.corrie.dblink.model.IUser;

/**
 * Interface to manage all the unic users sessions as well as DAO accesses for
 * users
 *
 * @author Heri Andrianjafy
 *
 */
public interface ISessionManager {

	/**
	 * Adds a session to the manager
	 *
	 * @param activeUser
	 *            Logged in user
	 */
	public void addSession(IUser activeUser);

	/**
	 * Closes the current user session of the manager
	 *
	 * @param uiSession
	 *            Current UI Session
	 */
	public void closeSession(UISession uiSession);

	/**
	 * Closes the session of the user in parameter
	 *
	 * @param activeUser
	 *            User to log out
	 */
	public void logoutUser(IUser user);

	/**
	 * Returns the user associated with the ui session
	 *
	 * @param session
	 *            Current UI session
	 * @return User logged in this session (can be null)
	 */
	public IUser getUser(UISession session);

	/**
	 * Checks if the user is connected
	 *
	 * @param user
	 *            User to check
	 * @return True if user is connected, false otherwise
	 */
	public boolean isUserOnline(IUser user);

	/**
	 * Returns the required sessionDelegate
	 *
	 * @param clazz
	 *            This class is that Business object class that you want the DAO
	 *            for (NOT THE DAO CLASS ITSELF)
	 * @return the DAO object
	 */
	public ISessionManagerDelegate getDelegateForClass(Class<?> clazz);

}
