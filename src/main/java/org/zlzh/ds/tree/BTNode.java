/*
 * @(#)BTNode.java	1.0  2011-8-7
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package org.zlzh.ds.tree;
/**
 * <p>Title: �������ڵ����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010 - 2011</p>
 * @author zenglizhi
 * @date 2011-8-7
 * @version 1.0
 * @see BinaryTree<E>
 * @since 1.0
 */
public class BTNode<E> implements java.io.Serializable{
	
	private static final long serialVersionUID = -5933738420706117236L;
	/**
	 * �ڵ㱣������ݶ���
	 */
	private E data;		 			
    /**
     * ��ڵ㡢�ҽڵ�
     */
	private BTNode<E> left, right;
	/**
	 * ���ڵ�
	 */
	private BTNode<E> parent;			
	/**
	 * 
	 * @param data ���ݶ���
	 */
	public BTNode(E data) {      
        this(data, null, null);
    }
    public BTNode(E data, BTNode<E> left, BTNode<E> right) {
        this.data = data;           
        this.left = left;
        this.right = right;
        if(null!=left)
        	this.left.parent = this;
        if(null!= right)
        	this.right.parent = this;
        this.parent = null;
    }
    /**
     * @return E ���ؽ�����ݶ���
     */
    public E getData() {         
        return data;
    }
    /**
     * �趨����ֵ
     */
    public void setData(E data) { 
        this.data = data;
    }
    /**
     * @return BTNode<E>���������ӽ��
     */
    public BTNode<E> getLeft() {      
        return left;
    }
    /**
     * @return BTNode<E>�������ҽ��
     */
    public BTNode<E> getRight() {     
        return right;
    }
    /**
     * Ϊ��ǰ�ڵ�������ڵ�
     * @param data ���ݶ���
     * @return BTNode<E>�������ڵ�
     */
    public BTNode<E> setLeft(E data){
    	return setLeft(new BTNode<E>(data));
    }
    /**
     * Ϊ��ǰ�ڵ�������ڵ�
     */
    public BTNode<E> setLeft(BTNode<E> left) {
        this.left = left;
        this.left.parent = this;
        return left;
    }
    
    /**
     * Ϊ��ǰ�ڵ������ҽڵ�
     * @param data ���ݶ���
     * @return BTNode<E>������ҽڵ�
     */
    public BTNode<E> setRight(E data){
    	return setRight(new BTNode<E>(data));
    }
    
    /**
	 *Ϊ��ǰ�ڵ�������ڵ�
     */
    public BTNode<E> setRight(BTNode<E> right) {
        this.right = right;
        this.right.parent = this;
        return right;
    }
    
    /**
     * @return BTNode<E>���ظ��ڵ�
     */
    public BTNode<E> getParent(){
    	return parent;
    }
 
}
