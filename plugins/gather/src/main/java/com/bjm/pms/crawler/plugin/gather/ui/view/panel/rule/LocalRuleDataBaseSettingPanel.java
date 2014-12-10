package com.bjm.pms.crawler.plugin.gather.ui.view.panel.rule;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.util.DBConnectionManager;
import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.gather.constant.SystemConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseBean;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerDataBaseCriteria;
import com.bjm.pms.crawler.plugin.gather.service.beans.SelectValueBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.DataBaseComboBoxModel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.ICrawlerService;
import com.bjm.pms.crawler.view.base.service.beans.ColumnBean;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.service.beans.ExtendFieldsBean;
import com.bjm.pms.crawler.view.base.service.beans.RuleDataBaseBean;
import com.bjm.pms.crawler.view.core.cache.ICowSwingCacheManager;
import com.bjm.pms.crawler.view.core.cache.support.CacheKeyConstant;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
/**
 * 本地远程数据库设置
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-24 下午2:13:54
 * @version 1.0
 */
@Component("localRuleDataBaseSettingPanel")
public class LocalRuleDataBaseSettingPanel extends AbstractContentPanel<RuleDataBaseBean>{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/**数据库服务类*/
	@Resource(name="crawlerDataBaseService")
	private ICrawlerService<CrawlerDataBaseBean,CrawlerDataBaseCriteria> crawlerDataBaseService;
	/**缓存管理*/
	@Resource(name="cowSwingCacheManager")
	private ICowSwingCacheManager crawlerCacheManager;
	private CrawlerRuleBean crawlerRuleBean;
	/** 数据库连接管理*/
	private DBConnectionManager connectionManager;
	
	
	/**是否保存至指定数据库单选按钮*/
	private javax.swing.JRadioButton saveToDataBaseYesButton;
	/**是否存至指定数据库单选按钮*/
	private javax.swing.JRadioButton saveToDataBaseNoButton;
	/**是否存至指定数据库单选按钮组*/
	private javax.swing.ButtonGroup saveToDataBaseButtonGroup;
	/**是否存至指定数据库标签*/
	private javax.swing.JLabel saveToDataBaseLabel;
	
	/**数据库标签*/
	private javax.swing.JLabel dataBaseLabel;
	/**数据库下拉*/
	private JComboBox dataBaseCombo;
	
	/**分页标签*/
	private javax.swing.JLabel pageTagLabel;
	/**分页标签名称输入框*/
	private javax.swing.JTextField pageTagField;
	
	/**数据库表列标签*/
	private javax.swing.JLabel dataBaseTableLabel;
	/**数据库表Panel*/
	private JPanel dataBaseTableJlistPanel;
	/**数据库表JList*/
	private JList dataBaseTableJlist;
	/***数据库表默认ListModel*/
	private DefaultListModel dataBaseTableSelectListModel;
	
	
	/**数据库表列标签*/
	private javax.swing.JLabel dataBaseTableColumnLabel;
	/**数据库表列Panel*/
	private JPanel dataBaseTableColumnJlistPanel;
	/**数据库表列JList*/
	private JList dataBaseTableColumnJlist;
	/***数据库表表默认ListModel*/
	private DefaultListModel dataBaseTableColumnSelectListModel;
	
	
	/**备选值标签*/
	private javax.swing.JLabel selectValueLabel;
	/**备选值Panel*/
	private JPanel selectValueJlistPanel;
	/**备选值JList*/
	private JList selectValueJlist;
	/***备选值默认ListModel*/
	private DefaultListModel selectValueSelectListModel;

	/**备选自定义值标签*/
	private javax.swing.JLabel defaultSelectValueLabel;
	/**备选自定义值Panel*/
	private JPanel defaultSelectValueJlistPanel;
	/**备选自定义值JList*/
	private JList defaultSelectValueJlist;
	/***备选值自定义ListModel*/
	private DefaultListModel defaultSelectValueSelectListModel;

	
	
	/**已经选择的表标签*/
	private javax.swing.JLabel hasSelectTableLabel;
	/**已经选择的表Panel*/
	private JPanel hasSelectTableJlistPanel;
	/**已经选择的表JList*/
	private JList hasSelectTableJlist;
	/**内容默认ListModel*/
	private DefaultListModel hasSelectTableListModel;
	
	
	/**已经选择的数据标签*/
	private javax.swing.JLabel hasSelectValueLabel;
	/**是否是主表*/
	private javax.swing.JRadioButton isPriTableRadio;
	/**已经选择的数据Panel*/
	private JPanel hasSelectValueJlistPanel;
	/**已经选择的数据JList*/
	private JList hasSelectValueJlist;
	/**内容默认ListModel*/
	private DefaultListModel hasSelectValueListModel;
	
	
	/**选择btn*/
	private JButton dataBaseTableContentAddBtn;  
	/**选择btn*/
	private JButton dataBaseTableContentAddExtendFieldsBtn;  
	/**选择自定义btn*/
	private JButton dataBaseTableContentAddDefaultFieldsBtn;  
	/**删除btn*/
	private JButton dataBaseTableContentDelBtn;  
	/**修改btn*/
	private JButton dataBaseTableContentModifyBtn;  
	
	
	/**默认ListModel*/
	private DefaultListModel defaultListModel = new DefaultListModel();
	private String saveToDataBaseSelectValue;
	private String dataBaseId;
	private Map<String,Map<String,ColumnBean>> hasSelectedValueMap;
	/**是否是主表*/
	private String isPriTableSelectValue = "";
	
	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		saveToDataBaseLabel = new javax.swing.JLabel();
		saveToDataBaseYesButton = new javax.swing.JRadioButton();
		saveToDataBaseNoButton = new javax.swing.JRadioButton();
		saveToDataBaseButtonGroup = new javax.swing.ButtonGroup();
		
		dataBaseLabel = new javax.swing.JLabel();
		
		
		dataBaseTableLabel = new javax.swing.JLabel();
		dataBaseTableJlistPanel = new JPanel(new BorderLayout());
		dataBaseTableSelectListModel = new DefaultListModel();
		dataBaseTableJlist = new JList(dataBaseTableSelectListModel);  
		dataBaseTableJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		pageTagLabel = new javax.swing.JLabel();
		pageTagField = new javax.swing.JTextField();
		
		dataBaseTableColumnLabel = new javax.swing.JLabel();
		dataBaseTableColumnJlistPanel = new JPanel(new BorderLayout());
		dataBaseTableColumnSelectListModel = new DefaultListModel();
		dataBaseTableColumnJlist = new JList(dataBaseTableColumnSelectListModel); 
		dataBaseTableColumnJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		selectValueLabel = new javax.swing.JLabel();
		selectValueJlistPanel = new JPanel(new BorderLayout());
		selectValueSelectListModel = new DefaultListModel();
		selectValueJlist = new JList(selectValueSelectListModel); 
		
		
		defaultSelectValueLabel = new javax.swing.JLabel();
		defaultSelectValueJlistPanel = new JPanel(new BorderLayout());
		defaultSelectValueSelectListModel = new DefaultListModel();
		defaultSelectValueJlist = new JList(defaultSelectValueSelectListModel); 
		
		
		
		dataBaseTableContentAddBtn = new JButton();
		dataBaseTableContentAddExtendFieldsBtn = new JButton();
		dataBaseTableContentAddDefaultFieldsBtn = new JButton();
		dataBaseTableContentDelBtn = new JButton();
		dataBaseTableContentModifyBtn = new JButton();
		
		
		hasSelectTableLabel = new javax.swing.JLabel();
		hasSelectTableJlistPanel = new JPanel(new BorderLayout());
		hasSelectTableListModel = new DefaultListModel();
		hasSelectTableJlist = new JList(hasSelectTableListModel); 
		
		
		
		hasSelectValueLabel = new javax.swing.JLabel();
		isPriTableRadio = new javax.swing.JRadioButton();
		isPriTableRadio.setEnabled(false);
		hasSelectValueJlistPanel = new JPanel(new BorderLayout());
		hasSelectValueListModel = new DefaultListModel();
		hasSelectValueJlist = new JList(hasSelectValueListModel); 
		
	
		
		
		
		saveToDataBaseLabel.setText(LanguageLoader.getString("RuleContentSetting.saveToDataBase"));
		add(saveToDataBaseLabel);
		saveToDataBaseLabel.setBounds(20, 15, 250, 15);
		saveToDataBaseYesButton.setText(LanguageLoader.getString("Common.yes"));
		saveToDataBaseNoButton.setText(LanguageLoader.getString("Common.no"));
		saveToDataBaseNoButton.setSelected(true);
		saveToDataBaseSelectValue = Constant.NO;
		saveToDataBaseYesButton.setBackground(null);
		saveToDataBaseNoButton.setBackground(null);
		saveToDataBaseButtonGroup.add(saveToDataBaseYesButton);
		saveToDataBaseButtonGroup.add(saveToDataBaseNoButton);
		add(saveToDataBaseYesButton);
		add(saveToDataBaseNoButton);
		saveToDataBaseYesButton.setBounds(130, 15, 70, 21);
		saveToDataBaseNoButton.setBounds(210, 15, 70, 21);
		
		
		dataBaseLabel.setText(LanguageLoader.getString("RuleContentSetting.dataBase"));
		add(dataBaseLabel);
		dataBaseLabel.setBounds(340, 15, 250, 15);
		
		dataBaseCombo = new JComboBox(new DataBaseComboBoxModel());
		dataBaseCombo.setBounds(420, 15, 200, 20);
		add(dataBaseCombo);
		
		pageTagLabel.setText(LanguageLoader.getString("RuleContentSetting.pageTag"));
		add(pageTagLabel);
		pageTagLabel.setBounds(20, 45, 250, 15);
		
		pageTagField.setColumns(20);
		add(pageTagField);
		pageTagField.setBounds(130, 45, 300, 21);
		
		
		dataBaseTableLabel.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTable"));
		add(dataBaseTableLabel);
		dataBaseTableLabel.setBounds(20, 75, 250, 15);
		
		dataBaseTableJlistPanel.add(new JScrollPane(dataBaseTableJlist));
		dataBaseTableJlistPanel.setBounds(20, 105, 180, 200);
		add(dataBaseTableJlistPanel);
		
		
		
		dataBaseTableColumnLabel.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableColumn"));
		add(dataBaseTableColumnLabel);
		dataBaseTableColumnLabel.setBounds(220, 75, 250, 15);
		 
		dataBaseTableColumnJlistPanel.add(new JScrollPane(dataBaseTableColumnJlist));
		dataBaseTableColumnJlistPanel.setBounds(220, 105, 180, 200);
		add(dataBaseTableColumnJlistPanel);
		
		
		
		
		selectValueLabel.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableColumnValue"));
		add(selectValueLabel);
		selectValueLabel.setBounds(420, 75, 250, 15);
		
		selectValueJlistPanel.add(new JScrollPane(selectValueJlist));
		selectValueJlistPanel.setBounds(420, 105, 180, 85);
		add(selectValueJlistPanel);
		
		
		defaultSelectValueLabel.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableColumnDefaultValue"));
		add(defaultSelectValueLabel);
		defaultSelectValueLabel.setBounds(420, 195, 250, 15);
		
		
		defaultSelectValueJlistPanel.add(new JScrollPane(defaultSelectValueJlist));
		defaultSelectValueJlistPanel.setBounds(420, 220, 180, 85);
		add(defaultSelectValueJlistPanel);
		
		
		
		dataBaseTableContentAddBtn.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableAddBtn"));
		add(dataBaseTableContentAddBtn);
		dataBaseTableContentAddBtn.setBounds(145, 325, 60, 20);
		
		dataBaseTableContentAddExtendFieldsBtn.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableAddExtendFieldsBtn"));
		add(dataBaseTableContentAddExtendFieldsBtn);
		dataBaseTableContentAddExtendFieldsBtn.setBounds(225, 325, 60, 20);
		
		dataBaseTableContentAddDefaultFieldsBtn.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableAddDefaultFieldsBtn"));
		add(dataBaseTableContentAddDefaultFieldsBtn);
		dataBaseTableContentAddDefaultFieldsBtn.setBounds(305, 325, 70, 20);
		
		dataBaseTableContentDelBtn.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableDelBtn"));
		add(dataBaseTableContentDelBtn);
		dataBaseTableContentDelBtn.setBounds(385, 325, 60, 20);
		
		dataBaseTableContentModifyBtn.setText(LanguageLoader.getString("RuleContentSetting.dataBaseTableModifyBtn"));
		add(dataBaseTableContentModifyBtn);
		dataBaseTableContentModifyBtn.setBounds(465, 325, 60, 20);
		
		
		hasSelectTableLabel.setText(LanguageLoader.getString("RuleContentSetting.hasSelectTable"));
		add(hasSelectTableLabel);
		hasSelectTableLabel.setBounds(20, 355, 250, 15);
		
		
		isPriTableRadio.setText(LanguageLoader.getString("RuleContentSetting.isPriTable"));
		isPriTableRadio.setBackground(null);
		add(isPriTableRadio);
		isPriTableRadio.setBounds(80, 355, 100, 15);
		

		hasSelectValueLabel.setText(LanguageLoader.getString("RuleContentSetting.hasSelectValue"));
		add(hasSelectValueLabel);
		hasSelectValueLabel.setBounds(220, 355, 250, 15);
		
		
		
		hasSelectTableListModel = new DefaultListModel();
		hasSelectTableJlist = new JList(hasSelectTableListModel);  
		hasSelectTableJlistPanel.add(new JScrollPane(hasSelectTableJlist));
		hasSelectTableJlistPanel.setBounds(20, 385, 180, 150);
		add(hasSelectTableJlistPanel);
		
		
		
		
		hasSelectValueListModel = new DefaultListModel();
		hasSelectValueJlist = new JList(hasSelectValueListModel);  
		hasSelectValueJlistPanel.add(new JScrollPane(hasSelectValueJlist));
		hasSelectValueJlistPanel.setBounds(220, 385, 380, 150);
		add(hasSelectValueJlistPanel);


		
	}
	
	
	@Override
	protected RuleDataBaseBean populateData() {
		RuleDataBaseBean ruleDataBaseBean = new RuleDataBaseBean();
		ruleDataBaseBean.setSaveToDataBaseFlag(this.saveToDataBaseSelectValue);
		ruleDataBaseBean.setDataBaseId(dataBaseId);
		ruleDataBaseBean.setPrimaryTable(isPriTableSelectValue);
		if(!hasSelectedValueMap.isEmpty()){
			ruleDataBaseBean.setHasSelectedValueMap(hasSelectedValueMap);
		}
		ruleDataBaseBean.setContentPageTag(pageTagField.getText());
		return ruleDataBaseBean;
	}
	public void initData(RuleDataBaseBean t,CrawlerRuleBean crawlerRuleBean){
		if(null == t){
			t = new RuleDataBaseBean();
		}
		hasSelectedValueMap = new HashMap<String,Map<String,ColumnBean>>();
		this.crawlerRuleBean = crawlerRuleBean;
		fillComponentData(t);
	}


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(RuleDataBaseBean ruleDataBaseBean) {
		
		List<CrawlerDataBaseBean> resultList = crawlerDataBaseService.getList(new CrawlerDataBaseCriteria(),SystemConstant.SQLMAP_ID_LIST_CRAWLER_DATABASE);
		Map<String,CrawlerDataBaseBean> resultMap = new HashMap<String,CrawlerDataBaseBean>();
		for(CrawlerDataBaseBean crawlerDataBaseBean : resultList){
			resultMap.put(crawlerDataBaseBean.getDataBaseId()+"", crawlerDataBaseBean);
		}
		dataBaseCombo.setModel(new DataBaseComboBoxModel(resultList));
		dataBaseCombo.repaint();
		dataBaseCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CrawlerDataBaseBean crawlerDataBaseBean = (CrawlerDataBaseBean) dataBaseCombo.getSelectedItem();
				dataBaseId = crawlerDataBaseBean.getDataBaseId() + "";
				dataBaseTableJlist.setModel(defaultListModel);
				if(null == getDataBaseInfoCache() || null == getDataBaseInfoCache().get(crawlerDataBaseBean.getDataBaseId()) || getDataBaseInfoCache().get(crawlerDataBaseBean.getDataBaseId()).isEmpty()){
					loadTableInfo();
				}else{
					Map<String,List<ColumnBean>> tableNameMap = getDataBaseInfoCache().get(crawlerDataBaseBean.getDataBaseId());
					logger.info("从缓存中读取数据库表信息");
					dataBaseTableSelectListModel.clear();
					dataBaseTableColumnSelectListModel.clear();
					for(String tableName : tableNameMap.keySet()){
						dataBaseTableSelectListModel.addElement(tableName);
					}
					dataBaseTableJlist.setModel(dataBaseTableSelectListModel);
				}
			}
		});
		
		if(StringUtils.isNotBlank(ruleDataBaseBean.getDataBaseId())){
			dataBaseCombo.setSelectedItem(resultMap.get(ruleDataBaseBean.getDataBaseId()));
		}else{
			dataBaseCombo.setSelectedItem("");
		}
		hasSelectTableListModel.clear();
		hasSelectValueListModel.clear();
		if(null != ruleDataBaseBean.getHasSelectedValueMap() && !ruleDataBaseBean.getHasSelectedValueMap().isEmpty()){
			hasSelectedValueMap = ruleDataBaseBean.getHasSelectedValueMap();
			for(Iterator<String> key = hasSelectedValueMap.keySet().iterator();key.hasNext();){
				hasSelectTableListModel.addElement(key.next());
			}
			hasSelectTableJlist.setSelectedIndex(0);
			Map<String,ColumnBean> columnMap = hasSelectedValueMap.get(hasSelectTableJlist.getSelectedValue());
	  		for(Iterator<String> key = columnMap.keySet().iterator();key.hasNext();){
	  			hasSelectValueListModel.addElement(columnMap.get(key.next()));
	  		}
		}
		
	
		if(Constant.YES.equals(ruleDataBaseBean.getSaveToDataBaseFlag())){
			saveToDataBaseYesButton.setSelected(true);
			saveToDataBaseSelectValue = Constant.YES;
			changeDataBaseInputState(true);
		}else{
			saveToDataBaseNoButton.setSelected(true);
			saveToDataBaseSelectValue = Constant.NO;
			changeDataBaseInputState(false);
		}
		selectValueSelectListModel.clear();
		selectValueSelectListModel.addElement(new SelectValueBean(LanguageLoader.getString("System.DataBaseSettingValueKyContent"),GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT));
		selectValueSelectListModel.addElement(new SelectValueBean(LanguageLoader.getString("System.DataBaseSettingValueKyContentId"),GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_ID));
		selectValueSelectListModel.addElement(new SelectValueBean(LanguageLoader.getString("System.DataBaseSettingValueKyContentTitle"),GatherConstant.DATA_BASE_SETTING_VALUES_KRY_CONTENT_TITLE));
        selectValueSelectListModel.addElement(new SelectValueBean(LanguageLoader.getString("System.DataBaseSettingValueKyContentViewDate"),GatherConstant.DATA_BASE_SETTING_VALUES_KRY_VIEW_DATE));
		
        if(null!= this.crawlerRuleBean.getRuleFieldsBean() && CollectionUtils.isNotEmpty(this.crawlerRuleBean.getRuleFieldsBean().getExtendFields())){
			List<ExtendFieldsBean> extendFieldList = this.crawlerRuleBean.getRuleFieldsBean().getExtendFields();
			for(ExtendFieldsBean extendFieldsBean : extendFieldList){
				selectValueSelectListModel.addElement(new SelectValueBean(extendFieldsBean.getFields(),extendFieldsBean.getFields(),GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP));
				
			}
		}
		if(StringUtils.isNotBlank(ruleDataBaseBean.getPrimaryTable())){
			isPriTableSelectValue = ruleDataBaseBean.getPrimaryTable();
		}
		
		pageTagField.setText(ruleDataBaseBean.getContentPageTag());
		
		List<CrawlerDiyDataConfigBean> diyDataList = (List<CrawlerDiyDataConfigBean>) this.crawlerCacheManager.getValue(CacheKeyConstant.CACHE_KEY_DIYDATA);
		if(CollectionUtils.isEmpty(diyDataList)){
			diyDataList = (List<CrawlerDiyDataConfigBean>)this.crawlerCacheManager.loadCacheByKey(CacheKeyConstant.CACHE_KEY_DIYDATA);
		}
		if(!CollectionUtils.isEmpty(diyDataList)){
			defaultSelectValueSelectListModel.clear();
			for(CrawlerDiyDataConfigBean crawlerDiyDataConfigBean : diyDataList){
				defaultSelectValueSelectListModel.addElement(new SelectValueBean(crawlerDiyDataConfigBean.getName(),crawlerDiyDataConfigBean.getValue(),crawlerDiyDataConfigBean.getType()));
			}
		}
	}
	/**
	 * 加载数据库表信息
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-17 上午11:23:01
	 * @version 1.0
	 * @exception
	 */
	private void loadTableInfo(){
		Connection conn = null;
		try{
			conn = getConnectionManager().getConnection(dataBaseId);
			if (null == conn) {
				 JOptionPane.showMessageDialog(null,LanguageLoader.getString("System.DataBaseSettingErrorMessage"),
				 LanguageLoader.getString("System.DataBaseSettingError"),
				 JOptionPane.CLOSED_OPTION);
			} else {
				logger.info("立即加载数据库表信息");
				DatabaseMetaData dbmd = conn.getMetaData();
				ResultSet tSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE","VIEW"});
				dataBaseTableSelectListModel.clear();
				dataBaseTableColumnSelectListModel.clear();
				while (tSet.next()) {
					dataBaseTableSelectListModel.addElement(tSet.getString("TABLE_NAME"));
				}
				dataBaseTableJlist.setModel(dataBaseTableSelectListModel);
				tSet.close();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			if(null != conn){
				getConnectionManager().freeConnection(dataBaseId, conn);
			}
		}
	}
	/**
	 * 加载指定表字段信息
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-17 上午11:41:26
	 * @version 1.0
	 * @exception 
	 * @param tableName
	 */
	private void loadColumnInfo(String tableName){
		Connection conn = null;
		try{
			conn = getConnectionManager().getConnection(dataBaseId);
			if(null != conn && null != tableName && StringUtils.isNotBlank(tableName.toString())){
				logger.info("及时读取表字段信息");
				String sql = "select * from " + tableName;
                ResultSet rsSet = conn.createStatement().executeQuery(sql);
                ResultSetMetaData rsData = rsSet.getMetaData();
                for (int i = 1; i <= rsData.getColumnCount(); i++) {
                	dataBaseTableColumnSelectListModel.addElement(new ColumnBean(rsData.getColumnName(i),rsData.getColumnTypeName(i),"","",GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC,rsData.isNullable(i)));
                }
                dataBaseTableColumnJlist.setModel(dataBaseTableColumnSelectListModel);
                rsSet.close();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			if(null != conn){
				getConnectionManager().freeConnection(dataBaseId, conn);
			}
		} 
	}
	protected void initActionListener(){
		class SaveToDataBaseBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (saveToDataBaseYesButton.isSelected()) {
					saveToDataBaseSelectValue = Constant.YES;
					changeDataBaseInputState(true);
	             } else {
	                saveToDataBaseSelectValue = Constant.NO;
					changeDataBaseInputState(false);
	             }
			}
		}
		saveToDataBaseYesButton.addActionListener(new SaveToDataBaseBtnActionAdapter());
		saveToDataBaseNoButton.addActionListener(new SaveToDataBaseBtnActionAdapter());
		MouseAdapter mouseListener = new MouseAdapter() {  
		      public void mouseClicked(MouseEvent mouseEvent) {  
		        JList theList = (JList) mouseEvent.getSource();  
		        int index = theList.locationToIndex(mouseEvent.getPoint());  
		          if (index >= 0) {  
		            Object value = theList.getModel().getElementAt(index);  
		            dataBaseTableColumnSelectListModel.clear();
		            if(null == getDataBaseInfoCache() || null == getDataBaseInfoCache().get(Integer.valueOf(dataBaseId)) || getDataBaseInfoCache().get(Integer.valueOf(dataBaseId)).isEmpty()){
		            	loadColumnInfo(value.toString());
					}else{
						Map<String,List<ColumnBean>> tableNameMap = getDataBaseInfoCache().get(Integer.valueOf(dataBaseId));
						List<ColumnBean> columnList = tableNameMap.get(value.toString());
						if(CollectionUtils.isEmpty(columnList)){
							loadColumnInfo(value.toString());
						}else{
							logger.info("从缓存中读取表字段信息");
							for(ColumnBean column : columnList){
								dataBaseTableColumnSelectListModel.addElement(new ColumnBean(column.getColumnName(),column.getColumnType(),"","",GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC,column.getIsNullable()));
			                }
			                dataBaseTableColumnJlist.setModel(dataBaseTableColumnSelectListModel);
						}
					}
		          }
		      }  
		    };  
		dataBaseTableJlist.addMouseListener(mouseListener);  
		
		MouseAdapter hasSelectMouseListener = new MouseAdapter() {  
		      public void mouseClicked(MouseEvent mouseEvent) { 
		    	  JList theList = (JList) mouseEvent.getSource();  
			      int index = theList.locationToIndex(mouseEvent.getPoint());  
			      if (index >= 0) {  
			    	Object value = theList.getModel().getElementAt(index);  
			    	hasSelectValueLabel.setText(LanguageLoader.getString("RuleContentSetting.hasSelectValue")+":"+value);
					Map<String,ColumnBean> columnMap = hasSelectedValueMap.get(value.toString());
			  		hasSelectValueListModel.clear();
			  		for(Iterator<String> key = columnMap.keySet().iterator();key.hasNext();){
			  			hasSelectValueListModel.addElement(columnMap.get(key.next()));
			  		}
			  		isPriTableRadio.setEnabled(true);
			  		if(isPriTableSelectValue.equals(value.toString())){
			  			isPriTableRadio.setSelected(true);
			  		}else{
			  			isPriTableRadio.setSelected(false);
			  		}
			      }else{
			    	  isPriTableRadio.setEnabled(false);
			      }
		      }
		};
		hasSelectTableJlist.addMouseListener(hasSelectMouseListener);
		
		
		dataBaseTableContentAddBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dataBaseTableColumnJlist.getSelectedIndex()!=-1){
					ColumnBean tempBean = (ColumnBean)dataBaseTableColumnJlist.getSelectedValue();
					if(null == hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()) || !hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()).containsKey(tempBean.getColumnName())){
						String inputValue = JOptionPane.showInputDialog(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValue")); 
						if(StringUtils.isNotBlank(inputValue)){
							ColumnBean columnBean = new ColumnBean();
							columnBean.setColumnName(tempBean.getColumnName());
							columnBean.setColumnType(tempBean.getColumnType());
							columnBean.setColumnValueView(inputValue);
							columnBean.setColumnValue(inputValue);
							columnBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC);
							addSelectValue(columnBean);
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueIsNotBlank"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}else{
						JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnExist"),
								 LanguageLoader.getString("Common.alertTitle"),
								 JOptionPane.CLOSED_OPTION);
					}
				}
			}
		});
		
		dataBaseTableContentAddExtendFieldsBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dataBaseTableColumnJlist.getSelectedIndex()!=-1){
					ColumnBean tempBean = (ColumnBean)dataBaseTableColumnJlist.getSelectedValue();
					SelectValueBean selectValueBean = (SelectValueBean)selectValueJlist.getSelectedValue(); 
					//扩展字段 则需要提醒是保存值还是保存键
					if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(selectValueBean.getType())){
						int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleContentSetting.dynamicMapInfo"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
						if(result == 0){
							createMultColumnBean(tempBean,selectValueBean,GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY);
						}else{
							createMultColumnBean(tempBean,selectValueBean,GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE);
						}
					}else{//如果系统提供服务，则要检查字段是否重复
						if(null == hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()) || !hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()).containsKey(tempBean.getColumnName())){
							createSingleColumnBean(tempBean,selectValueBean,GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC);
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnExist"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}
				}
			}
			/**
			 * 创建多值ColumnBean
			 * <p>方法说明:</>
			 * <li></li>
			 * @author DuanYong
			 * @since 2014-9-26 上午10:24:56
			 * @version 1.0
			 * @exception 
			 * @param tempBean
			 * @param selectValueBean
			 * @param columnValueType
			 */
			private void createMultColumnBean(ColumnBean tempBean,SelectValueBean selectValueBean,String columnValueType){
				if(null!= selectValueBean && StringUtils.isNotBlank(selectValueBean.getValue())){
					Map<String,ColumnBean> tempMap = hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString());
					if(null == tempMap){
						createColumnBean(tempBean,selectValueBean,columnValueType);
					}else{
						ColumnBean tBean = tempMap.get(tempBean.getColumnName());
						if(null == tBean){
							createColumnBean(tempBean,selectValueBean,columnValueType);
						}else{
							if(!tBean.getColumnValueType().equals(columnValueType)){
								JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueType"),
										 LanguageLoader.getString("Common.alertTitle"),
										 JOptionPane.CLOSED_OPTION);
								return;
							}
							if(!tBean.getColumnValue().contains(selectValueBean.getValue())){
								tBean.setColumnValue(tBean.getColumnValue()+","+selectValueBean.getValue());
								tBean.setColumnValueView(tBean.getColumnValueView()+","+selectValueBean.getValueName());
							}else{
								JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnExist"),
										 LanguageLoader.getString("Common.alertTitle"),
										 JOptionPane.CLOSED_OPTION);
							}
						}
					}
				}else{
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueIsNotBlank"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
				}
			}
			/**
			 * 创建单值ColumnBean
			 * <p>方法说明:</>
			 * <li></li>
			 * @author DuanYong
			 * @since 2014-9-26 上午10:24:56
			 * @version 1.0
			 * @exception 
			 * @param tempBean
			 * @param selectValueBean
			 * @param columnValueType
			 */
			private void createSingleColumnBean(ColumnBean tempBean,SelectValueBean selectValueBean,String columnValueType){
				if(null!= selectValueBean && StringUtils.isNotBlank(selectValueBean.getValue())){
					createColumnBean(tempBean,selectValueBean,columnValueType);
				}else{
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueIsNotBlank"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
				}
			}
			
			private void createColumnBean(ColumnBean tempBean,SelectValueBean selectValueBean,String columnValueType){
				ColumnBean columnBean = new ColumnBean();
				columnBean.setColumnName(tempBean.getColumnName());
				columnBean.setColumnType(tempBean.getColumnType());
				columnBean.setColumnValueView(selectValueBean.getValueName());
				columnBean.setColumnValue(selectValueBean.getValue());
				columnBean.setColumnValueType(columnValueType);
				addSelectValue(columnBean);
			}
			
		});
		
		dataBaseTableContentAddDefaultFieldsBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dataBaseTableColumnJlist.getSelectedIndex()!=-1){
					ColumnBean tempBean = (ColumnBean)dataBaseTableColumnJlist.getSelectedValue();
					if(null == hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()) || !hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString()).containsKey(tempBean.getColumnName())){
						SelectValueBean selectValueBean = (SelectValueBean)defaultSelectValueJlist.getSelectedValue(); 
						if(null != selectValueBean && StringUtils.isNotBlank(selectValueBean.getValue())){
							ColumnBean columnBean = new ColumnBean();
							columnBean.setColumnName(tempBean.getColumnName());
							columnBean.setColumnType(tempBean.getColumnType());
							columnBean.setColumnValueView(selectValueBean.getValueName());
							columnBean.setColumnValue(selectValueBean.getValue());
							if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(selectValueBean.getType())){
								int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleContentSetting.staticMapInfo"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
								if(result == 0){
									columnBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_KEY);
								}else{
									columnBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE);
								}
							}else{
								columnBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MULIT);
							}
							addSelectValue(columnBean);
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableDefaultColumnValueIsNotBlank"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}else{
						JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnExist"),
								 LanguageLoader.getString("Common.alertTitle"),
								 JOptionPane.CLOSED_OPTION);
					}
				}
			}
		});
		
		
		dataBaseTableContentDelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
				if(result == 0){
					if(hasSelectTableJlist.getSelectedIndex()!=-1){
						if(hasSelectValueJlist.getSelectedIndex()!=-1){
							ColumnBean tempBean = (ColumnBean)hasSelectValueJlist.getSelectedValue();
							hasSelectedValueMap.get(hasSelectTableJlist.getSelectedValue()).remove(tempBean.getColumnName());
							Map<String,ColumnBean> columnMap = hasSelectedValueMap.get(hasSelectTableJlist.getSelectedValue());
							columnMap.remove(hasSelectValueJlist.getSelectedValue());
							hasSelectValueListModel.clear();
					  		for(Iterator<String> key = columnMap.keySet().iterator();key.hasNext();){
					  			hasSelectValueListModel.addElement(columnMap.get(key.next()));
					  		}
						}else{
							hasSelectedValueMap.remove(hasSelectTableJlist.getSelectedValue());
							hasSelectTableListModel.clear();
							hasSelectValueListModel.clear();
							for(Iterator<String> key = hasSelectedValueMap.keySet().iterator();key.hasNext();){
								hasSelectTableListModel.addElement(key.next());
							}
							hasSelectTableJlist.setSelectedIndex(0);
						}
						
					}else{
						JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableIsEmpty"),
								 LanguageLoader.getString("Common.alertTitle"),
								 JOptionPane.CLOSED_OPTION);
					}
				}
			}
		});
		
		dataBaseTableContentModifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasSelectValueJlist.getSelectedIndex()!=-1){
					ColumnBean tempBean = (ColumnBean)hasSelectValueJlist.getSelectedValue();
					//静态值
					if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC.equals(tempBean.getColumnValueType())){
						String inputValue = JOptionPane.showInputDialog(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValue")); 
						if(StringUtils.isNotBlank(inputValue)){
							tempBean.setColumnValueView(inputValue);
							tempBean.setColumnValue(inputValue);
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueIsNotBlank"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}else if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MULIT.equals(tempBean.getColumnValueType()) || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_KEY.equals(tempBean.getColumnValueType()) || GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE.equals(tempBean.getColumnValueType())){
						if(defaultSelectValueJlist.getSelectedIndex() != -1){
							SelectValueBean selectValueBean = (SelectValueBean)defaultSelectValueJlist.getSelectedValue(); 
							if(null != selectValueBean && StringUtils.isNotBlank(selectValueBean.getValue())){
								int corfm = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("Core.version_select_Confirm"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
								if(corfm == 0){
									tempBean.setColumnValueView(selectValueBean.getValueName());
									tempBean.setColumnValue(selectValueBean.getValue());
									if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(selectValueBean.getType())){
										int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleContentSetting.staticMapInfo"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
										if(result == 0){
											tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_KEY);
										}else{
											tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE);
										}
									}else{
										tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_MULIT);
									}
								}
							}
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableDefaultColumnValueIsNotBlank"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}else if(tempBean.getColumnValueType().contains(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC) || tempBean.getColumnValueType().equals((GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC))){
						if(selectValueJlist.getSelectedIndex() != -1){
							int corfm = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("Core.version_select_Confirm"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
							if(corfm == 0){
								SelectValueBean selectValueBean = (SelectValueBean)selectValueJlist.getSelectedValue();
								tempBean.setColumnValue(selectValueBean.getValue());
								tempBean.setColumnValueView(selectValueBean.getValueName());
								tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC);
								if(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP.equals(selectValueBean.getType())){
									int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleContentSetting.dynamicMapInfo"),LanguageLoader.getString("Common.alertTitle"), JOptionPane.YES_NO_OPTION); 
									if(result == 0){
										tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY);
									}else{
										tempBean.setColumnValueType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE);
									}
								}
							}
						}else{
							JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueIsNotBlank"),
									 LanguageLoader.getString("Common.alertTitle"),
									 JOptionPane.CLOSED_OPTION);
						}
					}
				}else{
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleContentSetting.dataBaseSelectValueIsEmpty"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
				}
			}
		});
		
		isPriTableRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isPriTableRadio.isSelected()) {
					isPriTableSelectValue = hasSelectTableJlist.getSelectedValue().toString();
                }else{
                	isPriTableSelectValue = "";
                }
			}
		});
		
	}
	
	private void addSelectValue(ColumnBean columnBean){
		Map<String,ColumnBean> columnMap = null;
		if(!hasSelectedValueMap.containsKey(dataBaseTableJlist.getSelectedValue().toString())){
			columnMap = new HashMap<String,ColumnBean>();
			columnMap.put(columnBean.getColumnName(), columnBean);
			hasSelectedValueMap.put(dataBaseTableJlist.getSelectedValue().toString(), columnMap);
			hasSelectTableListModel.addElement(dataBaseTableJlist.getSelectedValue().toString());
		}else{
			columnMap = hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString());
			columnMap.put(columnBean.getColumnName(), columnBean);
		}
		hasSelectValueLabel.setText(LanguageLoader.getString("RuleContentSetting.hasSelectValue")+":"+dataBaseTableJlist.getSelectedValue().toString());
		hasSelectTableJlist.setSelectedValue(dataBaseTableJlist.getSelectedValue().toString(), false);
		columnMap = hasSelectedValueMap.get(dataBaseTableJlist.getSelectedValue().toString());
		hasSelectValueListModel.clear();
		for(Iterator<String> key = columnMap.keySet().iterator();key.hasNext();){
			hasSelectValueListModel.addElement(columnMap.get(key.next()));
		}
	}
	
	/**
	 * 改变数据库配置相关选项状态
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-24 上午9:37:27
	 * @param b
	 * @return void
	 */
    private void changeDataBaseInputState(boolean b){
	    dataBaseLabel.setEnabled(b);
	    dataBaseCombo.setEnabled(b);
		
    	saveToDataBaseLabel.setEnabled(b);
    	dataBaseLabel.setEnabled(b);
    	dataBaseCombo.setEnabled(b);
    	dataBaseTableLabel.setEnabled(b);
    	dataBaseTableJlistPanel.setEnabled(b);
    	dataBaseTableJlist.setEnabled(b);
    	dataBaseTableColumnLabel.setEnabled(b);
    	dataBaseTableColumnJlistPanel.setEnabled(b);
    	dataBaseTableColumnJlist.setEnabled(b);
    	selectValueLabel.setEnabled(b);
    	selectValueJlistPanel.setEnabled(b);
    	selectValueJlist.setEnabled(b);
    	hasSelectTableLabel.setEnabled(b);
    	hasSelectTableJlistPanel.setEnabled(b);
    	hasSelectTableJlist.setEnabled(b);
    	hasSelectValueLabel.setEnabled(b);
    	hasSelectValueJlistPanel.setEnabled(b);
    	hasSelectValueJlist.setEnabled(b);
    	dataBaseTableContentAddBtn.setEnabled(b);
    	dataBaseTableContentAddExtendFieldsBtn.setEnabled(b);
    	dataBaseTableContentDelBtn.setEnabled(b);
		
		
    }
    
    public DBConnectionManager getConnectionManager() {
		if(null == connectionManager){
			connectionManager = DBConnectionManager.getInstance();
		}
		return connectionManager;
	}
    /**
     * 读取缓存信息
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2014-9-17 下午12:39:13
     * @version 1.0
     * @exception 
     * @return
     */
    private Map<Integer,Map<String,List<ColumnBean>>>  getDataBaseInfoCache(){
    	return (Map<Integer,Map<String,List<ColumnBean>>>) this.crawlerCacheManager.getValue(CacheKeyConstant.CACHE_KEY_DATA_BASE);
    }

}
