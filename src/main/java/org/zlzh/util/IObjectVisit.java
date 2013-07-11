/*
 * @(#)IObjectVisit.java  2013-6-7	
 *
 */

package org.zlzh.util;

/**
 * <p>Title:对象访问接口</p>
 * <p>Description:遍历对象时，传入对象访问接口，在遍历过程中可以对对象进行访问操作</p>
 * T:需要访问的对象类型
 * @author:  zenglizhi
 * @time:    2013-6-7
 * @version:  v1.0
 */
public interface IObjectVisit<T> {

	

	/**
	 * 执行访问操作
	 * @param o 正在访问的对象
	 */
	public void visit(T o);
	
	
	
	
}
