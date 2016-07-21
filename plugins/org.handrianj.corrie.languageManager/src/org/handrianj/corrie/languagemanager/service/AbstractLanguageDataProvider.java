package org.handrianj.corrie.languagemanager.service;

import java.util.HashMap;
import java.util.Map;

import org.handrianj.corrie.languagemanager.Activator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLanguageDataProvider implements ILanguageDataProvider {

	private static Logger logger = LoggerFactory.getLogger(AbstractLanguageDataProvider.class);

	@Override
	public Map<ILang, Map<String, String>> getLanguageMap() {

		if (logger.isDebugEnabled()) {
			logger.debug("Retreving language maps for " + getPluginID());
		}
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext
				.getServiceReference(ILanguageManagerService.class.getName());
		ILanguageManagerService languageManagerService = (ILanguageManagerService) bundleContext
				.getService(serviceReference);

		Map<ILang, Map<String, String>> languageMap = new HashMap<>();

		if (logger.isDebugEnabled()) {
			logger.debug("Retreving default maps");
		}

		Map<String, String> defaultMap = getDefaultTextsMap();

		ILang defaultLanguage = languageManagerService.getDefaultLanguage();

		languageMap.put(defaultLanguage, defaultMap);

		if (logger.isDebugEnabled()) {
			logger.debug("Retreving all other maps");
		}

		for (ILang currentLanguage : languageManagerService.getAvailableLanguages()) {

			Map<String, String> textMapForLang = getTextMapForLang(currentLanguage.getId());

			// If text was found
			if ((textMapForLang != null) && !textMapForLang.isEmpty()) {
				languageMap.put(currentLanguage, textMapForLang);
			} else if (logger.isDebugEnabled()) {
				logger.debug("Map for " + currentLanguage.getId() + " is not provided");
			}

		}

		return languageMap;
	}

	/**
	 * Returns the map Key-Text of the language ID in parameter. This method
	 * MUST be implemented by subclasses.
	 *
	 * @param id
	 *            ID of the language to retrieve
	 * @return Map containing the textID as the key and the text as the value
	 */
	protected abstract Map<String, String> getTextMapForLang(int id);

}
