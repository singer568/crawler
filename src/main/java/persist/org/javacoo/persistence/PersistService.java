/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.persistence;

import java.util.List;
/**
 * 持久层服务实现代表类接口
 *@author DuanYong
 *@since 2012-11-6下午8:51:00
 *@version 1.0
 */
public interface PersistService {
	/**
	 * 通过SQLMAP插入数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:31
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @return int
	 */
	int insertBySqlMap(String sqlId,final Object parameterObject);
	/**
	 * 修改
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:43
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @return int
	 */
	int updateBySqlMap(String sqlId,final Object parameterObject);
	/**
	 * 删除
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:49
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @return int
	 */
	int deleteBySqlMap(String sqlId,final Object parameterObject);
	/**
	 * 查询
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:30:54
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @return Object
	 */
	Object findObjectBySqlMap(String sqlId, final Object parameterObject);
	/**
	 * 查询列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:31:08
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @return List<?>
	 */
	List<?> findListBySqlMap(String sqlId,final Object parameterObject);
	/**
	 * 查询分页列表
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-6 上午9:31:08
	 * @param sqlId SQLMAP id
	 * @param parameterObject
	 * @param offset
	 * @param limit
	 * @return PaginationSupport<?>
	 */
	PaginationSupport<?> findPaginatedListBySqlMap(String sqlId,final Object parameterObject,int offset,int limit);

}
