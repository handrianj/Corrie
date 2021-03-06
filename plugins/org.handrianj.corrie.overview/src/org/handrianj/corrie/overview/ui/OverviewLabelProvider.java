package org.handrianj.corrie.overview.ui;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.graphics.Image;
import org.handrianj.corrie.datamodel.structure.impl.TreeStructure;
import org.handrianj.corrie.languagemanager.service.ILang;
import org.handrianj.corrie.languagemanager.service.ILanguageManagerService;
import org.handrianj.corrie.overview.Activator;
import org.handrianj.corrie.overview.internal.AbstractMenuItemData;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class OverviewLabelProvider extends BaseLabelProvider implements ILabelProvider {
	private ILanguageManagerService languageManagerService;

	public OverviewLabelProvider() {
		super();
		BundleContext BContext = Activator.getDefault().getBundle().getBundleContext();
		ServiceReference<?> serviceReference = BContext.getServiceReference(ILanguageManagerService.class.getName());
		languageManagerService = (ILanguageManagerService) BContext.getService(serviceReference);
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		ILang currentLanguage = languageManagerService.getCurrentLanguage(RWT.getUISession());
		if (element instanceof TreeStructure) {

			Object value = ((TreeStructure) element).getValue();

			return ((AbstractMenuItemData) value).getText(currentLanguage);

		}
		return null;
	}

}
