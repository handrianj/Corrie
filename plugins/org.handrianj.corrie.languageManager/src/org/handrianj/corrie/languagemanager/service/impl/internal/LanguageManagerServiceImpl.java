package org.handrianj.corrie.languagemanager.service.impl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.handrianj.corrie.languagemanager.service.ILang;
import org.handrianj.corrie.languagemanager.service.ILanguageDataProvider;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerListener;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguageManagerServiceImpl implements ILanguageManagerService {

	private static final String UNKNOWN_STRING = ": Undefined for ";

	private static final String EXTENSION_POINT_LANGUAGE_FILES = "org.handrianj.corrie.languagemanager.langProvider";

	private static final String EXTENSION_POINT_LANGUAGES = "org.handrianj.corrie.languagemanager.languages";

	private static final String EXTENSION_POINT_LANGUAGE_DEFAULTS = "org.handrianj.corrie.languagemanager.defaults";

	private static final String ATTRIB_LANGUAGE_ID = "langId";

	private static final String ATTRIB_LANGUAGE_NAME = "languageName";

	private static final String ATTRIB_LANGUAGE_DEFAULT = "defaultLanguage";

	private static final String ATTRIB_PROVIDER_CLASS = "LanguageProviderClass";

	private Map<ILang, Map<String, Map<String, String>>> languageToData = new ConcurrentHashMap<>();

	private Map<UISession, ILang> sessionLanguages = new ConcurrentHashMap<>();

	private List<LanguageData> allLanguages = new ArrayList<>();

	private LanguageData defaultLanguage;

	private static Logger logger = LoggerFactory.getLogger(LanguageManagerServiceImpl.class);

	// Map that stores the current session with the differents UI Threads, this
	// is doe in order to avoir invalid thread access
	private Map<UISession, ListenerList<ILanguageManagerListener>> sessionsMap = new HashMap<>();

	public LanguageManagerServiceImpl() {

	}

	@Override
	public synchronized String getMessage(String pluginID, String key, UISession session) {

		ILang languageKeys = sessionLanguages.get(session);

		if (languageKeys == null) {

			setCurrentLanguage(defaultLanguage, session);
			languageKeys = defaultLanguage;
		}

		Map<String, Map<String, String>> allLanguageMap = languageToData.get(languageKeys);

		if (allLanguageMap != null) {
			Map<String, String> pluginMap = allLanguageMap.get(pluginID);

			if (pluginMap != null) {
				String string = pluginMap.get(key);

				if (string != null) {
					return string;
				}
			}
		}
		return key + UNKNOWN_STRING + languageKeys.getName();

	}

	@Override
	public void init() {

		logger.info("Initialization of the Language Service");
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		readLanguageTypes(registry);

		// Read the defaults first
		readLanguageFiles(registry, EXTENSION_POINT_LANGUAGE_DEFAULTS);

		// Then read all the other files
		readLanguageFiles(registry, EXTENSION_POINT_LANGUAGE_FILES);

	}

	private void readLanguageTypes(IExtensionRegistry registry) {

		IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT_LANGUAGES);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (IConfigurationElement element : iExtension.getConfigurationElements()) {

					String id = null;
					String name = null;
					String defaultLanguage = null;

					id = element.getAttribute(ATTRIB_LANGUAGE_ID);
					name = element.getAttribute(ATTRIB_LANGUAGE_NAME);
					defaultLanguage = element.getAttribute(ATTRIB_LANGUAGE_DEFAULT);

					LanguageData newLanguage = new LanguageData(Integer.parseInt(id), name);

					if (Boolean.parseBoolean(defaultLanguage)) {
						this.defaultLanguage = newLanguage;
					}

					allLanguages.add(newLanguage);

				}
			}
		}

		logger.info("Extension point {} found {} languagues", EXTENSION_POINT_LANGUAGES, allLanguages.size());

	}

	private void readLanguageFiles(IExtensionRegistry registry, String extension) {
		IExtensionPoint point = registry.getExtensionPoint(extension);
		if (point != null) {

			IExtension[] extensions = point.getExtensions();
			for (IExtension iExtension : extensions) {
				for (IConfigurationElement element : iExtension.getConfigurationElements()) {

					ILanguageDataProvider dataProvider = null;

					try {
						dataProvider = (ILanguageDataProvider) element.createExecutableExtension(ATTRIB_PROVIDER_CLASS);
					} catch (CoreException e1) {
						logger.error(e1.getMessage(), e1);
					}

					// Get the plugin ID
					String pluginID = dataProvider.getPluginID();

					// Get the language map from this Language data provider
					Map<ILang, Map<String, String>> languageMap = dataProvider.getLanguageMap();

					// Get the entry set for formatting correctly
					Set<Entry<ILang, Map<String, String>>> entrySet = languageMap.entrySet();

					for (Entry<ILang, Map<String, String>> entry : entrySet) {

						// Check if the language map already exists for this
						// language
						Map<String, Map<String, String>> map = languageToData.get(entry.getKey());

						// If map is null for the language, initialize it
						if (map == null) {
							map = new HashMap<>();
						}

						// Put the pluginID + texts in the map
						map.put(pluginID, entry.getValue());

						// Put lang + texts data in the full map
						languageToData.put(entry.getKey(), map);

					}

				}
			}
		}
	}

	@Override
	public synchronized void setCurrentLanguage(ILang ln, UISession session) {
		// Execute the updates back in thread UI use the current session as key

		sessionLanguages.put(session, ln);
		ListenerList<ILanguageManagerListener> listenerList = sessionsMap.get(session);
		final Object[] listeners = listenerList.getListeners();
		session.exec(new Runnable() {

			@Override
			public void run() {

				for (Object currentListener : listeners) {
					ILanguageManagerListener lml = (ILanguageManagerListener) currentListener;
					lml.languageChanged();
				}
			}
		});

	}

	@Override
	public synchronized ILang getCurrentLanguage(UISession session) {
		return sessionLanguages.get(session);
	}

	@Override

	public synchronized void addLanguageManagerListener(ILanguageManagerListener listener, UISession session) {

		ListenerList<ILanguageManagerListener> sessionListeners = sessionsMap.get(session);

		if (sessionListeners == null) {
			sessionListeners = new ListenerList<ILanguageManagerListener>();
		}

		sessionListeners.add(listener);

		sessionsMap.put(RWT.getUISession(), sessionListeners);

	}

	@Override

	public void removeLanguageManagerListener(ILanguageManagerListener listener, UISession session) {
		ListenerList<ILanguageManagerListener> sessionListeners = sessionsMap.get(session);

		if (sessionListeners != null) {
			sessionListeners.remove(listener);
		}

	}

	@Override
	public synchronized void clearSession(UISession session) {
		sessionLanguages.remove(session);
	}

	public void clearData() {
		sessionLanguages.clear();
		sessionLanguages = null;
	}

	@Override
	public List<ILang> getAvailableLanguages() {
		return Collections.unmodifiableList(allLanguages);
	}

	@Override
	public ILang getDefaultLanguage() {
		return defaultLanguage;
	}

}
