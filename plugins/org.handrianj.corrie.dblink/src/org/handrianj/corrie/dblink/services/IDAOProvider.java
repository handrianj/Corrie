package org.handrianj.corrie.dblink.services;

import org.handrianj.corrie.dblink.model.IDAObject;

/**
 * Service used to store all the DAO objects
 *
 * @author Henry
 *
 */
public interface IDAOProvider {

	/**
	 * Get the DB access for the object class in parameter
	 *
	 * @param clazz
	 * @return
	 */
	IDAObject getDBAccessForClass(Class<?> clazz);

	/**
	 * Close the DB session
	 */
	void closeSession();

}
