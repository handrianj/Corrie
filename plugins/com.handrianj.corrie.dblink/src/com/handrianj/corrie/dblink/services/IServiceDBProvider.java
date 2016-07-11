package com.handrianj.corrie.dblink.services;

import com.handrianj.corrie.dblink.model.IDBObjectAccessor;

/**
 * Service used to store all the DB access classes
 *
 * @author Henry
 *
 */
public interface IServiceDBProvider {

	/**
	 * Get the DB access for the object class in parameter
	 *
	 * @param clazz
	 * @return
	 */
	IDBObjectAccessor getDBAccessForClass(Class<?> clazz);

}
