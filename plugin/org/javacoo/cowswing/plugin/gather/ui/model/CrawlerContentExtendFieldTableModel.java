/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerExtendFieldBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;

/**
 * 内容扩展字段model
 *@author DuanYong
 *@since 2013-3-25上午10:59:06
 *@version 1.0
 */
public class CrawlerContentExtendFieldTableModel extends AbstractCrawlerTableModel<CrawlerExtendFieldBean>{

	private static final long serialVersionUID = 1L;
    public CrawlerContentExtendFieldTableModel(List<String> columnNames){
    	super(columnNames);
    }
    public CrawlerContentExtendFieldTableModel(List<String> columnNames,List<CrawlerExtendFieldBean> dataList){
    	super(columnNames,dataList);
    }
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.model.AbstractCrawlerTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerExtendFieldBean entity = dataList.get(rowIndex);

		switch (columnIndex) {
		case 0:
			value = entity.getExtendFieldId();
			break;
		case 1:
			value = entity.getFieldName();
			break;
		case 2:
			value = entity.getFieldValue();
			break;
		}
		return value;
	}

}
