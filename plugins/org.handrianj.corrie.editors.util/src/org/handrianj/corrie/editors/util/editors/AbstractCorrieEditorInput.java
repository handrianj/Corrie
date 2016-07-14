package org.handrianj.corrie.editors.util.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

/**
 * Abstract class used to define a unic editor input
 *
 * @author Heri Andrianjafy
 *
 */
public abstract class AbstractCorrieEditorInput<T extends Object> implements ICorrieEditorInput<T> {

	private T data;

	public AbstractCorrieEditorInput() {
		// nothing to do
	}

	@Override
	public boolean exists() {
		// nothing to do
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// nothing to do
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		// nothing to do
		return null;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// nothing to do
		return null;
	}

	@Override
	public void setData(T element) {
		data = element;
	}

	@Override
	public T getData() {
		return data;
	}

}
