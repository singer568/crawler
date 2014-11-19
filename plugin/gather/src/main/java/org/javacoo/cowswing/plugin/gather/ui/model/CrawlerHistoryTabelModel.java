package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerHistoryBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;

/**
 * 采集历史TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerHistoryTabelModel extends AbstractCrawlerTableModel<CrawlerHistoryBean>{
    
	private static final long serialVersionUID = 1L;
	
	public CrawlerHistoryTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerHistoryTabelModel(List<String> columnNames,List<CrawlerHistoryBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerHistoryBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			entity.setTitle(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerHistoryBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getHistoryId();
			break;
		case 1:
			value = entity.getTitle();
			break;
		case 2:
			value = entity.getUrl();
			break;
		case 3:
			value = entity.getDateStr();
			break;
		}
		return value;
	}

}
