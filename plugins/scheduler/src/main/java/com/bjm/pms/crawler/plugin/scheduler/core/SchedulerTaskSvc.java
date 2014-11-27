package com.bjm.pms.crawler.plugin.scheduler.core;

import java.util.List;

import com.bjm.pms.crawler.plugin.scheduler.domain.Scheduler;

/**
 * 定时任务接口
 * @author javacoo
 * @since 2011-11-04
 * @update 2012-04-13
 */
public interface SchedulerTaskSvc {
	/**
	 * 开始计划任务
	 * @param scheduler 任务对象
	 * @return true/false
	 */
	boolean start(Scheduler scheduler);
	/**
	 * 结束计划任务
	 * @param scheduler 任务对象
	 * @return true/false
	 */
	boolean stop(Scheduler scheduler);
	/**
	 * 取得关联任务map
	 * @param scheduler 任务对象
	 * @return 关联任务map
	 */
	List<SchedulerTaskBean> associateTaskList(Scheduler scheduler);
}
