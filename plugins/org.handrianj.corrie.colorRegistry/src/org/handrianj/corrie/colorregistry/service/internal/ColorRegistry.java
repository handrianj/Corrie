package org.handrianj.corrie.colorregistry.service.internal;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.handrianj.corrie.colorregistry.service.IColorRegistry;
import org.handrianj.corrie.ressourcegc.AbstractRessourceGC;

public class ColorRegistry extends AbstractRessourceGC<Integer> implements IColorRegistry {

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
			keyToColor.put(colorCode, col);
			addRessource(colorCode);
		} else {

			updateTimestampForRessource(colorCode);
		}

		return col;
	}

	private int getColorCode(int r, int g, int b, int a) {
		return (r << 24) + (g << 16) + (b << 8) + (a);
	}

	@Override
	protected void clearRessource(Integer key) {
		Color color = keyToColor.get(key);

		keyToColor.remove(key);
		color.dispose();

	}

}
