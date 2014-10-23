/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.persistence.mybatis;

import java.util.List;

import org.javacoo.persistence.PaginationSupport;

/**
 * IBatis服务接口
 *@author DuanYong
 *@since 2012-11-6上午9:44:55
 *@version 1.0
 */
public interface MyBatisPersistence {
	/**
	 * 通过SQL Mapping工具从映射文件中执行给定ID对应的INSERT SQL语句.
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:31
	 * @param sqlId 在SQL Mapping映射文件中定义的插入标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @return Object 
	 */
	int save(String sqlId,final Object parameterObject);
	/**
	 * 通过SQL Mapping工具从映射文件中执行给定ID对应的UPDATE SQL语句.
	 * <p>方法说明:</p>
	 * <STRONG>[注]:此处更新是通过SQL语句直接更新,会有与Hibernate的缓存不同步问题.请特别留意.</STRONG>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:43
	 * @param sqlId 在SQL Mapping映射文件中定义的更新标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @return int 返回更新数据记录数
	 */
	int modify(String sqlId,final Object parameterObject);
	/**
	 * 删除满足条件的记录.
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:49
	 * @param sqlId 在SQL Mapping映射文件中定义的删除标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @return int 返回成功删除的数据条数
	 */
	int remove(String sqlId,final Object parameterObject);
	/**
	 * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的对象结果集
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:54
	 * @param sqlId 在SQL Mapping映射文件中定义的查询标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @return Object 返回唯一一条满足条件的结果对象
	 */
	Object get(String sqlId, final Object parameterObject);
	/**
	 * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的对象结果集
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 下午12:39:21
	 * @param sqlId 在SQL Mapping映射文件中定义的查询标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @return List<?> 满足条件的对象集合
	 */
	List<?> getList(String sqlId, final Object parameterObject);
	/**
	 * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的分页结果对象
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 下午12:39:21
	 * @param sqlId 在SQL Mapping映射文件中定义的查询标识
	 * @param parameterObject SQL执行中所需要的参数绑定对象
	 * @param offset 查询的起始行数
	 * @param limit 返回的最大行数
	 * @return PaginationSupport<?> 返回分页结果对象
	 */
	PaginationSupport<?> getPaginatedResult(String sqlId, final Object parameterObject,int offset,int limit);
}
