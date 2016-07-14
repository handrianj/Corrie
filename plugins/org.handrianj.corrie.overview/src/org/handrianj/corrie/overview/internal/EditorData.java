package org.handrianj.corrie.overview.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.ui.PlatformUI;
import org.handrianj.corrie.editors.util.editors.IEditorPilotService;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.overview.Activator;
import org.handrianj.corrie.overview.lang.IOverviewLang;
import org.handrianj.corrie.serviceregistry.ServiceRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class EditorData extends AbstractMenuItemData {

	private List<String> roleID;
	private ILanguageManagerService languageManagerService;
	private IEditorPilotService pilotService;

	public EditorData(String itemId, int order, Map<Integer, String> langMap, List<String> roleId) {
		super(itemId, order, langMap);
		this.roleID = roleId;
		languageManagerService = ServiceRegistry.getLanguageManagerService();
		pilotService = ServiceRegistry.getPilotService();
	}

	public List<String> getRoleID() {
		return roleID;
	}

	@Override
	public void elementSelected(Object pilotObject) {

		UISession uiSession = RWT.getUISession();

		if (ServiceRegistry.getSessionManager().getUser(RWT.getUISession()) == null) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					languageManagerService.getMessage(Activator.PLUGIN_ID, IOverviewLang.EXPIRE_SESSION, uiSession),
					languageManagerService.getMessage(Activator.PLUGIN_ID, IOverviewLang.EXPIRE_SESSION_INFO,
							uiSession));
			JavaScriptExecutor executor = RWT.getClient().getService(JavaScriptExecutor.class);
			executor.execute("setTimeout(function(){window.location.reload(true);}," + 500 + ");"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (pilotService == null) {
			BundleContext context = Activator.getDefault().getBundle().getBundleContext();
			ServiceReference<?> serviceReference = context.getServiceReference(IEditorPilotService.class.getName());
			pilotService = (IEditorPilotService) context.getService(serviceReference);
		}

		pilotService.openEditor(getId(), pilotObject, false, true);

	}

}
