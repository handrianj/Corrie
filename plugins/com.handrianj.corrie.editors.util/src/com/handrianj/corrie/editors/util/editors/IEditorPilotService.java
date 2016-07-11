package com.handrianj.corrie.editors.util.editors;

import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.ui.IEditorReference;

/**
 * Service used to link multiple editors between themselves. It is impossible to
 * directly open an editor from another editor because of the Dependancy Cycle.
 * So we go trough this service to open or close the different editors from
 * ohter editors.
 *
 * @author Heri Andrianjafy
 *
 */
public interface IEditorPilotService {

	/**
	 * Opens an editor using the editor ID and an object to set as an input of
	 * this editor
	 *
	 * @param editorID
	 *            ID of the editor
	 * @param inputObject
	 *            Input of the editor
	 * @param closeOthers
	 *            True if you want to close others editors, false otherwise
	 * @param updateInput
	 *            True if you want to update the input of the editor, false
	 *            otherwise
	 */
	public void openEditor(String editorID, Object inputObject, boolean closeOthers, boolean updateInput);

	/**
	 * Opens the perspective given in parameter
	 *
	 * @param perspectiveID
	 *            ID of the perspective to open
	 */
	void openPerspective(String perspectiveID);

	/**
	 * Close the editor
	 *
	 * @param editorID
	 *            ID of the editor to close
	 */
	void closeEditor(String editorID);

	/**
	 * Allows the caller to update the input of an already opened editor
	 *
	 * @param editorID
	 *            ID of the editor to update
	 * @param input
	 *            Input of the editor
	 */
	void updateEditorInput(String editorID, ICorrieEditorInput<?> input);

	/**
	 * Hides the editor passed in reference
	 *
	 * @param session
	 *            Current UI Session
	 * @param reference
	 *            IEditorReference to hide
	 */
	void hideEditor(UISession session, IEditorReference reference);

	/**
	 * Shows the editor passed in reference
	 *
	 * @param session
	 *            Current UI session
	 * @param editorId
	 *            ID of the editor to show
	 *
	 *
	 */
	void showEditor(UISession session, String editorId);

	/**
	 * Cleans all the hide/show status for this session before closing
	 * (necessary for disposing hidden editors)
	 *
	 * @param session
	 *            Current UI session
	 */
	void closeSession(UISession session);

}
