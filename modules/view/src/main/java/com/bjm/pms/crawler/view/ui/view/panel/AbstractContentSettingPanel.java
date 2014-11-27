/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;
import com.bjm.pms.crawler.view.ui.listener.VerifierListener;
/**
 * 抽象内容设置面板,主要用于在主窗口中显示
 *@author DuanYong
 *@since 2013-3-13上午9:43:31
 *@version 1.0
 * @param <T>
 */
public abstract class AbstractContentSettingPanel<T> extends JPanel implements IPage,VerifierListener,IContentPanel<T>{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private boolean hasInit = false;
	private boolean isDefaultPage;
	protected JPanel buttonBar;
	
	protected AbstractContentSettingPanel() {
		super();
	}
	public void init(){
		if(!hasInit){
			BorderLayout layout = new BorderLayout();
			this.setLayout(layout);

			initComponents();
			initActionListener();
			
			if (getBottomPane() != null) {
				this.add(getBottomPane(), BorderLayout.SOUTH);
			}
			if (getCenterPane() != null) {
				this.add(getCenterPane(), BorderLayout.CENTER);
			}
			
			CowSwingObserver.getInstance().addCrawlerListener(this);
			hasInit = true;
		}
		initData(null);
	}
	protected Component getBottomPane() {
		return buttonBar;
	}
	protected Component getCenterPane() {
		return null;
	}
	protected Component getButtonBar() {
		if (buttonBar == null) {
			buttonBar = new JPanel();
			FlowLayout layou = new FlowLayout(FlowLayout.LEADING, 3, 2);
			layou.setAlignment(FlowLayout.RIGHT);
			buttonBar.setLayout(layou);
		}
		return buttonBar;
	}
	/**
	 * 初始化事件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-16 上午11:00:17
	 * @return void
	 */
	protected void initActionListener(){
		
	}
	/**
	 * 更新监听执行事件
	 */
	public void update(CowSwingEvent event) {
		
	}
	/**
	 * 失败时调用
	 */
	@Override
	public void invalidData(String message, JComponent component) {
		CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.ValidDataChangeEvent,message));
		getToolkit().beep();
	}

	public void validData(JComponent jComponent) {
		CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.ValidDataChangeEvent,""));
	}
	/**
	 * 初始化数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-20 上午10:54:47
	 * @return void
	 */
	public void initData(T t){
		
	};
	/**
	 * 取得内容对象
	 */
	public final T getData() {
		return populateData();
	}
	/**
	 * 完成销毁动作
	 */
	public void dispose(){
		
	}

	/**
	 * 组装数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-16 上午10:10:14
	 * @return T
	 */
	protected abstract T populateData();
	/**
	 * 初始化组件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-16 上午10:10:28
	 * @return void
	 */
	protected abstract void initComponents();
	/**
	 * 填充页面控件数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 上午10:54:08
	 * @param t
	 * @return void
	 */
	protected abstract void fillComponentData(T t);
	public String getPageId() {
		return this.getClass().getName();
	}
	public String getPageName() {
		return this.getClass().getSimpleName();
	}
	public void disposePage() {
		this.dispose();
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
