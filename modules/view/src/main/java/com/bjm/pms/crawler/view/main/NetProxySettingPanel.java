/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;


/**
 * 网络代理设置面板
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-3 上午9:49:53
 * @version 1.0
 */
public class NetProxySettingPanel extends AbstractContentPanel<NetProxySettingBean> {
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/** 代理地址标签 */
	private javax.swing.JLabel proxyHostLabel;
	/** 代理地址输入框 */
	private javax.swing.JTextField proxyHostField;
	/** 代理端口标签 */
	private javax.swing.JLabel proxyPortLabel;
	/** 代理端口输入框 */
	private javax.swing.JTextField proxyPortField;
	/** 用户名标签 */
	private javax.swing.JLabel userNameLabel;
	/** 用户名输入框 */
	private javax.swing.JTextField userNameField;
	/** 密码标签 */
	private javax.swing.JLabel passwordLabel;
	/** 密码输入框 */
	private javax.swing.JTextField passwordField;
	/** 域标签 */
	private javax.swing.JLabel domainLabel;
	/** 用域输入框 */
	private javax.swing.JTextField domainField;
	/**是否启用*/
	private JCheckBox isUsedCheckBox;
	private static NetProxySettingPanel instance = null;
	
	private NetProxySettingPanel(){
		
	}
	
	
	/**
	 * @return the instance
	 */
	public static NetProxySettingPanel getInstance() {
		if(null == instance){
			instance = new NetProxySettingPanel();
		}
		return instance;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected NetProxySettingBean populateData() {
		NetProxySettingBean netSettingBean = new NetProxySettingBean();
		netSettingBean.setProxyHost(proxyHostField.getText());
		netSettingBean.setProxyPort(proxyPortField.getText());
		netSettingBean.setUserName(userNameField.getText());
		netSettingBean.setPassword(passwordField.getText());
		netSettingBean.setDomain(domainField.getText());
		netSettingBean.setIsUsed(Boolean.valueOf(isUsedCheckBox.isSelected()).toString());
		return netSettingBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {

		proxyHostLabel = new javax.swing.JLabel();
		proxyHostLabel.setText("代理地址:");
		addCmp(proxyHostLabel);
		proxyHostLabel.setBounds(20, 15, 80, 15);

		proxyHostField = new javax.swing.JTextField();
		proxyHostField.setColumns(20);
		proxyHostField.setText("");
		addCmp(proxyHostField);
		proxyHostField.setBounds(110, 15, 130, 21);

		proxyPortLabel = new javax.swing.JLabel();
		proxyPortLabel.setText("代理端口:");
		addCmp(proxyPortLabel);
		proxyPortLabel.setBounds(20, 45, 80, 15);

		proxyPortField = new javax.swing.JTextField();
		proxyPortField.setColumns(20);
		proxyPortField.setText("");
		addCmp(proxyPortField);
		proxyPortField.setBounds(110, 45, 130, 21);

		userNameLabel = new javax.swing.JLabel();
		userNameLabel.setText("用户名:");
		addCmp(userNameLabel);
		userNameLabel.setBounds(20, 75, 80, 15);

		userNameField = new javax.swing.JTextField();
		userNameField.setColumns(20);
		userNameField.setText("");
		addCmp(userNameField);
		userNameField.setBounds(110, 75, 130, 21);
		
		
		passwordLabel = new javax.swing.JLabel();
		passwordLabel.setText("密码:");
		addCmp(passwordLabel);
		passwordLabel.setBounds(20, 105, 80, 15);

		passwordField = new javax.swing.JTextField();
		passwordField.setColumns(20);
		passwordField.setText("");
		addCmp(passwordField);
		passwordField.setBounds(110, 105, 130, 21);
		
		
		domainLabel = new javax.swing.JLabel();
		domainLabel.setText("域:");
		addCmp(domainLabel);
		domainLabel.setBounds(20, 135, 80, 15);

		domainField = new javax.swing.JTextField();
		domainField.setColumns(20);
		domainField.setText("");
		addCmp(domainField);
		domainField.setBounds(110, 135, 130, 21);

		isUsedCheckBox = new JCheckBox("是否启用");
		isUsedCheckBox.setOpaque(false);
		addCmp(isUsedCheckBox);
		isUsedCheckBox.setBounds(110, 165, 130, 21);
	}
	private void addCmp(Component comp){
		setupComponent(comp);
		add(comp);
	}
	private void setupComponent(Component c) {
		c.setFont(new Font("微软雅黑", 1, 14));
		c.setForeground(new Color(37, 81, 54));
	}
	/**
	 * 初始化事件
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-11-16 上午11:00:17
	 * @return void
	 */
	protected void initActionListener() {
		
		
	}

	public void initData(NetProxySettingBean t) {
		if (t == null) {
			t = new NetProxySettingBean();
		}
		fillComponentData(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#fillComponentData
	 * (java.lang.Object)
	 */
	@Override
	protected void fillComponentData(NetProxySettingBean t) {
		proxyHostField.setText(t.getProxyHost());
		proxyPortField.setText(t.getProxyPort());
		userNameField.setText(t.getUserName());
		passwordField.setText(t.getPassword());
		domainField.setText(t.getDomain());
		isUsedCheckBox.setSelected(Boolean.valueOf(t.getIsUsed()));
	}

	/**
	 * 更新监听执行事件
	 */
	public void update(CowSwingEvent event) {
		
	}

}
