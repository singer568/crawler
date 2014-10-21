package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.base.utils.FileUtils;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerContentResource;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 采集内容资源服务类
 *@author DuanYong
 *@since 2012-11-27上午11:29:00
 *@version 1.0
 */
@Service("crawlerContentResourceService")
public class CrawlerContentResourceServiceImpl extends AbstractCrawlerService<CrawlerContentResourceBean,CrawlerContentResource,CrawlerContentResourceCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerContentResourceBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentResource crawlerContentResource = new CrawlerContentResource();
		BeanUtils.copyProperties(crawlerContentResource, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerContentResource);
		return crawlerContentResource.getResourceId();
	}
	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerContentResourceBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentResource crawlerContentResource = new CrawlerContentResource();
		BeanUtils.copyProperties(crawlerContentResource, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerContentResource);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerContentResourceBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerContentResource crawlerContentResource = new CrawlerContentResource();
		BeanUtils.copyProperties(crawlerContentResource, bean);
		//删除文件系统资源
		deleteResource(bean);
		int resultNum = this.getPersistService().deleteBySqlMap(sqlMapId, crawlerContentResource);
		return resultNum;
	}
	/**
	 * 删除资源
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-23 下午5:04:56
	 * @version 1.0
	 * @exception 
	 * @param bean
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void deleteResource(CrawlerContentResourceBean bean) throws IllegalAccessException, InvocationTargetException{
		CrawlerContentResourceCriteria q = new CrawlerContentResourceCriteria();
		q.setIsLocal(Constant.YES);
		if(CollectionUtils.isNotEmpty(bean.getContentIdList())){
			q.setContentIdList(bean.getContentIdList());
		}else if(CollectionUtils.isNotEmpty(bean.getResourceIdList())){
			q.setResourceIdList(bean.getResourceIdList());
		}else if(CollectionUtils.isNotEmpty(bean.getRuleIdList())){
			q.setRuleIdList(bean.getRuleIdList());
		}
		
		List<CrawlerContentResource> resultList = doGetList(q,GatherConstant.SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE);
		if(CollectionUtils.isNotEmpty(resultList)){
			for(CrawlerContentResource crawlerContentResource : resultList){
				if(StringUtils.isNotBlank(crawlerContentResource.getPath())){
					FileUtils.deleteFile(Constant.SYSTEM_ROOT_PATH+Constant.SYSTEM_SEPARATOR+crawlerContentResource.getPath(),true);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerContentResource doGet(CrawlerContentResourceBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("resourceId", bean.getResourceId());
		return (CrawlerContentResource) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerContentResource> doGetList(CrawlerContentResourceCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(null != t.getRuleId()){
//			paramMap.put("ruleId", t.getRuleId());
//		}
//		if(null != t.getContentId()){
//			paramMap.put("contentId", t.getContentId());
//		}
		return (List<CrawlerContentResource>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	
	protected CrawlerContentResourceBean translateBean(CrawlerContentResource crawlerContentResource) throws IllegalAccessException, InvocationTargetException{
		CrawlerContentResourceBean crawlerContentResourceBean = new CrawlerContentResourceBean();
		BeanUtils.copyProperties(crawlerContentResourceBean, crawlerContentResource);
    	return crawlerContentResourceBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected PaginationSupport<CrawlerContentResource> doGetPaginatedList(
			CrawlerContentResourceCriteria q, String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<CrawlerContentResource>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}
}
