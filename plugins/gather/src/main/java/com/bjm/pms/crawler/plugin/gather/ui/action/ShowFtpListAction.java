/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.view.panel.FtpListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.PageContainer;

/**
 * 显示FTP配置列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午9:35:42
 * @version 1.0
 */
@Component("showFtpListAction")
public class ShowFtpListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="ftpListPage")
	private FtpListPage ftpListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	public ShowFtpListAction(){
		super(LanguageLoader.getString("System.FtpSetting"),ImageLoader.getImageIcon("CrawlerResource.ftp"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(ftpListPage, ftpListPage.getPageId());
		ftpListPage.init();
	}
}
