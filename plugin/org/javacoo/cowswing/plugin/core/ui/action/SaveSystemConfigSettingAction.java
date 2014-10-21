/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;


/**
 * 保存参数配置
 *@author DuanYong
 *@since 2013-3-12上午11:14:25
 *@version 1.0
 */
public class SaveSystemConfigSettingAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	public SaveSystemConfigSettingAction(){
		super(LanguageLoader.getString("Common.finish"),ImageLoader.getImageIcon("CrawlerResource.commonSave"));
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
