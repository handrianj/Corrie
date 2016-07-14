package org.handrianj.corrie.main;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;

/**
 * This workbench advisor creates the window advisor, and specifies the
 * perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		// Always launch the default empty perspective
		return InitPerpective.ID;
	}

	@Override
	public boolean preShutdown() {
		ServiceRegistry.getPilotService().closeSession(RWT.getUISession());
		return super.preShutdown();
	}
}
