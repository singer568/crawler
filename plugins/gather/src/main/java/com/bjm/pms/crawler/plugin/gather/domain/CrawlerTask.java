package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;

/**
 * 采集任务
 *@author DuanYong
 *@since 2012-11-23下午3:47:06
 *@version 1.0
 */
public class CrawlerTask implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer taskId;
	private Integer ruleId;
	private Integer total;
	private Integer complete;
	private String status;
	private String ruleName;
	private String type;
	
	

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
    
	
	
}
