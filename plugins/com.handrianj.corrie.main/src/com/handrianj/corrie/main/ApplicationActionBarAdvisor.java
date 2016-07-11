package com.handrianj.corrie.main;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.main.lang.IMainLang;
import com.handrianj.corrie.utilsui.dialog.action.LogoutAction;
import com.handrianj.corrie.utilsui.dialog.action.UpdatePasswordAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IAction logoutAction;
	private IAction updatePasswordAction;
	private ILanguageManagerService languageService;

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);

		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext
				.getServiceReference(ILanguageManagerService.class.getName());
		languageService = (ILanguageManagerService) bundleContext.getService(serviceReference);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

		/*
		 * exitAction = ActionFactory.QUIT.create(window); register(exitAction);
		 */
		logoutAction = new LogoutAction(window, languageService);
		updatePasswordAction = new UpdatePasswordAction(window, languageService, false);
		register(logoutAction);
		register(updatePasswordAction);

	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {

		UISession uiSession = RWT.getUISession();
		MenuManager sessionMenu = new MenuManager(
				"&" + languageService.getMessage(Activator.PLUGIN_ID, IMainLang.SESSION_MENU_1, uiSession),
				languageService.getMessage(Activator.PLUGIN_ID, IMainLang.SESSION_MENU_1, uiSession));

		menuBar.add(sessionMenu);

		// Session menu add logout
		sessionMenu.add(updatePasswordAction);
		sessionMenu.add(logoutAction);

	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		// Nothing in the coolbar yet
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		coolBar.add(logoutAction);
	}
}
