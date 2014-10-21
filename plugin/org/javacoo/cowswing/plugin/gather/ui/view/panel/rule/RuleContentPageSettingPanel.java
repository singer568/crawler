package org.javacoo.cowswing.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.RuleContentPageBean;
import org.javacoo.cowswing.ui.view.panel.AbstractContentPanel;
import org.springframework.stereotype.Component;
/**
 * 内容分页属性采集规则设置
 *@author DuanYong
 *@since 2012-11-10上午9:55:07
 *@version 1.0
 */
@Component("ruleContentPageSettingPanel")
public class RuleContentPageSettingPanel extends AbstractContentPanel<RuleContentPageBean>{
	private static final long serialVersionUID = 1L;
	/**内容分页属性输入框*/
	private javax.swing.JTextArea contentPageArea;
	/**内容分页属性标签*/
	private javax.swing.JLabel contentPageLabel;
	/**内容分页过滤属性输入框*/
	private javax.swing.JTextArea contentPageFilterArea;
	

	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		
		
		contentPageLabel = new javax.swing.JLabel();
		contentPageArea = new javax.swing.JTextArea(5,20);
		
		contentPageFilterArea = new javax.swing.JTextArea(5,20);
		
		JLabel contentLabel = new javax.swing.JLabel(LanguageLoader.getString("RuleContentSetting.content"));
		JLabel filterLabel = new javax.swing.JLabel(LanguageLoader.getString("RuleContentSetting.filter"));
		
		contentLabel.setBounds(180, 15, 250, 15);
		add(contentLabel);
		filterLabel.setBounds(440, 15, 250, 15);
		add(filterLabel);
		
		
		contentPageLabel.setText(LanguageLoader.getString("RuleContentSetting.contentPage"));
		add(contentPageLabel);
		contentPageLabel.setBounds(20, 45, 250, 15);
		
		JPanel contentPageAreaPanel = new JPanel(new BorderLayout()); 
		contentPageArea.setLineWrap(true);//激活自动换行功能 
		contentPageArea.setWrapStyleWord(true);//激活断行不断字功能 
		contentPageAreaPanel.add(new JScrollPane(contentPageArea));
		//contentPageArea.setBounds(130, 45, 180, 51);
		contentPageAreaPanel.setBounds(100, 45, 250, 100);
		add(contentPageAreaPanel);
		
		JPanel contentPageFilterAreaPanel = new JPanel(new BorderLayout()); 
		contentPageFilterArea.setLineWrap(true);
		contentPageFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		contentPageFilterAreaPanel.add(new JScrollPane(contentPageFilterArea));
		//contentPageFilterArea.setBounds(390, 45, 180, 51);
		contentPageFilterAreaPanel.setBounds(360, 45, 250, 100);
		add(contentPageFilterAreaPanel);
	}
	
	protected void fillComponentData(RuleContentPageBean ruleContentPageBean){
		contentPageArea.setText(ruleContentPageBean.getPaginationStart());
		contentPageFilterArea.setText(ruleContentPageBean.getPaginationEnd());
	}
	
	public void initData(RuleContentPageBean t){
		if(null == t){
			t = new RuleContentPageBean();
		}
		fillComponentData(t);
	}
	
	@Override
	protected RuleContentPageBean populateData() {
		RuleContentPageBean ruleContentPageBean = new RuleContentPageBean();
		ruleContentPageBean.setPaginationStart(contentPageArea.getText());
		ruleContentPageBean.setPaginationEnd(contentPageFilterArea.getText());
		return ruleContentPageBean;
	}
	

}
