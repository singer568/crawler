package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;

/**
 * 采集规则TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerRuleTabelModel extends AbstractCrawlerTableModel<CrawlerRuleBean>{
    
	private static final long serialVersionUID = 1L;
	
	public CrawlerRuleTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerRuleTabelModel(List<String> columnNames,List<CrawlerRuleBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerRuleBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			entity.setRuleName(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerRuleBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getRuleId();
			break;
		case 1:
			value = entity.getRuleName();
			break;
		case 2:
			value = entity.getStartTimeStr();
			break;
		case 3:
			value = entity.getEndTimeStr();
			break;
		case 4:
			value = entity.getUseTime();
			break;
		case 5:
			value = entity.getStatusStr();
			break;
		case 6:
			value = entity.getTotalItem();
			break;
		}
		return value;
	}

}
