/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.view.panel;

import javax.swing.JPanel;

import org.javacoo.cowswing.core.event.CowSwingListener;

/**
 * TabPanel接口
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-25 下午2:58:59
 * @version 1.0
 */
public interface ITabPanel extends CowSwingListener{
	/**
	 * 取得名名称
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午2:59:45
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	String getTanPanelName();
	/**
	 * 取得索引
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-8-13 下午2:36:51
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	int getTabPanelIndex();
	/**
	 * 初始化
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午3:22:43
	 * @version 1.0
	 * @exception
	 */
	void init();
	/**
	 * 取得当前panel
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午3:27:03
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	JPanel getPanel();

}
