/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;

/**
 * 数据库信息model
 *@author DuanYong
 *@since 2013-2-14上午9:39:33
 *@version 1.0
 */
public class CrawlerDataBaseTableModel extends AbstractCrawlerTableModel<CrawlerDataBaseBean>{

	private static final long serialVersionUID = 1L;

	public CrawlerDataBaseTableModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerDataBaseTableModel(List<String> columnNames,List<CrawlerDataBaseBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerDataBaseBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			entity.setDescription(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerDataBaseBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getDataBaseId();
			break;
		case 1:
			value = entity.getDescription();
			break;
		case 2:
			value = entity.getUrl();
			break;
		case 3:
			value = entity.getClassName();
			break;
		case 4:
			value = entity.getUserName();
			break;
		case 5:
			value = entity.getPassword();
			break;
		case 6:
			value = entity.getType();
			break;
		}
		return value;
	}

}
