package com.handrianj.corrie.datamodel.structure.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Tree structure used to store anything in the form of a tree
 *
 * @author Heri Andrianjafy
 *
 * @param <T>
 */
public class TreeStructure<T> {

	private T currentElement;

	private Set<TreeStructure<T>> childrenElements = new HashSet<>();

	private TreeStructure<T> parent;

	public TreeStructure(T currentElement) {
		this.currentElement = currentElement;
	}

	public boolean addChildren(TreeStructure<T> children) {
		children.setParent(this);
		if (!childrenElements.add(children)) {
			children.setParent(null);
			return false;
		}
		return true;

	}

	public Set<TreeStructure<T>> getChildren() {
		return Collections.unmodifiableSet(childrenElements);
	}

	public T getValue() {
		return currentElement;
	}

	public void setParent(TreeStructure<T> parent) {
		this.parent = parent;
	}

	public TreeStructure<T> getParent() {
		return parent;
	}

	public void purgeTree() {
		currentElement = null;
		for (TreeStructure<T> child : childrenElements) {
			child.purgeTree();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((currentElement == null) ? 0 : currentElement.hashCode());
		result = (prime * result) + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TreeStructure other = (TreeStructure) obj;
		if (currentElement == null) {
			if (other.currentElement != null) {
				return false;
			}
		} else if (!currentElement.equals(other.currentElement)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}

}
