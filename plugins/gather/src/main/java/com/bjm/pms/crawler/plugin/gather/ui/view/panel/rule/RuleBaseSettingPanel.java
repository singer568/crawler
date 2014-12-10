package com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.service.beans.RuleBaseBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.listener.IntegerVerifier;
import com.bjm.pms.crawler.view.ui.listener.TextVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
/**
 * 基本参数设置panel
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("ruleBaseSettingPanel")
public class RuleBaseSettingPanel extends AbstractContentPanel<RuleBaseBean>{
	private static final long serialVersionUID = 1L;
	/**采集名称输入框*/
	private javax.swing.JTextField siteField;
	/**采集名称标签*/
	private javax.swing.JLabel siteLabel;
	/**页面编码输入框*/
	private javax.swing.JTextField pageEncodingField;
	/**页面编码标签*/
	private javax.swing.JLabel pageEncodingLabel;
	/**暂停时间输入框*/
	private javax.swing.JTextField sleepTimeField;
	/**暂停时间标签*/
	private javax.swing.JLabel sleepTimeLabel;
	/**是否采集资源单选按钮*/
	private javax.swing.JRadioButton extractResYesButton;
	/**是否采集资源单选按钮*/
	private javax.swing.JRadioButton extractResNoButton;
	/**是否采集资源单选按钮组*/
	private javax.swing.ButtonGroup extractResButtonGroup;
	/**是否采集资源标签*/
	private javax.swing.JLabel extractResLabel;
	/**是否除去连接单选按钮*/
	private javax.swing.JRadioButton removeLinkYesButton;
	/**是否除去连接单选按钮*/
	private javax.swing.JRadioButton removeLinkNoButton;
	/**是否除去连接单选按钮组*/
	private javax.swing.ButtonGroup removeLinkButtonGroup;
	/**是否除去连接标签*/
	private javax.swing.JLabel removeLinkLabel;
	/**是否去重单选按钮*/
	private javax.swing.JRadioButton removeRepeatYesButton;
	/**是否去重单选按钮*/
	private javax.swing.JRadioButton removeRepeatNoButton;
	/**是否去重单选按钮组*/
	private javax.swing.ButtonGroup removeRepeatButtonGroup;
	/**是否去重标签*/
	private javax.swing.JLabel removeRepeatLabel;
	/**是否使用代理单选按钮*/
	private javax.swing.JRadioButton proxyYesButton;
	/**是否使用代理单选按钮*/
	private javax.swing.JRadioButton proxyNoButton;
	/**是否使用代理单选按钮组*/
	private javax.swing.ButtonGroup proxyButtonGroup;
	/**是否使用代理标签*/
	private javax.swing.JLabel proxyLabel;
	
	
	/**采集顺序标签*/
	private javax.swing.JLabel gatherOrderLabel;
	/**采集顺序单选按钮*/
	private javax.swing.JRadioButton gatherOrderYesButton;
	/**采集顺序单选按钮*/
	private javax.swing.JRadioButton gatherOrderNoButton;
	/**采集顺序单选按钮组*/
	private javax.swing.ButtonGroup gatherOrderButtonGroup;
	
	
	
	/**代理地址输入框*/
	private javax.swing.JTextField proxyAddressField;
	/**代理地址标签*/
	private javax.swing.JLabel proxyAddressLabel;
	/**端口入框*/
	private javax.swing.JTextField proxyPortField;
	/**端口标签*/
	private javax.swing.JLabel proxyPortLabel;
	
	/**是否随机日期标签*/
	private javax.swing.JLabel randomDateLabel;
	/**是否随机日期单选按钮*/
	private javax.swing.JRadioButton randomDateYesButton;
	/**是否随机日期单选按钮*/
	private javax.swing.JRadioButton randomDateNoButton;
	/**是否随机日期单选按钮组*/
	private javax.swing.ButtonGroup randomDateButtonGroup;
	/**日期格式标签*/
	private javax.swing.JLabel dateFormatLabel;
	/**日期格式ComboBox*/
	private JComboBox dateFormatComboBox;
	/**开始日期Label*/
	private javax.swing.JLabel startRandomDateLabel;
	/**开始日期Field*/
	private javax.swing.JTextField startRandomDateField;
	/**结束日期Label*/
	private javax.swing.JLabel endRandomDateLabel;
	/**结束日期Field*/
	private javax.swing.JTextField endRandomDateField;
	
	
	/**页面补全URL入框*/
	private javax.swing.JTextField repairPageField;
	/**页面补全URL标签*/
	private javax.swing.JLabel repairPageLabel;
	/**资源补全URL入框*/
	private javax.swing.JTextField repairResField;
	/**资源补全URL标签*/
	private javax.swing.JLabel repairResLabel;
	/**内容分页补全URL入框*/
	private javax.swing.JTextField repairPaginationField;
	/**内容分页补全URL标签*/
	private javax.swing.JLabel repairPaginationLabel;
	/**替换字符输入框*/
	private javax.swing.JTextArea replaceWordArea;
	/**替换字符标签*/
	private javax.swing.JLabel replaceWordLabel;
	/**采集数量*/
	private javax.swing.JLabel gatherNumLabel;
	/**采集数量入框*/
	private javax.swing.JTextField gatherNumField;
	
	
	
	private String removeLinkValue;
	private String removeRepeatValue;
	private String extractResValue;
	private String proxySelectValue;
	private String randomDateSelectValue;
	private String gatherOrderSelectValue;
	

	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		siteLabel = new javax.swing.JLabel();
		siteField = new javax.swing.JTextField();
		
		pageEncodingLabel = new javax.swing.JLabel();
		pageEncodingField = new javax.swing.JTextField();

		sleepTimeLabel = new javax.swing.JLabel();
		sleepTimeField = new javax.swing.JTextField();
		
		extractResLabel = new javax.swing.JLabel();
		extractResYesButton = new javax.swing.JRadioButton();
		extractResNoButton = new javax.swing.JRadioButton();
		extractResButtonGroup = new javax.swing.ButtonGroup();
		
		removeLinkLabel = new javax.swing.JLabel();
		removeLinkYesButton = new javax.swing.JRadioButton();
		removeLinkNoButton = new javax.swing.JRadioButton();
		removeLinkButtonGroup = new javax.swing.ButtonGroup();
		
		removeRepeatLabel = new javax.swing.JLabel();
		removeRepeatYesButton = new javax.swing.JRadioButton();
		removeRepeatNoButton = new javax.swing.JRadioButton();
		removeRepeatButtonGroup = new javax.swing.ButtonGroup();
		
		
		gatherOrderLabel = new javax.swing.JLabel();
		gatherOrderYesButton = new javax.swing.JRadioButton();
		gatherOrderNoButton = new javax.swing.JRadioButton();
		gatherOrderButtonGroup = new javax.swing.ButtonGroup();
		
		proxyLabel = new javax.swing.JLabel();
		proxyYesButton = new javax.swing.JRadioButton();
		proxyNoButton = new javax.swing.JRadioButton();
		proxyButtonGroup = new javax.swing.ButtonGroup();
		
		proxyAddressLabel = new javax.swing.JLabel();
		proxyAddressField = new javax.swing.JTextField();

		proxyPortLabel = new javax.swing.JLabel();
		proxyPortField = new javax.swing.JTextField();
		
		randomDateLabel = new javax.swing.JLabel();
		randomDateYesButton = new javax.swing.JRadioButton();
		randomDateNoButton = new javax.swing.JRadioButton();
		randomDateButtonGroup = new javax.swing.ButtonGroup();
		dateFormatLabel = new javax.swing.JLabel();
		String[] data = new String[]{"yyyy","yyyy-MM","yyyy-MM-dd","yyyy-MM-dd HH:mm:ss.SSS"};
		dateFormatComboBox = new JComboBox(data);
		startRandomDateLabel = new javax.swing.JLabel();
		startRandomDateField = new javax.swing.JTextField();
		endRandomDateLabel = new javax.swing.JLabel();
		endRandomDateField= new javax.swing.JTextField();
		
		
		
		
		
		
		
		repairPageLabel = new javax.swing.JLabel();
		repairPageField = new javax.swing.JTextField();
		
		repairResLabel = new javax.swing.JLabel();
		repairResField = new javax.swing.JTextField();
		
		repairPaginationLabel = new javax.swing.JLabel();
		repairPaginationField = new javax.swing.JTextField();
		
		replaceWordLabel = new javax.swing.JLabel();
		replaceWordArea = new javax.swing.JTextArea(4,40);
		
		gatherNumLabel = new javax.swing.JLabel();
		gatherNumField = new javax.swing.JTextField();
		
		
		
		
		
		siteLabel.setText(LanguageLoader.getString("RuleContentSetting.name"));
		add(siteLabel);
		siteLabel.setBounds(20, 15, 250, 15);

		siteField.setColumns(20);
		siteField.setInputVerifier(new TextVerifier(this,false));
		siteField.setText(LanguageLoader.getString("RuleContentSetting.nameDefaultValue"));
		add(siteField);
		siteField.setBounds(130, 15, 490, 21);
		
		
		pageEncodingLabel.setText(LanguageLoader.getString("RuleContentSetting.pageEncoding"));
		pageEncodingLabel.setBounds(340, 45, 250, 15);
		add(pageEncodingLabel);

		pageEncodingField.setColumns(20);
		add(pageEncodingField);
		pageEncodingField.setBounds(420, 45, 200, 21);
		
		
		sleepTimeLabel.setText(LanguageLoader.getString("RuleContentSetting.sleepTime"));
		add(sleepTimeLabel);
		sleepTimeLabel.setBounds(20, 45, 250, 15);

		sleepTimeField.setColumns(20);
		sleepTimeField.setInputVerifier(new IntegerVerifier(this, false, 1, 10000));
		add(sleepTimeField);
		sleepTimeField.setBounds(130, 45, 200, 21);
		
		removeLinkLabel.setText(LanguageLoader.getString("RuleContentSetting.removeLink"));
		add(removeLinkLabel);
		removeLinkLabel.setBounds(20, 75, 250, 15);
		
		removeLinkYesButton.setText(LanguageLoader.getString("Common.yes"));
		removeLinkNoButton.setText(LanguageLoader.getString("Common.no"));
		removeLinkNoButton.setSelected(true);
		removeLinkValue = Constant.NO;
		removeLinkYesButton.setBackground(null);
		removeLinkNoButton.setBackground(null);
		removeLinkButtonGroup.add(removeLinkYesButton);
		removeLinkButtonGroup.add(removeLinkNoButton);
		add(removeLinkYesButton);
		add(removeLinkNoButton);
		removeLinkYesButton.setBounds(130, 75, 70, 21);
		removeLinkNoButton.setBounds(210, 75, 70, 21);
		
		extractResLabel.setText(LanguageLoader.getString("RuleContentSetting.extractRes"));
		add(extractResLabel);
		extractResLabel.setBounds(340, 75, 250, 15);
		extractResYesButton.setText(LanguageLoader.getString("Common.yes"));
		extractResNoButton.setText(LanguageLoader.getString("Common.no"));
		extractResNoButton.setSelected(true);
		extractResValue = Constant.NO;
		extractResYesButton.setBackground(null);
		extractResNoButton.setBackground(null);
		extractResButtonGroup.add(extractResYesButton);
		extractResButtonGroup.add(extractResNoButton);
		add(extractResYesButton);
		add(extractResNoButton);
		extractResYesButton.setBounds(420, 75, 70, 21);
		extractResNoButton.setBounds(500, 75, 70, 21);
		
		
		removeRepeatLabel.setText(LanguageLoader.getString("RuleContentSetting.removeRepeat"));
		add(removeRepeatLabel);
		removeRepeatLabel.setBounds(20, 105, 250, 15);
		removeRepeatYesButton.setText(LanguageLoader.getString("Common.yes"));
		removeRepeatNoButton.setText(LanguageLoader.getString("Common.no"));
		removeRepeatNoButton.setSelected(true);
		removeRepeatValue = Constant.NO;
		removeRepeatYesButton.setBackground(null);
		removeRepeatNoButton.setBackground(null);
		removeRepeatButtonGroup.add(removeRepeatYesButton);
		removeRepeatButtonGroup.add(removeRepeatNoButton);
		add(removeRepeatYesButton);
		add(removeRepeatNoButton);
		removeRepeatYesButton.setBounds(130, 105, 70, 21);
		removeRepeatNoButton.setBounds(210, 105, 70, 21);
		
		proxyLabel.setText(LanguageLoader.getString("RuleContentSetting.proxy"));
		add(proxyLabel);
		proxyLabel.setBounds(340, 105, 250, 15);
		proxyYesButton.setText(LanguageLoader.getString("Common.yes"));
		proxyNoButton.setText(LanguageLoader.getString("Common.no"));
		proxyNoButton.setSelected(true);
		proxySelectValue = Constant.NO;
		proxyYesButton.setBackground(null);
		proxyNoButton.setBackground(null);
		proxyButtonGroup.add(proxyYesButton);
		proxyButtonGroup.add(proxyNoButton);
		add(proxyYesButton);
		add(proxyNoButton);
		proxyYesButton.setBounds(420, 105, 70, 21);
		proxyNoButton.setBounds(500, 105, 70, 21);
		
		
		
		randomDateLabel.setText(LanguageLoader.getString("RuleContentSetting.randomDate"));
		add(randomDateLabel);
		randomDateLabel.setBounds(20, 135, 250, 15);
		randomDateYesButton.setText(LanguageLoader.getString("Common.yes"));
		randomDateNoButton.setText(LanguageLoader.getString("Common.no"));
		randomDateNoButton.setSelected(true);
		randomDateSelectValue = Constant.NO;
		randomDateYesButton.setBackground(null);
		randomDateNoButton.setBackground(null);
		randomDateButtonGroup.add(randomDateYesButton);
		randomDateButtonGroup.add(randomDateNoButton);
		add(randomDateYesButton);
		add(randomDateNoButton);
		randomDateYesButton.setBounds(130, 135, 70, 21);
		randomDateNoButton.setBounds(210, 135, 70, 21);
		
		
		
		
		gatherOrderLabel.setText(LanguageLoader.getString("RuleContentSetting.gatherOrder"));
		add(gatherOrderLabel);
		gatherOrderLabel.setBounds(340, 135, 250, 15);
		gatherOrderYesButton.setText(LanguageLoader.getString("RuleContentSetting.gatherOrderYes"));
		gatherOrderNoButton.setText(LanguageLoader.getString("RuleContentSetting.gatherOrderNo"));
		gatherOrderNoButton.setSelected(true);
		gatherOrderSelectValue = Constant.NO;
		gatherOrderYesButton.setBackground(null);
		gatherOrderNoButton.setBackground(null);
		gatherOrderButtonGroup.add(gatherOrderYesButton);
		gatherOrderButtonGroup.add(gatherOrderNoButton);
		add(gatherOrderYesButton);
		add(gatherOrderNoButton);
		gatherOrderYesButton.setBounds(420, 135, 80, 21);
		gatherOrderNoButton.setBounds(500, 135, 80, 21);
		
		
		
		
		
		
		proxyAddressLabel.setText(LanguageLoader.getString("RuleContentSetting.proxyAddress"));
		add(proxyAddressLabel);
		proxyAddressLabel.setBounds(20, 165, 250, 15);

		proxyAddressField.setColumns(20);
		add(proxyAddressField);
		proxyAddressField.setBounds(130, 165, 200, 21);
		
		
		proxyPortLabel.setText(LanguageLoader.getString("RuleContentSetting.proxyPort"));
		add(proxyPortLabel);
		proxyPortLabel.setBounds(340, 165, 250, 15);

		proxyPortField.setColumns(20);
		proxyPortField.setInputVerifier(new IntegerVerifier(this, true, 1, 10000));
		add(proxyPortField);
		proxyPortField.setBounds(420, 165, 200, 21);
		
		
		
		
		
		
		
		
		
		gatherNumLabel.setText(LanguageLoader.getString("RuleContentSetting.gatherNum"));
		add(gatherNumLabel);
		gatherNumLabel.setBounds(20, 195, 250, 15);

		gatherNumField.setColumns(20);
		gatherNumField.setInputVerifier(new IntegerVerifier(this, true, 1, 10000));
		add(gatherNumField);
		gatherNumField.setBounds(130, 195, 200, 21);
		
		
		dateFormatLabel.setText(LanguageLoader.getString("RuleContentSetting.dateFormat"));
		add(dateFormatLabel);
		dateFormatLabel.setBounds(340, 195, 250, 15);

		dateFormatComboBox.setBounds(420, 195, 200, 21);
		add(dateFormatComboBox);
		
		startRandomDateLabel.setText(LanguageLoader.getString("RuleContentSetting.startRandomDate"));
		add(startRandomDateLabel);
		startRandomDateLabel.setBounds(20, 225, 250, 15);

		startRandomDateField.setColumns(20);
		add(startRandomDateField);
		startRandomDateField.setBounds(130, 225, 200, 21);
		
		
		endRandomDateLabel.setText(LanguageLoader.getString("RuleContentSetting.endRandomDate"));
		add(endRandomDateLabel);
		endRandomDateLabel.setBounds(340, 225, 250, 15);

		endRandomDateField.setColumns(20);
		add(endRandomDateField);
		endRandomDateField.setBounds(420, 225, 200, 21);
		
		
		
		
		repairPageLabel.setText(LanguageLoader.getString("RuleContentSetting.repairPage"));
		add(repairPageLabel);
		repairPageLabel.setBounds(20, 255, 250, 15);

		repairPageField.setColumns(20);
		add(repairPageField);
		repairPageField.setBounds(130, 255, 490, 21);
		
		repairResLabel.setText(LanguageLoader.getString("RuleContentSetting.repairRes"));
		add(repairResLabel);
		repairResLabel.setBounds(20, 285, 250, 15);

		repairResField.setColumns(20);
		add(repairResField);
		repairResField.setBounds(130, 285, 490, 21);
		
		repairPaginationLabel.setText(LanguageLoader.getString("RuleContentSetting.repairPagination"));
		add(repairPaginationLabel);
		repairPaginationLabel.setBounds(20, 315, 250, 15);

		repairPaginationField.setColumns(20);
		add(repairPaginationField);
		repairPaginationField.setBounds(130, 315, 490, 21);
		
		
		replaceWordLabel.setText(LanguageLoader.getString("RuleContentSetting.replaceWord"));
		add(replaceWordLabel);
		replaceWordLabel.setBounds(20, 345, 250, 15);
		
		JPanel replaceWordAreaPanel = new JPanel(new BorderLayout()); 
		replaceWordArea.setLineWrap(true);
		replaceWordArea.setWrapStyleWord(true);//激活断行不断字功能 
		replaceWordAreaPanel.add(new JScrollPane(replaceWordArea));
		replaceWordAreaPanel.setBounds(130, 345, 490, 100);
		add(replaceWordAreaPanel);
		
		
		
	}
	/**
	 * 填充页面控件数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 上午10:54:08
	 * @param ruleBaseBean
	 * @return void
	 */
	protected void fillComponentData(RuleBaseBean ruleBaseBean){
		logger.info("填充页面控件数据");
		if(StringUtils.isNotBlank(ruleBaseBean.getRuleName())){
			siteField.setText(ruleBaseBean.getRuleName());
		}
		pageEncodingField.setText(ruleBaseBean.getPageEncoding());
		
		sleepTimeField.setText(String.valueOf(ruleBaseBean.getPauseTime()));
		
		if(Constant.YES.equals(ruleBaseBean.getReplaceLinkFlag())){
			removeLinkYesButton.setSelected(true);
			removeLinkValue = Constant.YES;
		}else{
			removeLinkNoButton.setSelected(true);
			removeLinkValue = Constant.NO;
		}
	
		if(Constant.YES.equals(ruleBaseBean.getSaveResourceFlag())){
			extractResYesButton.setSelected(true);
			extractResValue = Constant.YES;
		}else{
			extractResNoButton.setSelected(true);
			extractResValue = Constant.NO;
		}
	
		if(Constant.YES.equals(ruleBaseBean.getRepeatCheckFlag())){
			removeRepeatYesButton.setSelected(true);
			removeRepeatValue = Constant.YES;
		}else{
			removeRepeatNoButton.setSelected(true);
			removeRepeatValue = Constant.NO;
		}
		
		if(Constant.YES.equals(ruleBaseBean.getUseProxyFlag())){
			proxyYesButton.setSelected(true);
			proxySelectValue = Constant.YES;
			changeProxyState(true);
		}else{
			proxyNoButton.setSelected(true);
			proxySelectValue = Constant.NO;
			changeProxyState(false);
		}
		
		if(Constant.YES.equals(ruleBaseBean.getRandomDateFlag())){
			randomDateYesButton.setSelected(true);
			randomDateSelectValue = Constant.YES;
			changeRandomDateState(true);
		}else{
			randomDateNoButton.setSelected(true);
			randomDateSelectValue = Constant.NO;
			changeRandomDateState(false);
		}
		
		if(Constant.YES.equals(ruleBaseBean.getGatherOrderFlag())){
			gatherOrderYesButton.setSelected(true);
			gatherOrderSelectValue = Constant.YES;
		}else{
			gatherOrderNoButton.setSelected(true);
			gatherOrderSelectValue = Constant.NO;
		}
		
		if(StringUtils.isNotBlank(ruleBaseBean.getDateFormat())){
			dateFormatComboBox.setSelectedItem(ruleBaseBean.getDateFormat());
		}
		startRandomDateField.setText(ruleBaseBean.getStartRandomDate());
		
		endRandomDateField.setText(ruleBaseBean.getEndRandomDate());
		
		proxyAddressField.setText(ruleBaseBean.getProxyAddress());
		
		proxyPortField.setText(ruleBaseBean.getProxyPort());
		
		repairPageField.setText(ruleBaseBean.getUrlRepairUrl());
		
		repairResField.setText(ruleBaseBean.getResourceRepairUrl());
		
		repairPaginationField.setText(ruleBaseBean.getPaginationRepairUrl());
		
		replaceWordArea.setText(ruleBaseBean.getReplaceWords());
		gatherNumField.setText(ruleBaseBean.getGatherNum());
		
	}
	
	/**
	 * 初始化监听事件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-16 上午9:36:42
	 * @return void
	 */
	protected void initActionListener(){
		class RemoveLinkBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (removeLinkYesButton.isSelected()) {
					 	removeLinkValue = Constant.YES;
	                } else if (removeLinkNoButton.isSelected()) {
	                	removeLinkValue = Constant.NO;
	                }
			}
		}
		removeLinkYesButton.addActionListener(new RemoveLinkBtnActionAdapter());
		removeLinkNoButton.addActionListener(new RemoveLinkBtnActionAdapter());
		
		class ExtractResBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (extractResYesButton.isSelected()) {
					 	extractResValue = Constant.YES;
	                } else if (extractResNoButton.isSelected()) {
	                	extractResValue = Constant.NO;
	                }
			}
		}
		extractResYesButton.addActionListener(new ExtractResBtnActionAdapter());
		extractResNoButton.addActionListener(new ExtractResBtnActionAdapter());
		
		class RemoveRepeatBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (removeRepeatYesButton.isSelected()) {
					 	removeRepeatValue = Constant.YES;
	                } else if (removeRepeatNoButton.isSelected()) {
	                	removeRepeatValue = Constant.NO;
	                }
			}
		}
		removeRepeatYesButton.addActionListener(new RemoveRepeatBtnActionAdapter());
		removeRepeatNoButton.addActionListener(new RemoveRepeatBtnActionAdapter());
		
		
		class ProxyBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (proxyYesButton.isSelected()) {
					 	proxySelectValue = Constant.YES;
					 	changeProxyState(true);
	                } else if (proxyNoButton.isSelected()) {
	                	proxySelectValue = Constant.NO;
	                	changeProxyState(false);
	                }
			}
		}
		proxyYesButton.addActionListener(new ProxyBtnActionAdapter());
		proxyNoButton.addActionListener(new ProxyBtnActionAdapter());
		
		class RandomBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (randomDateYesButton.isSelected()) {
					 randomDateSelectValue = Constant.YES;
					 changeRandomDateState(true);
	                } else if (randomDateNoButton.isSelected()) {
	                	randomDateSelectValue = Constant.NO;
	                	changeRandomDateState(false);
	                }
			}
		}
		randomDateYesButton.addActionListener(new RandomBtnActionAdapter());
		randomDateNoButton.addActionListener(new RandomBtnActionAdapter());
		
		
		class GatherOrderBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (gatherOrderYesButton.isSelected()) {
					 gatherOrderSelectValue = Constant.YES;
	                } else if (gatherOrderNoButton.isSelected()) {
	                	gatherOrderSelectValue = Constant.NO;
	                }
			}
		}
		gatherOrderYesButton.addActionListener(new GatherOrderBtnActionAdapter());
		gatherOrderNoButton.addActionListener(new GatherOrderBtnActionAdapter());
		
	}
	private void changeProxyState(boolean b){
		proxyAddressField.setEnabled(b);
		proxyAddressLabel.setEnabled(b);
		proxyPortField.setEnabled(b);
		proxyPortLabel.setEnabled(b);
	}
	
	private void changeRandomDateState(boolean b){
		dateFormatLabel.setEnabled(b);
		dateFormatComboBox.setEnabled(b);
		startRandomDateLabel.setEnabled(b);
		startRandomDateField.setEnabled(b);
		endRandomDateLabel.setEnabled(b);
		endRandomDateField.setEnabled(b);
	}
	
	
	@Override
	protected RuleBaseBean populateData() {
		RuleBaseBean ruleBaseBean = new RuleBaseBean();
		ruleBaseBean.setRuleName(siteField.getText());
		ruleBaseBean.setPauseTime(Integer.valueOf(sleepTimeField.getText()));
		ruleBaseBean.setPageEncoding(pageEncodingField.getText());
		ruleBaseBean.setReplaceLinkFlag(this.removeLinkValue);
		ruleBaseBean.setSaveResourceFlag(this.extractResValue);
		ruleBaseBean.setRepeatCheckFlag(this.removeRepeatValue);
		ruleBaseBean.setUseProxyFlag(this.proxySelectValue);
		ruleBaseBean.setGatherOrderFlag(this.gatherOrderSelectValue);
		ruleBaseBean.setProxyAddress(proxyAddressField.getText());
		ruleBaseBean.setProxyPort(proxyPortField.getText());
		ruleBaseBean.setUrlRepairUrl(repairPageField.getText());
		ruleBaseBean.setResourceRepairUrl(repairResField.getText());
		ruleBaseBean.setPaginationRepairUrl(repairPaginationField.getText());
		ruleBaseBean.setReplaceWords(replaceWordArea.getText());
		ruleBaseBean.setRandomDateFlag(this.randomDateSelectValue);
		if(null != dateFormatComboBox.getSelectedItem()){
			ruleBaseBean.setDateFormat(dateFormatComboBox.getSelectedItem().toString());
		}
		ruleBaseBean.setStartRandomDate(startRandomDateField.getText());
		ruleBaseBean.setEndRandomDate(endRandomDateField.getText());
		ruleBaseBean.setGatherNum(gatherNumField.getText());
		return ruleBaseBean;
	}
	
	public void initData(RuleBaseBean t){
		if(t == null){
			t = new RuleBaseBean();
		}
		fillComponentData(t);
	}
	/**
	 * 完成销毁动作
	 */
	public void dispose(){
		super.dispose();
	}
}

