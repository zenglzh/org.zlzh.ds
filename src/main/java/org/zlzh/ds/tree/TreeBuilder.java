/*
*   This file reference Brillig Toolkit.
*/
package org.zlzh.ds.tree;


/**
 * Factory interface for creating trees from strings and vice versa.
 *
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public interface TreeBuilder <E> {

	/**
	 * Build a tree from the given string.
	 * <p>
	 * The returned Tree can be used as input to the buildString method to
	 * create a String.
	 * <p>
	 * The returned Tree will typically hold to the property that
	 * treeString.equals(buildString(tree)). In some implementations, the
	 * transformation can be lossy such that this property does not hold.
	 */
	public Tree<E> buildTree(String treeString);

	/**
	 * Build a string representing the given tree.
	 * <p>
	 * The returned string will always serve as input to the buildTree method to
	 * generate a tree.
	 * <p>
	 * The returned string will typically hold to the property that
	 * tree.equals(buildTree(string)). In some implementations, the
	 * transformation can be lossy such that this property does not hold.
	 */
	public String buildString(Tree<E> tree);
}
