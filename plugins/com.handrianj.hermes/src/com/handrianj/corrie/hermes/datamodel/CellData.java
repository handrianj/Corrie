package com.handrianj.corrie.hermes.datamodel;

/**
 * Class used for storing the cell data
 *
 * @author Heri Andrianjafy
 *
 */
public class CellData {

	private String value = "";

	private short rgbBackground;

	private short rgbForeground;

	private short pattern;

	public CellData(String content) {
		this(content, (short) 0, (short) 0, (short) 0);
	}

	public CellData(String content, short rgbBack, short rgbFront) {
		this(content, rgbBack, rgbFront, (short) 0);
	}

	public CellData(String content, short rgbBack, short rgbFront, short pattern) {
		super();
		this.value = content;
		if (rgbBack != 64) {
			this.rgbBackground = rgbBack;
		} else {
			this.rgbBackground = -1;
		}

		if (rgbFront != 64) {
			this.rgbForeground = rgbFront;
		} else {
			this.rgbForeground = -1;
		}
		this.pattern = pattern;
	}

	public String getValue() {
		return value;
	}

	public short getRgb() {
		return rgbBackground;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setRgbFront(short rgbFront) {
		this.rgbBackground = rgbFront;
	}

	public void setRgbBackground(short rgbBackground) {
		this.rgbBackground = rgbBackground;
	}

	public short getBackgroundIndex() {
		return rgbBackground;
	}

	public short getForegroundIndex() {
		return rgbForeground;
	}

	public short getPattern() {
		return pattern;
	}
}
