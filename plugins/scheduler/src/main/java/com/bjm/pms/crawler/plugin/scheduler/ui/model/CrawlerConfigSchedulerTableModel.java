/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.ui.model;

import java.util.List;

import com.bjm.pms.crawler.plugin.scheduler.service.beans.SchedulerBean;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;

/**
 * 定时任务model
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午2:27:11
 * @version 1.0
 */
public class CrawlerConfigSchedulerTableModel extends AbstractCrawlerTableModel<SchedulerBean>{

	private static final long serialVersionUID = 1L;
	public CrawlerConfigSchedulerTableModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerConfigSchedulerTableModel(List<String> columnNames,List<SchedulerBean> dataList){
		super(columnNames,dataList);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.model.AbstractCrawlerTableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		SchedulerBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = entity.getSchedulerId();
			break;
		case 1:
			value = entity.getName();
			break;
		case 2:
			value = entity.getStartTime();
			break;
		case 3:
			value = entity.getEndTime();
			break;
		case 4:
			value = entity.getStatus();
			break;
		}
		return value;
	}

}
