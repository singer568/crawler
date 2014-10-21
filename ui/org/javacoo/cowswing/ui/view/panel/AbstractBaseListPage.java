package org.javacoo.cowswing.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.annotation.Resource;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.cowswing.ui.util.PaginationBar;

/**
 * 抽象类
 *@author DuanYong
 *@since 2012-11-5下午3:27:54
 *@version 1.0
 */
public abstract class AbstractBaseListPage extends JPanel implements IPage {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
    
	private boolean hasInit = false;
	
	private boolean isDefaultPage;

	
	protected JPanel buttonBar;

	/**
	 * 分页状态栏
	 */
	@Resource(name="paginationBar")
	protected PaginationBar paginationBar;

	/**
	 * Layout for this AbstractPage is BorderLayout. <br/>
	 * getTopPane() -- north <br/>
	 * getCenterPane() -- center <br/>
	 * getBottomPane() -- south <br/>
	 * getLeftPane() -- west <br/>
	 * getRightPane() -- ease <br/>
	 * */
	protected AbstractBaseListPage() {
		super();
	}
	public void init(){
		if(!hasInit){
			BorderLayout layout = new BorderLayout();
			this.setLayout(layout);

			//top pane.
			if (getTopPane() != null) {
				this.add(getTopPane(), BorderLayout.NORTH);
			}
			//center pane.
			if (getCenterPane() != null) {
				this.add(getCenterPane(), BorderLayout.CENTER);
			}
	        //bottom pane.
			if (getBottomPane() != null) {
				this.add(getBottomPane(), BorderLayout.SOUTH);
			}
			//left pane.
			if (getLeftPane() != null) {
				this.add(getLeftPane(), BorderLayout.WEST);
			}
	       //right pane.
			if (getRightPane() != null) {
				this.add(getRightPane(), BorderLayout.EAST);
			}
			// Add IPassbookObserver
			CowSwingObserver.getInstance().addCrawlerListener(this);
			hasInit = true;
		}
		initData();
	}
	/**
	 * 初始化数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-10 上午11:02:11
	 * @return void
	 */
	protected void initData(){
    	showData(0,Constant.PAGE_LIMIT);
    }
	/**
	 * @return buttonBar. layout for this pane is FlowLayout.
	 * */
	protected Component getButtonBar() {
		if (buttonBar == null) {
			buttonBar = new JPanel();
			FlowLayout layou = new FlowLayout(FlowLayout.LEADING, 3, 2);
			buttonBar.setLayout(layou);
		}
		return buttonBar;
	}

	/**
	 * @return getTopPane
	 * */
	protected Component getTopPane() {
		return getButtonBar();
	}
	
	/**
	 * @return getCenterPane
	 * */
	protected Component getCenterPane() {
		return null;
	}

	/**
	 * @return getBottomPane If is not created, not layout to this page.
	 * */
	protected Component getBottomPane() {
		return paginationBar;
	}
	
	protected Component getLeftPane() {
		return null;
	}
	
	protected Component getRightPane() {
		return null;
	}
	
	public String getPageId() {
		return this.getClass().getName();
	}

	public String getPageName() {
		return this.getClass().getSimpleName();
	}

	public void disposePage() {
		CowSwingObserver.getInstance().removeCrawlerListener(this);
	}

	@Override
	public boolean isDefaultPage() {
		return isDefaultPage;
	}

	@Override
	public void setDefaultPage(boolean bool) {
		isDefaultPage = bool;
	}
	public void showData(int pageNumber,int pageSize){
		
	}

}
