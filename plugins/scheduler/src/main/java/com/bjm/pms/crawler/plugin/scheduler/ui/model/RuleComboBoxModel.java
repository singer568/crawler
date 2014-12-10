/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;

/**
 * 采集规则下拉model
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午10:58:53
 * @version 1.0
 */
public class RuleComboBoxModel extends AbstractListModel implements ComboBoxModel{
	private static final long serialVersionUID = 1L;
    private CrawlerRuleBean crawlerRuleBean;
    private List<CrawlerRuleBean> crawlerRuleBeanList = new ArrayList<CrawlerRuleBean>();
    public RuleComboBoxModel(){
    }
    public RuleComboBoxModel(List<CrawlerRuleBean> crawlerRuleBeanList){
    	this.crawlerRuleBeanList.addAll(crawlerRuleBeanList);
    }
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return this.crawlerRuleBeanList.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.crawlerRuleBeanList.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return this.crawlerRuleBean;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(Object arg0) {
		this.crawlerRuleBean = (CrawlerRuleBean) arg0;
	}
}
