package com.handrianj.corrie.utilsui;

import org.eclipse.swt.browser.Browser;

/**
 * Interface used for IO services it will be a stack of temporary files stored
 * as bytes
 *
 * @author Heri Andrianjafy
 *
 */
public interface IFileDownloadService {

	/**
	 * Recovers the data from the file in the stack. WARNING the file will be
	 * deleted from the stack
	 *
	 * @param fileName
	 *            name of the file
	 * @return a byte array representing the file
	 */
	public byte[] popFile(String fileName);

	/**
	 * Launches the download for a file
	 *
	 * @param browser
	 *            browser to be used for download
	 * @param fileName
	 *            Name of the File
	 * @param data
	 *            byte array represeting the file
	 */
	public void download(Browser browser, String fileName, byte[] data);

}
