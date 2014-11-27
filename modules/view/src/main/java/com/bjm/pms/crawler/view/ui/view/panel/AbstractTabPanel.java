/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * TabPanel接口抽象实现类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-25 下午3:07:59
 * @version 1.0
 */
public abstract class AbstractTabPanel extends JPanel implements ITabPanel{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private boolean hasInit = false;
	protected AbstractTabPanel(){
		super();
	}
	public void init(){
		if(!hasInit){
			BorderLayout layout = new BorderLayout();
			this.setLayout(layout);
			this.add(getCenterPanel(), BorderLayout.CENTER);
			CowSwingObserver.getInstance().addCrawlerListener(this);
			hasInit = true;
		}
		initOther();
	}
	public JPanel getPanel(){
		return this;
	}
	/**
	 * 取得中间面板组件
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午3:12:31
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	protected abstract Component getCenterPanel();
	/**
	 * 初始化其他
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午6:08:17
	 * @version 1.0
	 * @exception
	 */
	protected void initOther(){}
}
