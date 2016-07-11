package com.handrianj.corrie.viewsmanager.manager.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.handrianj.corrie.viewsmanager.ui.IViewData;
import com.handrianj.corrie.viewsmanager.ui.IViewsManagerService;

public class ViewsManagerServiceImpl implements IViewsManagerService {

	private Map<String, List<IViewData>> perspectiveToViewMap = new ConcurrentHashMap<>();

	private final String NONE = "NONE";

	@Override
	public void addView(IViewData view, String perspective) {

		String perspectiveToShow = perspective;

		if (perspectiveToShow == null) {
			perspectiveToShow = NONE;
		}
		List<IViewData> list = perspectiveToViewMap.get(perspectiveToShow);

		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(view);
		perspectiveToViewMap.put(perspectiveToShow, list);

	}

	@Override
	public void removeView(IViewData view, String perspective) {
		String perspectiveToShow = perspective;

		if (perspectiveToShow == null) {
			perspectiveToShow = NONE;
		}
		List<IViewData> list = perspectiveToViewMap.get(perspectiveToShow);

		if (list != null) {
			list.remove(view);
		}

	}

	@Override
	public List<IViewData> getAllViewsForPerspective(String perspective) {

		String perspectiveToShow = perspective;

		if (perspectiveToShow == null) {
			perspectiveToShow = NONE;
		}

		List<IViewData> list = perspectiveToViewMap.get(perspectiveToShow);

		if (list == null) {
			list = new ArrayList<>();
		}
		return Collections.unmodifiableList(list);
	}

	public void clearData() {
		perspectiveToViewMap.clear();
		perspectiveToViewMap = null;
	}

}
