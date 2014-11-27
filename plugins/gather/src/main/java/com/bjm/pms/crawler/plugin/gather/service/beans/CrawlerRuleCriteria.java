package com.bjm.pms.crawler.plugin.gather.service.beans;

import java.util.Date;
import java.util.List;

import com.bjm.pms.crawler.persist.PaginationCriteria;
import com.bjm.pms.crawler.view.base.constant.Constant;



/**
 * 采集规则查询对象
 *@author DuanYong
 *@since 2012-12-8下午7:19:50
 *@version 1.0
 */
public class CrawlerRuleCriteria extends PaginationCriteria{
	private static final long serialVersionUID = 1L;
	/**规则ID*/
	private Integer ruleId;
	/**规则ID集合*/
	private List<Integer> ruleIdList;
	/**规则名称*/
    private String ruleName;
	/**规则类型*/
    private String ruleType = Constant.NO;
    /**采集开始时间*/
    private Date startTime;
    /**采集开始时间*/
    private String startTimeStr;
    /**采集结束时间*/
	private Date endTime;
    /**采集结束时间*/
	private String endTimeStr;
	/**采集状态*/
	private String status;
	/**采集状态Str*/
	private String statusStr;
	/**采集总数*/
	private Integer totalItem;
	/**采集基本参数json字符串*/
    private String ruleBaseConfig;
    /**采集内容参数json字符串*/
    private String ruleContentConfig;
    /**采集分页参数json字符串*/
    private String ruleContentPageConfig;
    /**采集评论参数json字符串*/
    private String ruleCommentConfig;
    /**采集扩展字段参数json字符串*/
    private String ruleFieldsConfig;
    
    
    
    /**采集基本参数值对象*/
    private RuleBaseBean ruleBaseBean;
    /**采集评论参数值对象*/
    private RuleCommentBean ruleCommentBean;
    /**采集内容参数值对象*/
    private RuleContentBean ruleContentBean;
    /**采集内容分页参数值对象*/
    private RuleContentPageBean ruleContentPageBean;
    /**采集远程数据库参数值对象*/
    private RuleDataBaseBean ruleDataBaseBean;
    /**总共用时*/
	private String useTime;
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	
	
	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	/**
	 * @return the ruleType
	 */
	public String getRuleType() {
		return ruleType;
	}
	/**
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	
	
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRuleBaseConfig() {
		return ruleBaseConfig;
	}
	public void setRuleBaseConfig(String ruleBaseConfig) {
		this.ruleBaseConfig = ruleBaseConfig;
	}
	public String getRuleContentConfig() {
		return ruleContentConfig;
	}
	public void setRuleContentConfig(String ruleContentConfig) {
		this.ruleContentConfig = ruleContentConfig;
	}
	public String getRuleContentPageConfig() {
		return ruleContentPageConfig;
	}
	public void setRuleContentPageConfig(String ruleContentPageConfig) {
		this.ruleContentPageConfig = ruleContentPageConfig;
	}
	public String getRuleCommentConfig() {
		return ruleCommentConfig;
	}
	public void setRuleCommentConfig(String ruleCommentConfig) {
		this.ruleCommentConfig = ruleCommentConfig;
	}
	public String getRuleFieldsConfig() {
		return ruleFieldsConfig;
	}
	public void setRuleFieldsConfig(String ruleFieldsConfig) {
		this.ruleFieldsConfig = ruleFieldsConfig;
	}
	public RuleBaseBean getRuleBaseBean() {
		return ruleBaseBean;
	}
	public void setRuleBaseBean(RuleBaseBean ruleBaseBean) {
		this.ruleBaseBean = ruleBaseBean;
	}
	public RuleCommentBean getRuleCommentBean() {
		return ruleCommentBean;
	}
	public void setRuleCommentBean(RuleCommentBean ruleCommentBean) {
		this.ruleCommentBean = ruleCommentBean;
	}
	public RuleContentBean getRuleContentBean() {
		return ruleContentBean;
	}
	public void setRuleContentBean(RuleContentBean ruleContentBean) {
		this.ruleContentBean = ruleContentBean;
	}
	public RuleContentPageBean getRuleContentPageBean() {
		return ruleContentPageBean;
	}
	public void setRuleContentPageBean(RuleContentPageBean ruleContentPageBean) {
		this.ruleContentPageBean = ruleContentPageBean;
	}
	
	
	public RuleDataBaseBean getRuleDataBaseBean() {
		return ruleDataBaseBean;
	}
	public void setRuleDataBaseBean(RuleDataBaseBean ruleDataBaseBean) {
		this.ruleDataBaseBean = ruleDataBaseBean;
	}
	public String getUseTime() {
		this.useTime = "";
		if (null != this.getEndTime() && null != this.getStartTime()) {
			long timeInSeconds = (this.getEndTime().getTime() - this
					.getStartTime().getTime());
			long days, hours, minutes, seconds;
			// 1(day)*24(hour)*60(minite)*60(seconds)*1000(millisecond)
			days = timeInSeconds / 86400000;
			timeInSeconds = timeInSeconds - (days * 3600 * 24 * 1000);
			// 1(hour)*60(minite)*60(seconds)*1000(millisecond)
			hours = timeInSeconds / 3600000;
			timeInSeconds = timeInSeconds - (hours * 3600 * 1000);
			// 1(seconds)*1000(millisecond)
			minutes = timeInSeconds / 60000;
			timeInSeconds = timeInSeconds - (minutes * 60 * 1000);
			// 1(seconds)*1000(millisecond)
			seconds = timeInSeconds / 1000;
			this.useTime = days + "天" + hours + "小时" + minutes + "分" + seconds
					+ "秒";
		}
		return this.useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	
	

}
