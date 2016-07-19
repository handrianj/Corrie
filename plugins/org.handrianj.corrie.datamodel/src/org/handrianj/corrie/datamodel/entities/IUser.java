package org.handrianj.corrie.datamodel.entities;

import java.util.Set;

/**
 * Interface used to define a user in the application
 *
 * @author Heri Andrianjafy
 *
 */
public interface IUser {

	/**
	 * Returns the username of this user
	 *
	 * @return Returns the username
	 */
	String getUserName();

	/**
	 * Returns the different roles ID of this user
	 *
	 * @return A set containing the ID of each roles hold by the user
	 */
	Set<Integer> getRoles();

	/**
	 * Returns the ID of this user
	 *
	 * @return the user Id
	 */
	public String getUserId();

	/**
	 * Return password
	 *
	 * @return the password
	 */
	public String getPassWord();

	/**
	 * Return if the user is active or not, 0 is no active, 1 is active
	 *
	 * @return true if the user is active, false otherwise
	 */
	public boolean isActive();

	/**
	 * Forcing the override of hashcode
	 *
	 * @return
	 */
	@Override
	public int hashCode();

	/**
	 * Forcing the override of equals
	 *
	 * @return
	 */
	@Override
	public boolean equals(Object obj);

}
