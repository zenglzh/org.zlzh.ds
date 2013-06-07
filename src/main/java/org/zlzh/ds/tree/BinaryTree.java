/*
 * @(#)BinaryTree.java	1.0  2011-8-7
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package org.zlzh.ds.tree;

import java.util.Iterator;
import java.util.Stack;

/**
 * <p>Title:双向二叉树 </p>
 * <p>Description: 通过指定一个根节点构造二叉树
 * 提供对二叉树的遍历和迭代的操作，遍历二叉树需要指定二叉树节点访问接口<code>IBTreeVisit<E></code>,
 * 建议通过获取iterator() 实现对二叉树的迭代操作 ,默认 pre_iterator。</p>
 * <p>Copyright: Copyright (c) 2010 - 2011</p>
 * @author zenglizhi
 * @date 2011-8-7
 * @version 1.0
 * @see java.lang.Iterable<T>
 * @see BTNode<E>
 * @see IBTreeVisit<E>
 * @since 1.0
 */
public class BinaryTree<E> implements Iterable<E> ,java.io.Serializable{

	private static final long serialVersionUID = 3167707051087942770L;
	
	/**
	 * 根节点
	 */
	private transient BTNode<E> _root;
     
	/**
	 * 二叉树节点访问接口
	 */
	private transient IBTreeVisit<E> _treeVisit;
	
    /**
     * 构造一颗只有根节点的二叉树
     * @param root 根节点对象
     */
    public BinaryTree(BTNode<E> root) {
        this._root = root;
    }
    
    /**
     * 返回树的根节点 
     * @return BTNode<E>
     */
    public BTNode<E> getRoot() {
        return _root;
    }
    
    /**
     * 设置二叉树节点访问器
     */
    public void setTreeVisitor(IBTreeVisit<E> treeVisit){
    	_treeVisit = treeVisit;
    }
    /**
     * 访问节点，需要设置二叉树节点访问对象 。
     * @param node 正在访问的节点对象
     */
    private void visit(BTNode<E> node) {
    	_treeVisit.visit(node);
    }
  
    public boolean isEmpty(){
    	return null == _root;
    }

    /**
     * 从当前二叉树根节点开始查找节点数据对象为e 的节点
     * @param e 节点数据对象
     * @return BTNode<E> 节点
     */
    public BTNode<E> find(E e){
    	return find(_root,e);
    }

    /**
     * 指定节点对象
     * @param node 指定的节点
     * @param e 节点数据对象
     * @return BTNode<E> 找到的节点
     */
    public BTNode<E> find(BTNode<E> node, E e) {
    	if(null == node)
			return null;
    	BTNode<E> n = null;
		if(e.equals(node.getData()))
			return node;
		if (null == n) {
			n = find(node.getLeft(),e);			
		}
		if(null == n)
			n = find(node.getRight(),e);
		return n;
	}
    /**
     * 先序遍历
     * @exception NullPointerException  未设置ITreeVisit
     * @see BinaryTree.setTreeVisitor(IBTreeVisit<E>)
     */
    public void preorder(){
    	preorder(_root);
    }
    private void preorder(BTNode<E> p){
		Stack<BTNode<E>> stack = new Stack<BTNode<E>>();
		while(null != p || !stack.isEmpty()){
			if(null != p){
				visit(p);
				stack.push(p);
				p =p.getLeft();
			}else {
				 p = stack.pop();
				 p = p.getRight();
			}
		}
	}

    /**
     *  中序遍历
     * @exception NullPointerException  未设置ITreeVisit
     * @see BinaryTree.setTreeVisitor(IBTreeVisit<E>)
     */
    public void inorder(){
    	inorder(_root);	
    }
	private void inorder(BTNode<E> p){
		Stack<BTNode<E>> stack = new Stack<BTNode<E>>();
		while(null != p || !stack.isEmpty()){
			if (null !=p) {
				stack.push(p);
				p = p.getLeft();
			}else {
				p = stack.pop();
				visit(p);
				p = p.getRight();
			}
		}
	}
	/**
	 * 后序遍历 
     * @exception NullPointerException  未设置ITreeVisit
     * @see BinaryTree.setTreeVisitor(IBTreeVisit<E>)
	 */
    public void postorder(){
    	postorder(_root);
    }

    private void postorder(BTNode<E> p) {
        if (p != null) {
             postorder(p.getLeft());
             postorder(p.getRight());
             visit(p);
         }
    }
//    private void postorder2(BTNode<E> p){
//        BTNode<E> q = p;
//        Stack<BTNode<E>> stack = new Stack<BTNode<E>>();
//        while(p!=null){
//        	while(p.getLeft()!=null){
//        		stack.push(p);
//        		p = p.getLeft();
//        	}
//            while(p.getRight()==null||p.getRight()==q){
//                visit(p);
//                q = p;
//                if(stack.empty())
//                    return;
//                p = stack.pop();
//            }
//            stack.push(p);
//            p = p.getRight();
//        }
//    }
    /**
     * 返回二叉树元素节点的一个迭代器
	 * 默认先序遍历
	 */
    public Iterator<E> iterator() {
		return preIterator();//default iterator
	}
    /**
     * @return Iterator<E>先序遍历迭代器
     */
	public Iterator<E> preIterator(){
		return new PreItr();
	}
	/**
	 * @return Iterator<E> 中序遍历迭代器
	 */
	public Iterator<E> inIterator(){
		return new InItr();
	}
    /**
     * @return Iterator<E> 后序遍历迭代器
     */
	public Iterator<E> postIterator(){
		return  new PostItr();
	}

	/////////***** iterator *********///////////////////////
	/**
	 * 先序遍历迭代器
	 */
	private class PreItr implements Iterator<E>{
		 Stack<BTNode<E>> stack ;
		 PreItr() {
			stack  = new Stack<BTNode<E>>();
			if (!isEmpty()) {
				stack.push(getRoot());
			}
		}
		 
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		public E next() {
			BTNode<E> node = stack.pop();
			if(null != node.getRight())
				stack.push(node.getRight());
			if(null != node.getLeft())
				stack.push(node.getLeft());
			return node.getData();
		}

		public void remove() {
		}
	}
	/**
	 * 中序遍历迭代器
	 */
	private class InItr implements Iterator<E>{

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public E next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub
			
		}
	}
	 
	/**
	 *后序遍历迭代器
	 */
	 private class PostItr implements Iterator<E> {

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public E next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub
			
		}
	 }


	
	 
	 
	 
	 
	 
}
