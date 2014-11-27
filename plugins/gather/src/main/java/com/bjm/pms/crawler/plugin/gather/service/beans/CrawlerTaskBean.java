package com.bjm.pms.crawler.plugin.gather.service.beans;

import com.bjm.pms.crawler.view.base.service.beans.CrawlerBaseBean;


/**
 * 采集任务值对象
 *@author DuanYong
 *@since 2012-11-23下午4:03:30
 *@version 1.0
 */
public class CrawlerTaskBean extends CrawlerBaseBean{
	/**任务ID*/
	private Integer taskId;
	/**规则ID*/
	private Integer ruleId;
	/**总数*/
	private Integer total;
	/**完成数量*/
	private Integer complete;
	/**状态*/
	private String status;
	/**状态Str*/
	private String statusStr;
	/**规则名称*/
	private String ruleName;
	/**任务类型：1:采集任务,2:FTP上传任务,3:入库任务*/
	private String type; 
	/**描述*/
	private String desc;
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	

}
