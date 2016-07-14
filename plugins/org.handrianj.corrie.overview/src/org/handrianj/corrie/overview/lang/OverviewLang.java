package org.handrianj.corrie.overview.lang;

import java.util.HashMap;
import java.util.Map;

import org.handrianj.corrie.languagemanager.service.AbstractLanguageDataProvider;

public class OverviewLang extends AbstractLanguageDataProvider implements IOverviewLang {

	@Override
	public Map<String, String> getDefaultTextsMap() {
		Map<String, String> map = new HashMap<>();

		map.put(EXPIRE_SESSION, EXPIRE_SESSION_TXT_EN);
		map.put(EXPIRE_SESSION_INFO, EXPIRE_SESSION_INFO_TXT_EN);

		return map;
	}

	@Override
	public String getPluginID() {
		return "org.handrianj.corrie.overview";
	}

	@Override
	protected Map<String, String> getTextMapForLang(int id) {
		// Returns null because default
		return null;
	}

}
