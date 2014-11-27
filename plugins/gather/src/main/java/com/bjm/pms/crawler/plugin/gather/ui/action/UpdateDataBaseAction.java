/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.DataBaseListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;

/**
 * 修改数据库信息
 *@author DuanYong
 *@since 2013-2-14上午9:50:03
 *@version 1.0
 */
@Component("updateDataBaseAction")
public class UpdateDataBaseAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	@Resource(name="dataBaseListPage")
    private DataBaseListPage dataBaseListPage;
    public UpdateDataBaseAction(){
    	super(LanguageLoader.getString("System.DataBaseUpdate"),ImageLoader.getImageIcon("CrawlerResource.systemDataBaseEdit"));
    	this.setEnabled(false);
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dataBaseListPage.getDataBaseSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_MODIFY, LanguageLoader.getString("System.DataBaseUpdate"));
		dataBaseListPage.getDataBaseSettingDialog().setVisible(true);
	}

}
