package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.base.utils.DateUtil;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerHistory;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerHistoryBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerHistoryCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 采集历史服务类
 *@author DuanYong
 *@since 2012-11-27上午11:32:26
 *@version 1.0
 */
@Service("crawlerHistoryService")
public class CrawlerHistoryServiceImpl extends AbstractCrawlerService<CrawlerHistoryBean,CrawlerHistory,CrawlerHistoryCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerHistoryBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerHistory crawlerContentHistory = new CrawlerHistory();
		BeanUtils.copyProperties(crawlerContentHistory, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerContentHistory);
		return crawlerContentHistory.getHistoryId();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerHistoryBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerHistory crawlerContentHistory = new CrawlerHistory();
		BeanUtils.copyProperties(crawlerContentHistory, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerContentHistory);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerHistoryBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerHistory crawlerContentHistory = new CrawlerHistory();
		BeanUtils.copyProperties(crawlerContentHistory, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerContentHistory);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerHistory doGet(CrawlerHistoryBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("historyId", bean.getHistoryId());
		return (CrawlerHistory) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerHistory> doGetList(CrawlerHistoryCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(null != t.getRuleId()){
//			paramMap.put("ruleId", t.getRuleId());
//		}
//		if(null != t.getContentId()){
//			paramMap.put("contentId", t.getContentId());
//		}
//		if(StringUtils.isNotBlank(t.getUrl())){
//			paramMap.put("url", t.getUrl());
//		}
//		if(StringUtils.isNotBlank(t.getTitle())){
//			paramMap.put("title", t.getTitle());
//		}
		return (List<CrawlerHistory>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	protected CrawlerHistoryBean translateBean(CrawlerHistory crawlerContentHistory) throws IllegalAccessException, InvocationTargetException{
		CrawlerHistoryBean crawlerContentHistoryBean = new CrawlerHistoryBean();
		BeanUtils.copyProperties(crawlerContentHistoryBean, crawlerContentHistory);
		if(null != crawlerContentHistoryBean.getDate()){
			crawlerContentHistoryBean.setDateStr(DateUtil.dateToStr(crawlerContentHistoryBean.getDate(), "yyyy-MM-dd HH:mm:ss"));
		}
    	return crawlerContentHistoryBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerHistory> doGetPaginatedList(
			CrawlerHistoryCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerHistory>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
}
