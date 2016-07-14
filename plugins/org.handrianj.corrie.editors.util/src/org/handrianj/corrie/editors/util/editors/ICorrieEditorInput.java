package org.handrianj.corrie.editors.util.editors;

import org.eclipse.ui.IEditorInput;

/**
 * Input used by all the unic editors
 *
 * @author Heri Andrianjafy
 *
 */
public interface ICorrieEditorInput<T extends Object> extends IEditorInput {

	/**
	 * Set the element of the input
	 *
	 * @param element
	 *            Object to set a data
	 */
	void setData(T element);

	/**
	 * Returns the element of the input
	 *
	 * @return the Data of the input
	 */
	T getData();

}
