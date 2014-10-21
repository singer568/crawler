/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service.beans;

/**
 * 值选择bean
 *@author DuanYong
 *@since 2013-3-21下午6:10:38
 *@version 1.0
 */
public class SelectValueBean {
	/**值*/
	private String value;
	/**值名称*/
	private String valueName;
	/**类型*/
	private String type;
	
	/**
	 * @param value
	 * @param valueName
	 */
	public SelectValueBean(String valueName, String value) {
		super();
		this.value = value;
		this.valueName = valueName;
	}
	/**
	 * @param value
	 * @param valueName
	 */
	public SelectValueBean(String valueName, String value,String type) {
		super();
		this.value = value;
		this.valueName = valueName;
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return valueName;
	}
	
	
	
	

}
