package com.bjm.pms.crawler.plugin.gather.ui.model;

import java.util.List;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerContentBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;

/**
 * 采集内容TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerContentTabelModel extends AbstractCrawlerTableModel<CrawlerContentBean>{
    
	private static final long serialVersionUID = 1L;
	
	public CrawlerContentTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerContentTabelModel(List<String> columnNames,List<CrawlerContentBean> dataList){
		super(columnNames,dataList);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerContentBean entity = dataList.get(rowIndex);
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
		CrawlerContentBean entity = dataList.get(rowIndex);
		

		switch (columnIndex) {
		case 0:
			value = entity.getContentId();
			break;
		case 1:
			value = entity.getTitle();
			break;
		case 2:
			value = entity.getSaveDateStr();
			break;
		case 3:
			value = entity.getViewDateStr();
			break;
		case 4:
			value = Constant.YES.equals(entity.getHasSave()) ? LanguageLoader.getString("Common.yes") : LanguageLoader.getString("Common.no");
			break;
		}
		
		return value;
	}

}
