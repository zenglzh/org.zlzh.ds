/*
 * @(#)IVisit.java  2011-8-8	
 */

package org.zlzh.ds.tree;

import org.zlzh.util.IObjectVisit;

/**
 * <p>Title:二叉树节点访问接口</p>
 * <p>Description:遍历二叉树时调用该方法</p>
 * @author:  zenglizhi
 * @time:    2011-8-8
 * @version:  v1.0
 * @see:BinaryTree<E>
 */
public interface IBTreeVisit<E> extends IObjectVisit<BTNode<E>>{

	/**
	 * 访问节点二叉树节点 node
	 */
	public void visit(BTNode<E> node);
	
	 
}
