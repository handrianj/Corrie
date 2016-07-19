package org.handrianj.corrie.dblink.services;

import java.util.Collection;

/**
 * * OSGI Service use to store the constants values of the DB. We use it to
 * reduce the calls to DB when retrieving values that don't update very often.
 * This service doesn't update real time so if you need to update the values you
 * need to reboot the application (This service is Optional)
 *
 * @author Heri Andrianjafy
 *
 * @param <C>
 *            Class or Interface allowing a connection to database
 * @param <T>
 *            Class or Interface that defines an object which has a constant
 *            value in DB
 */
public interface IConstantService<C, T> {

	/**
	 * Initialize the service with the constants
	 */
	void init(C connector);

	/**
	 * Get the attribute name for the language
	 *
	 * @param attribute
	 * @param languageId
	 * @return
	 */

	String getStringforAttribute(T attribute, int languageId, int index);

	/**
	 * Returns all the values for an attribute
	 *
	 * @param attribute
	 * @param languageId
	 * @return
	 */
	Collection<String> getAllValuesforAttribute(T attribute, int languageId);

	/**
	 * Returns the key of the attribute
	 *
	 * @param attribute
	 * @param languageId
	 * @param index
	 * @return
	 */
	String getKeyforAttributeIndex(T attribute, int languageId, int index);

	/**
	 * Returns all the key values for one attribute
	 *
	 * @param attribute
	 * @param languageId
	 * @return
	 */
	Collection<Integer> getAllKeysForAttribute(T attribute, int languageId);

}
