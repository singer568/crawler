package org.javacoo.cowswing.plugin.gather.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 采集历史持久对象
 *@author DuanYong
 *@since 2012-11-27上午10:37:51
 *@version 1.0
 */
public class CrawlerHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer historyId;
	private List<Integer> historyIdList;
	private Integer contentId;
	private Integer ruleId;
	private String url;
	private String title;
	private String description;
	private Date date;
	
	
   
	
	
    
	

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

	public List<Integer> getHistoryIdList() {
		return historyIdList;
	}

	public void setHistoryIdList(List<Integer> historyIdList) {
		this.historyIdList = historyIdList;
	}
    
	

}
