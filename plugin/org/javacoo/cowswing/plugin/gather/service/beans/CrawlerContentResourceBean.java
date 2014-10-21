package org.javacoo.cowswing.plugin.gather.service.beans;

import java.util.List;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.service.beans.CrawlerBaseBean;


/**
 * 采集内容资源值对象
 *@author DuanYong
 *@since 2012-11-27上午10:16:37
 *@version 1.0
 */
public class CrawlerContentResourceBean extends CrawlerBaseBean{
	/**资源主键*/
	private Integer resourceId;
	/**资源主键集合*/
	private List<Integer> resourceIdList;
	/**内容主键*/
	private Integer contentId;
	/**内容ID集合*/
	private List<Integer> contentIdList;
	/**规则主键*/
	private Integer ruleId;
	/**规则主键集合*/
	private List<Integer> ruleIdList;
	/**路径*/
	private String path;
	/**名称*/
	private String name;
	/**类型*/
	private String type;
	/**是否已经上传*/
	private String hasUpload = Constant.NO;
	/**是否已经处理*/
	private String hasDealWith = Constant.NO;
	/**是否是本地文件*/
	private String isLocal = Constant.NO;
	
	
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
	
	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
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
	public List<Integer> getContentIdList() {
		return contentIdList;
	}
	public void setContentIdList(List<Integer> contentIdList) {
		this.contentIdList = contentIdList;
	}
	public List<Integer> getResourceIdList() {
		return resourceIdList;
	}
	public void setResourceIdList(List<Integer> resourceIdList) {
		this.resourceIdList = resourceIdList;
	}
	
}
