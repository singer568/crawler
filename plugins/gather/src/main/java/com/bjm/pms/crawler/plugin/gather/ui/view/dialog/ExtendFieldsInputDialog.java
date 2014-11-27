/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.dialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;



/**
 *@author DuanYong
 *@since 2013-3-4上午9:57:53
 *@version 1.0
 */
@Component("extendFieldsInputDialog")
public class ExtendFieldsInputDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;
	// 窗体高度大小
	private static final int FRAME_WIDTH = 360;
	private static final int FRAME_HEIGHT = 320;
	
	private static int centerIndent = 5;

	/**字段名称*/
	private JLabel columnNameLabel;
	/**字段名称*/
	private JLabel columnNameValueLabel;
	/**字段类型*/
	private JLabel columnTypeValueLabel;
	/**字段值Label*/
	private JLabel columnValueLabel;
	/**字段值Text*/
	private JTextField columnValueText;
	/**说明Label*/
	private JLabel memoLabel;
	/**说明Text*/
	private JTextField memoText;
	/**是否随机时间Label*/
	private JLabel isRandomDateLabel;
	/**是否随机CheckBox*/
	private JCheckBox isRandomDatetCheckBox;

	/**时间格式Label*/
	private JLabel formatDateLabel;
	/**时间格式ComboBox*/
	private JComboBox formatDateComboBox;

	
	public ExtendFieldsInputDialog(){
		super(FRAME_WIDTH,FRAME_HEIGHT,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
			centerPane.setAlignmentX(TOP_ALIGNMENT);
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			centerPane.setLayout(gridbag);

			int top = 0;
			int left = 15;
			int bottom = 5;
			int right = 5;
			Insets inserts = new Insets(top, left, bottom, right);
			
			// 字段名称
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.insets = inserts;
			c.anchor = GridBagConstraints.SOUTHWEST;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			gridbag.setConstraints(getColumnNameLabel(), c);
			centerPane.add(getColumnNameLabel());

			// 字段值
			c.gridx = 1;
			gridbag.setConstraints(getColumnNameValueLabel(), c);
			centerPane.add(getColumnNameValueLabel());
			
			c.gridx = 2;
			gridbag.setConstraints(getColumnTypeValueLabel(), c);
			centerPane.add(getColumnTypeValueLabel());

			// 空白行
			c.gridx = 3;
			c.weightx = 1.0;
			centerPane.add(new JLabel(), c);
			c.weightx = 0.0;

			//字段值Label
			c.gridx = 0;
			c.gridy = 1;
			gridbag.setConstraints(getColumnValueLabel(), c);
			centerPane.add(getColumnValueLabel());

			//字段值Text
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(getColumnValueText(), c);
			centerPane.add(getColumnValueText());

			//说明
			c.gridx = 0;
			c.gridy = 2;
			gridbag.setConstraints(getMemoLabel(), c);
			centerPane.add(getMemoLabel());

			//说明Text
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(getMemoText(), c);
			centerPane.add(getMemoText());

			c.insets = new Insets(centerIndent, left, 5, 5);
			//随机日期
			c.gridx = 0;
			c.gridy = 3;
			gridbag.setConstraints(getIsRandomDateLabel(), c);
			centerPane.add(getIsRandomDateLabel());

			//随机日期CheckBox
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(getIsRandomDatetCheckBox(), c);
			centerPane.add(getIsRandomDatetCheckBox());

			c.insets = inserts;
			//随机日期格式
			c.gridx = 0;
			c.gridy = 4;
			gridbag.setConstraints(getFormatDateLabel(), c);
			centerPane.add(getFormatDateLabel());

			//随机下拉选择
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(getFormatDateComboBox(), c);
			centerPane.add(getFormatDateComboBox());
		}
		return centerPane;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	protected void initData(String type) {
		
	}
	@Override
	protected void initState() {
		

	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}

	
	public JButton getHelpButton() {
		return null;
	}
	public void setValue(String columnNameValue,String columnType){
		getColumnNameValueLabel().setText(columnNameValue);
		getColumnTypeValueLabel().setText(columnType);
	}
	public JLabel getColumnNameLabel() {
		if (columnNameLabel == null) {
			columnNameLabel = new JLabel(LanguageLoader.getString("RuleContentSetting.dataBaseTablePreColumn"));
		}
		return columnNameLabel;
	}
	public JLabel getColumnNameValueLabel() {
		if (columnNameValueLabel == null) {
			columnNameValueLabel = new JLabel();
		}
		return columnNameValueLabel;
	}
	
	public JLabel getColumnTypeValueLabel() {
		if (columnTypeValueLabel == null) {
			columnTypeValueLabel = new JLabel();
		}
		return columnTypeValueLabel;
	}
	public JLabel getColumnValueLabel() {
		if (columnValueLabel == null) {
			columnValueLabel = new JLabel(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnValueLabel"));
		}
		return columnValueLabel;
	}
	public JTextField getColumnValueText() {
		if (columnValueText == null) {
			columnValueText = new JTextField();
		}
		return columnValueText;
	}
	private JLabel getMemoLabel() {
		if (memoLabel == null) {
			memoLabel = new JLabel(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnMemo"));
		}
		return memoLabel;
	}

	private JTextField getMemoText() {
		if (memoText == null) {
			memoText = new JTextField();
		}
		return memoText;
	}
	public JLabel getIsRandomDateLabel() {
		if (isRandomDateLabel == null) {
			isRandomDateLabel = new JLabel(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnIsRandomDate"));
		}
		return isRandomDateLabel;
	}
	public JCheckBox getIsRandomDatetCheckBox() {
		if (isRandomDatetCheckBox == null) {
			isRandomDatetCheckBox = new JCheckBox(LanguageLoader
					.getString("Yes"));
			isRandomDatetCheckBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getFormatDateComboBox().setEnabled(
							isRandomDatetCheckBox.isSelected());
					getFormatDateComboBox().setSelectedIndex(-1);
				}
			});
		}
		return isRandomDatetCheckBox;
	}
	public JLabel getFormatDateLabel() {
		if (formatDateLabel == null) {
			formatDateLabel = new JLabel(LanguageLoader.getString("RuleContentSetting.dataBaseTableExtendColumnFormatDate"));
		}
		return formatDateLabel;
	}
	public JComboBox getFormatDateComboBox() {
		if (formatDateComboBox == null) {
			String[] data = new String[]{"yyyy","yyyy-MM","yyyy-MM-dd","yyyy-MM-dd HH:mm:ss.SSS"};
			formatDateComboBox = new JComboBox(data);
			formatDateComboBox.setEnabled(false);
		}
		return formatDateComboBox;
	}
	
	
	

}
