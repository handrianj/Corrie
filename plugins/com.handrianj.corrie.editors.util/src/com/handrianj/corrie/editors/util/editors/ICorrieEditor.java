package com.handrianj.corrie.editors.util.editors;

import org.eclipse.ui.IEditorPart;

/**
 * Interface user to define a unic editor
 *
 * @author Heri Andrianjafy
 *
 */
public interface ICorrieEditor<T extends Object> extends IEditorPart {

	/**
	 * Return the editor ID (Usually the canonical name of the class)
	 *
	 * @return The editor ID
	 */
	String getID();

	/**
	 * Updates the input of the editor to change the displayed content
	 *
	 * @param input
	 *            new input to be used by the editor
	 */
	void updateInput(ICorrieEditorInput<T> input);

	/**
	 * Returns the typed editor input
	 *
	 * @return the Input in a ICorrieEditorInputFormat
	 */
	ICorrieEditorInput<T> getInput();

}
