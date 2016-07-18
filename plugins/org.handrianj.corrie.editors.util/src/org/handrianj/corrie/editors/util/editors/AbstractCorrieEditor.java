package org.handrianj.corrie.editors.util.editors;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.EditorPart;
import org.handrianj.corrie.editors.util.Activator;
import org.handrianj.corrie.editors.util.factories.EditorInputFactory;
import org.handrianj.corrie.editors.util.factories.IEditorInputFactory;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerListener;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.viewsmanager.ui.ICorrieView;
import org.handrianj.corrie.viewsmanager.ui.IViewData;
import org.handrianj.corrie.viewsmanager.ui.IViewsManagerService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Standard abstract editor for Corrie
 *
 * @author U477733
 *
 * @param <T>
 */
public abstract class AbstractCorrieEditor<T extends Object> extends EditorPart
		implements ICorrieEditor<T>, ILanguageManagerListener {

	private IEditorPilotService pilotService;
	private IEditorInputFactory<T> factory;

	@Override
	public void createPartControl(Composite parent) {
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext.getServiceReference(IEditorPilotService.class.getName());
		pilotService = (IEditorPilotService) bundleContext.getService(serviceReference);
		factory = (IEditorInputFactory<T>) EditorInputFactory.getFactory(getID());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// nothing to do

	}

	@Override
	public void doSaveAs() {
		// nothing to do

	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Used to update all the currently openned Views with the object in
	 * parameter
	 *
	 * @param obj
	 */
	protected void updateViewsInput(Object obj) {

		IWorkbench workbench = getSite().getWorkbenchWindow().getWorkbench();

		// Use the service to open the view with the perspective
		BundleContext context = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = context.getServiceReference(IViewsManagerService.class.getName());
		IViewsManagerService service = (IViewsManagerService) context.getService(serviceReference);

		List<IViewData> allViewsForPerspective = service
				.getAllViewsForPerspective(getSite().getWorkbenchWindow().getActivePage().getPerspective().getId());

		IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();

		for (IViewData viewData : allViewsForPerspective) {

			IViewPart findView = activePage.findView(viewData.getID());

			if ((findView != null) && (findView instanceof ICorrieView)) {
				((ICorrieView) findView).updateInput(obj);
			}
		}
	}

	@Override
	public void dispose() {
		BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = bundleContext
				.getServiceReference(ILanguageManagerService.class.getName());
		ILanguageManagerService languageManagerService = (ILanguageManagerService) bundleContext
				.getService(serviceReference);
		languageManagerService.removeLanguageManagerListener(this, RWT.getUISession());
		super.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ICorrieEditorInput<T> getInput() {
		if (getEditorInput() instanceof ICorrieEditorInput<?>) {
			return (ICorrieEditorInput<T>) getEditorInput();

		}
		return null;
	}

	@Override
	public void updateInput(ICorrieEditorInput<T> input) {
		setInput(input);

	}

	protected void generateAndSetInput(Object objectInput) {
		ICorrieEditorInput<T> leadstransRatio = factory.createInput(objectInput);
		pilotService.updateEditorInput(getID(), leadstransRatio);
	}

	protected GridLayout getZeroMarginLayout(int nbCol, boolean sameSize) {
		GridLayout layout = new GridLayout(nbCol, sameSize);
		layout.marginWidth = layout.marginHeight = 0;
		return layout;
	}

}
