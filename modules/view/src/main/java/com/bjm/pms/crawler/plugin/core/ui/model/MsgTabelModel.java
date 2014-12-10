/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.model;

import java.util.List;

import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;
import com.bjm.pms.crawler.webservice.manager.beans.FeedBackBean;
import com.bjm.pms.crawler.webservice.manager.beans.MsgBean;

/**
 * 消息TabelModel
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-10-1 下午4:44:43
 * @version 1.0
 */
public class MsgTabelModel extends AbstractCrawlerTableModel<MsgBean>{
	private static final long serialVersionUID = 1L;
	public MsgTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public MsgTabelModel(List<String> columnNames,List<MsgBean> dataList){
		super(columnNames,dataList);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		MsgBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = entity.getMsgTitle();
			break;
		case 1:
			value = entity.getMsgContent();
			break;
		case 2:
			
			value = DateUtil.dateToStr(entity.getSendTime(), "yyyy-MM-dd HH:mm:ss");
			break;
		}
		return value;
	}
	

}
