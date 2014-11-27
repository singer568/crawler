/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.panel;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.beans.CrawlerFtpConfigBean;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.listener.IntegerVerifier;
import com.bjm.pms.crawler.view.ui.listener.TextVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;


/**
 * FTP参数设置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:26:54
 * @version 1.0
 */
@Component("ftpSettingPanel")
public class FtpSettingPanel extends AbstractContentPanel<CrawlerFtpConfigBean>{

	private static final long serialVersionUID = 1L;
	/**FTP服务器名称输入框*/
	private javax.swing.JTextField nameField;
	/**FTP服务器名称标签*/
	private javax.swing.JLabel nameLabel;
	/**FTP服务器hostname*/
	private javax.swing.JTextField urlField;
	/**FTP服务器hostname标签*/
	private javax.swing.JLabel urlLabel;
	/**FTP服务器端口输入框*/
	private javax.swing.JTextField portField;
	/**FTP服务器端口标签*/
	private javax.swing.JLabel portLabel;
	/**FTP登录账号输入框*/
	private javax.swing.JTextField userNameField;
	/**FTP登录账号标签*/
	private javax.swing.JLabel userNameLabel;
	/**FTP登录密码输入框*/
	private javax.swing.JTextField passwordField;
	/**FTP登录密码标签*/
	private javax.swing.JLabel passwordLabel;
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected CrawlerFtpConfigBean populateData() {
		CrawlerFtpConfigBean crawlerFtpConfigBean = new CrawlerFtpConfigBean();
		crawlerFtpConfigBean.setFtpName(nameField.getText());
		crawlerFtpConfigBean.setFtpPassword(passwordField.getText());
		crawlerFtpConfigBean.setFtpPort(portField.getText());
		crawlerFtpConfigBean.setFtpUrl(urlField.getText());
		crawlerFtpConfigBean.setFtpUserName(userNameField.getText());
		return crawlerFtpConfigBean;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		nameLabel = new javax.swing.JLabel();
		nameField = new javax.swing.JTextField();
		
		portLabel = new javax.swing.JLabel();
		portField = new javax.swing.JTextField();
		
		urlLabel = new javax.swing.JLabel();
		urlField = new javax.swing.JTextField();
		
		userNameLabel = new javax.swing.JLabel();
		userNameField = new javax.swing.JTextField();
		
		passwordLabel = new javax.swing.JLabel();
		passwordField = new javax.swing.JTextField();
		
		
		
		nameLabel.setText(LanguageLoader.getString("System.Ftp_name"));
		add(nameLabel);
		nameLabel.setBounds(20, 15, 80, 15);

		nameField.setColumns(20);
		add(nameField);
		nameField.setBounds(100, 15, 200, 21);
		nameField.setInputVerifier(new TextVerifier(this, false));
		
		
		urlLabel.setText(LanguageLoader.getString("System.Ftp_url"));
		add(urlLabel);
		urlLabel.setBounds(20, 45, 80, 15);

		urlField.setColumns(20);
		add(urlField);
		urlField.setBounds(100, 45, 200, 21);
		urlField.setInputVerifier(new TextVerifier(this, false));
		
		
		
		portLabel.setText(LanguageLoader.getString("System.Ftp_port"));
		add(portLabel);
		portLabel.setBounds(20, 75, 80, 15);

		portField.setColumns(20);
		add(portField);
		portField.setBounds(100, 75, 200, 21);
		portField.setInputVerifier(new IntegerVerifier(this, false, 1, 10000));
		
		
		userNameLabel.setText(LanguageLoader.getString("System.Ftp_userName"));
		add(userNameLabel);
		userNameLabel.setBounds(20, 105, 80, 15);

		userNameField.setColumns(20);
		add(userNameField);
		userNameField.setBounds(100, 105, 200, 21);
		userNameField.setInputVerifier(new TextVerifier(this, false));
		
		
		passwordLabel.setText(LanguageLoader.getString("System.Ftp_password"));
		add(passwordLabel);
		passwordLabel.setBounds(20, 135, 80, 15);

		passwordField.setColumns(20);
		add(passwordField);
		passwordField.setBounds(100, 135, 200, 21);
		passwordField.setInputVerifier(new TextVerifier(this, false));
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(CrawlerFtpConfigBean t) {
		logger.info("填充页面控件数据");
		nameField.setText(t.getFtpName());
		portField.setText(t.getFtpPort());
		urlField.setText(t.getFtpUrl());
		userNameField.setText(t.getFtpUserName());
		passwordField.setText(t.getFtpPassword());
	}
	
	public void initData(CrawlerFtpConfigBean t){
		if(t == null){
			t = new CrawlerFtpConfigBean();
		}
		fillComponentData(t);
	}

}
