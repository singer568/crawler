package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 采集内容持久对象
 *@author DuanYong
 *@since 2012-11-27上午9:35:55
 *@version 1.0
 */
public class CrawlerContent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer contentId;
	private List<Integer> contentIdList;
	private Integer ruleId;
	private List<Integer> ruleIdList;
	private String title;
	private String content;
	private Date saveDate;
	private Date viewDate;
	private String hasSave;
	private String brief;
	private String titleImg;
	
	

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
    
	public List<Integer> getContentIdList() {
		return contentIdList;
	}

	public void setContentIdList(List<Integer> contentIdList) {
		this.contentIdList = contentIdList;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
    
	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the hasSave
	 */
	public String getHasSave() {
		return hasSave;
	}

	/**
	 * @param hasSave the hasSave to set
	 */
	public void setHasSave(String hasSave) {
		this.hasSave = hasSave;
	}

	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * @return the titleImg
	 */
	public String getTitleImg() {
		return titleImg;
	}

	/**
	 * @param titleImg the titleImg to set
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
    
	
}
