package org.handrianj.corrie.languagemanager.service;

import java.util.Map;

/**
 * This interface is used to defined a language data provider, it is critical
 * for each plugin to have its own language provider. You must have at least one
 * languageDataProvider per plugin if it displays text
 *
 * @author Heri Andrianjafy
 *
 */
public interface ILanguageDataProvider {

	/**
	 * Returns association between the language ID and the list of strings. By
	 * default this map will be filled with the content from
	 * {@link getDefaultTextsMap()}. If you want to add new languages, you will
	 * have to override this method and ADD your languages files to the default
	 * generated map.
	 *
	 * @return Map with the Language associated to their language datas
	 */
	public Map<ILang, Map<String, String>> getLanguageMap();

	/**
	 * Returns the plugin ID
	 *
	 * @return The ID of the plugin that will use this provided
	 */
	public String getPluginID();

	/**
	 * Returns the defaults text to use if the language was not specified (or if
	 * the language was not found)
	 *
	 * @return A map of textID-Text
	 */
	public Map<String, String> getDefaultTextsMap();

}
