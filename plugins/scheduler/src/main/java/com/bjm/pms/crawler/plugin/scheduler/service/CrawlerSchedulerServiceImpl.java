/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.scheduler.domain.Scheduler;
import com.bjm.pms.crawler.plugin.scheduler.service.beans.SchedulerBean;
import com.bjm.pms.crawler.plugin.scheduler.service.beans.SchedulerCriteria;
import com.bjm.pms.crawler.view.base.service.AbstractCrawlerService;

/**
 * 定时任务服务类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 上午11:50:18
 * @version 1.0
 */
@Component("crawlerSchedulerService")
public class CrawlerSchedulerServiceImpl extends AbstractCrawlerService<SchedulerBean,Scheduler,SchedulerCriteria>{

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doInsert(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doInsert(SchedulerBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Scheduler scheduling = new Scheduler();
		BeanUtils.copyProperties(scheduling, t);
		this.getPersistService().insertBySqlMap(sqlMapId, scheduling);
		return scheduling.getSchedulerId();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doUpdate(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doUpdate(SchedulerBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Scheduler scheduling = new Scheduler();
		BeanUtils.copyProperties(scheduling, t);
		return this.getPersistService().updateBySqlMap(sqlMapId, scheduling);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doDelete(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected int doDelete(SchedulerBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Scheduler scheduling = new Scheduler();
		BeanUtils.copyProperties(scheduling, t);
		return this.getPersistService().deleteBySqlMap(sqlMapId, scheduling);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGet(org.javacoo.crawler.service.beans.CrawlerBaseBean, java.lang.String)
	 */
	@Override
	protected Scheduler doGet(SchedulerBean t, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("schedulerId", t.getSchedulerId());
		return (Scheduler) this.getPersistService().findObjectBySqlMap(sqlMapId, paramMap);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetList(java.lang.Object, java.lang.String)
	 */
	@Override
	protected List<Scheduler> doGetList(SchedulerCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (List<Scheduler>) this.getPersistService().findListBySqlMap(sqlMapId, q);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#doGetPaginatedList(java.lang.Object, java.lang.String)
	 */
	@Override
	protected PaginationSupport<Scheduler> doGetPaginatedList(
			SchedulerCriteria q, String sqlMapId)
			throws IllegalAccessException, InvocationTargetException {
		return (PaginationSupport<Scheduler>) this.getPersistService().findPaginatedListBySqlMap(sqlMapId, q, q.getStartIndex(), q.getPageSize());
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.service.AbstractCrawlerService#translateBean(java.lang.Object)
	 */
	@Override
	protected SchedulerBean translateBean(Scheduler e)
			throws IllegalAccessException, InvocationTargetException {
		SchedulerBean schedulingBean = new SchedulerBean();
		BeanUtils.copyProperties(schedulingBean, e);
		return schedulingBean;
	}

}
