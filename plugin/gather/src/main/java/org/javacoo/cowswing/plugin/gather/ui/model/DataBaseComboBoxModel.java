/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;

/**
 * 数据库下来model
 *@author DuanYong
 *@since 2013-2-19下午5:41:02
 *@version 1.0
 */
public class DataBaseComboBoxModel extends AbstractListModel implements ComboBoxModel{

	private static final long serialVersionUID = 1L;
    private CrawlerDataBaseBean crawlerDataBaseBean;
    private List<CrawlerDataBaseBean> crawlerDataBaseBeanList = new ArrayList<CrawlerDataBaseBean>();
    public DataBaseComboBoxModel(){
    }
    public DataBaseComboBoxModel(List<CrawlerDataBaseBean> crawlerDataBaseBeanList){
    	this.crawlerDataBaseBeanList.addAll(crawlerDataBaseBeanList);
    }
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return this.crawlerDataBaseBeanList.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.crawlerDataBaseBeanList.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return this.crawlerDataBaseBean;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object arg0) {
		this.crawlerDataBaseBean = (CrawlerDataBaseBean) arg0;
	}

}
