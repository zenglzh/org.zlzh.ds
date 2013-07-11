/*
*   This file reference by Brillig Toolkit.
*/
package org.zlzh.ds.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.zlzh.util.Escaper;
import org.zlzh.util.IObjectAdapter;

/**
 * <p> Title:双向树数据结构</p>
 * E: 树节点存放的数据对象
 * @author: zenglizhi
 * @time: 2013-6-9
 * @version: v1.0
 */
public class Tree<E> implements Iterable<Tree<E>>,Cloneable,Serializable{

	private static final long serialVersionUID = -360536055185124669L;


	/**
	 * 树遍历方式
	 */
	public enum Traversal {
		BREADTH_FIRST, DEPTH_FIRST, REVERSE_BREADTH_FIRST, REVERSE_DEPTH_FIRST
	};

	/**
	 * 树节点包含的数据对象
	 */
	private E data;
	/**
	 * 树节点包含的孩子节点
	 */
	private List<Tree<E>> children;
	/**
	 * 树节点的父节点
	 */
	private Tree<E> parent;
	
	/**
	 * 树节点的路径：当前节点到根节点的节点列表。
	 * @see #getPath()
	 */
	private LinkedList<Tree<E>> _path;

	public Tree(E data) {
		this.data = data;
		children = null;
		parent = null;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	/**
	 * @return List<Tree<E>> 孩子节点列表
	 */
	public List<Tree<E>> getChildren() {
		return children;
	}

	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}

	public int numChildren() {
		return children != null ? children.size() : 0;
	}

	/**
	 * @return Tree<E> 父节点
	 */
	public Tree<E> getParent() {
		return parent;
	}
	 /**
	  * @return is leaf node
     */
    public boolean isLeaf(){
    	return !hasChildren();
    }
	
    /**
     * @return boolean is root node ,has no parent
     */
    public boolean isRoot(){
    	return null == parent;
    }
    
	/**
	 * Get the depth of this node in its tree, where the root is at depth 0.
	 */
	public int depth() {
		int result = 0;
		Tree<E> p = parent;
		while (p != null) {
			p = p.parent;
			++result;
		}
		return result;
	}

	/**
	 * Get the depth of this node in its tree from the given ancestor node.
	 */
	public int depth(Tree<E> ancestor) {
		int result = 0;
		Tree<E> p = parent;
		while (p != ancestor && p != null) {
			p = p.parent;
			++result;
		}
		return result;
	}

	/**
	 * Get the number of siblings (including this node).
	 */
	public int getNumSiblings() {
		int result = 0;
		if (parent != null && parent.children != null) {
			result = parent.children.size();
		}
		return result;
	}

	/**
	 * Get this tree's siblings (including this tree), all having the same
	 * parent.
	 */
	public List<Tree<E>> getLocalSiblings() {
		List<Tree<E>> result = new ArrayList<Tree<E>>();
		if (parent == null) {
			result.add(this);
		} else {
			result.addAll(parent.children);
		}
		return result;
	}

	/**
	 * Get this node's local sibling position.
	 */
	public int getLocalSiblingPosition() {
		int result = 0;
		if (parent != null) {
			result = getPosition(this, parent.children);
		}
		return result;
	}

	/**
	 * Get the sibling node that follows this sibling or null if this is the
	 * last sibling.
	 */
	public Tree<E> getNextSibling() {
		Tree<E> result = null;
		if (parent != null) {
			final List<Tree<E>> siblings = parent.children;
			final int sibPos = getPosition(this, siblings) + 1;
			if (sibPos < siblings.size()) {
				result = siblings.get(sibPos);
			}
		}
		return result;
	}

	/**
	 * Get the sibling node that precedes this sibling or null if this is the
	 * first sibling.
	 */
	public Tree<E> getPrevSibling() {
		Tree<E> result = null;
		if (parent != null) {
			final List<Tree<E>> siblings = parent.children;
			final int sibPos = getPosition(this, siblings) - 1;
			if (sibPos >= 0) {
				result = siblings.get(sibPos);
			}
		}

		return result;
	}

	private final int getPosition(Tree<E> node, List<Tree<E>> siblings) {
		int result = 0;

		// NOTE: need to do instance equality, not .equals to find the right
		// node!
		for (Tree<E> sibling : siblings) {
			if (node == sibling)
				return result;
			++result;
		}

		return result;
	}

	/**
	 * Get all nodes at this node's depth (including this tree).
	 */
	public List<Tree<E>> getGlobalSiblings() {
		return getRoot().treesAtDepth(depth());
	}

	/**
	 * Get this node's global sibling position.
	 */
	public int getGlobalSiblingPosition() {
		return getPosition(this, getGlobalSiblings());
	}

	/**
	 * Get the root of this tree.
	 */
	public Tree<E> getRoot() {
		Tree<E> root = this;
		while (root.parent != null) {
			root = root.parent;
		}
		return root;
	}

	/**
	 * Determine whether this node is an ancestor of the other.
	 * <p>
	 * Note that a node is not its own ancestor.
	 * @param other The other node.
	 * @return true if this node is an ancestor of other.
	 */
	public boolean isAncestor(Tree<E> other) {
		Tree<E> parent = other.parent;
		while (parent != null) {
			if (parent == this)
				return true;
			parent = parent.parent;
		}
		return false;
	}

	/**
	 * Determine whether this node is a descendant of the other.
	 * <p>
	 * Note that a node is not its own descendant.
	 * @param other The other node.
	 * @return true if this node is an descendant of other.
	 */
	public boolean isDescendant(Tree<E> other) {
		return other.isAncestor(this);
	}

	/**
	 * Return all trees at the given depth (0-based) from this node, where this
	 * node is depth 0, its children are depth 1, and so on.
	 * <p>
	 * @return the nodes at the specified depth (possibly empty, but not null).
	 */
	public List<Tree<E>> treesAtDepth(int depth) {
		List<Tree<E>> result = new ArrayList<Tree<E>>();
		if (depth == 0) {
			result.add(this);
		} else if (depth > 0) {
			if (children != null) {
				for (Tree<E> child : children) {
					result.addAll(child.treesAtDepth(depth - 1));
				}
			}
		}
		return result;
	}

	/**
	 * Compute (1+) the maximum depth of nodes under this tree (0-based).
	 * <p>
	 * The depth of a tree with no children is 1; etc.
	 */
	public int maxDepth() {
		int depth = 1;
		if (children != null) {
			int maxChildDepth = 0;
			for (Tree<E> child : children) {
				int childDepth = child.maxDepth();
				if (childDepth > maxChildDepth) {
					maxChildDepth = childDepth;
				}
			}
			depth += maxChildDepth;
		}
		return depth;
	}

	/**
	 * Compute the number of leaf nodes in this tree.
	 * <p>
	 * If this tree is a leaf itself(has no children) the number of leaves is 1
	 */
	public int numLeaves() {
		int leaves = 1;
		if (null == children) {
			int childLeafCount = 0;
			for (Tree<E> child : children) {
				childLeafCount += child.numLeaves();
			}
			return childLeafCount;
		}
		return leaves;
	}

	public Tree<E> addChild(E childData) {
		Tree<E> result = new Tree<E>(childData);
		addChild(result);
		return result;
	}

	public void addChild(Tree<E> child) {
		if (null == children) {
			children = new ArrayList<Tree<E>>();
		}
		children.add(child);
		child.parent = this;
	}

	/**
	 * Get the path of nodes from the root to this node.
	 */
	public List<Tree<E>> getPath() {
		if (null == _path) {
			_path = new LinkedList<Tree<E>>();
			Tree<E> curNode = this;
			while (null != curNode) {
				_path.addFirst(curNode);
				curNode = curNode.getParent();
			}
		}
		return _path;
	}

	public List<List<E>> getPaths() {
		return getPathsAux(new ArrayList<E>());
	}

	public String getPathString() {
		final StringBuilder result = new StringBuilder();
		Tree<E> node = this;
		while (null != node) {
			if (result.length() > 0)
				result.insert(0, '.');
			result.insert(0, node.getData().toString());
			node = node.getParent();
		}
		return result.toString();
	}
	
	public Iterator<Tree<E>> iterator() {
		return breadthFirstIterator();// default iterator 
	}
	
	public TraversalIterator<E> breadthFirstIterator(){
		return new BreadthFirstIterator<E>(this);
	}
	
	public TraversalIterator<E> depthFirstIterator(){
		return  new DepthFirstIterator<E>(this);
	}
	/**
	 * Get a TraversalIterator<T>, which is an Iterator<Tree<T>>, starting from
	 * this node.
	 */
	public TraversalIterator<E> iterator(Traversal traversal) {
		TraversalIterator<E> result = null;
		switch (traversal) {
		case REVERSE_DEPTH_FIRST:
			result = new ReverseDepthFirstIterator<E>(this);
			break;
		case DEPTH_FIRST:
			result = depthFirstIterator();
			break;
		case REVERSE_BREADTH_FIRST:
			result = new ReverseBreadthFirstIterator<E>(this);
			break;
		default:
			result = breadthFirstIterator();
		}
		return result;
	}

	/**
	 * Collect the nodes whose data equals the given data.
	 */
	public List<Tree<E>> findNodes(E data, Traversal traversal) {
		List<Tree<E>> result = null;
		for (Iterator<Tree<E>> it = iterator(traversal); it.hasNext();) {
			final Tree<E> node = it.next();
			final E nodeData = node.getData();
			if (nodeData == data || (nodeData != null && nodeData.equals(data))) {
				if (result == null)
					result = new ArrayList<Tree<E>>();
				result.add(node);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param data
	 * @return Tree<E>
	 */
	public Tree<E> findFirst(E data){
		return findFirst(data,Traversal.BREADTH_FIRST);
	}
	
	public Tree<E> findFirst(E data, Traversal traversal) {
		Tree<E> result = null;
		for (Iterator<Tree<E>> it = iterator(traversal); it.hasNext();) {
			final Tree<E> node = it.next();
			final E nodeData = node.getData();
			if (nodeData == data || (nodeData != null && nodeData.equals(data))) {
				result = node;
				break;
			}
		}
		return result;
	}

	/**
	 * Prune this node from its tree.
	 */
	public void prune() {
		this.parent = null;
	}

	/**
	 * Prune this node from its tree as specified.
	 */
	public void prune(boolean disconnectParent, boolean disconnectAsChild) {
		if (disconnectAsChild && parent != null && parent.children != null) {
			parent.children.remove(this);
			if (parent.children.size() == 0)
				parent.children = null;
		}
		if (disconnectParent)
			this.parent = null;
	}

	/**
	 * Move this node's children to another parent.
	 */
	public void moveChildrenTo(Tree<E> newParent) {
		if (this.children != null && this.children.size() > 0) {
			final List<Tree<E>> children = new ArrayList<Tree<E>>(this.children);
			for (Tree<E> child : children) {
				child.prune(true, true);
				newParent.addChild(child);
			}
		}
	}

	/**
	 * Prune this node's children from the tree.
	 * <p>
	 * Note: each child's "parent" will still point to this node. Fix them if
	 * needed.
	 * @return the pruned children.
	 */
	public List<Tree<E>> pruneChildren() {
		final List<Tree<E>> result = children;
		// don't worry about cleaning up back-references in children to this as
		// parent?
		this.children = null;
		return result;
	}
	
	/**
	 * clear children from the tree 
	 */
	public void clear(){
		if(null != children){
			children.clear();
			children = null;
		}
	}

	/**
	 * @return List<Tree<E>> gather all leaves
	 */
	public List<Tree<E>> gatherLeaves() {
		final List<Tree<E>> result = new ArrayList<Tree<E>>();
		_gatherLeaves(result);
		return result;
	}

	private final void _gatherLeaves(List<Tree<E>> result) {
		if (this.children == null)
			result.add(this);
		else {
			for (Tree<E> child : children) {
				child._gatherLeaves(result);
			}
		}
	}

	// O(n) impl -- top down
	public Tree<E> getDeepestCommonAncestor(Tree<E> other) {
		if (other == null)
			return null;
		if (this == other)
			return this;
		Tree<E> result = null;
		final Iterator<Tree<E>> myPathIter = getPath().iterator();
		final Iterator<Tree<E>> otherPathIter = other.getPath().iterator();
		while (myPathIter.hasNext() && otherPathIter.hasNext()) {
			final Tree<E> myNode = myPathIter.next();
			final Tree<E> otherNode = otherPathIter.next();
			if (myNode != otherNode) {
				break; // diverged.
			} else {
				result = myNode;
			}
		}

		return result;
	}

	/**
	 * // O(n-squared) impl -- bottom up public Tree<T>
	 * getDeepestCommonAncestor(Tree<T> other) { if (other == null) return null;
	 * if (this == other) return this;
	 * 
	 * if (isAncestor(other)) return this; if (other.isAncestor(this)) return
	 * other;
	 * 
	 * do { other = other.getParent(); if (other != null &&
	 * other.isAncestor(this)) { return other; } } while (other != null);
	 * 
	 * return null; }
	 */
	// TODO : include toString(String pictureString)? adding/inserting nodes?,
	// deleting/renaming nodes?
	private List<List<E>> getPathsAux(List<E> basePath) {
		List<List<E>> result = new ArrayList<List<E>>();
		List<E> path = new ArrayList<E>(basePath);
		path.add(data);

		if (children != null) {
			for (Iterator<Tree<E>> it = children.iterator(); it.hasNext();) {
				Tree<E> child = it.next();
				result.addAll(child.getPathsAux(path));
			}
		} else {
			result.add(path);
		}

		return result;
	}
	
	@Override
	public String toString() {
		return toString(ESCAPER);
	}

	/**
	 * @param escaper
	 * @return String
	 */
	public String toString(Escaper escaper) {
		String result = null;
		final String data = getData() == null ? "<null>" : getData().toString();

		List<Tree<E>> children = getChildren();
		if (children == null || children.size() == 0) {
			result = escaper.escape(data);
		} else {
			StringBuilder buffer = new StringBuilder();
			buffer.append('(');
			buffer.append(escaper.escape(data));
			for (Iterator<Tree<E>> it = children.iterator(); it.hasNext();) {
				Tree<E> child = it.next();
				buffer.append(' ');
				buffer.append(child.toString(escaper));
			}
			buffer.append(')');
			result = buffer.toString();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		boolean result = false;
		Tree<E> otherTree = null;
		if(other instanceof Tree){
			otherTree = (Tree<E>) other;
		}
		if (otherTree != null) {
			if (data == null && otherTree.data != null) {
				return false;
			} else if ((data == null && otherTree.data == null)
					|| (data.equals(otherTree.data))) {
				if (children == null) {
					result = (otherTree.children == null);
				} else if (otherTree.children != null) {
					result = children.equals(otherTree.children);
				}
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result * 31 + (data == null ? 0 : data.hashCode());
		if (children != null) {
			result = result * 31 + children.hashCode();
		}
		return result;
	}
	

    /**
     * Returns a shallow copy of this <tt>Tree</tt> instance.  (The
     * elements themselves are not copied.)
     * The parent and path are not copied. 
     * @return a clone of this <tt>Tree</tt> instance
     */
    @SuppressWarnings("unchecked")
	@Override
	public Object clone() {
    	Tree<E> t = null;
		try {
		     t = (Tree<E>) super.clone();
		} catch (CloneNotSupportedException e) {
		    // this shouldn't happen, since we are Cloneable
		    throw new InternalError();
		}
		t.parent = null;
		t._path = null;
	    if(null != children){
	    	t.children = null;
		    for (Tree<E> tree : children) {
		    	Tree<E> c = (Tree<E>) tree.clone();
				t.addChild(c);
			}
	    }
	    return t;
    }
    
    /**
     * Returns a <tt>Tree</tt> instance. This instance was replace 
     * the original data of tree node object
     * The method didn't change the structure of a tree, but changed the tree of data objects
     * @param objectAdapter
     * @see com.jiuqi.report.common.utils.IObjectAdapter<E, F>
     * @return Tree<T>
     */
    public <F> Tree<F> adapter(IObjectAdapter<E,F> objectAdapter){
    	Tree<F> t= new Tree<F>(objectAdapter.adapter(data));
    	if(null != children){
    		for (Tree<E> tree : children) {
    			Tree<F> conver = tree.adapter(objectAdapter);
    			t.addChild(conver);
    		}
    	}
    	return t;
    }
    
    
	static final Escaper ESCAPER = new Escaper(new String[][] {
			{ "&", "&amp;" }, // ampersand -- this MUST be one of FIRST 2!
			{ ";", "&sc;" }, // semicolon -- this MUST be one of FIRST 2!

			{ " ", "&space;" }, // space
			{ "\"", "&dq;" }, // double quotes

			{ "(", "&lp;" }, // left paren
			{ ")", "&rp;" }, // right paren
			{ "[", "&lsb;" }, // left square brace
			{ "]", "&rsb;" }, // right square brace

	}, 2);
	
	
}
