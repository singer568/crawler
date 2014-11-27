/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.domain;

import java.io.Serializable;

/**
 * 远程表信息
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-22 上午9:23:15
 * @version 1.0
 */
public class RemoteTableColumnInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**表名称*/
	private String tableName;
	/**列名称*/
	private String columnName;
	/**列类型*/
	private String columnType;
	/**列类型*/
	private String columnTypeIndex;
	/**长度*/
	private String length;
	private String decimalDigits;
	private String columnComment;
	private String defaultValue;
	private String isNullAble;
	private String dataType;
	private String objectType;
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @return the columnTypeIndex
	 */
	public String getColumnTypeIndex() {
		return columnTypeIndex;
	}
	/**
	 * @param columnTypeIndex the columnTypeIndex to set
	 */
	public void setColumnTypeIndex(String columnTypeIndex) {
		this.columnTypeIndex = columnTypeIndex;
	}
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return the decimalDigits
	 */
	public String getDecimalDigits() {
		return decimalDigits;
	}
	/**
	 * @param decimalDigits the decimalDigits to set
	 */
	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	/**
	 * @return the columnComment
	 */
	public String getColumnComment() {
		return columnComment;
	}
	/**
	 * @param columnComment the columnComment to set
	 */
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * @return the isNullAble
	 */
	public String getIsNullAble() {
		return isNullAble;
	}
	/**
	 * @param isNullAble the isNullAble to set
	 */
	public void setIsNullAble(String isNullAble) {
		this.isNullAble = isNullAble;
	}
	
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}
	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	

}
