package org.handrianj.corrie.usermanager.editor;

/**
 * Represents the data of a role : ID and name
 *
 * @author Heri Andrianjafy
 *
 */
public interface IRoleItem {

	/**
	 * Returns the role ID
	 *
	 * @return the ID of the role
	 */
	int getId();

	/**
	 * Returns the role name
	 *
	 * @return the name of the role
	 */
	String getName();

}
