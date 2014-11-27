package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 采集内容资源持久对象
 *@author DuanYong
 *@since 2012-11-27上午10:25:42
 *@version 1.0
 */
public class CrawlerContentResource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer resourceId;
	private List<Integer> resourceIdList;
	private Integer contentId;
	private List<Integer> contentIdList;
	private Integer ruleId;
	private List<Integer> ruleIdList;
	private String path;
	private String name;
	private String type;
	private String hasUpload;
	private String hasDealWith;
	private String isLocal;
	
	
   
	
	
    
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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
    
	


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Integer> getResourceIdList() {
		return resourceIdList;
	}

	public void setResourceIdList(List<Integer> resourceIdList) {
		this.resourceIdList = resourceIdList;
	}

	/**
	 * @return the hasUpload
	 */
	public String getHasUpload() {
		return hasUpload;
	}

	/**
	 * @param hasUpload the hasUpload to set
	 */
	public void setHasUpload(String hasUpload) {
		this.hasUpload = hasUpload;
	}
    
	/**
	 * @return the hasDealWith
	 */
	public String getHasDealWith() {
		return hasDealWith;
	}

	/**
	 * @param hasDealWith the hasDealWith to set
	 */
	public void setHasDealWith(String hasDealWith) {
		this.hasDealWith = hasDealWith;
	}

	/**
	 * @return the isLocal
	 */
	public String getIsLocal() {
		return isLocal;
	}

	/**
	 * @param isLocal the isLocal to set
	 */
	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}
    
	
}
