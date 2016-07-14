package org.handrianj.corrie.applicationmanagerservice;

/**
 * Used for the application configuration contains the editor ID and the initial
 * perspective ID
 *
 * @author Heri Andrianjafy
 *
 */
public interface IApplicationConfig {

	/**
	 * Gets the initial ID of the application
	 *
	 * @return The ID of the editor that will be used as default
	 */
	public String getInitialEditorID();

	/**
	 * Gets the initial perspective of the application (can return null for
	 * default perspective)
	 *
	 * @return the ID of the Perspective that will be used as default, null will
	 *         lead to default perspective
	 */
	public String getInitialPerspectiveID();

}
