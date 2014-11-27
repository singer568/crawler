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
 * 添加数据库信息
 *@author DuanYong
 *@since 2013-2-14上午9:48:51
 *@version 1.0
 */
@Component("addDataBaseAction")
public class AddDataBaseAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	@Resource(name="dataBaseListPage")
    private DataBaseListPage dataBaseListPage;
    public AddDataBaseAction(){
    	super(LanguageLoader.getString("System.DataBaseAdd"),ImageLoader.getImageIcon("CrawlerResource.systemDataBaseAdd"));
    	putValue(SHORT_DESCRIPTION, LanguageLoader.getString("System.DataBaseAdd"));
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		dataBaseListPage.getDataBaseSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("System.DataBaseAdd"));
		dataBaseListPage.getDataBaseSettingDialog().setVisible(true);
	}

}
