package com.bjm.pms.crawler.plugin.tool.ui.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

import org.apache.commons.collections.CollectionUtils;

import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageBean;
import com.bjm.pms.crawler.view.ui.model.AbstractCrawlerTableModel;
import com.bjm.pms.crawler.view.ui.util.ImageUtils;
/**
 * 图片TabelModel
 *@author DuanYong
 *@since 2012-11-5下午10:34:59
 *@version 1.0
 */
public class ImageTabelModel extends AbstractCrawlerTableModel<ImageBean> implements Runnable{
	private static final long serialVersionUID = 1L;
	private static final int iconWidth = 95;
	private static final int iconHeight = 82;
	private Map<String,ImageIcon> imagesCache = new ConcurrentHashMap<String,ImageIcon>();
	public ImageTabelModel(List<String> columnNames){
		super(columnNames);
	}
	public ImageTabelModel(List<String> columnNames,List<ImageBean> dataList){
		super(columnNames,dataList);
	}
	
	public void setData(List<ImageBean> dataList){
		super.setData(dataList);
	}
	
	private void initImagesCache(){
		if(CollectionUtils.isNotEmpty(dataList)){
			for(ImageBean bean : dataList){
				if(!imagesCache.containsKey(bean.getName())){
					imagesCache.put(bean.getName(), new ImageIcon(ImageUtils.scaleByWH(bean.getPath(), iconWidth, iconHeight,true)));
					this.fireTableDataChanged();
				}
			}
		}
	}
	public void removeRow(int row,String name) {
		super.removeRow(row);
		if(imagesCache.containsKey(name)){
			imagesCache.remove(name);
		}
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		logger.info("设置值："+value);
		if(value == null || value.equals(this.getValueAt(rowIndex, columnIndex))){
			return;
		}
		ImageBean entity = dataList.get(rowIndex);
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
		ImageBean entity = dataList.get(rowIndex);

		switch (columnIndex) {
		case 0:
			value = entity.getName();
			break;
		case 1:
			value = entity.getType();
			break;
		case 2:
			value = entity.getSize();
			break;
		case 3:
            value = imagesCache.get(entity.getName());
			break;
		}
		return value;
	}
	
	public Class<?> getColumnClass(int columnIndex){
		Class<?> value = String.class;
		switch (columnIndex) {
		case 3:
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
	public Map<String, ImageIcon> getImagesCache() {
		return imagesCache;
	}
	public void setImagesCache(Map<String, ImageIcon> imagesCache) {
		this.imagesCache = imagesCache;
	}
	

}
