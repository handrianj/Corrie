package com.handrianj.corrie.dblink.services;

import com.handrianj.corrie.dblink.model.IDAObject;

/**
 * Service used to store all the DB access classes
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

}
