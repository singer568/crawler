package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentResourceBean;
import org.javacoo.cowswing.ui.model.AbstractCrawlerTableModel;
import org.javacoo.cowswing.ui.util.ImageUtils;
/**
 * 采集内容资源TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class CrawlerContentResourceTabelModel extends AbstractCrawlerTableModel<CrawlerContentResourceBean> implements Runnable{
	private static final long serialVersionUID = 1L;
	private static final int iconWidth = 95;
	private static final int iconHeight = 82;
	private Map<Integer,ImageIcon> imagesCache = new ConcurrentHashMap<Integer,ImageIcon>();
	public CrawlerContentResourceTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public CrawlerContentResourceTabelModel(List<String> columnNames,List<CrawlerContentResourceBean> dataList){
		super(columnNames,dataList);
	}
	
	public void setData(List<CrawlerContentResourceBean> dataList){
		super.setData(dataList);
	}
	
	private void initImagesCache(){
		if(CollectionUtils.isNotEmpty(dataList)){
			for(CrawlerContentResourceBean bean : dataList){
				if(!imagesCache.containsKey(bean.getResourceId())){
					imagesCache.put(bean.getResourceId(), new ImageIcon(ImageUtils.scaleByWH(bean.getPath(), iconWidth, iconHeight,true)));
					this.fireTableDataChanged();
				}
			}
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		CrawlerContentResourceBean entity = dataList.get(rowIndex);
		switch (columnIndex) {
		case 1:
			entity.setName(value.toString());
			break;
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		CrawlerContentResourceBean entity = dataList.get(rowIndex);

		switch (columnIndex) {
		case 0:
			value = entity.getResourceId();
			break;
		case 1:
			value = entity.getName();
			break;
		case 2:
			value = entity.getType();
			break;
		case 3:
			value = entity.getPath();
			break;
		case 4:
			value = Constant.YES.equals(entity.getHasUpload()) ? LanguageLoader.getString("Common.yes") : LanguageLoader.getString("Common.no");
			break;
		case 5:
			value = Constant.YES.equals(entity.getIsLocal()) ? LanguageLoader.getString("Common.yes") : LanguageLoader.getString("Common.no");
			break;
		case 6:
//			 ImageIcon imageIcon = new ImageIcon(ImageUtils.scaleByWH(entity.getPath(), iconWidth, iconHeight));
			 value = imagesCache.get(entity.getResourceId());
			break;
		
		}
		return value;
	}
	
	public Class<?> getColumnClass(int columnIndex){
		Class<?> value = String.class;
		switch (columnIndex) {
		case 6:
			value = ImageIcon.class;
			break;
		}
		return value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.initImagesCache();
	}
	

}
