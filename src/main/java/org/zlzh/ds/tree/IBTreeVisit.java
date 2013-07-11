/*
 * @(#)IVisit.java  2011-8-8	
 */

package org.zlzh.ds.tree;

import org.zlzh.util.IObjectVisit;

/**
 * <p>Title:�������ڵ���ʽӿ�</p>
 * <p>Description:����������ʱ���ø÷���</p>
 * @author:  zenglizhi
 * @time:    2011-8-8
 * @version:  v1.0
 * @see:BinaryTree<E>
 */
public interface IBTreeVisit<E> extends IObjectVisit<BTNode<E>>{

	/**
	 * ���ʽڵ�������ڵ� node
	 */
	public void visit(BTNode<E> node);
	
	 
}
