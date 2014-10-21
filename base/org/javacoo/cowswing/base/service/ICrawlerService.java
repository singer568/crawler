package org.javacoo.cowswing.base.service;

import java.util.List;

import org.javacoo.persistence.PaginationSupport;

/**
 * 采集器服务公共接口
 * <p>说明：</p>
 * 采集器所有服务都实现此接口
 *@author DuanYong
 *@since 2012-12-1下午8:53:57
 *@param <T> 值对象类型
 *@param <Q> 查询对象类型
 *@version 1.0
 */
public interface ICrawlerService<T,Q> {
	/**
	 * 插入
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:31
	 * @param t
	 * @param sqlMapId
	 * @return int
	 */
	int insert(T t,String sqlMapId);
	/**
	 * 修改
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:43
	 * @param t
	 * @param sqlMapId
	 * @return int
	 */
	int update(T t,String sqlMapId);
	/**
	 * 删除
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:49
	 * @param t
	 * @param sqlMapId
	 * @return int
	 */
	int delete(T t,String sqlMapId);
	/**
	 * 查询
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:54
	 * @param t
	 * @param sqlMapId
	 * @return T
	 */
	T get(T t,String sqlMapId);
	/**
	 * 查询列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:31:08
	 * @param q
	 * @param sqlMapId
	 * @return List<T>
	 */
	List<T> getList(Q q,String sqlMapId);
	/**
	 * 查询列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:31:08
	 * @param q
	 * @param sqlMapId
	 * @return PaginationSupport<T>
	 */
	PaginationSupport<T> getPaginatedList(Q q,String sqlMapId);

}
