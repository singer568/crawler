/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.DiyDataListPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.stereotype.Component;

/**
 * 显示自定义数据列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午9:35:42
 * @version 1.0
 */
@Component("showDiyDataListAction")
public class ShowDiyDataListAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="diyDataListPage")
	private DiyDataListPage diyDataListPage;
	@Resource(name="pageContainer")
    private PageContainer pageContainer;
	public ShowDiyDataListAction(){
		super(LanguageLoader.getString("System.DiySetting"),ImageLoader.getImageIcon("CrawlerResource.toolbarDiydataList"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		pageContainer.addPage(diyDataListPage, diyDataListPage.getPageId());
		diyDataListPage.init();
	}
}
