package org.javacoo.cowswing.plugin.gather.service.beans;

import java.util.List;

import org.javacoo.persistence.PaginationCriteria;



/**
 * 采集内容分页查询对象
 *@author DuanYong
 *@since 2012-11-27上午10:16:37
 *@version 1.0
 */
public class CrawlerContentPaginationCriteria extends PaginationCriteria{
	private static final long serialVersionUID = 1L;
	/**分页主键*/
	private Integer paginationId;
	/**分页主键集合*/
	private List<Integer> paginationIdList;
	/**内容主键*/
	private Integer contentId;
	/**内容ID集合*/
	private List<Integer> contentIdList;
	/**规则主键*/
	private Integer ruleId;
	/**规则主键集合*/
	private List<Integer> ruleIdList;
	/**内容*/
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
	
	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
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
	public List<Integer> getPaginationIdList() {
		return paginationIdList;
	}
	public void setPaginationIdList(List<Integer> paginationIdList) {
		this.paginationIdList = paginationIdList;
	}
	
}
