/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.DiyDataListPage;
import org.springframework.stereotype.Component;

/**
 * 更新自定义数据
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:06:17
 * @version 1.0
 */
@Component("updateDiyDataAction")
public class UpdateDiyDataAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	@Resource(name="diyDataListPage")
	private DiyDataListPage diyDataListPage;
	public UpdateDiyDataAction(){
    	super(LanguageLoader.getString("System.DiyUpdate"),ImageLoader.getImageIcon("CrawlerResource.toolbarDiydataEdit"));
    	this.setEnabled(false);
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		diyDataListPage.getDiyDataSettingDialog().init(crawlerMainFrame, Constant.OPTION_TYPE_MODIFY, LanguageLoader.getString("System.DiyUpdate"));
		diyDataListPage.getDiyDataSettingDialog().setVisible(true);
	}
}
