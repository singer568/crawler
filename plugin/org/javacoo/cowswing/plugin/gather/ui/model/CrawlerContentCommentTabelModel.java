package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentCommentBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;
/**
 * 采集内容评论TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerContentCommentTabelModel extends AbstractCrawlerTableModel<CrawlerContentCommentBean>{
    
	private static final long serialVersionUID = 1L;
	
	public CrawlerContentCommentTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerContentCommentTabelModel(List<String> columnNames,List<CrawlerContentCommentBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerContentCommentBean entity = dataList.get(rowIndex);
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
		CrawlerContentCommentBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getCommentId();
			break;
		case 1:
			value = entity.getContent();
			break;
		}
		return value;
	}

}
