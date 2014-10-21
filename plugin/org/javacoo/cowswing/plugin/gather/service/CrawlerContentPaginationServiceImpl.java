package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerContentPagination;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentPaginationBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentPaginationCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 采集内容分页服务类
 *@author DuanYong
 *@since 2012-11-27上午11:16:27
 *@version 1.0
 */
@Service("crawlerContentPaginationService")
public class CrawlerContentPaginationServiceImpl extends AbstractCrawlerService<CrawlerContentPaginationBean,CrawlerContentPagination,CrawlerContentPaginationCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerContentPaginationBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentPagination crawlerContentPagination = new CrawlerContentPagination();
		BeanUtils.copyProperties(crawlerContentPagination, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerContentPagination);
		return crawlerContentPagination.getPaginationId();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerContentPaginationBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentPagination crawlerContentPagination = new CrawlerContentPagination();
		BeanUtils.copyProperties(crawlerContentPagination, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerContentPagination);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerContentPaginationBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentPagination crawlerContentPagination = new CrawlerContentPagination();
		BeanUtils.copyProperties(crawlerContentPagination, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerContentPagination);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerContentPagination doGet(CrawlerContentPaginationBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("paginationId", bean.getPaginationId());
		return (CrawlerContentPagination) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerContentPagination> doGetList(CrawlerContentPaginationCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(null != t.getRuleId()){
//			paramMap.put("ruleId", t.getRuleId());
//		}
//		if(null != t.getContentId()){
//			paramMap.put("contentId", t.getContentId());
//		}
		return (List<CrawlerContentPagination>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	
	protected CrawlerContentPaginationBean translateBean(CrawlerContentPagination crawlerContentPagination) throws IllegalAccessException, InvocationTargetException{
		CrawlerContentPaginationBean crawlerContentPaginationBean = new CrawlerContentPaginationBean();
		BeanUtils.copyProperties(crawlerContentPaginationBean, crawlerContentPagination);
    	return crawlerContentPaginationBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerContentPagination> doGetPaginatedList(
			CrawlerContentPaginationCriteria q, String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerContentPagination>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
}
