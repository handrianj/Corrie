package com.handrianj.corrie.usermanager.Lang;

import java.util.HashMap;
import java.util.Map;

import com.handrianj.corrie.languagemanager.service.AbstractLanguageDataProvider;

public abstract class AbstractUserManagerLang extends AbstractLanguageDataProvider implements IUserManagerLang {

	@Override
	public Map<String, String> getDefaultTextsMap() {
		Map<String, String> map = new HashMap<>();

		map.put(USER_MANAGER, USER_MANAGER_TEXT_EN);
		map.put(USER_CODE, USER_CODE_TEXT_EN);
		map.put(USER_NAME, USER_NAME_TEXT_EN);
		map.put(USER_ROLE, USER_ROLE_TEXT_EN);
		map.put(PASSWORD, PASSWORD_TEXT_EN);
		map.put(SEARCH, SEARCH_TEXT_EN);
		map.put(UPDATE, UPDATE_TEXT_EN);
		map.put(NONE, NONE_TEXT_EN);
		map.put(NO_SEARCH_RESULT, NO_SEARCH_RESULT_TEXT_EN);
		map.put(SYS_INFO, SYS_INFO_TEXT_EN);
		map.put(SEARCH_ERROR, SEARCH_ERROR_TEXT_EN);
		map.put(UPDATE_ERROR, UPDATE_ERROR_TEXT_EN);
		map.put(CONFIRM, CONFIRM_TEXT_EN);
		map.put(UPDATE_OK, UPDATE_OK_TEXT_EN);
		map.put(DELETE, DELETE_TEXT_EN);
		map.put(DELETE_OK, DELETE_OK_TEXT_EN);
		map.put(INSERT, INSERT_OK_TEXT_EN);
		map.put(INSERT_ERROR, INSERT_ERROR_TEXT_EN);
		map.put(ERROR, ERROR_TEXT_EN);
		map.put(UPDATE_OR_NOT, UPDATE_OR_NOT_TEXT_EN);
		map.put(INSERT_OR_NOT, INSERT_OR_NOT_TEXT_EN);
		map.put(DELETE_OR_NOT, DELETE_OR_NOT_TEXT_EN);
		map.put(ACTIVE, ACTIVE_TEXT_EN);
		map.put(NO_ACTIVE, NO_ACTIVE_TEXT_EN);
		map.put(ROLE_SELECTION, ROLE_SELECTION_TEXT_EN);

		return map;
	}

	@Override
	public String getPluginID() {
		return "com.capsa.corrie.userManager";
	}

}
