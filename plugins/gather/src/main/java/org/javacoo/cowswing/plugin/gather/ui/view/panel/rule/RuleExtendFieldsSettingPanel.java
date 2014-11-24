package org.javacoo.cowswing.plugin.gather.ui.view.panel.rule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.service.beans.ExtendFieldsBean;
import org.javacoo.cowswing.plugin.gather.service.beans.RuleFieldsBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerExtendFieldsTabelModel;
import org.javacoo.cowswing.ui.view.panel.AbstractContentPanel;
/**
 * 扩展字段设置面板
 *@author DuanYong
 *@since 2013-3-18下午10:49:09
 *@version 1.0
 */
@org.springframework.stereotype.Component("ruleExtendFieldsSettingPanel")
public class RuleExtendFieldsSettingPanel extends AbstractContentPanel<RuleFieldsBean>{
	private static final long serialVersionUID = 1L;

	/**扩展字段panel*/
	private JComponent extendsFieldsPanel;
	/**添加扩展段Button*/
	private JButton addExtednsFieldsButton;
	/**删除扩展段Button*/
	private JButton deleteExtednsFieldsButton;
	/**删除扩展段JTable*/
	private JTable crawlerExtendFieldsTabel;
	/**删除扩展段TabelMode*/
	private CrawlerExtendFieldsTabelModel dataModel;
	
	/**
	 * 初始化面板控件
	 */
	protected void initComponents(){
		extendsFieldsPanel = new JPanel(new BorderLayout());
		extendsFieldsPanel.setBounds(20, 15, 600, 425);
		extendsFieldsPanel.add(getTopButtonPanel(), BorderLayout.NORTH);
		extendsFieldsPanel.add(getExtendsFieldsListPanel(), BorderLayout.CENTER);
		add(extendsFieldsPanel);
		
	
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
	private Component getCrawlerExtendFieldsTable() {
		if (crawlerExtendFieldsTabel == null) {
			crawlerExtendFieldsTabel = new JTable();
			dataModel = new CrawlerExtendFieldsTabelModel(getColumnNames());
			crawlerExtendFieldsTabel.setModel(dataModel);
			crawlerExtendFieldsTabel.setPreferredScrollableViewportSize(new Dimension(500, 70));
			crawlerExtendFieldsTabel.setFillsViewportHeight(true);

			crawlerExtendFieldsTabel.setAutoCreateRowSorter(true);
		}
		return crawlerExtendFieldsTabel;
	}
	
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("RuleContentSetting.extndsFieldName"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.extndsInclude"));
		columnNames.add(LanguageLoader.getString("RuleContentSetting.extndsFilter"));
		return columnNames;
	}
	protected Component getTopButtonPanel() {
		JPanel topBar = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 3, 2);
		layout.setAlignment(FlowLayout.RIGHT);
		topBar.setLayout(layout);
		class AddExtednsFieldsAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public AddExtednsFieldsAction(){
				super(LanguageLoader.getString("RuleContentSetting.addExtndsFields"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleAdd"));
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
				super(LanguageLoader.getString("RuleContentSetting.deleteExtndsFields"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleDelete"));
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
				for(ExtendFieldsBean extendFieldsBean: dataModel.getData()){
					logger.info(extendFieldsBean.toString());
				}
				
			}

		}
		addExtednsFieldsButton = new JButton(new AddExtednsFieldsAction());
		topBar.add(addExtednsFieldsButton);
		deleteExtednsFieldsButton = new JButton(new DeleteExtednsFieldsAction());
		topBar.add(deleteExtednsFieldsButton);
		return topBar;
	}
	
	
	protected void fillComponentData(RuleFieldsBean ruleFieldsBean){
		if(CollectionUtils.isNotEmpty(ruleFieldsBean.getExtendFields())){
			dataModel.setData(ruleFieldsBean.getExtendFields());
		}else{
			dataModel.setData(new ArrayList<ExtendFieldsBean>());
		}
	}
	

	@Override
	protected RuleFieldsBean populateData() {
		RuleFieldsBean ruleFieldsBean = new RuleFieldsBean();
		if(CollectionUtils.isNotEmpty(dataModel.getData())){
			ruleFieldsBean.setExtendFields(dataModel.getData());
		}
		return ruleFieldsBean;
	}
	public void initData(RuleFieldsBean t){
		if(t == null){
			t = new RuleFieldsBean();
		}
		fillComponentData(t);
	}

}
