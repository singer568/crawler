package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentPaginationBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;

/**
 * 采集内容分页TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerContentPaginationTabelModel extends AbstractCrawlerTableModel<CrawlerContentPaginationBean>{
    
	private static final long serialVersionUID = 1L;
	
	public CrawlerContentPaginationTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerContentPaginationTabelModel(List<String> columnNames,List<CrawlerContentPaginationBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerContentPaginationBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			entity.setContent(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerContentPaginationBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getPaginationId();
			break;
		case 1:
			value = entity.getContent();
			break;
		}
		return value;
	}

}
