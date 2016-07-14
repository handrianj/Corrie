package org.handrianj.corrie.overview.ui;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.handrianj.corrie.datamodel.structure.impl.TreeStructure;
import org.handrianj.corrie.overview.internal.AbstractMenuItemData;
import org.handrianj.corrie.overview.internal.ExtensionReader;

import org.handrianj.corrie.viewsmanager.ui.ICorrieView;

/**
 * View used for the layout overview
 *
 * @author Heri Andrianjafy
 *
 */
public class Overview extends ViewPart implements ICorrieView {

	public static String ID = "com.capsa.unic.overview.ui.overview";

	private TreeViewer treeViewer;

	private Object pilotObject;

	public Overview() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		// Read the extension points to have all items
		TreeStructure<Object> menuTree = ExtensionReader.readExtensionPoint();

		treeViewer = new TreeViewer(composite_1, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setLinesVisible(true);
		treeViewer.setContentProvider(new OverviewContentProvider());
		treeViewer.setComparator(new OverviewComparator());
		treeViewer.setLabelProvider(new OverviewLabelProvider());
		treeViewer.addSelectionChangedListener(createTreeSelectionListener());
		treeViewer.addFilter(new OverviewFilter());

		treeViewer.setInput(menuTree);

	}

	private ISelectionChangedListener createTreeSelectionListener() {
		return new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

				Object selectedNode = selection.getFirstElement();

				if (selectedNode instanceof TreeStructure) {

					if (((TreeStructure<Object>) selectedNode).getValue() instanceof AbstractMenuItemData) {
						AbstractMenuItemData data = (AbstractMenuItemData) ((TreeStructure<Object>) selectedNode)
								.getValue();
						data.elementSelected(pilotObject);
					}
				}
			}
		};

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();

	}

	public static String getID() {
		return ID;
	}

	@Override
	public void updateInput(Object input) {
		pilotObject = input;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
