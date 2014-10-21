/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service.beans;

import java.util.List;

import org.javacoo.cowswing.base.service.beans.CrawlerBaseBean;

/**
 * 扩展字段bean
 *@author DuanYong
 *@since 2013-3-24下午9:45:15
 *@version 1.0
 */
public class CrawlerExtendFieldBean extends CrawlerBaseBean{
	/**扩展字段主键*/
	private Integer extendFieldId;
	/**扩展字段ID集合*/
	private List<Integer> extendFieldIdList;
	/**内容主键*/
	private Integer contentId;
	/**内容ID集合*/
	private List<Integer> contentIdList;
	/**规则主键*/
	private Integer ruleId;
	/**规则主键集合*/
	private List<Integer> ruleIdList;
	/**字段名称*/
	private String fieldName;
	/**字段值*/
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
