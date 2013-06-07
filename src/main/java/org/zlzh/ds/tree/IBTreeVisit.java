/*
 * @(#)IVisit.java  2011-8-8	
 */

package org.zlzh.ds.tree;

import org.zlzh.IObjectVisit;

/**
 * <p>Title:�������ڵ���ʽӿ�</p>
 * <p>Description:����������ʱ���ø÷���</p>
 * @author:  zenglizhi
 * @time:    2011-8-8
 * @version:  v1.0
 * @see:BinaryTree<E>
 * @since:    SR5.0.1
 */
public interface IBTreeVisit<E> extends IObjectVisit<BTNode<E>>{

	/**
	 * ���ʽڵ�������ڵ� node
	 */
	public void visit(BTNode<E> node);
	
	 
}
