/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 扩展字段
 *@author DuanYong
 *@since 2013-3-24下午9:52:21
 *@version 1.0
 */
public class CrawlerExtendField implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer extendFieldId;
	private List<Integer> extendFieldIdList;
	private Integer contentId;
	private List<Integer> contentIdList;
	private Integer ruleId;
	private List<Integer> ruleIdList;
	private String fieldName;
	private String fieldValue;
	public Integer getExtendFieldId() {
		return extendFieldId;
	}
	public void setExtendFieldId(Integer extendFieldId) {
		this.extendFieldId = extendFieldId;
	}
	public List<Integer> getExtendFieldIdList() {
		return extendFieldIdList;
	}
	public void setExtendFieldIdList(List<Integer> extendFieldIdList) {
		this.extendFieldIdList = extendFieldIdList;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
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
	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}
	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
	
	
}
