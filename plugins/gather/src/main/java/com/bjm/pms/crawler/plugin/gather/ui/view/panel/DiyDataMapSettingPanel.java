/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.beans.CrawlerDiyDataConfigBean;
import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.SelectValueBean;
import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerDiyDataMapFieldsTabelModel;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.JsonUtils;
import com.bjm.pms.crawler.view.ui.listener.TextVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;


/**
 * 复杂自定义数据设置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:26:54
 * @version 1.0
 */
@Component("diyDataMapSettingPanel")
public class DiyDataMapSettingPanel extends AbstractContentPanel<CrawlerDiyDataConfigBean>{

	private static final long serialVersionUID = 1L;
	/**自定义数据名称输入框*/
	private javax.swing.JTextField nameField;
	/**自定义数据名称标签*/
	private javax.swing.JLabel nameLabel;
	
	/**添加自定义数据Button*/
	private JButton addExtednsFieldsButton;
	/**删除自定义数据Button*/
	private JButton deleteExtednsFieldsButton;
	/**删除自定义数据JTable*/
	private JTable crawlerExtendFieldsTabel;
	/**删除自定义数据TabelMode*/
	private CrawlerDiyDataMapFieldsTabelModel dataModel;
	
	/**自定义数据值标签*/
	private javax.swing.JLabel valueLabel;
	/**自定义数据描述输入框*/
	private javax.swing.JTextField descField;
	/**自定义数据描述标签*/
	private javax.swing.JLabel descLabel;
	/**自定义数据帮助标签*/
	private javax.swing.JLabel helpLabel;
	/**自定义键值对形式值*/
	private String mapValue = "";
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected CrawlerDiyDataConfigBean populateData() {
		if(checkData()){
			CrawlerDiyDataConfigBean crawlerDiyDataConfigBean = new CrawlerDiyDataConfigBean();
			crawlerDiyDataConfigBean.setName(nameField.getText());
			crawlerDiyDataConfigBean.setValue(getMapValue());
			crawlerDiyDataConfigBean.setDesc(descField.getText());
			crawlerDiyDataConfigBean.setType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP);
			return crawlerDiyDataConfigBean;
		}else{
			return null;
		}
	}
	protected void initActionListener(){
		
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		class AddExtednsFieldsAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public AddExtednsFieldsAction(){
				super(LanguageLoader.getString("System.addDiyMap"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
			}
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectValueBean selectValueBean = new SelectValueBean("","");
				dataModel.addRow(selectValueBean);
			}

		}
		class DeleteExtednsFieldsAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public DeleteExtednsFieldsAction(){
				super(LanguageLoader.getString("System.deleteDiyMap"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
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
				for(SelectValueBean selectValueBean: dataModel.getData()){
					logger.info(selectValueBean.toString());
				}
			}
		}
		
		
		
		nameLabel = new javax.swing.JLabel();
		nameField = new javax.swing.JTextField();
		
		
		
		valueLabel = new javax.swing.JLabel();
		
		descLabel = new javax.swing.JLabel();
		descField = new javax.swing.JTextField();
		
		helpLabel = new javax.swing.JLabel();
		
	
		
		
		nameLabel.setText(LanguageLoader.getString("System.Diy_name"));
		add(nameLabel);
		nameLabel.setBounds(20, 15, 80, 15);

		nameField.setColumns(20);
		add(nameField);
		nameField.setBounds(100, 15, 200, 21);
		nameField.setInputVerifier(new TextVerifier(this, false));
		
		
		valueLabel.setText(LanguageLoader.getString("System.Diy_value"));
		add(valueLabel);
		valueLabel.setBounds(20, 45, 80, 15);
		
		
		addExtednsFieldsButton = new JButton(new AddExtednsFieldsAction());
		add(addExtednsFieldsButton);
		addExtednsFieldsButton.setBounds(110, 45, 80, 15);

		deleteExtednsFieldsButton = new JButton(new DeleteExtednsFieldsAction());
		add(deleteExtednsFieldsButton);
		deleteExtednsFieldsButton.setBounds(200, 45, 80, 15);
		
		JScrollPane js = new JScrollPane(getCrawlerExtendFieldsTable());
		add(js);
		js.setBounds(20, 75, 280, 110);
		
		
		
		descLabel.setText(LanguageLoader.getString("System.Diy_desc"));
		add(descLabel);
		descLabel.setBounds(20, 190, 80, 15);
		
		descField.setColumns(20);
		add(descField);
		descField.setBounds(100, 190, 200, 21);
		
		helpLabel.setText(LanguageLoader.getString("System.Diy_map_help"));
		add(helpLabel);
		helpLabel.setBounds(20, 220, 280, 30);

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
			dataModel = new CrawlerDiyDataMapFieldsTabelModel(getColumnNames());
			crawlerExtendFieldsTabel.setModel(dataModel);
			crawlerExtendFieldsTabel.setPreferredScrollableViewportSize(new Dimension(500, 70));
			crawlerExtendFieldsTabel.setFillsViewportHeight(true);

			crawlerExtendFieldsTabel.setAutoCreateRowSorter(true);
		}
		return crawlerExtendFieldsTabel;
	}
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("System.Diy_map_key"));
		columnNames.add(LanguageLoader.getString("System.Diy_map_value"));
		return columnNames;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(CrawlerDiyDataConfigBean t) {
		logger.info("填充页面控件数据");
		nameField.setText(t.getName());
		mapValue = t.getValue();
		descField.setText(t.getDesc());
		dataModel.setData(parseValueToList(t.getValue()));
	}
	
	private List<SelectValueBean> parseValueToList(String value){
		List<SelectValueBean> returnList = new ArrayList<SelectValueBean>();
		if(StringUtils.isNotBlank(value)){
			List tempList = (ArrayList)JsonUtils.formatStringToObject(value, ArrayList.class);
			for(Object obj : tempList){
				returnList.add((SelectValueBean)JsonUtils.formatStringToObject(obj.toString(), SelectValueBean.class));
			}
		}
		return returnList;
	}
	private String getMapValue(){
		if(CollectionUtils.isNotEmpty(dataModel.getData())){
			return JsonUtils.formatObjectToJsonString(dataModel.getData());
		}
		return mapValue;
	}
	private boolean checkData(){
		List<SelectValueBean> returnList = dataModel.getData();
		if(CollectionUtils.isEmpty(returnList)){
			JOptionPane.showMessageDialog(null,LanguageLoader.getString("System.Diy_map_value_isEmpty"),
					 LanguageLoader.getString("Common.alertTitle"),
					 JOptionPane.CLOSED_OPTION);
			return false;
		}
		for(SelectValueBean tempBean : returnList){
			if(StringUtils.isBlank(tempBean.getValue()) || StringUtils.isBlank(tempBean.getValueName())){
				JOptionPane.showMessageDialog(null,LanguageLoader.getString("System.Diy_map_value_isEmpty"),
						 LanguageLoader.getString("Common.alertTitle"),
						 JOptionPane.CLOSED_OPTION);
				return false;
			}
		}
		return true;
	}
	public void initData(CrawlerDiyDataConfigBean t){
		if(t == null){
			t = new CrawlerDiyDataConfigBean();
		}
		fillComponentData(t);
	}

}
