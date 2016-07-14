package org.handrianj.corrie.languagemanager.service;

/**
 * Interface representing a language in the Corrie framework
 *
 * @author Heri Andrianjafy
 *
 */
public interface ILang {

	/**
	 * Returns the ID of the language
	 *
	 * @return an integer which represents the ID of the language
	 */
	int getId();

	/**
	 * Returns the name of the language
	 *
	 * @return Name of the language
	 */
	String getName();

}
