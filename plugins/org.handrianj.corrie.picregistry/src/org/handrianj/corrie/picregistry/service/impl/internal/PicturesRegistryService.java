package org.handrianj.corrie.picregistry.service.impl.internal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.handrianj.corrie.picregistry.Activator;
import org.handrianj.corrie.picregistry.service.IPictureRegistryService;
import org.handrianj.corrie.ressourcegc.AbstractRessourceGC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicturesRegistryService extends AbstractRessourceGC<String> implements IPictureRegistryService {

	private static Logger logger = LoggerFactory.getLogger(PicturesRegistryService.class);

	private static final String NO_PICTURE_KEY = "icons/nopic.png";

	private static final String EXTENSION_POINT = "org.handrianj.corrie.picregistry.preload";

	private static final String ATTRIB_PLUGIN_ID = "PluginID";

	private static final String ATTRIB_PICTURE_ADRESS_ID = "PictureName";

	private static final String ATTRIB_DISABLED_ID = "CanBeDisabled";

	private static final String ITEM_FOLDER = "Folder";

	private static final String ATTRIB_VERSIONS_NUM = "NumberOfVersions";

	private static final String ATTRIB_EXTENSION = "Extension";

	private static final String ITEM_PRELOADED_PICTURE = "PreloadedPicture";

	private Map<String, Image> registry;

	public PicturesRegistryService() {
		super("Picture management");
	}

	@Override
	public void preloadPictures() {

		// If registry not null, no need to preload pictures again
		if (registry == null) {

			logger.info("Registry initialization");

			// registry = new ImageRegistry();
			registry = new ConcurrentHashMap<>();

			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint point = registry.getExtensionPoint(EXTENSION_POINT);
			if (point != null) {

				IExtension[] extensions = point.getExtensions();
				for (IExtension iExtension : extensions) {
					for (final IConfigurationElement element : iExtension.getConfigurationElements()) {

						String pluginID = element.getAttribute(ATTRIB_PLUGIN_ID);

						readPictures(element, pluginID, "");

					}
				}
			}

			logger.info("Registry initialization complete with {} pictures total ", this.registry.size());
		}

	}

	private void readPictures(final IConfigurationElement element, String pluginID, String parentFolder) {
		IConfigurationElement[] folderList = element.getChildren(ITEM_FOLDER);

		for (IConfigurationElement currentFolder : folderList) {
			String folderAttrib = currentFolder.getAttribute(ITEM_FOLDER) + "/";

			if (logger.isDebugEnabled()) {
				logger.debug("Exploring folder " + folderAttrib);
			}

			IConfigurationElement[] currentPictures = currentFolder.getChildren(ITEM_PRELOADED_PICTURE);

			for (IConfigurationElement picture : currentPictures) {

				String pictureAdress = picture.getAttribute(ATTRIB_PICTURE_ADRESS_ID);
				String disabled = picture.getAttribute(ATTRIB_DISABLED_ID);
				int iterations = Integer.parseInt(picture.getAttribute(ATTRIB_VERSIONS_NUM));

				String extension = picture.getAttribute(ATTRIB_EXTENSION);

				String suffix = "";
				for (int i = 0; i < iterations; i++) {

					if (i != 0) {
						suffix = "_" + i;
					}

					String imageAdress = folderAttrib + pictureAdress + suffix + extension;

					if (logger.isDebugEnabled()) {
						logger.debug("Generating picture for " + imageAdress);
					}

					generateImage(pluginID, imageAdress, Boolean.parseBoolean(disabled));

				}

			}

			readPictures(currentFolder, pluginID, parentFolder + folderAttrib);

		}
	}

	@Override
	public void generateImage(String pluginID, String imageAdress, boolean generateDisabled) {

		String key = pluginID + imageAdress;

		// Get pic from the registry
		Image image = registry.get(key);

		// If image is null then load it
		if (image == null) {
			ImageDescriptor imgDescriptor = Activator.imageDescriptorFromPlugin(pluginID, imageAdress);

			// If the picture was loaded succesfully
			if (imgDescriptor != null) {
				image = imgDescriptor.createImage();

				registry.put(key, image);

				addRessource(key);

				if (generateDisabled) {
					key += IPictureRegistryService.DISABLED_PICTURES_ID;

					Image img = new Image(Display.getDefault(), image, SWT.IMAGE_DISABLE);
					registry.put(key, img);
					addRessource(key);
				}
			}
			// Else something bad happened, try load the error picture
			// (preloaded via the extension point)
			else {

				image = registry.get(Activator.PLUGIN_ID + NO_PICTURE_KEY);

			}
		} else {

			updateTimestampForRessource(key);

		}
	}

	@Override
	public String getKey(String pluginID, String imageAdress, boolean isEnabled) {
		String key = pluginID + imageAdress;

		if (!isEnabled) {
			key += DISABLED_PICTURES_ID;
		}
		return key;
	}

	@Override
	public Image getImage(String pluginId, String imgAdress) {
		return getImage(pluginId, imgAdress, true);
	}

	@Override
	public Image getImage(String pluginID, String imageAdress, boolean isEnabled) {
		String key = getKey(pluginID, imageAdress, isEnabled);
		Image image = registry.get(key);

		// If picture is null then return the default picture
		if (image == null) {

			logger.info("Image for " + key + " not found ! using no picture key");

			// We call generate in order to make sure
			image = registry.get(Activator.PLUGIN_ID + NO_PICTURE_KEY);

		} else {

			updateTimestampForRessource(getKey(pluginID, imageAdress, isEnabled));
		}

		return image;
	}

	public void clearData() {

		Collection<Image> values = registry.values();

		for (Image image : values) {
			image.dispose();
		}
		registry.clear();
		registry = null;
	}

	@Override
	protected void clearRessource(String key) {
		Image image = registry.get(key);

		registry.remove(key);
		image.dispose();
	}
}
