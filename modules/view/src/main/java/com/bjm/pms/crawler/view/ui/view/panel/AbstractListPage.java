package com.bjm.pms.crawler.view.ui.view.panel;

import javax.annotation.Resource;

/**
 * 抽象类
 *@author DuanYong
 *@since 2012-11-5下午3:27:54
 *@version 1.0
 */
public abstract class AbstractListPage extends AbstractBaseListPage {
	private static final long serialVersionUID = 1L;
	/**系统TabPanel*/
	@Resource(name="systemTabPanel")
    private SystemTabPanel systemTabPanel;
	/**系统容器*/
	@Resource(name="pageContainer")
    private PageContainer pageContainer;

	/**
	 * Layout for this AbstractPage is BorderLayout. <br/>
	 * getTopPane() -- north <br/>
	 * getCenterPane() -- center <br/>
	 * getBottomPane() -- south <br/>
	 * getLeftPane() -- west <br/>
	 * getRightPane() -- ease <br/>
	 * */
	protected AbstractListPage() {
		super();
	}
	/**
	 * 显示系统
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 上午10:11:27
	 * @version 1.0
	 * @exception 
	 * @param index
	 */
	public void showTabPanel(int index){
		pageContainer.show(systemTabPanel.getPageId());
		systemTabPanel.getTabbedPane().setSelectedIndex(index);
	}

}
