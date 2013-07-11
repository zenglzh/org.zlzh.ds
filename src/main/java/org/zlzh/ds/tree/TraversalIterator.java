/*
*   This file reference Brillig Toolkit.
*/
package org.zlzh.ds.tree;


import java.util.Iterator;

/**
 * <p>Title:树遍历迭代器接口</p>
 * <p> 
 *  Marker interface for iterating over a tree.
 * </p>
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public interface TraversalIterator<T> extends Iterator<Tree<T>> {

	
	/**
	 * Skip traversing into the last "next" node's children.
	 */
	public void skip();
	
	
	
	
	
}
