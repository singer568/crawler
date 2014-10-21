package org.javacoo.cowswing.plugin.gather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 采集内容评论持久对象
 *@author DuanYong
 *@since 2012-11-27上午10:07:30
 *@version 1.0
 */
public class CrawlerContentComment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer commentId;
	private List<Integer> commentIdList;
	private Integer contentId;
	private List<Integer> contentIdList;
	private Integer ruleId;
	private List<Integer> ruleIdList;
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

	public List<Integer> getCommentIdList() {
		return commentIdList;
	}

	public void setCommentIdList(List<Integer> commentIdList) {
		this.commentIdList = commentIdList;
	}
     
    

	

}
