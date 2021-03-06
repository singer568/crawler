package com.bjm.pms.crawler.plugin.gather.service.beans;

import java.util.List;

import com.bjm.pms.crawler.view.base.service.beans.CrawlerBaseBean;


/**
 * 采集内容评论值对象
 *@author DuanYong
 *@since 2012-11-27上午10:08:54
 *@version 1.0
 */
public class CrawlerContentCommentBean extends CrawlerBaseBean{
	/**评论主键*/
	private Integer commentId;
	/**评论主键集合*/
	private List<Integer> commentIdList;
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
	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	public List<Integer> getCommentIdList() {
		return commentIdList;
	}
	public void setCommentIdList(List<Integer> commentIdList) {
		this.commentIdList = commentIdList;
	}
    
	
}
