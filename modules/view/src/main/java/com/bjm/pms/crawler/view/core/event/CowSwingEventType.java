/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.event;


/**
 * 事件类型
 *@author DuanYong
 *@since 2012-11-4下午10:19:36
 *@version 1.0
 */
public enum CowSwingEventType {
	/** 采集规则Event. */
	NoTableChangeEvent,RuleTableChangeEvent,RuleTableAddEvent,RuleTableUpdateEvent,RuleTableDeleteEvent,
	/** 本地采集规则Event. */
	LocalRuleTableChangeEvent,LocalRuleTableAddEvent,LocalRuleTableUpdateEvent,LocalRuleTableDeleteEvent,
	/** 采集历史Event. */
	RuleHistoryTableChangeEvent,RuleHistoryTableAddEvent,RuleHistoryTableUpdateEvent,RuleHistoryTableDeleteEvent,
	/** 采集内容Event. */
	ContentTableChangeEvent,ContentTableAddEvent,ContentTableUpdateEvent,ContentTableDeleteEvent,
	/** 采集内容评论Event. */
	ContentCommentTableChangeEvent,ContentCommentTableAddEvent,ContentCommentTableUpdateEvent,ContentCommentTableDeleteEvent,
	/** 采集内容分页Event. */
	ContentPaginationTableChangeEvent,ContentPaginationTableAddEvent,ContentPaginationTableUpdateEvent,ContentPaginationTableDeleteEvent,
	/** 采集内容资源Event. */
	ContentResourceTableChangeEvent,ContentResourceTableAddEvent,ContentResourceTableUpdateEvent,ContentResourceTableDeleteEvent,
	/** 采集扩展字段Event. */
	ContentExtendFieldChangeEvent,ContentExtendFieldAddEvent,ContentExtendFieldUpdateEvent,ContentExtendFieldDeleteEvent,
	/** 采集任务Event. */
	TaskStatusChangeEvent,TaskStartEvent,TaskFinishedEvent,
	/** 入库任务Event. */
	SaveStatusChangeEvent,SaveStartEvent,SaveFinishedEvent,
	/** FTP上传任务Event. */
	FtpStatusChangeEvent,FtpStartEvent,FtpFinishedEvent,
	/** 图片处理任务Event. */
	DealWithImageStatusChangeEvent,DealWithImageStartEvent,DealWithImageFinishedEvent,
	/** 加载图片任务Event. */
	LoadImagesChangeEvent,DeleteImagesChangeEvent,
	/** 监控Event. */
	ProgressChangeEvent,
	/** 数据库信息Event. */
	DataBaseTableChangeEvent,DataBaseTableAddEvent,DataBaseTableUpdateEvent,DataBaseTableDeleteEvent,
	/** 系统配置信息Event. */
	ConfigTableChangeEvent,ConfigTableAddEvent,ConfigTableUpdateEvent,ConfigTableDeleteEvent,
	/** 定时任务配置信息Event. */
	SchedulerTableChangeEvent,SchedulerTableAddEvent,SchedulerTableUpdateEvent,SchedulerTableDeleteEvent,SchedulerTableExecuteEvent,SchedulerTableStopEvent,
	/** 数据校验*/
	ValidDataChangeEvent,
	/** 加载缓存*/
	CacheDataChangeEvent,CacheConfigChangeEvent,
	/** 系统启动*/
	SystemSatrtChangeEvent,SystemSpringInitComplateEvent,
	/**系统更新*/
	SystemVersionDownloadComplateEvent,
	/** 系统退出*/
	SystemExitChangeEvent,
	XEventType;
    
	public boolean isAlso(CowSwingEventType aEventType){
		if (aEventType == CowSwingEventType.RuleTableChangeEvent) {
			switch (this) {
			case RuleTableChangeEvent:
				return true;
			case RuleTableAddEvent:
				return true;
			case RuleTableUpdateEvent:
				return true;
			case RuleTableDeleteEvent:
				return true;
			default:
				return false;
			}
		}
		if (aEventType == CowSwingEventType.LocalRuleTableChangeEvent) {
			switch (this) {
			case LocalRuleTableChangeEvent:
				return true;
			case LocalRuleTableAddEvent:
				return true;
			case LocalRuleTableUpdateEvent:
				return true;
			case LocalRuleTableDeleteEvent:
				return true;
			default:
				return false;
			}
		}
		if (aEventType == CowSwingEventType.TaskStatusChangeEvent) {
			switch (this) {
			case TaskStatusChangeEvent:
				return true;
			case TaskFinishedEvent:
				return true;
			case TaskStartEvent:
				return true;
			default:
				return false;
			}
		}
		if (aEventType == CowSwingEventType.DataBaseTableChangeEvent) {
			switch (this) {
			case DataBaseTableChangeEvent:
				return true;
			case DataBaseTableAddEvent:
				return true;
			case DataBaseTableUpdateEvent:
				return true;
			case DataBaseTableDeleteEvent:
				return true;
			default:
				return false;
			}
		}
		if (aEventType == CowSwingEventType.ConfigTableChangeEvent) {
			switch (this) {
			case ConfigTableChangeEvent:
				return true;
			case ConfigTableAddEvent:
				return true;
			case ConfigTableUpdateEvent:
				return true;
			case ConfigTableDeleteEvent:
				return true;
			default:
				return false;
			}
		}
		
		if (aEventType == CowSwingEventType.SchedulerTableChangeEvent) {
			switch (this) {
			case SchedulerTableChangeEvent:
				return true;
			case SchedulerTableAddEvent:
				return true;
			case SchedulerTableUpdateEvent:
				return true;
			case SchedulerTableDeleteEvent:
				return true;
			case SchedulerTableExecuteEvent:
				return true;
			case SchedulerTableStopEvent:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

}
