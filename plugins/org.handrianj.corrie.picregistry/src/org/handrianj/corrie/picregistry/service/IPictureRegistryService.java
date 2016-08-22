package org.handrianj.corrie.picregistry.service;

import org.eclipse.swt.graphics.Image;
import org.handrianj.corrie.ressourcegc.IRessourceGC;

/**
 * Service used for the picture registry, use it when it comes to using pictures
 * in order to save JAVA MEMORY. Unused pictures for a long time will be
 * automatically disposed
 *
 * @author Heri Andrianjafy
 *
 */
public interface IPictureRegistryService extends IRessourceGC<String> {

	/**
	 * ID used to define the disabled pictures
	 */
	public static String DISABLED_PICTURES_ID = "disabledPicture";

	/**
	 * Generates and returns an image for use
	 *
	 * @param pluginID
	 *            ID of the plugin Calling the service
	 * @param imageAdress
	 *            Path of the picture inside the original plugin
	 * @param generateDisabled
	 *            TRUE if you want to generate disabled picture, FALSE otherwise
	 * @return a SWT Image to be used
	 */
	Image generateImage(String pluginID, String imageAdress, boolean generateDisabled);

	/**
	 * Generates the key to find the picture using multiple datas
	 *
	 * @param pluginID
	 *            ID of the plugin Calling the service
	 * @param imageAdress
	 *            Path of the picture inside the original plugin
	 * @param isDisabled
	 *            TRUE if you want to get the disabled picture, FALSE otherwise
	 * @return a String that will represent the ID of the picture
	 */
	String getKey(String pluginID, String imageAdress, boolean isEnabled);

	/**
	 * Returns the picture (by default the activated picture will be loaded)
	 *
	 * @param pluginId
	 *            ID of the plugin Calling the service
	 * @param imageAdress
	 *            Path of the picture inside the original plugin
	 * @return a SWT image to be used
	 */
	Image getImage(String pluginId, String imageAdress);

	/**
	 * Returns the picture using multiple datas
	 *
	 * @param pluginID
	 *            ID of the plugin Calling the service
	 * @param imageAdress
	 *            Path of the picture inside the original plugin
	 * @param isDisabled
	 *            True if you want to get the disabled picture, false otherwise
	 * @return
	 */
	Image getImage(String pluginID, String imageAdress, boolean isEnabled);

	/**
	 * Preloads the picture into the application when application launch please
	 * note that if pictures were already preloaded, there will be no effect
	 */
	void preloadPictures();

}
