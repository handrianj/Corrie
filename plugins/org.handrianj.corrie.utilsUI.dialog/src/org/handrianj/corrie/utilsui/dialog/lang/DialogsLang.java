package org.handrianj.corrie.utilsui.dialog.lang;

import java.util.HashMap;
import java.util.Map;

import org.handrianj.corrie.languagemanager.service.AbstractLanguageDataProvider;

public class DialogsLang extends AbstractLanguageDataProvider implements IDialogLang {

	@Override
	public Map<String, String> getDefaultTextsMap() {

		Map<String, String> map = new HashMap<>();
		map.put(LOG_IN_DIALOG_0, LOG_IN_DIALOG_0_TEXT_EN);
		map.put(LOG_IN_DIALOG_1, LOG_IN_DIALOG_1_TEXT_EN);
		map.put(LOG_IN_DIALOG_2, LOG_IN_DIALOG_2_TEXT_EN);
		map.put(LOG_IN_DIALOG_3, LOG_IN_DIALOG_3_TEXT_EN);
		map.put(LOG_IN_DIALOG_4, LOG_IN_DIALOG_4_TEXT_EN);
		map.put(LOGIN, LOGIN_TXT_EN_TEXT_EN);
		map.put(LOGOUT_INFO_1, LOGOUT_INFO_1_TEXT_EN);
		map.put(LOGOUT_INFO_2, LOGOUT_INFO_2_TEXT_EN);
		map.put(UPDATE_TITLE, UPDATE_TITLE_TEXT_EN);
		map.put(PASSWORD_CHANGE, PASSWORD_CHANGE_TEXT_EN);
		map.put(PASSWORD_CHANGE_SUCCESS, PASSWORD_CHANGE_SUCCESS_TEXT_EN);
		map.put(PASSWORD_ERRO, PASSWORD_ERRO_TEXT_EN);
		map.put(OLDPASSWORD_ERRO, OLDPASSWORD_ERRO_TEXT_EN);
		map.put(CHANGE_PASSWORD, CHANGE_PASSWORD_TEXT_EN);
		map.put(INPUTPASSWORDS, INPUTPASSWORDS_TEXT_EN);
		map.put(CURRENT_PASSWORD, CURRENT_PASSWORD_TEXT_EN);
		map.put(NEW_PASSWORD, NEW_PASSWORD_TEXT_EN);
		map.put(CONFIRM_PASSWORD, CONFIRM_PASSWORD_TEXT_EN);
		map.put(TOO_SHORT, TOO_SHORT_TEXT_EN);
		map.put(PASSWORD_NOT_MATCH, PASSWORD_NOT_MATCH_TEXT_EN);
		map.put(OK, OK_TEXT_EN);
		map.put(CANCEL, CANCEL_TEXT_EN);
		map.put(SESSION_MENU_2, SESSION_MENU_2_TEXT_EN);
		return map;
	}

	@Override
	public String getPluginID() {
		return "org.handrianj.corrie.utilsUI.dialog";
	}

	@Override
	protected Map<String, String> getTextMapForLang(int id) {
		// Returns null because default
		return null;
	}

}
