package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 采集规则持久对象
 *@author DuanYong
 *@since 2012-11-5下午10:43:05
 *@version 1.0
 */
public class CrawlerRule implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer ruleId;
	private List<Integer> ruleIdList;
    private String ruleName;
    private String ruleType;
    private Date startTime;
	private Date endTime;
	private String status;
	private Integer totalItem;
    private String ruleBaseConfig;
    private String ruleContentConfig;
    private String ruleContentPageConfig;
    private String ruleCommentConfig;
    private String ruleFieldsConfig;
    private String ruleDataBaseConfig;
    private String ruleFtpConfig;
    private String ruleImageSettingConfig;
    private String ruleLocalConfig;

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


	public String getRuleDataBaseConfig() {
		return ruleDataBaseConfig;
	}


	public void setRuleDataBaseConfig(String ruleDataBaseConfig) {
		this.ruleDataBaseConfig = ruleDataBaseConfig;
	}


	/**
	 * @return the ruleFtpConfig
	 */
	public String getRuleFtpConfig() {
		return ruleFtpConfig;
	}


	/**
	 * @param ruleFtpConfig the ruleFtpConfig to set
	 */
	public void setRuleFtpConfig(String ruleFtpConfig) {
		this.ruleFtpConfig = ruleFtpConfig;
	}


	/**
	 * @return the ruleImageSettingConfig
	 */
	public String getRuleImageSettingConfig() {
		return ruleImageSettingConfig;
	}


	/**
	 * @param ruleImageSettingConfig the ruleImageSettingConfig to set
	 */
	public void setRuleImageSettingConfig(String ruleImageSettingConfig) {
		this.ruleImageSettingConfig = ruleImageSettingConfig;
	}


	/**
	 * @return the ruleLocalConfig
	 */
	public String getRuleLocalConfig() {
		return ruleLocalConfig;
	}


	/**
	 * @param ruleLocalConfig the ruleLocalConfig to set
	 */
	public void setRuleLocalConfig(String ruleLocalConfig) {
		this.ruleLocalConfig = ruleLocalConfig;
	}
    
    
	

	
	


	
	
    
}
