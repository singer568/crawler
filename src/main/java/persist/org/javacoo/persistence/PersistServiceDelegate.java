/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.persistence;

import java.util.List;

import org.javacoo.persistence.mybatis.MyBatisPersistence;

/**
 * 持久层服务实现代表类
 * <p/>
 * 框架持久层服务的总实现类，组合了JDBC/SQLMAP三种持久服务实现。
 *@author DuanYong
 *@since 2012-11-6上午11:20:19
 *@version 1.0
 */
public class PersistServiceDelegate implements PersistService{
	/**MyBatis持久服务*/
    private MyBatisPersistence myBatisPersistence;
	
	
	/* (non-Javadoc)
	 * @see org.javacoo.icrawler.persist.PersistService#insertBySqlMap(java.lang.String, java.lang.Object)
	 */
	@Override
	public int insertBySqlMap(String sqlId, Object parameterObject) {
		return myBatisPersistence.save(sqlId, parameterObject);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.icrawler.persist.PersistService#updateBySqlMap(java.lang.String, java.lang.Object)
	 */
	@Override
	public int updateBySqlMap(String sqlId, Object parameterObject) {
		return myBatisPersistence.modify(sqlId, parameterObject);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.icrawler.persist.PersistService#deleteBySqlMap(java.lang.String, java.lang.Object)
	 */
	@Override
	public int deleteBySqlMap(String sqlId, Object parameterObject) {
		return myBatisPersistence.remove(sqlId, parameterObject);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.icrawler.persist.PersistService#findObjectBySqlMap(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object findObjectBySqlMap(String sqlId, Object parameterObject) {
		return myBatisPersistence.get(sqlId, parameterObject);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.icrawler.persist.PersistService#findListBySqlMap(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<?> findListBySqlMap(String sqlId, Object parameterObject) {
		return myBatisPersistence.getList(sqlId, parameterObject);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.persistence.PersistService#findPaginatedListBySqlMap(java.lang.String, java.lang.Object, int, int)
	 */
	@Override
	public PaginationSupport<?> findPaginatedListBySqlMap(String sqlId,
			Object parameterObject, int offset, int limit) {
		return myBatisPersistence.getPaginatedResult(sqlId, parameterObject,offset,limit);
	}
	public MyBatisPersistence getMyBatisPersistence() {
		return myBatisPersistence;
	}
	public void setMyBatisPersistence(MyBatisPersistence myBatisPersistence) {
		this.myBatisPersistence = myBatisPersistence;
	}
	
	
	

}
