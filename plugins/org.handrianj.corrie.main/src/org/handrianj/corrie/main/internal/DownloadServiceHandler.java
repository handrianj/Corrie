package org.handrianj.corrie.main.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rap.rwt.service.ServiceHandler;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.handrianj.corrie.utilsui.IFileDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler used to manage the file download
 *
 * @author Heri Andrianjafy
 *
 */
public class DownloadServiceHandler implements ServiceHandler {

	private static Logger logger = LoggerFactory.getLogger(DownloadServiceHandler.class);

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Which file to download?
		String fileName = request.getParameter("filename");

		if (logger.isDebugEnabled()) {
			logger.debug("Downloading file " + fileName);
		}

		IFileDownloadService fileRegistryService = ServiceRegistry.getFileRegistryService();

		// Get the file content
		byte[] download = fileRegistryService.popFile(fileName);

		if (download != null) {
			// Send the file in the response
			response.setContentType("application/octet-stream");
			response.setContentLength(download.length);
			String contentDisposition = "attachment; filename=\"" + fileName + "\"";
			response.setHeader("Content-Disposition", contentDisposition);
			response.getOutputStream().write(download);
		}

	}
}