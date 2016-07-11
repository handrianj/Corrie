package com.handrianj.corrie.main;

import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.handrianj.corrie.applicationmanagerservice.IApplicationConfig;
import com.handrianj.corrie.applicationmanagerservice.IApplicationManagerService;
import com.handrianj.corrie.editors.util.editors.IEditorPilotService;
import com.handrianj.corrie.serviceregistry.ServiceRegistry;

/**
 * Configures the initial size and appearance of a workbench window.
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setShellStyle(SWT.NO_TRIM);
		configurer.setShowMenuBar(true);
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
		configurer.setTitle("UNIC Main");
	}

	@Override
	public void postWindowOpen() {

		IEditorPilotService pilotService = ServiceRegistry.getPilotService();

		IApplicationManagerService applicationService = ServiceRegistry.getApplicationService();

		List<String> allAvailableApplications = applicationService.getAllAvailableApplications();

		String applicationName = allAvailableApplications.get(0);

		if (applicationName != null) {
			openApplication(pilotService, applicationService.getConfigForApplication(applicationName),
					applicationService);
		}

		super.postWindowOpen();
	}

	private void openApplication(IEditorPilotService pilotService, IApplicationConfig applicationConfiguration,
			IApplicationManagerService applicationService) {

		if (applicationConfiguration.getInitialPerspectiveID() != null) {
			pilotService.openPerspective(applicationConfiguration.getInitialPerspectiveID());
		}

		pilotService.openEditor(applicationConfiguration.getInitialEditorID(),
				ServiceRegistry.getSessionManager().getUser(RWT.getUISession()), false, true);

	}

	@Override
	public void postWindowCreate() {
		Shell shell = getWindowConfigurer().getWindow().getShell();
		shell.setMaximized(true);
	}

}
