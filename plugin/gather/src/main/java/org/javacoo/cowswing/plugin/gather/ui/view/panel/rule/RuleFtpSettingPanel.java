/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.rule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JComboBox;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.cache.ICowSwingCacheManager;
import org.javacoo.cowswing.core.cache.support.CacheKeyConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerFtpConfigBean;
import org.javacoo.cowswing.plugin.gather.ui.model.FtpComboBoxModel;
import org.javacoo.cowswing.ui.view.panel.AbstractContentPanel;
import org.springframework.stereotype.Component;

/**
 * FTP配置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-24 上午9:49:55
 * @version 1.0
 */
@Component("ruleFtpSettingPanel")
public class RuleFtpSettingPanel extends AbstractContentPanel<CrawlerFtpConfigBean>{

	private static final long serialVersionUID = 1L;

	/**缓存管理*/
	@Resource(name="cowSwingCacheManager")
	private ICowSwingCacheManager crawlerCacheManager;
	
	/**是否使用FTP单选按钮*/
	private javax.swing.JRadioButton useFtpYesButton;
	/**是否使用FTP单选按钮*/
	private javax.swing.JRadioButton useFtpNoButton;
	/**是否使用FTP单选按钮组*/
	private javax.swing.ButtonGroup useFtpButtonGroup;
	/**是否使用FTP标签*/
	private javax.swing.JLabel useFtpLabel;
	
	/**FTP标签*/
	private javax.swing.JLabel ftpLabel;
	/**FTP下拉*/
	private JComboBox ftpCombo;
	
	/**服务器端目录输入框*/
	private javax.swing.JTextField ftpDirPathField;
	/**服务器端目录标签*/
	private javax.swing.JLabel ftpDirPathLabel;
	
	private String useFtpSelectValue = Constant.NO;

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected CrawlerFtpConfigBean populateData() {
		CrawlerFtpConfigBean crawlerFtpConfigBean = (CrawlerFtpConfigBean) ftpCombo.getSelectedItem();
		if(null == crawlerFtpConfigBean){
			crawlerFtpConfigBean = new CrawlerFtpConfigBean();
		}
		crawlerFtpConfigBean.setUseFtpFlag(this.useFtpSelectValue);
		crawlerFtpConfigBean.setFtpDirPath(ftpDirPathField.getText());
		return crawlerFtpConfigBean;
	}
	protected void initActionListener(){
		class UseFtpBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (useFtpYesButton.isSelected()) {
					useFtpSelectValue = Constant.YES;
					changeUseFtpInputState(true);
	             } else {
	                useFtpSelectValue = Constant.NO;
	                changeUseFtpInputState(false);
	             }
			}
		}
		useFtpYesButton.addActionListener(new UseFtpBtnActionAdapter());
		useFtpNoButton.addActionListener(new UseFtpBtnActionAdapter());
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		useFtpLabel = new javax.swing.JLabel();
		useFtpYesButton = new javax.swing.JRadioButton();
		useFtpNoButton = new javax.swing.JRadioButton();
		useFtpButtonGroup = new javax.swing.ButtonGroup();
		
		useFtpLabel = new javax.swing.JLabel();
		
		ftpLabel = new javax.swing.JLabel();
	
		ftpCombo = new JComboBox(new FtpComboBoxModel());
		
		
		ftpDirPathLabel = new javax.swing.JLabel();
		ftpDirPathField = new javax.swing.JTextField();
		
		
		
		
		useFtpLabel.setText(LanguageLoader.getString("RuleContentSetting.useFtp"));
		add(useFtpLabel);
		useFtpLabel.setBounds(20, 15, 250, 15);
		useFtpYesButton.setText(LanguageLoader.getString("Common.yes"));
		useFtpNoButton.setText(LanguageLoader.getString("Common.no"));
		useFtpNoButton.setSelected(true);
		useFtpSelectValue = Constant.NO;
		useFtpYesButton.setBackground(null);
		useFtpNoButton.setBackground(null);
		useFtpButtonGroup.add(useFtpYesButton);
		useFtpButtonGroup.add(useFtpNoButton);
		add(useFtpYesButton);
		add(useFtpNoButton);
		useFtpYesButton.setBounds(130, 15, 70, 21);
		useFtpNoButton.setBounds(210, 15, 70, 21);
		
		
		ftpLabel.setText(LanguageLoader.getString("RuleContentSetting.ftp"));
		add(ftpLabel);
		
		ftpLabel.setBounds(340, 15, 250, 15);
		ftpCombo.setBounds(420, 15, 200, 20);
		add(ftpCombo);
		
		
		ftpDirPathLabel.setText(LanguageLoader.getString("RuleContentSetting.ftpDirPath"));
		add(ftpDirPathLabel);
		ftpDirPathLabel.setBounds(20, 45, 250, 15);
		
		ftpDirPathField.setColumns(20);
		add(ftpDirPathField);
		ftpDirPathField.setBounds(130, 45, 490, 21);
		
	}
	public void initData(CrawlerFtpConfigBean t){
		if(null == t){
			t = new CrawlerFtpConfigBean();
		}
		fillComponentData(t);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(CrawlerFtpConfigBean t) {
		@SuppressWarnings("unchecked")
		List<CrawlerFtpConfigBean> resultList = (List<CrawlerFtpConfigBean>) this.crawlerCacheManager.getValue(CacheKeyConstant.CACHE_KEY_FTP);
		if(CollectionUtils.isEmpty(resultList)){
			resultList = (List<CrawlerFtpConfigBean>)crawlerCacheManager.loadCacheByKey(CacheKeyConstant.CACHE_KEY_FTP);
		}
		ftpCombo.setModel(new FtpComboBoxModel(resultList));
		ftpCombo.repaint();
		
		if(null != t){
			ftpCombo.setSelectedItem(t);
		}else{
			ftpCombo.setSelectedItem("");
		}
		if(Constant.YES.equals(t.getUseFtpFlag())){
			useFtpYesButton.setSelected(true);
			useFtpSelectValue = Constant.YES;
			changeUseFtpInputState(true);
		}else{
			useFtpNoButton.setSelected(true);
			useFtpSelectValue = Constant.NO;
			changeUseFtpInputState(false);
		}
		if(StringUtils.isNotBlank(t.getFtpDirPath())){
			ftpDirPathField.setText(t.getFtpDirPath());
		}else{
			ftpDirPathField.setText("");
		}
	}
	/**
	 * 改变FTP配置相关选项状态
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-24 上午11:10:29
	 * @version 1.0
	 * @exception 
	 * @param b
	 */
	private void changeUseFtpInputState(boolean b){
		ftpLabel.setEnabled(b);
		ftpCombo.setEnabled(b);
		ftpDirPathField.setEnabled(b);
		ftpDirPathLabel.setEnabled(b);
	}

	/**
	 * @param crawlerCacheManager the crawlerCacheManager to set
	 */
	public void setCrawlerCacheManager(ICowSwingCacheManager crawlerCacheManager) {
		this.crawlerCacheManager = crawlerCacheManager;
	}
    
}
