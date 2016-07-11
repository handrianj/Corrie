package com.handrianj.corrie.languagemanager.service;

import java.util.List;

import org.eclipse.rap.rwt.service.UISession;

/**
 * Service used to store and retrieve the language of the software
 *
 * @author Heri Andrianjafy
 *
 */
public interface ILanguageManagerService {

	/**
	 * Reads the extension point and fills the map with all the datas
	 */
	public void init();

	/**
	 * Returns the text of the plugin using the language in parameter
	 *
	 * @param pluginID
	 *            ID of the plugin that needs the text
	 * @param key
	 *            The Text Key
	 * @param session
	 *            The current UI session
	 * @return Text to be displayed in the widget
	 */
	String getMessage(String pluginID, String key, UISession session);

	/**
	 * Adds a language listener on the service
	 *
	 * @param listener
	 *            Listener to add
	 * @param session
	 *            Current UI session
	 */
	public void addLanguageManagerListener(ILanguageManagerListener listener, UISession session);

	/**
	 * Removes a language listener from the service
	 *
	 * @param listener
	 *            Listener to remove
	 * @param session
	 *            Current UI session
	 */
	public void removeLanguageManagerListener(ILanguageManagerListener listener, UISession session);

	/**
	 * Clears the users session from the service
	 *
	 * @param session
	 *            current UI session
	 */
	public void clearSession(UISession session);

	/**
	 * Updates the language of the session in parameter
	 *
	 * @param ln
	 *            Language to set as current
	 * @param session
	 *            Current UI Sessio
	 */
	void setCurrentLanguage(ILang ln, UISession session);

	/**
	 * Returns the language used by the UI session
	 *
	 * @param session
	 *            Current session
	 * @return Currently used language
	 */
	ILang getCurrentLanguage(UISession session);

	/**
	 * Returns all the languages availables
	 *
	 * @return the list of all Available languages
	 */
	List<ILang> getAvailableLanguages();

	/**
	 * Returns the default application language
	 *
	 * @return the Default Language
	 */
	ILang getDefaultLanguage();
}
