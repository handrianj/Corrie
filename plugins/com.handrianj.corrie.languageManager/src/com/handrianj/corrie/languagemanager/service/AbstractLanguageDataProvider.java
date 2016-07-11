package com.handrianj.corrie.languagemanager.service;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.handrianj.corrie.languagemanager.Activator;

public abstract class AbstractLanguageDataProvider implements ILanguageDataProvider {

	@Override
	public Map<ILang, Map<String, String>> getLanguageMap() {
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext
				.getServiceReference(ILanguageManagerService.class.getName());
		ILanguageManagerService languageManagerService = (ILanguageManagerService) bundleContext
				.getService(serviceReference);

		Map<ILang, Map<String, String>> languageMap = new HashMap<>();

		Map<String, String> defaultMap = getDefaultTextsMap();

		ILang defaultLanguage = languageManagerService.getDefaultLanguage();

		languageMap.put(defaultLanguage, defaultMap);

		for (ILang currentLanguage : languageManagerService.getAvailableLanguages()) {

			Map<String, String> textMapForLang = getTextMapForLang(currentLanguage.getId());

			if ((textMapForLang != null) && !textMapForLang.isEmpty()) {
				languageMap.put(currentLanguage, textMapForLang);
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
