/*
 * @(#)IObjectAdapter.java  2013-6-26	
 *
 * Copyright  2010 Join-Cheer Corporation Copyright (c) All rights reserved.
 * BEIJING JOIN-CHEER SOFTWARE CO.,LTD
 */

package org.zlzh.util;

/**
 * <p>Title:对象适配接口</p>
 * <p>Description:
 * E:源数据对象的对象类型
 * F:适配以后的数据对象类型
 * </p>
 * @author:  zenglizhi
 * @time:    2013-6-26
 * @version:  v1.0
 * @since:    JQR5.0
 */
public interface IObjectAdapter<E,F> {
	

	/**
	 * 将对象 e 适配成对象 F 
	 * @param e 源对象
	 * @return F 转换以后的对象
	 */
	F adapter(E e);
	
	
	
	
	
}



