package com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.RuleBaseBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.RuleFieldsBean;
import com.bjm.pms.crawler.plugin.gather.service.local.LocalDocParser;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.listener.TextVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
/**
 * 本地采集基本参数设置panel
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-23 上午9:05:58
 * @version 1.0
 */
@Component("localRuleBaseSettingPanel")
public class LocalRuleBaseSettingPanel extends AbstractContentPanel<RuleBaseBean>{
	private static final long serialVersionUID = 1L;
	/**自动组装文档解析器LIST*/
	@Autowired
	private List<LocalDocParser> localDocParserList;
	/**采集名称输入框*/
	private javax.swing.JTextField siteField;
	/**采集名称标签*/
	private javax.swing.JLabel siteLabel;
	
	/**是否去重单选按钮*/
	private javax.swing.JRadioButton removeRepeatYesButton;
	/**是否去重单选按钮*/
	private javax.swing.JRadioButton removeRepeatNoButton;
	/**是否去重单选按钮组*/
	private javax.swing.ButtonGroup removeRepeatButtonGroup;
	/**是否去重标签*/
	private javax.swing.JLabel removeRepeatLabel;
	
	
	/**是否采集资源单选按钮*/
	private javax.swing.JRadioButton extractResYesButton;
	/**是否采集资源单选按钮*/
	private javax.swing.JRadioButton extractResNoButton;
	/**是否采集资源单选按钮组*/
	private javax.swing.ButtonGroup extractResButtonGroup;
	/**是否采集资源标签*/
	private javax.swing.JLabel extractResLabel;
	
	
	/**日期格式标签*/
	private javax.swing.JLabel dateFormatLabel;
	
	
	/**监听目录选择*/
	private javax.swing.JTextField repairPageField;
	/**监听目录选择标签*/
	private javax.swing.JLabel repairPageLabel;
	/**监听目录选择按钮*/
	private JButton savePathBtn;
	
	/**替换字符输入框*/
	private javax.swing.JTextArea replaceWordArea;
	/**替换字符标签*/
	private javax.swing.JLabel replaceWordLabel;

	private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
	
	
	private String removeRepeatValue;
	private String extractResValue;
	private String randomDateSelectValue;
	private List<String> docTypes = new ArrayList<String>();

	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		siteLabel = new javax.swing.JLabel();
		siteField = new javax.swing.JTextField();
		
		
		removeRepeatLabel = new javax.swing.JLabel();
		removeRepeatYesButton = new javax.swing.JRadioButton();
		removeRepeatNoButton = new javax.swing.JRadioButton();
		removeRepeatButtonGroup = new javax.swing.ButtonGroup();
		
		
		
		extractResLabel = new javax.swing.JLabel();
		extractResYesButton = new javax.swing.JRadioButton();
		extractResNoButton = new javax.swing.JRadioButton();
		extractResButtonGroup = new javax.swing.ButtonGroup();
		
		
		
		
		dateFormatLabel = new javax.swing.JLabel();
		
		
		
		
		
		
		
		
		repairPageLabel = new javax.swing.JLabel();
		repairPageField = new javax.swing.JTextField();
		
	
		
		replaceWordLabel = new javax.swing.JLabel();
		replaceWordArea = new javax.swing.JTextArea(4,40);
	
		
		
		
		siteLabel.setText(LanguageLoader.getString("RuleContentSetting.name"));
		add(siteLabel);
		siteLabel.setBounds(20, 15, 250, 15);

		siteField.setColumns(20);
		siteField.setInputVerifier(new TextVerifier(this,false));
		siteField.setText(LanguageLoader.getString("RuleContentSetting.nameDefaultValue"));
		add(siteField);
		siteField.setBounds(130, 15, 490, 21);
		
		

		
		removeRepeatLabel.setText(LanguageLoader.getString("RuleContentSetting.removeRepeat"));
		add(removeRepeatLabel);
		removeRepeatLabel.setBounds(20, 45, 250, 15);
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
		removeRepeatYesButton.setBounds(130, 45, 70, 21);
		removeRepeatNoButton.setBounds(210, 45, 70, 21);
		
		
		
		
		extractResLabel.setText(LanguageLoader.getString("Local.scanSubDir"));
		add(extractResLabel);
		extractResLabel.setBounds(340, 45, 250, 15);
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
		extractResYesButton.setBounds(420, 45, 70, 21);
		extractResNoButton.setBounds(500, 45, 70, 21);
		

		
	
		
		
		dateFormatLabel.setText(LanguageLoader.getString("Local.docType"));
		add(dateFormatLabel);
		dateFormatLabel.setBounds(20, 75, 250, 15);
		//dateFormatLabel.setBounds(340, 75, 250, 15);

		
		
		
		
		repairPageLabel.setText(LanguageLoader.getString("Local.scanDir"));
		add(repairPageLabel);
		repairPageLabel.setBounds(20, 105, 250, 15);

		repairPageField.setColumns(20);
		add(repairPageField);
		repairPageField.setBounds(130, 105, 395, 21);
		
		savePathBtn = new JButton(LanguageLoader.getString("Local.selectScanDir"),ImageLoader.getImageIcon("CrawlerResource.toolImageBrowse"));
		add(savePathBtn);
		savePathBtn.setBounds(530, 105, 85, 21);
		
		
		replaceWordLabel.setText(LanguageLoader.getString("RuleContentSetting.replaceWord"));
		add(replaceWordLabel);
		replaceWordLabel.setBounds(20, 135, 250, 15);
		
		JPanel replaceWordAreaPanel = new JPanel(new BorderLayout()); 
		replaceWordArea.setLineWrap(true);
		replaceWordArea.setWrapStyleWord(true);//激活断行不断字功能 
		replaceWordAreaPanel.add(new JScrollPane(replaceWordArea));
		replaceWordAreaPanel.setBounds(130, 135, 490, 100);
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
		
		if(Constant.YES.equals(ruleBaseBean.getRepeatCheckFlag())){
			removeRepeatYesButton.setSelected(true);
			removeRepeatValue = Constant.YES;
		}else{
			removeRepeatNoButton.setSelected(true);
			removeRepeatValue = Constant.NO;
		}
		
		if(Constant.YES.equals(ruleBaseBean.getSaveResourceFlag())){
			extractResYesButton.setSelected(true);
			extractResValue = Constant.YES;
		}else{
			extractResNoButton.setSelected(true);
			extractResValue = Constant.NO;
		}
		
		if(CollectionUtils.isNotEmpty(localDocParserList) && checkBoxList.isEmpty()){
			docTypes.clear();
			List<String> parserTypes = new ArrayList<String>();
			for(LocalDocParser parser : localDocParserList){
				parserTypes.addAll(parser.getDocTypeList());
			}
			//JCheckBox tempCkeck = null;
			int len = 0;
			for(String item : parserTypes){
				final JCheckBox tempCkeck = new JCheckBox(item);
				if(null != ruleBaseBean.getDateFormat() && ruleBaseBean.getDateFormat().contains(item)){
					tempCkeck.setSelected(true);
					docTypes.add(tempCkeck.getText());
				}else{
					tempCkeck.setSelected(false);
				}
				tempCkeck.setBounds(130+len, 75, 55, 21);
				tempCkeck.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(tempCkeck.isSelected()){
							docTypes.add(tempCkeck.getText());
						}else{
							docTypes.remove(tempCkeck.getText());
						}
					}
				});
				checkBoxList.add(tempCkeck);
				add(tempCkeck);
				len += 55;
			}
		}
		
		repairPageField.setText(ruleBaseBean.getUrlRepairUrl());
		replaceWordArea.setText(ruleBaseBean.getReplaceWords());
		
	}
	
	/**
	 * 初始化监听事件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-16 上午9:36:42
	 * @return void
	 */
	protected void initActionListener(){
		
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
		
		
		savePathBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setDialogTitle("Open class File");
				int result = jfc.showOpenDialog(null);
				if (result == 1) {
					return; // 撤销则返回
				} else {
					File f = jfc.getSelectedFile();// f为选择到的目录
					repairPageField.setText(f.getAbsolutePath());
				}
			}
		});
		
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
		
	}

	
	@Override
	protected RuleBaseBean populateData() {
		RuleBaseBean ruleBaseBean = new RuleBaseBean();
		ruleBaseBean.setRuleName(siteField.getText());
		ruleBaseBean.setSaveResourceFlag(this.extractResValue);
		ruleBaseBean.setRepeatCheckFlag(this.removeRepeatValue);
		ruleBaseBean.setUrlRepairUrl(repairPageField.getText());
		ruleBaseBean.setReplaceWords(replaceWordArea.getText());
		ruleBaseBean.setRandomDateFlag(this.randomDateSelectValue);
		if(!docTypes.isEmpty()){
			ruleBaseBean.setDateFormat(docTypes.toString().replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
		}
		return ruleBaseBean;
	}
	
	/**
	 * 取得扩展字段对象
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-24 下午2:48:00
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	public RuleFieldsBean getRuleFieldsBean(){
		RuleFieldsBean ruleFieldsBean = new RuleFieldsBean();
		if(!docTypes.isEmpty()){
			Map<String,String> fieldsMap = new HashMap<String,String>();
			//依次查找选择的解析器，所带的扩展字段
			for(LocalDocParser parser : localDocParserList){
				if(CollectionUtils.isNotEmpty(parser.getDocTypeList())){
					for(String key : parser.getDocTypeList()){
						if(docTypes.contains(key)){
							fieldsMap.putAll(parser.getValueMap());
							break;
						}
					}
				}
			}
			//找到了
			if(!fieldsMap.isEmpty()){
				List<ExtendFieldsBean> extendFields = new ArrayList<ExtendFieldsBean>();
				ExtendFieldsBean extendFieldsBean = null;
				for(String key : fieldsMap.keySet()){
					extendFieldsBean = new ExtendFieldsBean();
					extendFieldsBean.setFields(key);
					extendFields.add(extendFieldsBean);
				}
				ruleFieldsBean.setExtendFields(extendFields);
			}
		}
		return ruleFieldsBean;
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

