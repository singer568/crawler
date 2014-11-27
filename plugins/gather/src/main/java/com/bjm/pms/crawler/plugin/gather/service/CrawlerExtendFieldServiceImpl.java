package com.bjm.pms.crawler.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.gather.domain.CrawlerExtendField;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerExtendFieldCriteria;
import com.bjm.pms.crawler.view.base.service.AbstractCrawlerService;

/**
 * 采集历史服务类
 *@author DuanYong
 *@since 2012-11-27上午11:32:26
 *@version 1.0
 */
@Service("crawlerExtendFieldService")
public class CrawlerExtendFieldServiceImpl extends AbstractCrawlerService<CrawlerExtendFieldBean,CrawlerExtendField,CrawlerExtendFieldCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerExtendFieldBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerExtendField crawlerExtendField = new CrawlerExtendField();
		BeanUtils.copyProperties(crawlerExtendField, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerExtendField);
		return crawlerExtendField.getExtendFieldId();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerExtendFieldBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerExtendField crawlerExtendField = new CrawlerExtendField();
		BeanUtils.copyProperties(crawlerExtendField, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerExtendField);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerExtendFieldBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerExtendField crawlerExtendField = new CrawlerExtendField();
		BeanUtils.copyProperties(crawlerExtendField, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerExtendField);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerExtendField doGet(CrawlerExtendFieldBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("extendFieldId", bean.getExtendFieldId());
		return (CrawlerExtendField) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerExtendField> doGetList(CrawlerExtendFieldCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
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
		return (List<CrawlerExtendField>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	protected CrawlerExtendFieldBean translateBean(CrawlerExtendField crawlerExtendField) throws IllegalAccessException, InvocationTargetException{
		CrawlerExtendFieldBean crawlerExtendFieldBean = new CrawlerExtendFieldBean();
		BeanUtils.copyProperties(crawlerExtendFieldBean, crawlerExtendField);
    	return crawlerExtendFieldBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerExtendField> doGetPaginatedList(
			CrawlerExtendFieldCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerExtendField>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
}
