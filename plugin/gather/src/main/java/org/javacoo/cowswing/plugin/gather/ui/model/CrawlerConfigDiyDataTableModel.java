/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;

import org.javacoo.cowswing.plugin.core.service.beans.CrawlerConfigBean;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;


/**
 * 自定义数据信息model
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:09:48
 * @version 1.0
 */
public class CrawlerConfigDiyDataTableModel extends AbstractCrawlerTableModel<CrawlerConfigBean>{

	private static final long serialVersionUID = 1L;
	public CrawlerConfigDiyDataTableModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerConfigDiyDataTableModel(List<String> columnNames,List<CrawlerConfigBean> dataList){
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
			value = entity.getCrawlerDiyDataConfigBean().getName();
			break;
		case 2:
			value = GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(entity.getCrawlerDiyDataConfigBean().getType())?"键值对数据":entity.getCrawlerDiyDataConfigBean().getValue();
			break;
		case 3:
			value = GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(entity.getCrawlerDiyDataConfigBean().getType())?"键值对类型":"简单数据类型";
			break;
		case 4:
			value = entity.getCrawlerDiyDataConfigBean().getDesc();
			break;
		}
		return value;
	}

}
