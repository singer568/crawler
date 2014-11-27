/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JComboBox;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseBean;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;

/**
 * 数据库参数配置
 *@author DuanYong
 *@since 2013-2-14下午6:08:12
 *@version 1.0
 */
@Component("dataBaseSettingPanel")
public class DataBaseSettingPanel extends AbstractContentPanel<CrawlerDataBaseBean>{
	private static final long serialVersionUID = 1L;
	
	/**数据库信息描述输入框*/
	private javax.swing.JTextField descField;
	/**数据库信息描述标签*/
	private javax.swing.JLabel descLabel;
	/**驱动名称输入框*/
	private javax.swing.JTextField classNameField;
	/**驱动名称标签*/
	private javax.swing.JLabel classNameLabel;
	/**url输入框*/
	private javax.swing.JTextField urlField;
	/**url标签*/
	private javax.swing.JLabel urlLabel;
	/**用户名输入框*/
	private javax.swing.JTextField userNameField;
	/**用户名标签*/
	private javax.swing.JLabel userNameLabel;
	/**密码输入框*/
	private javax.swing.JTextField passwordField;
	/**密码标签*/
	private javax.swing.JLabel passwordLabel;
	/**数据库类型*/
	private JComboBox typeCombo;
	/**密码标签*/
	private javax.swing.JLabel typeLabel;
	/**
	 * 初始化面板控件
	 */
	@Override
	protected void initComponents() {
		descLabel = new javax.swing.JLabel();
		descField = new javax.swing.JTextField();
		
		String[] data = new String[CrawlerDataBaseBean.DATABASE_TYPE_MAP.size()];
		int i = 0;
		for(Iterator<String> it = CrawlerDataBaseBean.DATABASE_TYPE_MAP.keySet().iterator();it.hasNext();){
			data[i] = it.next();
			i++;
		}
		
		
		typeLabel = new javax.swing.JLabel();
		typeCombo = new JComboBox(data);
		typeCombo.setSize(50, 20);
		
		classNameLabel = new javax.swing.JLabel();
		classNameField = new javax.swing.JTextField();
		
		urlLabel = new javax.swing.JLabel();
		urlField = new javax.swing.JTextField();
		
		userNameLabel = new javax.swing.JLabel();
		userNameField = new javax.swing.JTextField();
		
		passwordLabel = new javax.swing.JLabel();
		passwordField = new javax.swing.JTextField();
		
		
		
		
		
		
		
		
		descLabel.setText(LanguageLoader.getString("System.DataBase_desc"));
		add(descLabel);
		descLabel.setBounds(20, 15, 250, 15);

		descField.setColumns(20);
		add(descField);
		descField.setBounds(130, 15, 490, 21);
		
		typeLabel.setText(LanguageLoader.getString("System.DataBase_type"));
		add(typeLabel);
		typeLabel.setBounds(20, 45, 250, 15);
		add(typeCombo);
		typeCombo.setBounds(130, 45, 490, 21);
		
		classNameLabel.setText(LanguageLoader.getString("System.DataBase_className"));
		add(classNameLabel);
		classNameLabel.setBounds(20, 75, 250, 15);

		classNameField.setColumns(20);
		add(classNameField);
		classNameField.setBounds(130, 75, 490, 21);
		
		
		urlLabel.setText(LanguageLoader.getString("System.DataBase_url"));
		add(urlLabel);
		urlLabel.setBounds(20, 105, 250, 15);

		urlField.setColumns(20);
		add(urlField);
		urlField.setBounds(130, 105, 490, 21);
		
		
		userNameLabel.setText(LanguageLoader.getString("System.DataBase_userName"));
		add(userNameLabel);
		userNameLabel.setBounds(20, 135, 250, 15);

		userNameField.setColumns(20);
		add(userNameField);
		userNameField.setBounds(130, 135, 490, 21);
		
		
		passwordLabel.setText(LanguageLoader.getString("System.DataBase_password"));
		add(passwordLabel);
		passwordLabel.setBounds(20, 175, 250, 15);

		passwordField.setColumns(20);
		add(passwordField);
		passwordField.setBounds(130, 175, 490, 21);
		
	}
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected CrawlerDataBaseBean populateData() {
		CrawlerDataBaseBean crawlerDataBaseBean = new CrawlerDataBaseBean();
		crawlerDataBaseBean.setClassName(classNameField.getText());
		crawlerDataBaseBean.setDescription(descField.getText());
		crawlerDataBaseBean.setPassword(passwordField.getText());
		crawlerDataBaseBean.setType(typeCombo.getSelectedItem().toString());
		crawlerDataBaseBean.setUrl(urlField.getText());
		crawlerDataBaseBean.setUserName(userNameField.getText());
		return crawlerDataBaseBean;
	}

	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(CrawlerDataBaseBean t) {
		logger.info("填充页面控件数据");
		descField.setText(t.getDescription());
		if(StringUtils.isNotBlank(t.getType())){
			typeCombo.setSelectedItem(t.getType());
		}
		classNameField.setText(t.getClassName());
		urlField.setText(t.getUrl());
		userNameField.setText(t.getUserName());
		passwordField.setText(t.getPassword());
	}
	/**
	 * 初始化监听事件
	 */
	protected void initActionListener(){
		typeCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				urlField.setText(CrawlerDataBaseBean.DATABASE_TYPE_URL_MAP.get(typeCombo.getSelectedItem().toString()));
				classNameField.setText(CrawlerDataBaseBean.DATABASE_TYPE_CLASSNAME_MAP.get(typeCombo.getSelectedItem().toString()));
			}
		});
	}
	public void initData(CrawlerDataBaseBean t){
		if(t == null){
			t = new CrawlerDataBaseBean();
		}
		fillComponentData(t);
	}
}
