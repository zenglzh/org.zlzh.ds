/*
 * @(#)IObjectAdapter.java  2013-6-26	
 *
 * Copyright  2010 Join-Cheer Corporation Copyright (c) All rights reserved.
 * BEIJING JOIN-CHEER SOFTWARE CO.,LTD
 */

package org.zlzh.util;

/**
 * <p>Title:��������ӿ�</p>
 * <p>Description:
 * E:Դ���ݶ���Ķ�������
 * F:�����Ժ�����ݶ�������
 * </p>
 * @author:  zenglizhi
 * @time:    2013-6-26
 * @version:  v1.0
 * @since:    JQR5.0
 */
public interface IObjectAdapter<E,F> {
	

	/**
	 * ������ e ����ɶ��� F 
	 * @param e Դ����
	 * @return F ת���Ժ�Ķ���
	 */
	F adapter(E e);
	
	
	
	
	
}



