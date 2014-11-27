package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 采集内容分页持久对象
 *@author DuanYong
 *@since 2012-11-27上午10:15:11
 *@version 1.0
 */
public class CrawlerContentPagination implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer paginationId;
	private List<Integer> paginationIdList;
	private Integer contentId;
	private List<Integer> contentIdList;
	private Integer ruleId;
	private List<Integer> ruleIdList;
	private String content;
	
	
   
	
	public Integer getPaginationId() {
		return paginationId;
	}

	public void setPaginationId(Integer paginationId) {
		this.paginationId = paginationId;
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

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Integer> getContentIdList() {
		return contentIdList;
	}

	public void setContentIdList(List<Integer> contentIdList) {
		this.contentIdList = contentIdList;
	}

	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}

	public List<Integer> getPaginationIdList() {
		return paginationIdList;
	}

	public void setPaginationIdList(List<Integer> paginationIdList) {
		this.paginationIdList = paginationIdList;
	}
    
     

	

}
