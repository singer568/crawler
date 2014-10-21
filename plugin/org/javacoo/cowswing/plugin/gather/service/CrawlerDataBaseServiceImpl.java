/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerDataBase;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 数据库服务对象
 *@author DuanYong
 *@since 2013-2-14上午9:21:31
 *@version 1.0
 */
@Service("crawlerDataBaseService")
public class CrawlerDataBaseServiceImpl extends AbstractCrawlerService<CrawlerDataBaseBean,CrawlerDataBase,CrawlerDataBaseCriteria> {

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(org.javacoo.crawler.plugin.service.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doInsert(CrawlerDataBaseBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerDataBase crawlerDataBase = new CrawlerDataBase();
		BeanUtils.copyProperties(crawlerDataBase, t);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerDataBase);
		return crawlerDataBase.getDataBaseId();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(org.javacoo.crawler.plugin.service.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doUpdate(CrawlerDataBaseBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerDataBase crawlerDataBase = new CrawlerDataBase();
		BeanUtils.copyProperties(crawlerDataBase, t);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerDataBase);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(org.javacoo.crawler.plugin.service.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doDelete(CrawlerDataBaseBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerDataBase crawlerDataBase = new CrawlerDataBase();
		BeanUtils.copyProperties(crawlerDataBase, t);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerDataBase);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(org.javacoo.crawler.plugin.service.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected CrawlerDataBase doGet(CrawlerDataBaseBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("dataBaseId", t.getDataBaseId());
		return (CrawlerDataBase)this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerDataBase> doGetList(CrawlerDataBaseCriteria q,
			String sqlMapId) throws IllegalAccessException,
			InvocationTargetException {
		return (List<CrawlerDataBase>)this.getPersistService().findListBySqlMap(sqlMapId, q);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerDataBase> doGetPaginatedList(
			CrawlerDataBaseCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerDataBase>)this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#translateBean(java.lang.Object)
	 */
	@Override
	protected CrawlerDataBaseBean translateBean(CrawlerDataBase e)
			throws IllegalAccessException, InvocationTargetException {
		CrawlerDataBaseBean crawlerDataBaseBean = new CrawlerDataBaseBean();
		BeanUtils.copyProperties(crawlerDataBaseBean, e);
		return crawlerDataBaseBean;
	}

}
