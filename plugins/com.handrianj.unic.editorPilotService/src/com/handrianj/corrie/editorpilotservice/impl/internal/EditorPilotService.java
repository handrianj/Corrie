package com.handrianj.corrie.editorpilotservice.impl.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.handrianj.corrie.editors.util.editors.ICorrieEditor;
import com.handrianj.corrie.editors.util.editors.ICorrieEditorInput;
import com.handrianj.corrie.editors.util.editors.IEditorPilotService;
import com.handrianj.corrie.editors.util.factories.EditorInputFactory;
import com.handrianj.corrie.editors.util.factories.IEditorInputFactory;

/**
 * @author Heri Andrianjafy
 *
 */
public class EditorPilotService implements IEditorPilotService {

	private Map<UISession, List<IEditorReference>> sessionToHiddenEditorsMap = new ConcurrentHashMap<>();

	private Map<UISession, IWorkbenchPage> sessionToPageMap = new ConcurrentHashMap<>();

	@Override
	public void openEditor(String editorID, Object inputObject, boolean closeOthers, boolean updateInput) {

		if ((editorID == null) || editorID.isEmpty()) {
			return;
		}
		IEditorInputFactory<?> factory = EditorInputFactory.getFactory(editorID);

		ICorrieEditorInput<?> input = factory.createInput(inputObject);

		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {

			boolean editorExists = false;
			IEditorReference[] editorReferences = activePage.getEditorReferences();

			// Find the editor among all the editor
			for (IEditorReference editorReference : editorReferences) {

				// If the editor is here activate it and give it focus
				if (editorReference.getId().equals(editorID)) {
					activePage.activate(editorReference.getEditor(true));
					editorExists = true;
				} else if (closeOthers) {
					activePage.closeEditor(editorReference.getEditor(false), false);
				}
			}

			// If editor doesn't exist then we open it manually
			if (!editorExists) {

				activePage.openEditor(input, editorID);
			}

			if (updateInput) {
				// Update the input of the editor
				updateEditorInput(editorID, input);
			}

		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateEditorInput(String editorID, ICorrieEditorInput input) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] lastEditor = activePage.findEditors(null, editorID, org.eclipse.ui.IWorkbenchPage.MATCH_ID);

		if (lastEditor[0].getEditor(false) instanceof ICorrieEditor) {
			ICorrieEditor<?> editor = (ICorrieEditor<?>) lastEditor[0].getEditor(true);
			editor.updateInput(input);
		}
	}

	@Override
	public void openPerspective(String perspectiveID) {
		// Open the perspective
		IWorkbench workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getWorkbench();
		IPerspectiveRegistry reg = workbench.getPerspectiveRegistry();
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IPerspectiveDescriptor currentPerspective = activePage.getPerspective();
		if (currentPerspective.getId().compareTo(perspectiveID) != 0) {
			activePage.setPerspective(reg.findPerspectiveWithId(perspectiveID));
		}
		activePage.resetPerspective();
	}

	@Override
	public synchronized void closeEditor(String editorID) {

		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		IEditorReference[] existingEditors = activePage.findEditors(null, editorID,
				org.eclipse.ui.IWorkbenchPage.MATCH_ID);

		// If editor is already open, close it before opening it again
		if ((existingEditors.length != 0) && (existingEditors[0].getEditor(true) instanceof ICorrieEditor)) {
			ICorrieEditor<?> editor = (ICorrieEditor<?>) existingEditors[0].getEditor(true);
			editor.getSite().getPage().closeEditor(editor, false);
		}
	}

	@Override
	public synchronized void hideEditor(UISession session, IEditorReference reference) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activePage.hideEditor(reference);

		List<IEditorReference> refList = sessionToHiddenEditorsMap.get(RWT.getUISession());

		if (refList == null) {
			refList = new ArrayList<>();
			sessionToHiddenEditorsMap.put(RWT.getUISession(), refList);
			sessionToPageMap.put(session, activePage);
		}

		refList.add(reference);

	}

	@Override
	public synchronized void showEditor(UISession session, String editorId) {
		IWorkbenchPage activePage = sessionToPageMap.get(session);

		List<IEditorReference> refList = sessionToHiddenEditorsMap.get(RWT.getUISession());

		if (refList != null) {
			// Find the editor among all the editor
			for (IEditorReference editorReference : refList) {

				if (editorReference.getId().compareTo(editorId) == 0) {

					if (refList != null) {
						refList.remove(editorReference);
					}

					activePage.showEditor(editorReference);

					break;
				}

			}
		}

	}

	public void clearData() {
		for (Entry<UISession, List<IEditorReference>> allReferences : sessionToHiddenEditorsMap.entrySet()) {
			List<IEditorReference> value = allReferences.getValue();
			if (value != null) {
				value.clear();
			}
		}
		sessionToHiddenEditorsMap.clear();
	}

	@Override
	public void closeSession(UISession session) {

		List<IEditorReference> allEditors = sessionToHiddenEditorsMap.get(session);
		IWorkbenchPage page = sessionToPageMap.get(session);
		if (allEditors != null) {
			for (IEditorReference iEditorReference : allEditors) {
				page.showEditor(iEditorReference);
			}

			allEditors.clear();
			sessionToHiddenEditorsMap.remove(session);
			sessionToPageMap.remove(session);

		}
	}
}
