package org.javacoo.cowswing.plugin.scheduler.core.support;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.plugin.scheduler.core.Scheduler;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTask;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTaskBean;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTaskManageSvc;
import org.javacoo.cowswing.plugin.scheduler.core.SchedulerTaskSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务管理服务接口实现类
 * @author javacoo
 * @since 2011-11-07
 */
@Component("schedulerTaskManageSvc")
public class SchedulerTaskManageSvcImpl implements SchedulerTaskManageSvc {
	protected Logger log = Logger.getLogger(this.getClass());
	/**任务管理对象MAP*/
	private static Map<Integer,TaskManage> taskManageMap = new ConcurrentHashMap<Integer, TaskManage>();
	/**定时任务服务对象MAP*/
	@Autowired
	private Map<String,SchedulerTaskSvc> schedulerTaskSvcMap;
    /**
     * 任务管理对象
     * @author javacoo
   	 * @since 2011-11-07
     */
    private class TaskManage{
    	/**任务调度*/
    	private final Scheduler scheduler = new Scheduler();
    	/**任务参数bean*/
    	private ScheduleWeekParamBean scheduleWeekParamBean;
    	/**定时任务*/
    	private final SchedulerTaskSvc schedulerTaskSvc;
    	private org.javacoo.cowswing.plugin.scheduler.domain.Scheduler cmsScheduler;
    	public TaskManage(SchedulerTaskSvc schedulerSvc,org.javacoo.cowswing.plugin.scheduler.domain.Scheduler cmsScheduler){
    		this.schedulerTaskSvc = schedulerSvc;
    		this.cmsScheduler = cmsScheduler;
    	}
    	/**
    	 * 解析计划表达式
    	 * @return
    	 */
    	private boolean parseSchedulerParam(){
    		scheduleWeekParamBean = new ScheduleWeekParamBean();
    		log.info("周期："+cmsScheduler.getWeek());
    		log.info("小时："+cmsScheduler.getHour());
    		scheduleWeekParamBean.setWeek(cmsScheduler.getWeek());
    		scheduleWeekParamBean.setHour(cmsScheduler.getHour());
    		scheduleWeekParamBean.setMinute(cmsScheduler.getMinute());
    		scheduleWeekParamBean.setSecond(cmsScheduler.getSecond());
    		return true;
    	}
    	/**
    	 * 开始
    	 */
    	public void start() {
    		if(parseSchedulerParam()){
    			scheduler.schedule(new SchedulerTask() {
    				public void run() {
    					processer();
    				}
    				private void processer() {
    					log.info("============开始执行计划任务=================");
    					schedulerTaskSvc.start(cmsScheduler);
    				}
    			}, new WeekScheduleIterator(scheduleWeekParamBean));
    		}
    	}
    	/**
    	 * 取消
    	 */
    	public void cancel() {
    		schedulerTaskSvc.stop(cmsScheduler);
    		scheduler.cancel();
    	}
    	
    }
    /**
     * 开始执行计划
     * @param scheduler 计划对象
     */
	public boolean start(org.javacoo.cowswing.plugin.scheduler.domain.Scheduler scheduler) {
		SchedulerTaskSvc schedulerSvc = getSchedulerTaskSvcByModuleType(scheduler.getServiceName());
		TaskManage taskManage = new TaskManage(schedulerSvc,scheduler);
		taskManage.start();
		taskManageMap.put(scheduler.getSchedulerId(), taskManage);
		return true;
	}
	/**
     * 停止执行计划
     * @param scheduler 计划对象
     */
	public boolean stop(org.javacoo.cowswing.plugin.scheduler.domain.Scheduler scheduler) {
		TaskManage taskManage = taskManageMap.get(scheduler.getSchedulerId());
		if(null != taskManage){
			taskManage.cancel();
		}
		return true;
	}
	/**
     * 取得计划关联的任务对象集合
     * @param scheduler 计划对象
     */
	public List<SchedulerTaskBean> associateTaskList(org.javacoo.cowswing.plugin.scheduler.domain.Scheduler scheduler) {
		SchedulerTaskSvc schedulerSvc = getSchedulerTaskSvcByModuleType(scheduler.getServiceName());
		return schedulerSvc.associateTaskList(scheduler);
	}
	
	/**
     * 根据模块的类型，取得定时任务服务对象
     * @param moduleType 模块类型
     */
	private SchedulerTaskSvc getSchedulerTaskSvcByModuleType(String moduleType){
		return schedulerTaskSvcMap.get(moduleType);
	}

	
}
