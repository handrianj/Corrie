/**
 *
 */
package org.handrianj.corrie.editors.util.factories;

import org.handrianj.corrie.editors.util.editors.ICorrieEditorInput;

/**
 * This interface is used to generate the input of every editor must be declared
 * in each editor using the extension point org.handrianj.corrie.editors.util.inputs
 * must be declared every time you want to use an editor
 *
 * @author Heri Andrianjafy
 *
 */
public interface IEditorInputFactory<T extends Object> {

	/**
	 * Returns the object to set into the input editor
	 *
	 * @param object
	 *            Object to set as the input data
	 * @return a ICorrieEditorInputObject
	 */
	public ICorrieEditorInput<T> createInput(Object object);

}
