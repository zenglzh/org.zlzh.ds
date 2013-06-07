/*
 * @(#)BTNode.java	1.0  2011-8-7
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package org.zlzh.ds.tree;
/**
 * <p>Title: 二叉树节点对象</p>
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
	 * 节点保存的数据对象
	 */
	private E data;		 			
    /**
     * 左节点、右节点
     */
	private BTNode<E> left, right;
	/**
	 * 父节点
	 */
	private BTNode<E> parent;			
	/**
	 * 
	 * @param data 数据对象
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
     * @return E 返回结点数据对象
     */
    public E getData() {         
        return data;
    }
    /**
     * 设定结点的值
     */
    public void setData(E data) { 
        this.data = data;
    }
    /**
     * @return BTNode<E>返回其左孩子结点
     */
    public BTNode<E> getLeft() {      
        return left;
    }
    /**
     * @return BTNode<E>返回其右结点
     */
    public BTNode<E> getRight() {     
        return right;
    }
    /**
     * 为当前节点生成左节点
     * @param data 数据对象
     * @return BTNode<E>构造的左节点
     */
    public BTNode<E> setLeft(E data){
    	return setLeft(new BTNode<E>(data));
    }
    /**
     * 为当前节点设置左节点
     */
    public BTNode<E> setLeft(BTNode<E> left) {
        this.left = left;
        this.left.parent = this;
        return left;
    }
    
    /**
     * 为当前节点生成右节点
     * @param data 数据对象
     * @return BTNode<E>构造的右节点
     */
    public BTNode<E> setRight(E data){
    	return setRight(new BTNode<E>(data));
    }
    
    /**
	 *为当前节点设置左节点
     */
    public BTNode<E> setRight(BTNode<E> right) {
        this.right = right;
        this.right.parent = this;
        return right;
    }
    
    /**
     * @return BTNode<E>返回父节点
     */
    public BTNode<E> getParent(){
    	return parent;
    }
 
}
