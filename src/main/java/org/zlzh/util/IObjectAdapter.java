/*
 * @(#)IObjectAdapter.java  2013-6-26	
 *
 */

package org.zlzh.util;

/**
 * <p>Title:���������ӿ�</p>
 * <p>Description:
 * E:Դ���ݶ����Ķ�������
 * F:�����Ժ������ݶ�������
 * </p>
 * @author:  zenglizhi
 * @time:    2013-6-26
 * @version:  v1.0
 */
public interface IObjectAdapter<E,F> {
	

	/**
	 * ������ e �����ɶ��� F 
	 * @param e Դ����
	 * @return F ת���Ժ��Ķ���
	 */
	F adapter(E e);
	
	
	
	
	
}



