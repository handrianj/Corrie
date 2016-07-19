package org.handrianj.corrie.sessionmanager.service;

import org.handrianj.corrie.datamodel.entities.IUser;

/**
 * Interface provided for additional actions in the session manager
 *
 * @author Heri Andrianjafy
 *
 */
public interface ISessionManagerDelegate {

	/**
	 * Closes a user session
	 *
	 * @param user
	 */
	void closeSession(IUser user);

	/**
	 * Used to clear all the data from the delegate
	 */
	void clearData();

}
