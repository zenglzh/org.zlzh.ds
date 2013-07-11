/*
*/
package org.zlzh.ds.tree;


import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * <p>Title:ReverseDepthFirstIterator</p>
 * <p>Description: Depth first tree traversal iterator.</p>
 * @author:  zenglizhi
 * @time:    2013-6-9
 * @version:  v1.0
 */
public class ReverseDepthFirstIterator<T> implements TraversalIterator<T> {

	private final LinkedList<Tree<T>> stack;
	private Tree<T> lastNode;

	public ReverseDepthFirstIterator(Tree<T> node) {
		this.stack = new LinkedList<Tree<T>>();
		for (DepthFirstIterator<T> i = new DepthFirstIterator<T>(node); i
				.hasNext();) {
			final Tree<T> tree = i.next();
			stack.addFirst(tree);
		}
		this.lastNode = null;
	}

	public boolean hasNext() {
		return (stack.size() > 0);
	}

	public Tree<T> next() {
		if (!hasNext())
			throw new NoSuchElementException("no more nodes!");
		// Grab the first element in the stack, which is the last element in the
		// tree
		Tree<T> result = stack.removeFirst();
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
	 * Skip traversing to next adjacent sibling node, if no siblings, back to
	 * parent.
	 */
	public void skip() {
		// TODO: I'm not sure how exactly I want to implement this
		// eventually...for now, this behavior doesn't make sense in
		// a reverse tree traversal, so I'll do it as described above
		stack.removeFirst();
	}
}
