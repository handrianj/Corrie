package org.handrianj.corrie.colorregistry.service.internal;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.handrianj.corrie.colorregistry.service.IColorRegistry;
import org.handrianj.corrie.ressourcegc.AbstractRessourceGC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorRegistry extends AbstractRessourceGC<Integer> implements IColorRegistry {

	private static Logger logger = LoggerFactory.getLogger(ColorRegistry.class);

	private ConcurrentHashMap<Integer, Color> keyToColor = new ConcurrentHashMap<>();

	public ColorRegistry() {
		super("Color check");
	}

	public ColorRegistry(long checkDelay) {
		super("Color check", checkDelay);
	}

	@Override
	public Color getColor(int r, int g, int b) {

		return getColor(r, g, b, 255);
	}

	@Override
	public Color getColor(int r, int g, int b, int a) {

		int colorCode = getColorCode(r, g, b, a);
		Color col = keyToColor.get(colorCode);

		if (col == null) {

			col = new Color(Display.getCurrent(), r, g, b, a);

			logger.info("Creating color " + colorCode);

			keyToColor.put(colorCode, col);
			addRessource(colorCode);
		} else {

			logger.info("Using color " + colorCode);
			updateTimestampForRessource(colorCode);
		}

		return col;
	}

	private int getColorCode(int r, int g, int b, int a) {
		return (r << 24) + (g << 16) + (b << 8) + (a);
	}

	@Override
	protected void clearRessource(Integer key) {

		logger.info("Clearing color " + key);
		Color color = keyToColor.get(key);

		if (color == null) {
			logger.info("Color is null");
		}

		keyToColor.remove(key);
		color.dispose();

	}

}
