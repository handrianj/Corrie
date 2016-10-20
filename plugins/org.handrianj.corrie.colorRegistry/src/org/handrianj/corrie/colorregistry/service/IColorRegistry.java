package org.handrianj.corrie.colorregistry.service;

import org.eclipse.swt.graphics.Color;
import org.handrianj.corrie.ressourcegc.IRessourceGC;

/**
 * Service used to manage the Eclipse colors in order to save memory. Unused
 * colors for a long time will be deleted.
 *
 * @author Heri Andrianjafy
 *
 */
public interface IColorRegistry extends IRessourceGC<Integer> {

	/**
	 * Generates and returns a color
	 *
	 * @param r
	 *            red value 0-255
	 * @param g
	 *            green value 0-255
	 * @param b
	 *            blue value 0-255
	 * @param a
	 *            alpha value 0-255
	 * @return
	 */
	Color getColor(int r, int g, int b);

	/**
	 * Generates and returns a color
	 *
	 * @param r
	 *            red value 0-255
	 * @param g
	 *            green value 0-255
	 * @param b
	 *            blue value 0-255
	 * @param a
	 *            alpha value 0-255
	 * @return
	 */
	Color getColor(int r, int g, int b, int a);

}
