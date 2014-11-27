/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.model;

import java.util.List;

import com.bjm.pms.crawler.plugin.gather.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;


/**
 * 扩展字段TabelModel
 *@author DuanYong
 *@since 2013-3-17下午8:32:34
 *@version 1.0
 */
public class CrawlerExtendFieldsTabelModel extends AbstractCrawlerTableModel<ExtendFieldsBean>{
	private static final long serialVersionUID = 1L;
	/**
	 * @param columnNames
	 */
	public CrawlerExtendFieldsTabelModel(List<String> columnNames) {
		super(columnNames);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		ExtendFieldsBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			entity.setFields(value.toString());
			break;
		case 1:
			entity.setFilterStart(value.toString());
			break;
		case 2:
			entity.setFilterEnd(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.model.AbstractCrawlerTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		ExtendFieldsBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = entity.getFields();
			break;
		case 1:
			value = entity.getFilterStart();
			break;
		case 2:
			value = entity.getFilterEnd();
			break;
		}
		return value;
	}

}
