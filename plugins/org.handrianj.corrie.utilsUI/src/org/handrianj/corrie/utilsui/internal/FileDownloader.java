package org.handrianj.corrie.utilsui.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.browser.Browser;
import org.handrianj.corrie.utilsui.IFileDownloadService;

public class FileDownloader implements IFileDownloadService {

	private Map<String, byte[]> fileToData = new ConcurrentHashMap<>();

	@Override
	public byte[] popFile(String fileName) {
		byte[] result = fileToData.get(fileName);
		fileToData.remove(fileName);
		return result;
	}

	@Override
	public void download(Browser browser, String fileName, byte[] data) {
		fileToData.put(fileName, data);
		browser.setUrl(createDownloadUrl(fileName));

	}

	private String createDownloadUrl(String filename) {
		StringBuilder url = new StringBuilder();
		url.append(RWT.getServiceManager().getServiceHandlerUrl("downloadServiceHandler"));
		url.append('&').append("filename").append('=').append(filename);
		return url.toString();
	}

}
