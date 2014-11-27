/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.view.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.listener.TextVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
import com.bjm.pms.crawler.webservice.manager.beans.FeedBackBean;

/**
 * 意见反馈信息PANEL
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 上午11:27:07
 * @version 1.0
 */
@Component("feedBackPanel")
public class FeedBackPanel extends AbstractContentPanel<FeedBackBean>{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/**电子邮件标签*/
	private javax.swing.JLabel emailLabel;
	/**电子邮件Field*/
	private javax.swing.JTextField emailField;
	/**电话标签*/
	private javax.swing.JLabel phoneLabel;
	/**电话Field*/
	private javax.swing.JTextField phoneField;
	/**qq标签*/
	private javax.swing.JLabel qqLabel;
	/**qqField*/
	private javax.swing.JTextField qqField;
	/**标题标签*/
	private javax.swing.JLabel titleLabel;
	/**标题Field*/
	private javax.swing.JTextField titleField;
	/**内容标签*/
	private javax.swing.JLabel contentLabel;
	/**内容JTextArea*/
	private javax.swing.JTextArea contentArea;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected FeedBackBean populateData() {
		FeedBackBean feedBackBean = new FeedBackBean();
		feedBackBean.setContent(contentArea.getText());
		feedBackBean.setEmail(emailField.getText());
		feedBackBean.setPhone(phoneField.getText());
		feedBackBean.setQq(qqField.getText());
		feedBackBean.setTitle(titleField.getText());
		return feedBackBean;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		emailLabel = new javax.swing.JLabel();
		emailLabel.setText(LanguageLoader.getString("Core.feedBack.email"));
		add(emailLabel);
		emailLabel.setBounds(20, 15, 80, 15);
		
		
		emailField = new javax.swing.JTextField();
		add(emailField);
		emailField.setBounds(110, 15, 320, 20);
		
		
		phoneLabel = new javax.swing.JLabel();
		phoneLabel.setText(LanguageLoader.getString("Core.feedBack.phone"));
		add(phoneLabel);
		phoneLabel.setBounds(20, 45, 80, 15);
		
		phoneField = new javax.swing.JTextField();
		add(phoneField);
		phoneField.setBounds(110, 45, 320, 20);
		
		
		qqLabel = new javax.swing.JLabel();
		qqLabel.setText(LanguageLoader.getString("Core.feedBack.qq"));
		add(qqLabel);
		qqLabel.setBounds(20, 75, 80, 15);
		
		qqField = new javax.swing.JTextField();
		add(qqField);
		qqField.setBounds(110, 75, 320, 20);
		
		titleLabel = new javax.swing.JLabel();
		titleLabel.setText(LanguageLoader.getString("Core.feedBack.title"));
		add(titleLabel);
		titleLabel.setBounds(20, 105, 80, 15);
		
		titleField = new javax.swing.JTextField();
		titleField.setInputVerifier(new TextVerifier(this,false));
		add(titleField);
		titleField.setBounds(110, 105, 320, 20);
		
		
		contentLabel = new javax.swing.JLabel();
		contentLabel.setText(LanguageLoader.getString("Core.feedBack.content"));
		add(contentLabel);
		contentLabel.setBounds(20, 135, 80, 15);
		
		contentArea = new javax.swing.JTextArea(4,40);
		JPanel replaceWordAreaPanel = new JPanel(new BorderLayout()); 
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);//激活断行不断字功能 
		replaceWordAreaPanel.add(new JScrollPane(contentArea));
		replaceWordAreaPanel.setBounds(110, 135, 320, 100);
		add(replaceWordAreaPanel);
	
	}
	protected void initActionListener(){
		
	}
	public void initData(FeedBackBean t){
		fillComponentData(t);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(FeedBackBean t) {
		
		
	}
	
}
