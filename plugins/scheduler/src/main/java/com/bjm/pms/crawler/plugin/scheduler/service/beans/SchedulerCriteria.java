/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.service.beans;

import java.util.Date;
import java.util.List;

import com.bjm.pms.crawler.persist.PaginationCriteria;

/**
 * 定时任务查询对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 上午11:15:44
 * @version 1.0
 */
public class SchedulerCriteria extends PaginationCriteria{

	private static final long serialVersionUID = 1L;
	
	/**任务主键*/
	private Integer schedulerId;
	/**任务主键集合*/
	private List<Integer> schedulerIdList;
	/**任务关联主键*/
	private Integer associateId;
	/**服务名称*/
	private String serviceName;
	/**任务名称*/
	private String name;
	/**任务开始时间*/
	private Date startTime;
	/**任务结束时间*/
	private Date endTime;
	/**任务状态*/
	private String status;
	/**任务表达式*/
	private String expression;
	/**周期*/
	private String week;
	/**时间*/
	private String hour;
	/**分钟*/
	private String minute;
	/**秒*/
	private String second;
	/**
	 * @return the schedulerId
	 */
	public Integer getSchedulerId() {
		return schedulerId;
	}
	/**
	 * @param schedulerId the schedulerId to set
	 */
	public void setSchedulerId(Integer schedulerId) {
		this.schedulerId = schedulerId;
	}
	/**
	 * @return the associateId
	 */
	public Integer getAssociateId() {
		return associateId;
	}
	/**
	 * @param associateId the associateId to set
	 */
	public void setAssociateId(Integer associateId) {
		this.associateId = associateId;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	/**
	 * @return the schedulerIdList
	 */
	public List<Integer> getSchedulerIdList() {
		return schedulerIdList;
	}
	/**
	 * @param schedulerIdList the schedulerIdList to set
	 */
	public void setSchedulerIdList(List<Integer> schedulerIdList) {
		this.schedulerIdList = schedulerIdList;
	}
	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}
	/**
	 * @return the minute
	 */
	public String getMinute() {
		return minute;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(String minute) {
		this.minute = minute;
	}
	/**
	 * @return the second
	 */
	public String getSecond() {
		return second;
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(String second) {
		this.second = second;
	}
	
	

}
