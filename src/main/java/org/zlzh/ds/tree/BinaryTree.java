/*
 * @(#)BinaryTree.java	1.0  2011-8-7
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package org.zlzh.ds.tree;

import java.util.Iterator;
import java.util.Stack;

/**
 * <p>Title:˫������� </p>
 * <p>Description: ͨ��ָ��һ�����ڵ㹹�������
 * �ṩ�Զ������ı����͵����Ĳ�����������������Ҫָ���������ڵ���ʽӿ�<code>IBTreeVisit<E></code>,
 * ����ͨ����ȡiterator() ʵ�ֶԶ������ĵ������� ,Ĭ�� pre_iterator��</p>
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
	 * ���ڵ�
	 */
	private transient BTNode<E> _root;
     
	/**
	 * �������ڵ���ʽӿ�
	 */
	private transient IBTreeVisit<E> _treeVisit;
	
    /**
     * ����һ��ֻ�и��ڵ�Ķ�����
     * @param root ���ڵ����
     */
    public BinaryTree(BTNode<E> root) {
        this._root = root;
    }
    
    /**
     * �������ĸ��ڵ� 
     * @return BTNode<E>
     */
    public BTNode<E> getRoot() {
        return _root;
    }
    
    /**
     * ���ö������ڵ������
     */
    public void setTreeVisitor(IBTreeVisit<E> treeVisit){
    	_treeVisit = treeVisit;
    }
    /**
     * ���ʽڵ㣬��Ҫ���ö������ڵ���ʶ��� ��
     * @param node ���ڷ��ʵĽڵ����
     */
    private void visit(BTNode<E> node) {
    	_treeVisit.visit(node);
    }
  
    public boolean isEmpty(){
    	return null == _root;
    }

    /**
     * �ӵ�ǰ���������ڵ㿪ʼ���ҽڵ����ݶ���Ϊe �Ľڵ�
     * @param e �ڵ����ݶ���
     * @return BTNode<E> �ڵ�
     */
    public BTNode<E> find(E e){
    	return find(_root,e);
    }

    /**
     * ָ���ڵ����
     * @param node ָ���Ľڵ�
     * @param e �ڵ����ݶ���
     * @return BTNode<E> �ҵ��Ľڵ�
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
     * �������
     * @exception NullPointerException  δ����ITreeVisit
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
     *  �������
     * @exception NullPointerException  δ����ITreeVisit
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
	 * ������� 
     * @exception NullPointerException  δ����ITreeVisit
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
     * ���ض�����Ԫ�ؽڵ��һ��������
	 * Ĭ���������
	 */
    public Iterator<E> iterator() {
		return preIterator();//default iterator
	}
    /**
     * @return Iterator<E>�������������
     */
	public Iterator<E> preIterator(){
		return new PreItr();
	}
	/**
	 * @return Iterator<E> �������������
	 */
	public Iterator<E> inIterator(){
		return new InItr();
	}
    /**
     * @return Iterator<E> �������������
     */
	public Iterator<E> postIterator(){
		return  new PostItr();
	}

	/////////***** iterator *********///////////////////////
	/**
	 * �������������
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
	 * �������������
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
	 *�������������
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
