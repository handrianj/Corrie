package org.handrianj.corrie.dblink.services;

import java.util.Collection;

/**
 * OSGI Service use to store the constants of the bounded attributes
 *
 * @author Heri Andrianjafy
 *
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
