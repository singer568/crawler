package com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerExtendFieldsTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerMidExtendFieldsTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.RuleSettingDialog;
import com.bjm.pms.crawler.plugin.gather.ui.view.dialog.TestRuleInfoDialog;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleContentBean;
import com.bjm.pms.crawler.view.ui.listener.IntegerVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
/**
 * 内容属性采集规则设置
 *@author DuanYong
 *@since 2012-11-10上午9:55:07
 *@version 1.0
 */
@Component("ruleContentSettingPanel")
public class RuleContentSettingPanel extends AbstractContentPanel<RuleContentBean>{
	private static final long serialVersionUID = 1L;
	/**采集地址输入框*/
	private javax.swing.JTextArea linkListArea;
	/**采集地址标签*/
	private javax.swing.JLabel linkListLabel;
	/**动态连接地址输入框*/
	private javax.swing.JTextField dynamicLinkField;
	/**动态连接地址标签*/
	private javax.swing.JLabel dynamicLinkLabel;
	/**动态连接地址说明标签*/
	private javax.swing.JLabel dynamicLinkDescLabel;
	/**动态页开始输入框*/
	private javax.swing.JTextField dynamicPageStartField;
	/**动态页开始标签*/
	private javax.swing.JLabel dynamicPageStartLabel;
	/**动态页开始结束输入框*/
	private javax.swing.JTextField dynamicPageEndField;
	/**动态页开始结束标签*/
	private javax.swing.JLabel dynamicPageEndLabel;
	/**连接区域属性输入框*/
	private javax.swing.JTextArea linkIncludeArea;
	/**连接区域属性标签*/
	private javax.swing.JLabel linkIncludeLabel;
	/**连接区域过滤属性输入框*/
	private javax.swing.JTextArea linkFilterArea;
	/**连接区域过滤属性标签*/
	private javax.swing.JLabel linkFilterLabel;
	
	
	/**中间连接扩展字段标签*/
	private javax.swing.JLabel extendsFieldsLabel;
	/**中间连接扩展字段panel*/
	private JComponent extendsFieldsPanel;
	/**添加中间连接扩展字段Button*/
	private JButton addExtednsFieldsButton;
	/**删除中间连接扩展字段Button*/
	private JButton deleteExtednsFieldsButton;
	/**中间连接扩展字段JTable*/
	private JTable crawlerExtendFieldsTabel;
	/**中间连接扩展字段TabelMode*/
	private CrawlerMidExtendFieldsTabelModel dataModel;
	
	
	/**描述区域属性输入框*/
	private javax.swing.JTextArea descIncludeArea;
	/**描述区域属性标签*/
	private javax.swing.JLabel descIncludeLabel;
	/**描述区域过滤属性输入框*/
	private javax.swing.JTextArea descFilterArea;
	/**描述区域过滤属性标签*/
	private javax.swing.JLabel descFilterLabel;
	/**内容区域属性输入框*/
	private javax.swing.JTextArea contentIncludeArea;
	/**内容区域属性标签*/
	private javax.swing.JLabel contentIncludeLabel;
	/**内容区域过滤属性输入框*/
	private javax.swing.JTextArea contentFilterArea;
	/**内容区域过滤属性标签*/
	private javax.swing.JLabel contentFilterLabel;
	/**测试采集规则按钮*/
	private JButton testButton;
	/**测试采集规则信息*/
	@Resource(name="testRuleInfoDialog")
	private TestRuleInfoDialog testRuleInfoDialog;
	@Resource(name="ruleSettingDialog")
	private RuleSettingDialog ruleSettingDialog;
	
	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		
		linkListLabel = new javax.swing.JLabel();
		linkListArea = new javax.swing.JTextArea(2,40);

		dynamicLinkLabel = new javax.swing.JLabel();
		dynamicLinkDescLabel = new javax.swing.JLabel();
		dynamicLinkField = new javax.swing.JTextField();
		
		dynamicPageStartLabel = new javax.swing.JLabel();
		dynamicPageStartField = new javax.swing.JTextField();
		
		dynamicPageEndLabel = new javax.swing.JLabel();
		dynamicPageEndField = new javax.swing.JTextField();
		
		
		linkIncludeLabel = new javax.swing.JLabel();
		linkIncludeArea = new javax.swing.JTextArea(3,5);
		
		linkFilterLabel = new javax.swing.JLabel();
		linkFilterArea = new javax.swing.JTextArea(3,5);
		
		extendsFieldsLabel = new javax.swing.JLabel();
		
		descIncludeLabel = new javax.swing.JLabel();
		descIncludeArea = new javax.swing.JTextArea(3,5);
		
		descFilterLabel = new javax.swing.JLabel();
		descFilterArea = new javax.swing.JTextArea(3,5);
		
		contentIncludeLabel = new javax.swing.JLabel();
		contentIncludeArea = new javax.swing.JTextArea(3,5);
		
		contentFilterLabel = new javax.swing.JLabel();
		contentFilterArea = new javax.swing.JTextArea(3,5);
		
		testButton = new JButton(LanguageLoader.getString("RuleContentSetting.testRule"),ImageLoader.getImageIcon("CrawlerResource.zoom"));
		
		
		
		
		linkListLabel.setText(LanguageLoader.getString("RuleContentSetting.linkList"));
		add(linkListLabel);
		linkListLabel.setBounds(20, 15, 250, 15);
		
		JPanel linkListAreaPanel = new JPanel(new BorderLayout()); 
		linkListArea.setLineWrap(true);
		linkListArea.setWrapStyleWord(true);//激活断行不断字功能 
		linkListAreaPanel.add(new JScrollPane(linkListArea));
		linkListAreaPanel.setBounds(100, 15, 490, 40);
		add(linkListAreaPanel);
		
		dynamicLinkDescLabel.setText(LanguageLoader.getString("RuleContentSetting.dynamicLinkDesc"));
		add(dynamicLinkDescLabel);
		dynamicLinkDescLabel.setBounds(100, 65, 450, 15);
		
		dynamicLinkLabel.setText(LanguageLoader.getString("RuleContentSetting.dynamicLink"));
		add(dynamicLinkLabel);
		dynamicLinkLabel.setBounds(20, 95, 250, 15);

		dynamicLinkField.setColumns(20);
		add(dynamicLinkField);
		dynamicLinkField.setBounds(100, 95, 490, 21);
		
		
		dynamicPageStartLabel.setText(LanguageLoader.getString("RuleContentSetting.dynamicPageStart"));
		add(dynamicPageStartLabel);
		dynamicPageStartLabel.setBounds(20, 125, 250, 15);

		dynamicPageStartField.setColumns(20);
		dynamicPageStartField.setInputVerifier(new IntegerVerifier(this, false, 1, 1000));
		add(dynamicPageStartField);
		dynamicPageStartField.setBounds(100, 125, 200, 21);
		
		
		dynamicPageEndLabel.setText(LanguageLoader.getString("RuleContentSetting.dynamicPageEnd"));
		add(dynamicPageEndLabel);
		dynamicPageEndLabel.setBounds(330, 125, 250, 15);

		dynamicPageEndField.setColumns(20);
		dynamicPageEndField.setInputVerifier(new IntegerVerifier(this, false, 1, 1000));
		add(dynamicPageEndField);
		dynamicPageEndField.setBounds(390, 125, 200, 21);
		
		
		linkIncludeLabel.setText(LanguageLoader.getString("RuleContentSetting.linkInclude"));
		add(linkIncludeLabel);
		linkIncludeLabel.setBounds(20, 155, 250, 15);

		
		JPanel linkIncludeAreaPanel = new JPanel(new BorderLayout()); 
		linkIncludeArea.setLineWrap(true);
		linkIncludeArea.setWrapStyleWord(true);//激活断行不断字功能 
		linkIncludeAreaPanel.add(new JScrollPane(linkIncludeArea));
		linkIncludeAreaPanel.setBounds(100, 155, 200, 60);
		add(linkIncludeAreaPanel);
		
		
		linkFilterLabel.setText(LanguageLoader.getString("RuleContentSetting.linkFilter"));
		add(linkFilterLabel);
		linkFilterLabel.setBounds(330, 155, 250, 15);

		JPanel linkFilterAreaPanel = new JPanel(new BorderLayout());
		linkFilterArea.setLineWrap(true);
		linkFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		linkFilterAreaPanel.add(new JScrollPane(linkFilterArea));
		linkFilterAreaPanel.setBounds(390, 155, 200, 60);
		add(linkFilterAreaPanel);
		
		
		
		extendsFieldsLabel.setText(LanguageLoader.getString("RuleContentSetting.middleLinkFilter"));
		add(extendsFieldsLabel);
		extendsFieldsLabel.setBounds(20, 225, 250, 15);
		
		extendsFieldsPanel = new JPanel(new BorderLayout());
		extendsFieldsPanel.setBounds(100, 225, 490, 120);
		extendsFieldsPanel.add(getTopButtonPanel(), BorderLayout.NORTH);
		extendsFieldsPanel.add(getExtendsFieldsListPanel(), BorderLayout.CENTER);
		add(extendsFieldsPanel);
		
		descIncludeLabel.setText(LanguageLoader.getString("RuleContentSetting.descInclude"));
		add(descIncludeLabel);
		descIncludeLabel.setBounds(20, 355, 250, 15);

		JPanel descIncludeAreaPanel = new JPanel(new BorderLayout());
		descIncludeArea.setLineWrap(true);
		descIncludeArea.setWrapStyleWord(true);//激活断行不断字功能 
		descIncludeAreaPanel.add(new JScrollPane(descIncludeArea));
		descIncludeAreaPanel.setBounds(100, 355, 200, 60);
		add(descIncludeAreaPanel);
		
		
		descFilterLabel.setText(LanguageLoader.getString("RuleContentSetting.descFilter"));
		add(descFilterLabel);
		descFilterLabel.setBounds(330, 355, 250, 15);

		JPanel descFilterAreaPanel = new JPanel(new BorderLayout());
		descFilterArea.setLineWrap(true);
		descFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		descFilterAreaPanel.add(new JScrollPane(descFilterArea));
		descFilterAreaPanel.setBounds(390, 355, 200, 60);
		add(descFilterAreaPanel);
		
		
		
		contentIncludeLabel.setText(LanguageLoader.getString("RuleContentSetting.contentInclude"));
		add(contentIncludeLabel);
		contentIncludeLabel.setBounds(20, 425, 250, 15);

		JPanel contentIncludeAreaPanel = new JPanel(new BorderLayout());
		contentIncludeArea.setLineWrap(true);
		contentIncludeArea.setWrapStyleWord(true);//激活断行不断字功能 
		contentIncludeAreaPanel.add(new JScrollPane(contentIncludeArea));
		contentIncludeAreaPanel.setBounds(100, 425, 200, 60);
		add(contentIncludeAreaPanel);
		
		
		contentFilterLabel.setText(LanguageLoader.getString("RuleContentSetting.contentFilter"));
		add(contentFilterLabel);
		contentFilterLabel.setBounds(330, 425, 250, 15);

		JPanel contentFilterAreaPanel = new JPanel(new BorderLayout());
		contentFilterArea.setLineWrap(true);
		contentFilterArea.setWrapStyleWord(true);//激活断行不断字功能 
		contentFilterAreaPanel.add(new JScrollPane(contentFilterArea));
		contentFilterAreaPanel.setBounds(390, 425, 200, 60);
		add(contentFilterAreaPanel);
		
		add(testButton);
		testButton.setBounds(270, 510, 120, 30);
		
		
		
		
		
		
		
		
		
		
	
	}
	protected JComponent getExtendsFieldsListPanel() {
		return new JScrollPane(getCrawlerExtendFieldsTable());
	}
	/**
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-3-17 下午9:08:40
	 * @return
	 * @return Component
	 */ 
	private java.awt.Component getCrawlerExtendFieldsTable() {
		if (crawlerExtendFieldsTabel == null) {
			crawlerExtendFieldsTabel = new JTable();
			dataModel = new CrawlerMidExtendFieldsTabelModel(getColumnNames());
			crawlerExtendFieldsTabel.setModel(dataModel);
			crawlerExtendFieldsTabel.setPreferredScrollableViewportSize(new Dimension(500, 70));
			crawlerExtendFieldsTabel.setFillsViewportHeight(true);

			crawlerExtendFieldsTabel.setAutoCreateRowSorter(true);
		}
		return crawlerExtendFieldsTabel;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("RuleContentSetting.midExtndsFieldName"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.midExtndsInclude"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.midExtndsFilter"));
		return columnNames;
	}
	protected java.awt.Component getTopButtonPanel() {
		JPanel topBar = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 3, 2);
		layout.setAlignment(FlowLayout.RIGHT);
		topBar.setLayout(layout);
		class AddExtednsFieldsAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public AddExtednsFieldsAction(){
				super(LanguageLoader.getString("RuleContentSetting.addMidLinkFields"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
			}
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				ExtendFieldsBean extendFieldsBean = new ExtendFieldsBean();
				dataModel.addRow(extendFieldsBean);
			}

		}
		class DeleteExtednsFieldsAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public DeleteExtednsFieldsAction(){
				super(LanguageLoader.getString("RuleContentSetting.delMidLinkFields"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
			}
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(crawlerExtendFieldsTabel.getSelectedRow() != -1){
					for(Integer selectRow : crawlerExtendFieldsTabel.getSelectedRows()){
						dataModel.removeRow(selectRow);
					}
				}
			}

		}
		addExtednsFieldsButton = new JButton(new AddExtednsFieldsAction());
		topBar.add(addExtednsFieldsButton);
		deleteExtednsFieldsButton = new JButton(new DeleteExtednsFieldsAction());
		topBar.add(deleteExtednsFieldsButton);
		return topBar;
	}
	protected void initActionListener(){
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(null == ruleSettingDialog.getRuleBaseSettingPanel().getData() ||
				    StringUtils.isBlank(ruleSettingDialog.getRuleBaseSettingPanel().getData().getPageEncoding())){
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.testRuleBaseIsBlank"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
					return;
				}
				CrawlerRuleBean crawlerRuleBean = new CrawlerRuleBean();
				crawlerRuleBean.setRuleBaseBean(ruleSettingDialog.getRuleBaseSettingPanel().getData());
				crawlerRuleBean.setRuleContentBean(populateData());
				if(null == crawlerRuleBean.getRuleContentBean().getAllPlans() || crawlerRuleBean.getRuleContentBean().getAllPlans().length == 0){
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.testRuleUrlIsBlank"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
					return;
				}
				testRuleInfoDialog.init(ruleSettingDialog, Constant.OPTION_TYPE_ADD, LanguageLoader.getString("RuleContentSetting.testRule"));
				testRuleInfoDialog.setCrawlerRuleBean(crawlerRuleBean);
				testRuleInfoDialog.startTest();
				testRuleInfoDialog.setVisible(true);
			}
		});
	}
	
	protected void fillComponentData(RuleContentBean ruleContentBean){
		linkListArea.setText(ruleContentBean.getPlanList());
		dynamicLinkField.setText(ruleContentBean.getDynamicAddr());
		dynamicPageStartField.setText(String.valueOf(ruleContentBean.getDynamicStart()));
		dynamicPageEndField.setText(String.valueOf(ruleContentBean.getDynamicEnd()));
		linkIncludeArea.setText(ruleContentBean.getLinksetStart());
		linkFilterArea.setText(ruleContentBean.getLinksetEnd());
		descIncludeArea.setText(ruleContentBean.getDescriptionStart());
		descFilterArea.setText(ruleContentBean.getDescriptionEnd());
		contentIncludeArea.setText(ruleContentBean.getContentStart());
		contentFilterArea.setText(ruleContentBean.getContentEnd());
		if(CollectionUtils.isNotEmpty(ruleContentBean.getMidExtendFields())){
			dataModel.setData(ruleContentBean.getMidExtendFields());
		}else{
			dataModel.setData(new ArrayList<ExtendFieldsBean>());
		}
	}
	

	@Override
	protected RuleContentBean populateData() {
		RuleContentBean ruleContentBean = new RuleContentBean();
		ruleContentBean.setContentEnd(contentFilterArea.getText());
		ruleContentBean.setContentStart(contentIncludeArea.getText());
		ruleContentBean.setDescriptionEnd(descFilterArea.getText());
		ruleContentBean.setDescriptionStart(descIncludeArea.getText());
		ruleContentBean.setDynamicAddr(dynamicLinkField.getText());
		ruleContentBean.setDynamicEnd(Integer.valueOf(dynamicPageEndField.getText()));
		ruleContentBean.setDynamicStart(Integer.valueOf(dynamicPageStartField.getText()));
		ruleContentBean.setLinksetEnd(linkFilterArea.getText());
		ruleContentBean.setLinksetStart(linkIncludeArea.getText());
		ruleContentBean.setPlanList(linkListArea.getText());
		if(CollectionUtils.isNotEmpty(dataModel.getData())){
			ruleContentBean.setMidExtendFields(dataModel.getData());
		}
		return ruleContentBean;
	}
	public void initData(RuleContentBean t){
		if(t == null){
			t = new RuleContentBean();
		}
		fillComponentData(t);
	}

}
