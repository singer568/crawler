/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.model;

import java.util.List;

import com.bjm.pms.crawler.plugin.core.service.beans.CrawlerConfigBean;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;


/**
 * FTP配置信息model
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:09:48
 * @version 1.0
 */
public class CrawlerConfigFtpTableModel extends AbstractCrawlerTableModel<CrawlerConfigBean>{

	private static final long serialVersionUID = 1L;
	public CrawlerConfigFtpTableModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerConfigFtpTableModel(List<String> columnNames,List<CrawlerConfigBean> dataList){
		super(columnNames,dataList);
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerConfigBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			value = entity.getConfigId();
			break;
		case 1:
			value = entity.getCrawlerFtpConfigBean().getFtpName();
			break;
		case 2:
			value = entity.getCrawlerFtpConfigBean().getFtpUrl();
			break;
		case 3:
			value = entity.getCrawlerFtpConfigBean().getFtpPort();
			break;
		case 4:
			value = entity.getCrawlerFtpConfigBean().getFtpUserName();
			break;
		case 5:
			value = entity.getCrawlerFtpConfigBean().getFtpPassword();
			break;
		}
		return value;
	}

}
