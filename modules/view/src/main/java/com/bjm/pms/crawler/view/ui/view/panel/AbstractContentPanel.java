package com.bjm.pms.crawler.view.ui.view.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.listener.VerifierListener;

/**
 * 内容面板抽象类
 *@author DuanYong
 *@since 2012-11-16上午10:04:16
 *@version 1.0
 */
public abstract class AbstractContentPanel<T> extends JPanel implements CowSwingListener,VerifierListener,IContentPanel<T>{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	//窗体高度大小
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	/**错误信息提示*/
	private javax.swing.JLabel errorLabel;
	
	public AbstractContentPanel(){
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT)); 
		setLayout(null);
		initComponents();
		initErrorLabel();
		initActionListener();
	}
	
	private void initErrorLabel(){
		errorLabel = new javax.swing.JLabel();
		
		errorLabel.setForeground(new java.awt.Color(255, 51, 51));
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(errorLabel);
        errorLabel.setBounds(30, 420, 400, 20);
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
		errorLabel.setText(message);
		errorLabel.setForeground(Color.red);
		//startButton.setEnabled(false); // turn off the start button
		getToolkit().beep();
	}

	public void validData(JComponent jComponent) {
		errorLabel.setText("");
		//startButton.setEnabled(true); // turn off the start button
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
	/**
	 * 取得错误信息描述
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-28 下午3:36:00
	 * @return
	 * @return String
	 */
	public String getErrorMsg(){
		return errorLabel.getText();
	}

}
