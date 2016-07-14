package com.handrianj.corrie.main;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceManager;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.dblink.services.IDBSessionService;
import com.handrianj.corrie.dblink.services.IDAOProvider;
import com.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import com.handrianj.corrie.main.internal.DownloadServiceHandler;
import com.handrianj.corrie.main.lang.IMainLang;
import com.handrianj.corrie.serviceregistry.ServiceRegistry;
import com.handrianj.corrie.sessionmanager.service.ISessionManager;
import com.handrianj.corrie.utilsui.dialog.ui.LogInDialog;

/**
 * This class controls all aspects of the application's execution and is
 * contributed through the plugin.xml.
 */
public class Application implements IApplication {

	private IDBSessionService<IUser> dbservice;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		WorkbenchAdvisor advisor = new ApplicationWorkbenchAdvisor();

		dbservice = ServiceRegistry.getDbservice();

		// Preload pictures here since pictures service need a context
		ServiceRegistry.getPictureRegistryService().preloadPictures();

		ServiceManager manager = RWT.getServiceManager();
		try {
			manager.registerServiceHandler("downloadServiceHandler", new DownloadServiceHandler());
		} catch (IllegalArgumentException ie) {
			// The handler was already registered
		}

		return displayDialog(display, ServiceRegistry.getLanguageManagerService(),
				ServiceRegistry.getServiceProviderForSession(RWT.getUISession()), advisor);

	}

	private Object displayDialog(Display display, ILanguageManagerService languageService, IDAOProvider service,
			WorkbenchAdvisor advisor) {

		// Open the login dialog
		LogInDialog login = new LogInDialog(display.getActiveShell(), languageService);

		// If we clicked on OK
		if (login.open() == 0) {

			// Gets the current User
			IUser activeUser = dbservice.login(login.getLogin(), login.getPassword());

			// if the user has been created
			UISession uiSession = RWT.getUISession();
			if ((activeUser != null) && (activeUser.isActive())) {

				// USe the service manager for the user
				BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
				ServiceReference<?> serviceReference = bundleContext
						.getServiceReference(ISessionManager.class.getName());
				ISessionManager sessionManager = (ISessionManager) bundleContext.getService(serviceReference);

				UISession currentUISession = uiSession;

				// If someone was already connected unconnect
				if (sessionManager.isUserOnline(activeUser)) {

					// Open confirmation message
					if (MessageDialog.openQuestion(display.getActiveShell(),
							languageService.getMessage(Activator.PLUGIN_ID, IMainLang.LOGIN_ERROR_ALREADY_1, uiSession),
							languageService.getMessage(Activator.PLUGIN_ID, IMainLang.LOGIN_ERROR_ALREADY_2,
									uiSession))) {

						sessionManager.logoutUser(activeUser);

						dbservice.closeDBsession(activeUser);

						return loginUser(display, languageService, service, advisor, activeUser, sessionManager,
								currentUISession);
					} else {
						displayDialog(display, languageService, service, advisor);
					}

				}
				return loginUser(display, languageService, service, advisor, activeUser, sessionManager,
						currentUISession);
			} else if ((activeUser != null) && (activeUser.isActive())) {
				MessageDialog.openWarning(display.getActiveShell(),
						languageService.getMessage(Activator.PLUGIN_ID, "LoginDialog_5", uiSession),
						languageService.getMessage(Activator.PLUGIN_ID, "LoginDialog_10", uiSession));
				displayDialog(display, languageService, service, advisor);
			} else {
				MessageDialog.openWarning(display.getActiveShell(),
						languageService.getMessage(Activator.PLUGIN_ID, "LoginDialog_5", uiSession),
						languageService.getMessage(Activator.PLUGIN_ID, "LoginDialog_6", uiSession));
				displayDialog(display, languageService, service, advisor);

			}

			return PlatformUI.createAndRunWorkbench(display, advisor);

		}
		return login;
	}

	@SuppressWarnings("serial")
	private Object loginUser(Display display, final ILanguageManagerService languageService, IDAOProvider service,
			WorkbenchAdvisor advisor, IUser activeUser, final ISessionManager sessionManager,
			final UISession currentUISession) {

		dbservice.initUser(activeUser);
		// Add listener on current session
		currentUISession.addUISessionListener(new UISessionListener() {

			@Override
			public void beforeDestroy(UISessionEvent event) {

				logoutUser(languageService, sessionManager, currentUISession);

			}
		});

		return PlatformUI.createAndRunWorkbench(display, advisor);
	}

	private void logoutUser(ILanguageManagerService languageService, ISessionManager sessionManager,
			UISession currentUISession) {
		ServiceRegistry.getDbservice().closeDBsession(sessionManager.getUser(currentUISession));
		sessionManager.closeSession(currentUISession);
		languageService.clearSession(currentUISession);

	}

	@Override
	public void stop() {
		// Do nothing
	}

}
