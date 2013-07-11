/*
*   This file reference Brillig Toolkit.
*/
package org.zlzh.ds.tree;


import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>Title:Depth first tree traversal iterator</p>
 * <p>Description:</p>
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public class DepthFirstIterator<T> implements TraversalIterator<T> {

	private final LinkedList<Tree<T>> queue;
	private Tree<T> lastNode;

	public DepthFirstIterator(Tree<T> node) {
		this.queue = new LinkedList<Tree<T>>();
		queue.add(node);
	}

	public boolean hasNext() {
		return (queue.size() > 0);
	}

	public Tree<T> next() {
		if (!hasNext())
			throw new NoSuchElementException("no more nodes!");
		Tree<T> result = queue.removeFirst();
		final List<Tree<T>> children = result.getChildren();
		if (children != null)
			queue.addAll(0, children);
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
			// we just added the children to the beginning of the queue, so lop
			// 'em off.
			final List<Tree<T>> children = lastNode.getChildren();
			if (children != null) {
				for (@SuppressWarnings("unused") Tree<T> child : children)
					queue.removeFirst();
			}
		}
	}
}
