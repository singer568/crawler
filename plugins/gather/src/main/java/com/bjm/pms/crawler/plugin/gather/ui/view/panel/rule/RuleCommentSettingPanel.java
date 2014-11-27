package com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.RuleCommentBean;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
/**
 * 评论属性采集规则设置
 *@author DuanYong
 *@since 2012-11-10上午9:55:07
 *@version 1.0
 */
@Component("ruleCommentSettingPanel")
public class RuleCommentSettingPanel extends AbstractContentPanel<RuleCommentBean>{
	private static final long serialVersionUID = 1L;

	/**评论列表入口属性输入框*/
	private javax.swing.JTextArea commentListArea;
	/**评论列表入口属性标签*/
	private javax.swing.JLabel commentListLabel;
	/**评论列表入口过滤属性输入框*/
	private javax.swing.JTextArea commentListFilterArea;
	
	/**评论内容列表区域属性输入框*/
	private javax.swing.JTextArea commentContentListArea;
	/**评论内容列表区域属性标签*/
	private javax.swing.JLabel commentContentListLabel;
	/**评论内容列表区域过滤属性输入框*/
	private javax.swing.JTextArea commentContentListFilterArea;
	
	
	/**评论内容属性输入框*/
	private javax.swing.JTextArea commentContentArea;
	/**评论内容属性标签*/
	private javax.swing.JLabel commentContentLabel;
	/**评论内容过滤属性输入框*/
	private javax.swing.JTextArea commentContentFilterArea;
	
	
	/**评论链接区域属性输入框*/
	private javax.swing.JTextArea commentLinkArea;
	/**评论链接区域属性标签*/
	private javax.swing.JLabel commentLinkLabel;
	/**评论链接区域过滤属性输入框*/
	private javax.swing.JTextArea commentLinkFilterArea;
	

	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		
		
		commentListLabel = new javax.swing.JLabel();
		commentListArea = new javax.swing.JTextArea(4,20);
		
		commentListFilterArea = new javax.swing.JTextArea(4,20);
		
		commentContentListLabel = new javax.swing.JLabel();
		commentContentListArea = new javax.swing.JTextArea(4,20);
		commentContentListFilterArea = new javax.swing.JTextArea(4,20);
		
		commentContentLabel = new javax.swing.JLabel();
		commentContentArea = new javax.swing.JTextArea(4,20);
		commentContentFilterArea = new javax.swing.JTextArea(4,20);
		
		commentLinkLabel = new javax.swing.JLabel();
		commentLinkArea = new javax.swing.JTextArea(4,20);
		commentLinkFilterArea = new javax.swing.JTextArea(4,20);
		
		JLabel contentLabel = new javax.swing.JLabel(LanguageLoader.getString("RuleContentSetting.content"));
		JLabel filterLabel = new javax.swing.JLabel(LanguageLoader.getString("RuleContentSetting.filter"));
		
		contentLabel.setBounds(180, 15, 250, 15);
		add(contentLabel);
		filterLabel.setBounds(440, 15, 250, 15);
		add(filterLabel);
		
		commentListLabel.setText(LanguageLoader.getString("RuleContentSetting.commentList"));
		add(commentListLabel);
		commentListLabel.setBounds(20, 45, 250, 15);

		JPanel commentListAreaPanel = new JPanel(new BorderLayout()); 
		commentListArea.setLineWrap(true);
		commentListArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentListAreaPanel.add(new JScrollPane(commentListArea));
		
		commentListAreaPanel.setBounds(100, 45, 250, 80);
		add(commentListAreaPanel);
		
		JPanel commentListFilterAreaPanel = new JPanel(new BorderLayout()); 
		commentListFilterArea.setLineWrap(true);
		commentListFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentListFilterAreaPanel.add(new JScrollPane(commentListFilterArea));
		commentListFilterAreaPanel.setBounds(360, 45, 250, 80);
		add(commentListFilterAreaPanel);
		
		
		
		commentContentListLabel.setText(LanguageLoader.getString("RuleContentSetting.commentContentList"));
		add(commentContentListLabel);
		commentContentListLabel.setBounds(20, 135, 250, 15);

		JPanel commentContentListAreaPanel = new JPanel(new BorderLayout()); 
		commentContentListArea.setLineWrap(true);
		commentContentListArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentContentListAreaPanel.add(new JScrollPane(commentContentListArea));
		commentContentListAreaPanel.setBounds(100, 135, 250, 80);
		add(commentContentListAreaPanel);
		
		

		JPanel commentContentListFilterAreaPanel = new JPanel(new BorderLayout()); 
		commentContentListFilterArea.setLineWrap(true);
		commentContentListFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentContentListFilterAreaPanel.add(new JScrollPane(commentContentListFilterArea));
		commentContentListFilterAreaPanel.setBounds(360, 135, 250, 80);
		add(commentContentListFilterAreaPanel);
		
		
		
		
		commentContentLabel.setText(LanguageLoader.getString("RuleContentSetting.commentContent"));
		add(commentContentLabel);
		commentContentLabel.setBounds(20, 225, 250, 15);

		JPanel commentContentAreaPanel = new JPanel(new BorderLayout()); 
		commentContentArea.setLineWrap(true);
		commentContentArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentContentAreaPanel.add(new JScrollPane(commentContentArea));
		commentContentAreaPanel.setBounds(100, 225, 250, 80);
		add(commentContentAreaPanel);
		
		

		JPanel commentContentFilterAreaPanel = new JPanel(new BorderLayout()); 
		commentContentFilterArea.setLineWrap(true);
		commentContentFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentContentFilterAreaPanel.add(new JScrollPane(commentContentFilterArea));
		commentContentFilterAreaPanel.setBounds(360, 225, 250, 80);
		add(commentContentFilterAreaPanel);
		
		
		
		commentLinkLabel.setText(LanguageLoader.getString("RuleContentSetting.commentLink"));
		add(commentLinkLabel);
		commentLinkLabel.setBounds(20, 315, 250, 15);

		JPanel commentLinkAreaPanel = new JPanel(new BorderLayout()); 
		commentLinkArea.setLineWrap(true);
		commentLinkArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentLinkAreaPanel.add(new JScrollPane(commentLinkArea));
		commentLinkAreaPanel.setBounds(100, 315, 250, 80);
		add(commentLinkAreaPanel);
		
		

		JPanel commentLinkFilterAreaPanel = new JPanel(new BorderLayout()); 
		commentLinkFilterArea.setLineWrap(true);
		commentLinkFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		commentLinkFilterAreaPanel.add(new JScrollPane(commentLinkFilterArea));
		commentLinkFilterAreaPanel.setBounds(360, 315, 250, 80);
		add(commentLinkFilterAreaPanel);
	}
	
	protected void fillComponentData(RuleCommentBean ruleCommentBean){
		commentListArea.setText(ruleCommentBean.getCommentIndexStart());
	
		commentListFilterArea.setText(ruleCommentBean.getCommentIndexEnd());
		
		commentContentListArea.setText(ruleCommentBean.getCommentAreaStart());
		
		commentContentListFilterArea.setText(ruleCommentBean.getCommentAreaEnd());
		
		commentContentArea.setText(ruleCommentBean.getCommentStart());
	
		commentContentFilterArea.setText(ruleCommentBean.getCommentEnd());
	
		commentLinkArea.setText(ruleCommentBean.getCommentLinkStart());
		
		commentLinkFilterArea.setText(ruleCommentBean.getCommentLinkEnd());
	}
	
	@Override
	protected RuleCommentBean populateData() {
		RuleCommentBean ruleCommentBean = new RuleCommentBean();
		ruleCommentBean.setCommentIndexStart(commentListArea.getText());
		ruleCommentBean.setCommentIndexEnd(commentListFilterArea.getText());
		ruleCommentBean.setCommentLinkStart(commentLinkArea.getText());
		ruleCommentBean.setCommentLinkEnd(commentLinkFilterArea.getText());
		ruleCommentBean.setCommentAreaStart(commentContentListArea.getText());
		ruleCommentBean.setCommentAreaEnd(commentContentFilterArea.getText());
		ruleCommentBean.setCommentStart(commentContentArea.getText());
		ruleCommentBean.setCommentEnd(commentContentFilterArea.getText());
		return ruleCommentBean;
	}
	public void initData(RuleCommentBean t){
		if(t == null){
			t = new RuleCommentBean();
		}
		fillComponentData(t);
	}

}
