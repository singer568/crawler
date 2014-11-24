package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.base.utils.DateUtil;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerContent;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 采集内容服务类
 *@author DuanYong
 *@since 2012-11-27上午11:14:36
 *@version 1.0
 */
@Service("crawlerContentService")
public class CrawlerContentServiceImpl extends AbstractCrawlerService<CrawlerContentBean,CrawlerContent,CrawlerContentCriteria>{
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerContentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContent crawlerContent = new CrawlerContent();
		BeanUtils.copyProperties(crawlerContent, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerContent);
		return crawlerContent.getContentId();
	}
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerContentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContent crawlerContent = new CrawlerContent();
		BeanUtils.copyProperties(crawlerContent, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerContent);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerContentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContent crawlerContent = new CrawlerContent();
		BeanUtils.copyProperties(crawlerContent, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerContent);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerContent doGet(CrawlerContentBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("contentId", bean.getContentId());
		return (CrawlerContent) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerContent> doGetList(CrawlerContentCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(null != t.getRuleId()){
//			paramMap.put("ruleId", t.getRuleId());
//		}
		return (List<CrawlerContent>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	
	protected CrawlerContentBean translateBean(CrawlerContent crawlerContent) throws IllegalAccessException, InvocationTargetException{
    	CrawlerContentBean crawlerContentBean = new CrawlerContentBean();
    	BeanUtils.copyProperties(crawlerContentBean, crawlerContent);
    	if(null != crawlerContentBean.getSaveDate()){
    		crawlerContentBean.setSaveDateStr(DateUtil.dateToStr(crawlerContentBean.getSaveDate(), "yyyy-MM-dd HH:mm:ss"));
		}
    	if(null != crawlerContentBean.getViewDate()){
    		crawlerContentBean.setViewDateStr(DateUtil.dateToStr(crawlerContentBean.getViewDate(), "yyyy-MM-dd HH:mm:ss"));
		}
    	return crawlerContentBean;
    }

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerContent> doGetPaginatedList(
			CrawlerContentCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerContent>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}


	
    
}
