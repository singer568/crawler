/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel;

import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDiyDataConfigBean;
import org.javacoo.cowswing.ui.listener.TextVerifier;
import org.javacoo.cowswing.ui.view.panel.AbstractContentPanel;
import org.springframework.stereotype.Component;


/**
 * 自定义数据设置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:26:54
 * @version 1.0
 */
@Component("diyDataSettingPanel")
public class DiyDataSettingPanel extends AbstractContentPanel<CrawlerDiyDataConfigBean>{

	private static final long serialVersionUID = 1L;
	/**自定义数据名称输入框*/
	private javax.swing.JTextField nameField;
	/**自定义数据名称标签*/
	private javax.swing.JLabel nameLabel;
	/**自定义数据*/
	private javax.swing.JTextField valueField;
	/**自定义数据值标签*/
	private javax.swing.JLabel valueLabel;
	/**自定义数据描述输入框*/
	private javax.swing.JTextField descField;
	/**自定义数据描述标签*/
	private javax.swing.JLabel descLabel;
	/**自定义数据帮助标签*/
	private javax.swing.JLabel helpLabel;
	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected CrawlerDiyDataConfigBean populateData() {
		CrawlerDiyDataConfigBean crawlerDiyDataConfigBean = new CrawlerDiyDataConfigBean();
		crawlerDiyDataConfigBean.setName(nameField.getText());
		crawlerDiyDataConfigBean.setValue(valueField.getText());
		crawlerDiyDataConfigBean.setDesc(descField.getText());
		crawlerDiyDataConfigBean.setType(GatherConstant.EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_SIMPLE);
		return crawlerDiyDataConfigBean;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		nameLabel = new javax.swing.JLabel();
		nameField = new javax.swing.JTextField();
		
		valueLabel = new javax.swing.JLabel();
		valueField = new javax.swing.JTextField();
		
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

		valueField.setColumns(20);
		add(valueField);
		valueField.setBounds(100, 45, 200, 21);
		valueField.setInputVerifier(new TextVerifier(this, false));
		
		
		
		descLabel.setText(LanguageLoader.getString("System.Diy_desc"));
		add(descLabel);
		descLabel.setBounds(20, 75, 80, 15);
		
		descField.setColumns(20);
		add(descField);
		descField.setBounds(100, 75, 200, 21);
		
		helpLabel.setText(LanguageLoader.getString("System.Diy_help"));
		add(helpLabel);
		helpLabel.setBounds(20, 105, 200, 15);

	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(CrawlerDiyDataConfigBean t) {
		logger.info("填充页面控件数据");
		nameField.setText(t.getName());
		valueField.setText(t.getValue());
		descField.setText(t.getDesc());
	}
	
	public void initData(CrawlerDiyDataConfigBean t){
		if(t == null){
			t = new CrawlerDiyDataConfigBean();
		}
		fillComponentData(t);
	}

}
