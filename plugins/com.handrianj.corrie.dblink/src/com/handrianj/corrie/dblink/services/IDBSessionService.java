package com.handrianj.corrie.dblink.services;

import com.handrianj.corrie.dblink.model.IUser;

/**
 * Service used to managed all the DB sessions for each user
 *
 * @author Heri Andrianjafy
 *
 */
public interface IDBSessionService<T extends IUser> {

	/**
	 * Returns the service instance associated to the user
	 *
	 * @param user
	 * @return
	 */
	public IDAOProvider getServiceForUser(T user);

	/**
	 * Closes the DB session for a user
	 *
	 * @param user
	 */
	public void closeDBsession(T user);

	/**
	 * Logs a user into the database
	 *
	 * @param login
	 * @param password
	 * @return
	 */
	public T login(String login, String password);

	/**
	 * Init user for db use
	 *
	 * @param user
	 */
	public void initUser(T user);

	/**
	 * Check if password match DB
	 *
	 * @param user
	 * @param password
	 * @return
	 */
	boolean isPasswordCorrect(T user, String password);

	/**
	 * Updates password in DB
	 *
	 * @param user
	 * @param newPassword
	 */
	public void updatePassword(T user, String newPassword);

	/**
	 * Code to call if application change
	 *
	 * @param applicationName
	 */
	public void applicationChanged(T user, String applicationName);
}
