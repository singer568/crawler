/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.model;

import java.util.List;

import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;
import com.bjm.pms.crawler.webservice.manager.beans.FeedBackBean;

/**
 * 留言反馈TabelModel
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 下午4:44:34
 * @version 1.0
 */
public class FeedBackTabelModel extends AbstractCrawlerTableModel<FeedBackBean>{
	private static final long serialVersionUID = 1L;
	public FeedBackTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public FeedBackTabelModel(List<String> columnNames,List<FeedBackBean> dataList){
		super(columnNames,dataList);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		FeedBackBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = entity.getId();
			break;
		case 1:
			value = entity.getTitle();
			break;
		case 2:
			value = entity.getReply();
			break;
		case 3:
			value = entity.getIp();
			break;
		}
		return value;
	}
	

}
