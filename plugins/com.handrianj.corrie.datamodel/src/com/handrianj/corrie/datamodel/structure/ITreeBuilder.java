package com.handrianj.corrie.datamodel.structure;

import com.handrianj.corrie.datamodel.structure.impl.TreeStructure;

/**
 * Interface used to create a tree builder. The role of a treebuilder is to
 * build a data tree.
 *
 * @author Henry
 *
 * @param <T>
 *            Any type of Object
 */
public interface ITreeBuilder<T extends Object> {

	/**
	 * Builds a tree
	 *
	 * @return a Tree
	 */
	public TreeStructure<T> buildTree();

}
