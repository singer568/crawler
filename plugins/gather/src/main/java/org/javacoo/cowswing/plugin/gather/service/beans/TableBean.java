/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.service.beans;

import java.util.List;

/**
 * 表类型信息
 *@author DuanYong
 *@since 2013-3-21下午8:45:45
 *@version 1.0
 */
public class TableBean {
	/**数据库表名*/
	private String tableName;
	/**数据库表字段列表*/
	private List<ColumnBean> columnList;
	/**是否是内容保存表*/
	private boolean isContentTable = false;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<ColumnBean> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnBean> columnList) {
		this.columnList = columnList;
	}
	
	public boolean isContentTable() {
		return isContentTable;
	}
	public void setContentTable(boolean isContentTable) {
		this.isContentTable = isContentTable;
	}
	@Override
	public String toString() {
		return "TableBean [tableName=" + tableName + ", columnList="
				+ columnList + "]";
	}
	
	

}
