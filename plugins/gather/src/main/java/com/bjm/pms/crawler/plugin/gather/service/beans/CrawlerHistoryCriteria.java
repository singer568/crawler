package com.bjm.pms.crawler.plugin.gather.service.beans;

import java.util.Date;
import java.util.List;

import com.bjm.pms.crawler.persist.PaginationCriteria;


/**
 * 采集历史查询对象
 *@author DuanYong
 *@since 2012-12-8下午7:35:23
 *@version 1.0
 */
public class CrawlerHistoryCriteria extends PaginationCriteria{
	private static final long serialVersionUID = 1L;
	/**历史主键*/
	private Integer historyId;
	/**历史ID集合*/
	private List<Integer> historyIdList;
	/**内容主键*/
	private Integer contentId;
	/**规则主键*/
	private Integer ruleId;
	/**URL*/
	private String url;
	/**名称*/
	private String title;
	/**描述*/
	private String description;
	/**日期*/
	private Date date;
    /**日期Str*/
	private String dateStr;
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public List<Integer> getHistoryIdList() {
		return historyIdList;
	}
	public void setHistoryIdList(List<Integer> historyIdList) {
		this.historyIdList = historyIdList;
	}
	
	
}
