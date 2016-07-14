package com.handrianj.corrie.viewsmanager.ui;

import java.util.List;

/**
 * The IViewsManagerService is a service to store all the views data according
 * to a perspective
 * 
 * @author Heri Andrianjafy
 *
 */
public interface IViewsManagerService {

	/**
	 * Adds a view to the service
	 *
	 * @param view
	 *            Viewdata to add
	 * @param perspective
	 *            ID of the perspective that will use the view
	 */
	void addView(IViewData view, String perspective);

	/**
	 * Removes a view from the service
	 *
	 * @param view
	 *            Viewdata to remove
	 * @param perspective
	 *            ID of the perspective that will have the view removed
	 */
	void removeView(IViewData view, String perspective);

	/**
	 * Returns all the views for a perspective
	 *
	 * @param perspective
	 * @return
	 */
	List<IViewData> getAllViewsForPerspective(String perspective);

}
