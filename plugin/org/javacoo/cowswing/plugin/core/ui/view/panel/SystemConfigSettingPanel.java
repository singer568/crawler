/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.view.panel;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Config;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.utils.PropertiesHelper;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.plugin.core.service.beans.SystemConfigBean;
import org.javacoo.cowswing.ui.listener.IntegerVerifier;
import org.javacoo.cowswing.ui.view.panel.AbstractContentSettingPanel;
import org.javacoo.cowswing.ui.view.panel.IPage;
import org.springframework.stereotype.Component;

/**
 * 系统参数配置设置
 *@author DuanYong
 *@since 2013-3-11下午3:25:17
 *@version 1.0
 */
@Component("systemConfigSettingPanel")
public class SystemConfigSettingPanel extends AbstractContentSettingPanel<SystemConfigBean> implements IPage{

	private static final long serialVersionUID = 1L;
	protected JComponent centerPane;
	/**主窗口宽输入框*/
	private javax.swing.JTextField frameWidthField;
	/**主窗口宽标签*/
	private javax.swing.JLabel frameWidthLabel;
	/**主窗口高输入框*/
	private javax.swing.JTextField frameHeightField;
	/**主窗口高标签*/
	private javax.swing.JLabel frameHeightLabel;
	/**是否显示广告单选按钮*/
	private javax.swing.JRadioButton showAdvertisementYesButton;
	/**是否显示广告单选按钮*/
	private javax.swing.JRadioButton showAdvertisementNoButton;
	/**是否显示广告单选按钮组*/
	private javax.swing.ButtonGroup showAdvertisementButtonGroup;
	/**是否显示广告标签*/
	private javax.swing.JLabel showAdvertisementLabel;
	/**是否播放音乐单选按钮*/
	private javax.swing.JRadioButton showMusicYesButton;
	/**是否播放音乐单选按钮*/
	private javax.swing.JRadioButton showMusicNoButton;
	/**是否播放音乐单选按钮组*/
	private javax.swing.ButtonGroup showMusicButtonGroup;
	/**是否播放音乐标签*/
	private javax.swing.JLabel showMusicLabel;
	/**欢迎音乐输入框*/
	private javax.swing.JTextField welocmeMusicNameField;
	/**欢迎音乐标签*/
	private javax.swing.JLabel welocmeMusicNameLabel;
	/**提示音乐输入框*/
	private javax.swing.JTextField msgMusicNameField;
	/**提示音乐标签*/
	private javax.swing.JLabel msgMusicNameLabel;
	/**广告地址输入框*/
	private javax.swing.JTextField advertisementPathField;
	/**广告地址标签*/
	private javax.swing.JLabel advertisementPathLabel;
	/**版本输入框*/
	private javax.swing.JTextField versionField;
	/**版本标签*/
	private javax.swing.JLabel versionLabel;
	/**
	 * 保存按钮
	 */
	private JButton saveButton;
	
	
	private String showAdvertisementValue;
	private String showMusicValue;
	
	public SystemConfigSettingPanel(){
		super();
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected SystemConfigBean populateData() {
		SystemConfigBean systemConfigBean = new SystemConfigBean();
		systemConfigBean.setAdvertisementPath(advertisementPathField.getText());
		if(StringUtils.isNotBlank(frameHeightField.getText())){
			systemConfigBean.setFrameHeight(Integer.valueOf(frameHeightField.getText()));
		}
		if(StringUtils.isNotBlank(frameWidthField.getText())){
			systemConfigBean.setFrameWidth(Integer.valueOf(frameWidthField.getText()));
		}
		systemConfigBean.setMsgMusicName(msgMusicNameField.getText());
		systemConfigBean.setShowAdvertisement(showAdvertisementValue);
		systemConfigBean.setShowMusic(showMusicValue);
		systemConfigBean.setVersion(versionField.getText());
		systemConfigBean.setWelocmeMusicName(welocmeMusicNameField.getText());
		
		return systemConfigBean;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		frameWidthLabel = new javax.swing.JLabel();
		frameWidthField = new javax.swing.JTextField();
		
		frameHeightLabel = new javax.swing.JLabel();
		frameHeightField = new javax.swing.JTextField();
		
		showAdvertisementLabel = new javax.swing.JLabel();
		showAdvertisementYesButton = new javax.swing.JRadioButton();
		showAdvertisementNoButton = new javax.swing.JRadioButton();
		showAdvertisementButtonGroup = new javax.swing.ButtonGroup();
		
		
		showMusicLabel = new javax.swing.JLabel();
		showMusicYesButton = new javax.swing.JRadioButton();
		showMusicNoButton = new javax.swing.JRadioButton();
		showMusicButtonGroup = new javax.swing.ButtonGroup();
		
		welocmeMusicNameLabel = new javax.swing.JLabel();
		welocmeMusicNameField = new javax.swing.JTextField();
		
		msgMusicNameLabel = new javax.swing.JLabel();
		msgMusicNameField = new javax.swing.JTextField();
		
		advertisementPathLabel = new javax.swing.JLabel();
		advertisementPathField = new javax.swing.JTextField();
		
		versionLabel = new javax.swing.JLabel();
		versionField = new javax.swing.JTextField();
		versionField.setEditable(false);
		
		
		
		
		frameWidthLabel.setText(LanguageLoader.getString("Core.frameWidth"));
	
		frameWidthField.setInputVerifier(new IntegerVerifier(this, false, 100, 1000));
		
		
		frameHeightLabel.setText(LanguageLoader.getString("Core.frameHeight"));

		frameHeightField.setInputVerifier(new IntegerVerifier(this, false, 100, 1000));
		
		
		showAdvertisementLabel.setText(LanguageLoader.getString("Core.showAdvertisement"));
		
		showAdvertisementYesButton.setText(LanguageLoader.getString("Common.yes"));
		showAdvertisementNoButton.setText(LanguageLoader.getString("Common.no"));
		showAdvertisementNoButton.setSelected(true);
		showAdvertisementValue = Constant.NO;
		showAdvertisementYesButton.setBackground(null);
		showAdvertisementNoButton.setBackground(null);
		showAdvertisementButtonGroup.add(showAdvertisementYesButton);
		showAdvertisementButtonGroup.add(showAdvertisementNoButton);
		
		showMusicLabel.setText(LanguageLoader.getString("Core.showMusic"));
		showMusicYesButton.setText(LanguageLoader.getString("Common.yes"));
		showMusicNoButton.setText(LanguageLoader.getString("Common.no"));
		showMusicNoButton.setSelected(true);
		showMusicValue = Constant.NO;
		showMusicYesButton.setBackground(null);
		showMusicNoButton.setBackground(null);
		showMusicButtonGroup.add(showMusicYesButton);
		showMusicButtonGroup.add(showMusicNoButton);
		
		
		welocmeMusicNameLabel.setText(LanguageLoader.getString("Core.welocmeMusicName"));

		
		
		msgMusicNameLabel.setText(LanguageLoader.getString("Core.msgMusicName"));

		
		
		
		advertisementPathLabel.setText(LanguageLoader.getString("Core.advertisementPath"));

		
		versionLabel.setText(LanguageLoader.getString("Core.version"));
		
		class SyetemConfigSettingAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public SyetemConfigSettingAction(){
				super(LanguageLoader.getString("Common.finish"),ImageLoader.getImageIcon("CrawlerResource.commonSave"));
			}
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemConfigBean systemConfigBean = getData();
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_AD_PATH, systemConfigBean.getAdvertisementPath());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_FRAME_WIDTH, systemConfigBean.getFrameWidth()+"");
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_FRAME_HEIGHT, systemConfigBean.getFrameHeight()+"");
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_MSG, systemConfigBean.getMsgMusicName());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_AD, systemConfigBean.getShowAdvertisement());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC, systemConfigBean.getShowMusic());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_VERSION, systemConfigBean.getVersion());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_WELCOME, systemConfigBean.getWelocmeMusicName());
				
				PropertiesHelper.writeMapProperties(Config.CRAWLER_CONFIG_RESOURCES_PATH_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT), Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT), "");
			}

		}
		saveButton = new JButton(new SyetemConfigSettingAction());
		
	}
	
	@Override
	protected JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			centerPane.setLayout(gridbag);

			int top = 0;
			int left = 25;
			int bottom = 5;
			int right = 5;
			Insets inserts = new Insets(top, left, bottom, right);
			
			// 字段名称
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.insets = inserts;
			c.anchor = GridBagConstraints.NORTH;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			// 线程数
			gridbag.setConstraints(frameWidthLabel, c);
			centerPane.add(frameWidthLabel);
			
			c.gridx = 1;
			c.weightx = 0.1;
			c.gridwidth = 2;
			gridbag.setConstraints(frameWidthField, c);
			centerPane.add(frameWidthField);
			
			
			//超时时间
			c.gridx = 0;
			c.gridy = 1;
			gridbag.setConstraints(frameHeightLabel, c);
			centerPane.add(frameHeightLabel);

			
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(frameHeightField, c);
			centerPane.add(frameHeightField);
			//资源保存根目录
			c.gridx = 0;
			c.gridy = 2;
			gridbag.setConstraints(showAdvertisementLabel, c);
			centerPane.add(showAdvertisementLabel);
			
			c.gridx = 1;
			c.gridwidth = 1;
			gridbag.setConstraints(showAdvertisementYesButton, c);
			centerPane.add(showAdvertisementYesButton);
			
			c.gridx = 2;
			c.gridwidth = 1;
			gridbag.setConstraints(showAdvertisementNoButton, c);
			centerPane.add(showAdvertisementNoButton);
			
			//资源保存相对目录
			c.gridx = 0;
			c.gridy = 3;
			gridbag.setConstraints(showMusicLabel, c);
			centerPane.add(showMusicLabel);
			
			c.gridx = 1;
			c.gridwidth = 1;
			gridbag.setConstraints(showMusicYesButton, c);
			centerPane.add(showMusicYesButton);
			
			c.gridx = 2;
			c.gridwidth = 1;
			gridbag.setConstraints(showMusicNoButton, c);
			centerPane.add(showMusicNoButton);
			
			//采集资源类型
			c.gridx = 0;
			c.gridy = 4;
			gridbag.setConstraints(welocmeMusicNameLabel, c);
			centerPane.add(welocmeMusicNameLabel);
			
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(welocmeMusicNameField, c);
			centerPane.add(welocmeMusicNameField);
			
			//采集媒体资源类型
			c.gridx = 0;
			c.gridy = 5;
			gridbag.setConstraints(msgMusicNameLabel, c);
			centerPane.add(msgMusicNameLabel);
			
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(msgMusicNameField, c);
			centerPane.add(msgMusicNameField);
			
			//是否重命名
			c.gridx = 0;
			c.gridy = 6;
			gridbag.setConstraints(advertisementPathLabel, c);
			centerPane.add(advertisementPathLabel);
			
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(advertisementPathField, c);
			centerPane.add(advertisementPathField);
			
			//默认替换字符
			c.gridx = 0;
			c.gridy = 8;
			gridbag.setConstraints(versionLabel, c);
			centerPane.add(versionLabel);
			
			c.gridx = 1;
			c.gridwidth = 2;
			gridbag.setConstraints(versionField, c);
			centerPane.add(versionField);
			
			
			
			// 空白行
			c.gridx = 0;
			c.gridy = 9;
			c.weighty = 1.0;
			centerPane.add(new JLabel(), c);
		}
		return centerPane;
	}
	
	@Override
	protected JComponent getBottomPane() {
		getButtonBar();
		buttonBar.add(saveButton);
		return buttonBar;
	}
	
	
	public void initData(SystemConfigBean systemConfigBean){
		if(null == systemConfigBean){
			systemConfigBean = new SystemConfigBean();
		}
		systemConfigBean.setAdvertisementPath(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_AD_PATH));
		systemConfigBean.setFrameHeight(Integer.valueOf(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_FRAME_WIDTH)));
		systemConfigBean.setFrameWidth(Integer.valueOf(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_FRAME_HEIGHT)));
		systemConfigBean.setMsgMusicName(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_MSG));
		systemConfigBean.setShowAdvertisement(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_AD));
		systemConfigBean.setShowMusic(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC));
		systemConfigBean.setVersion(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_VERSION));
		systemConfigBean.setWelocmeMusicName(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_WELCOME));
		
		fillComponentData(systemConfigBean);
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(SystemConfigBean t) {
		frameWidthField.setText(t.getFrameWidth()+"");
		frameHeightField.setText(t.getFrameHeight()+"");
		welocmeMusicNameField.setText(t.getWelocmeMusicName());
		msgMusicNameField.setText(t.getMsgMusicName());
		advertisementPathField.setText(t.getAdvertisementPath());
		versionField.setText(t.getVersion());
		if(Constant.YES.equals(t.getShowAdvertisement())){
			showAdvertisementYesButton.setSelected(true);
			showAdvertisementValue = Constant.YES;
			changeAdState(true);
		}else{
			showAdvertisementNoButton.setSelected(true);
			showAdvertisementValue = Constant.NO;
			changeAdState(false);
		}
		if(Constant.YES.equals(t.getShowMusic())){
			showMusicYesButton.setSelected(true);
			showMusicValue = Constant.YES;
			changeMusicState(true);
		}else{
			showMusicNoButton.setSelected(true);
			showMusicValue = Constant.NO;
			changeMusicState(false);
		}
	}
	
	private void changeMusicState(boolean b){
		welocmeMusicNameField.setEnabled(b);
		msgMusicNameField.setEnabled(b);
		msgMusicNameLabel.setEnabled(b);
		welocmeMusicNameLabel.setEnabled(b);
	}
	private void changeAdState(boolean b){
		advertisementPathField.setEnabled(b);
		advertisementPathLabel.setEnabled(b);
	}
	protected void initActionListener(){
		class ShowAdvertisementBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (showAdvertisementYesButton.isSelected()) {
					 showAdvertisementValue = Constant.YES;
					 changeAdState(true);
	                } else if (showAdvertisementNoButton.isSelected()) {
	                	showAdvertisementValue = Constant.NO;
	                	changeAdState(false);
	                }
			}
		}
		showAdvertisementYesButton.addActionListener(new ShowAdvertisementBtnActionAdapter());
		showAdvertisementNoButton.addActionListener(new ShowAdvertisementBtnActionAdapter());
		
		class ShowMusicBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (showMusicYesButton.isSelected()) {
					 showMusicValue = Constant.YES;
					 changeMusicState(true);
	                } else if (showMusicNoButton.isSelected()) {
	                	showMusicValue = Constant.NO;
	                	changeMusicState(false);
	                }
			}
		}
		showMusicYesButton.addActionListener(new ShowMusicBtnActionAdapter());
		showMusicNoButton.addActionListener(new ShowMusicBtnActionAdapter());
		
		
			
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#getPageName()
	 */
	@Override
	public String getPageName() {
		return LanguageLoader.getString("Core.config");
	}

	

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#showData(int, int)
	 */
	@Override
	public void showData(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub
		
	}
}
