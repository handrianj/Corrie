package org.handrianj.corrie.main.lang;

import java.util.HashMap;
import java.util.Map;

import org.handrianj.corrie.languagemanager.service.AbstractLanguageDataProvider;

public class MainLang extends AbstractLanguageDataProvider implements IMainLang {

	@Override
	public Map<String, String> getDefaultTextsMap() {

		Map<String, String> map = new HashMap<>();
		map.put(USER_INCORECT, USER_INCORECT_TEXT_EN);
		map.put(LOGIN_ERROR_COMBINATION, LOGIN_ERROR_COMBINATION_TEXT_EN);
		map.put(SESSION_MENU_1, SESSION_MENU_1_TEXT_EN);
		map.put(SESSION_MENU_2, SESSION_MENU_2_TEXT_EN);
		map.put(LOGIN_ERROR_ALREADY_1, LOGIN_ERROR_ALREADY_1_TEXT_EN);
		map.put(LOGIN_ERROR_ALREADY_2, LOGIN_ERROR_ALREADY_2_TEXT_EN);
		map.put(LOGIN_ERROR_ALREADY_3, LOGIN_ERROR_ALREADY_3_TEXT_EN);
		return map;
	}

	@Override
	public String getPluginID() {
		return "org.handrianj.corrie.main";
	}

	@Override
	protected Map<String, String> getTextMapForLang(int id) {
		// Returns null because default
		return null;
	}
}
