/*
 * @(#)IObjectVisit.java  2013-6-7	
 *
 */

package org.zlzh.util;

/**
 * <p>Title:������ʽӿ�</p>
 * <p>Description:��������ʱ�����������ʽӿڣ��ڱ��������п��ԶԶ�����з��ʲ���</p>
 * T:��Ҫ���ʵĶ�������
 * @author:  zenglizhi
 * @time:    2013-6-7
 * @version:  v1.0
 */
public interface IObjectVisit<T> {

	

	/**
	 * ִ�з��ʲ���
	 * @param o ���ڷ��ʵĶ���
	 */
	public void visit(T o);
	
	
	
	
}
