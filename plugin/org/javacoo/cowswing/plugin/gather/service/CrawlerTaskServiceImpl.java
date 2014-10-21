package org.javacoo.cowswing.plugin.gather.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.AbstractCrawlerService;
import org.javacoo.cowswing.plugin.gather.domain.CrawlerTask;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;
import org.javacoo.persistence.PaginationSupport;
import org.springframework.stereotype.Service;

/**
 * 采集任务服务类
 *@author DuanYong
 *@since 2012-11-23下午4:09:48
 *@version 1.0
 */
@Service("crawlerTaskService")
public class CrawlerTaskServiceImpl extends AbstractCrawlerService<CrawlerTaskBean,CrawlerTask,CrawlerTaskCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doInsert(CrawlerTaskBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerTask crawlerTask = new CrawlerTask();
		BeanUtils.copyProperties(crawlerTask, bean);
		this.getPersistService().insertBySqlMap(sqlMapId, crawlerTask);
		return crawlerTask.getTaskId();
	}
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doUpdate(CrawlerTaskBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerTask crawlerTask = new CrawlerTask();
		BeanUtils.copyProperties(crawlerTask, bean);
		return this.getPersistService().updateBySqlMap(sqlMapId, crawlerTask);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(java.lang.String, java.lang.Object)
	 */
	@Override
	protected int doDelete(CrawlerTaskBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		CrawlerTask crawlerTask = new CrawlerTask();
		BeanUtils.copyProperties(crawlerTask, bean);
		return this.getPersistService().deleteBySqlMap(sqlMapId, crawlerTask);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(java.lang.String, java.lang.Object)
	 */
	@Override
	protected CrawlerTask doGet(CrawlerTaskBean bean,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ruleId", bean.getRuleId());
		paramMap.put("type", bean.getType());
		return (CrawlerTask) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<CrawlerTask> doGetList(CrawlerTaskCriteria q,String sqlMapId) throws IllegalAccessException, InvocationTargetException {
//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		paramMap.put("status", t.getStatus());
		return (List<CrawlerTask>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}
	
	protected CrawlerTaskBean translateBean(CrawlerTask crawlerTask) throws IllegalAccessException, InvocationTargetException{
		CrawlerTaskBean crawlerTaskBean = new CrawlerTaskBean();
		BeanUtils.copyProperties(crawlerTaskBean, crawlerTask);
    	if(null != crawlerTaskBean && StringUtils.isNotBlank(crawlerTaskBean.getStatus())){
    		if(Constant.TASK_STATUS_RUN.equals(crawlerTaskBean.getStatus())){
    			crawlerTaskBean.setStatusStr(LanguageLoader.getString("Task.run"));
			}else if(Constant.TASK_STATUS_PAUSE.equals(crawlerTaskBean.getStatus())){
				crawlerTaskBean.setStatusStr(LanguageLoader.getString("Task.pause"));
			}else if(Constant.TASK_STATUS_COMPLETE.equals(crawlerTaskBean.getStatus())){
				crawlerTaskBean.setStatusStr(LanguageLoader.getString("Task.complete"));
			}else{
				crawlerTaskBean.setStatusStr(LanguageLoader.getString("Task.stop"));
			}
		}
    	return crawlerTaskBean;
    }


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String, int, int)
	 */
	@Override
	protected PaginationSupport<CrawlerTask> doGetPaginatedList(
			CrawlerTaskCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}
}
