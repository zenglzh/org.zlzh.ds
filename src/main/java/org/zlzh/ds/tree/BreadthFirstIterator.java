/*
*   This file reference Brillig Toolkit.
* Copyright  2010 Join-Cheer Corporation Copyright (c) All rights reserved.
*/
package org.zlzh.ds.tree;


import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * <p>Title:双向树结构。广度优先迭代器类</p>
 * <p>Description:Breadth first tree traversal iterator.</p>
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public class BreadthFirstIterator<E> implements TraversalIterator<E> {

	private final LinkedList<Tree<E>> queue;
	private Tree<E> lastNode;

	public BreadthFirstIterator(Tree<E> node) {
		this.queue = new LinkedList<Tree<E>>();
		queue.add(node);
		this.lastNode = null;
	}

	public boolean hasNext() {
		return (queue.size() > 0);
	}

	public Tree<E> next() {
		if (!hasNext())
			throw new NoSuchElementException("no more nodes!");
		Tree<E> result = queue.removeFirst();
		final List<Tree<E>> children = result.getChildren();
		if (children != null)
			queue.addAll(children);
		lastNode = result;
		return result;
	}

	/**
	 * Prune the current iterator node from the tree.
	 */
	public void remove() {
		if (lastNode != null) {
			lastNode.prune(true, true);
			skip();
		}
	}

	/**
	 * Skip traversing into the last "next" node's children.
	 */
	public void skip() {
		if (lastNode != null) {
			// we just added the children to the end of the queue, so lop 'em
			// off.
			final List<Tree<E>> children = lastNode.getChildren();
			if (children != null) {
				for (@SuppressWarnings("unused")Tree<E> child : children)
					queue.removeLast();
			}
		}
	}
}
