package org.javacoo.cowswing.plugin.scheduler.core.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTaskBean;
import org.javacoo.cowswing.plugin.scheduler.domain.Scheduler;
import org.javacoo.crawler.core.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 计划任务接口-采集器实现类-多线程版
 * @author javacoo
 * @since 2011-11-02
 * @version 1.0 
 */
@Component("schedulerRuleSvc")
public class SchedulerRuleSvcImpl extends AbstractSchedulerTaskSvc {
	protected Logger log = Logger.getLogger(this.getClass());
	
	/**采集服务对象*/
	@Autowired
	private CrawlerService crawlerService;
	
	
	
	
	
	public void setCrawlerService(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}
	@Override
	protected boolean execute(Scheduler scheduler) {
		return crawlerService.start(scheduler.getAssociateId(),true);
	}
	
	 /**
	 * 取得关联任务map
	 * @return 关联任务map
	 */
	public List<SchedulerTaskBean> associateTaskList(Scheduler scheduler){
		List<SchedulerTaskBean> resultList = new ArrayList<SchedulerTaskBean>();
		
		return resultList;
	}
	
	

	
	
	
}
