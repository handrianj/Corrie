package org.handrianj.corrie.dataeditor.lang;

import java.util.HashMap;
import java.util.Map;

import org.handrianj.corrie.languagemanager.service.AbstractLanguageDataProvider;

public abstract class AbstractDataEditorLang extends AbstractLanguageDataProvider implements IDataEditorLang {

	protected Map<String, String> getDefaultMap() {
		Map<String, String> map = new HashMap<>();

		map.put(SEARCH_DIALOG, SEARCH_DIALOG_TEXT_EN);
		map.put(UPDATE_BTN, UPDATE_BTN_TEXT_EN);
		map.put(ADD_BTN, ADD_BTN_TEXT_EN);
		map.put(SELECT_TABLE, SELECT_TABLE_TEXT_EN);
		map.put(REMOVE_BTN, REMOVE_BTN_TEXT_EN);
		map.put(EDITOR_TITLE, EDITOR_TITLE_TEXT_EN);
		map.put(ERROR, ERROR_TEXT_EN);
		map.put(MANDATORY, MANDATORY_TEXT_EN);
		map.put(WRONG_FORMAT, WRONG_FORMAT_TEXT_EN);
		map.put(EXPECTED, EXPECTED_TEXT_EN);
		map.put(BIG, BIG_TEXT_EN);
		map.put(SHORT, SHORT_TEXT_EN);
		map.put(INTEGER, INTEGER_TEXT_EN);
		return map;
	}

	@Override
	public String getPluginID() {
		return "org.handrianj.corrie.dataEditor";
	}

}
