/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerFtpConfigBean;

/**
 * 数据库下拉model
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午10:58:53
 * @version 1.0
 */
public class FtpComboBoxModel extends AbstractListModel implements ComboBoxModel{
	private static final long serialVersionUID = 1L;
    private CrawlerFtpConfigBean crawlerFtpConfigBean;
    private List<CrawlerFtpConfigBean> crawlerFtpConfigBeanList = new ArrayList<CrawlerFtpConfigBean>();
    public FtpComboBoxModel(){
    }
    public FtpComboBoxModel(List<CrawlerFtpConfigBean> crawlerFtpConfigBeanList){
    	this.crawlerFtpConfigBeanList.addAll(crawlerFtpConfigBeanList);
    }
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return this.crawlerFtpConfigBeanList.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.crawlerFtpConfigBeanList.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return this.crawlerFtpConfigBean;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object arg0) {
		this.crawlerFtpConfigBean = (CrawlerFtpConfigBean) arg0;
	}
}
