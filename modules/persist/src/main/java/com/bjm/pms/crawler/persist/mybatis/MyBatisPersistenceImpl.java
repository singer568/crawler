/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.persist.mybatis;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.bjm.pms.crawler.persist.PaginationSupport;

/**
 * IBatis服务接口实现类
 *
 * @author DuanYong
 * @since 2012-11-6上午9:56:47
 * @version 1.0
 */
public class MyBatisPersistenceImpl extends SqlSessionDaoSupport implements
		MyBatisPersistence {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.icrawler.persistence.mybatis.MyBatisPersistence#save(String
	 * ,Object)
	 */
	@Override
	public int save(String sqlId, Object parameterObject) {
		return getSqlSession().insert(sqlId, parameterObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.icrawler.persistence.mybatis.MyBatisPersistence#modify(String
	 * ,Object)
	 */
	@Override
	public int modify(String sqlId, Object parameterObject) {
		return getSqlSession().update(sqlId, parameterObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.icrawler.persistence.mybatis.MyBatisPersistence#delete(String
	 * ,Object)
	 */
	@Override
	public int remove(String sqlId, final Object parameterObject) {
		return getSqlSession().delete(sqlId, parameterObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.icrawler.persistence.mybatis.MyBatisPersistence#get(String
	 * ,Object)
	 */
	@Override
	public Object get(String sqlId, final Object parameterObject) {
		return getSqlSession().selectOne(sqlId, parameterObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.icrawler.persistence.mybatis.MyBatisPersistence#getList(String
	 * ,Object,int,int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PaginationSupport<?> getPaginatedResult(String sqlId,
			final Object parameterObject, int offset, int limit) {
		Integer count = null;
		try {
			count = (Integer) getSqlSession().selectOne(sqlId + "_COUNT",
					parameterObject);
		} catch (Exception e) {
			String msg = "配置文件中不存在COUNT语句或发生其它例外,无法执行总数统计. SqlMap id:" + sqlId;
			logger.warn(msg, e);
		}
		int tmpOffset = (offset < 0 ? 0 : offset);
		int tmpLimit = (limit <= 0 ? 1 : limit);

		if (count == null || count.intValue() <= 0) {
			logger.trace("执行COUNT后,返回的结果数为0,表示该SQL执行无结果数据返回.因此提前终止其数据查询并立即返回空集.");
			// return new PaginationSupport(new LinkedList(), 0, tmpLimit,
			// tmpOffset);//tmpMaxRows tmpOffset 暂时无用 先注释掉
			return new PaginationSupport(new LinkedList(), 0, tmpOffset,
					tmpLimit);
		}
		List<?> resultList = getSqlSession().selectList(sqlId, parameterObject,
				new RowBounds(tmpOffset, tmpLimit));
		return new PaginationSupport(resultList, count.intValue(), tmpOffset,
				tmpLimit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.persistence.mybatis.MyBatisPersistence#getList(java.lang.
	 * String, java.lang.Object)
	 */
	@Override
	public List<?> getList(String sqlId, Object parameterObject) {
		return getSqlSession().selectList(sqlId, parameterObject);
	}

}
