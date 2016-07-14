package org.handrianj.corrie.applicationmanagerservice;

import java.util.List;

/**
 * Service used to check the applications to be launched (can work with multiple
 * applications config but it is actually not supported by the whole framework)
 *
 * @author Heri Andrianjafy
 *
 */
public interface IApplicationManagerService {

	/**
	 * Initializes the service
	 */
	void init();

	/**
	 * Returns the application configuration item
	 *
	 * @param application
	 *            The Name of the application to check
	 * @return The configuration for the application asked
	 */
	public IApplicationConfig getConfigForApplication(String application);

	/**
	 * Returns all the applications name available
	 *
	 * @return a List of the name of all the applications available
	 */
	public List<String> getAllAvailableApplications();
}
