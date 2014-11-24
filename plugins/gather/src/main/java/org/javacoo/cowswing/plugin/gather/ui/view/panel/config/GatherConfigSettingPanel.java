/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.javacoo.cowswing.base.constant.Config;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.utils.PropertiesHelper;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.plugin.gather.service.beans.GatherConfigSettingBean;
import org.javacoo.cowswing.ui.view.panel.AbstractContentSettingPanel;
import org.springframework.stereotype.Component;

/**
 * 采集参数设置
 *@author DuanYong
 *@since 2013-3-12上午10:11:46
 *@version 1.0
 */
@Component("gatherConfigSettingPanel")
public class GatherConfigSettingPanel extends AbstractContentSettingPanel<GatherConfigSettingBean>{
	private static final long serialVersionUID = 1L;
	protected JComponent centerPane;
	/**开启线程数Label*/
	private JLabel threadNumLabel;
	/**开启线程数Text*/
	private JTextField threadNumTextField;
	/**超时时间Label*/
	private JLabel taskTimeOutLabel;
	/**超时时间Text*/
	private JTextField taskTimeOutTextField;
	/**资源保存根目录Label*/
	private JLabel resSaveRootPathLabel;
	/**资源保存根目录Text*/
	private JTextField resSaveRootPathTextField;
	/**资源保存相对目录Label*/
	private JLabel resSavePathLabel;
	/**资源保存相对目录Text*/
	private JTextField resSavePathTextField;
	/**允许采集进来的外部资源类型Label*/
	private JLabel extractResTypeLabel;
	/**允许采集进来的外部资源类型Text*/
	private JTextField extractResTypeTextField;
	/**允许采集进来的外部媒体资源类型Label*/
	private JLabel extractMediaResTypeLabel;
	/**允许采集进来的外部媒体资源类型Text*/
	private JTextField extractMediaResTypeTextField;
	/**资源是否重命名Label*/
	private JLabel replaceResNameLabel;
	/**资源是否重命名CheckBox*/
	private JCheckBox replaceResNameCheckBox;
	/**默认替换字符Label*/
	private JLabel defaultWordsLabel;
	/**默认替换字符Text*/
	private JTextField defaultWordsTextField;
	/**代理服务器MAP列表Label*/
	private JLabel proxyServerListLabel;
	/**代理服务器MAP列表panel*/
	private JPanel proxyServerListPanel;
	/**代理服务器MAP列表Area*/
	private JTextArea proxyServerListTextArea;
	/**默认全局替换字符Label*/
	private JLabel defaultCommonReplaceWordsLabel;
	/**默认全局替换字符panel*/
	private JPanel defaultCommonReplaceWordsPanel;
	/**默认全局替换字符Area*/
	private JTextArea defaultCommonReplaceWordsTextArea;
	

	/**默认替换字符Label*/
	private JLabel acceptLabel;
	/**默认替换字符Text*/
	private JTextField acceptTextField;
	/**默认替换字符Label*/
	private JLabel acceptLanguageLabel;
	/**默认替换字符Text*/
	private JTextField acceptLanguageTextField;
	/**默认替换字符Label*/
	private JLabel acceptCharsetLabel;
	/**默认替换字符Text*/
	private JTextField acceptCharsetTextField;
	/**默认替换字符Label*/
	private JLabel keepAliveLabel;
	/**默认替换字符Text*/
	private JTextField keepAliveTextField;
	/**默认替换字符Label*/
	private JLabel connectionLabel;
	/**默认替换字符Text*/
	private JTextField connectionTextField;
	/**默认替换字符Label*/
	private JLabel cacheControlLabel;
	/**默认替换字符Text*/
	private JTextField cacheControlTextField;
	/**默认替换字符Label*/
	private JLabel userAgentLabel;
	/**默认全局替换字符panel*/
	private JPanel userAgentPanel;
	/**默认替换字符Text*/
	private JTextArea userAgentTextArea;
	/**
	 * 保存按钮
	 */
	private JButton saveButton;
	
	private String replaceResNameValue;

	public GatherConfigSettingPanel(){
		super();
	}
	@Override
	protected JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			centerPane.setLayout(gridbag);

			int top = 0;
			int left = 5;
			int bottom = 5;
			int right = 5;
			Insets inserts = new Insets(top, left, bottom, right);
			
			// 字段名称
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.insets = inserts;
			c.anchor = GridBagConstraints.SOUTHEAST;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			// 线程数
			gridbag.setConstraints(threadNumLabel, c);
			centerPane.add(threadNumLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(threadNumTextField, c);
			centerPane.add(threadNumTextField);
			
			//添加空格
//			c.gridx = 1;
//			centerPane.add(new JLabel(), c);
//			c.gridx = 2;
//			centerPane.add(new JLabel(), c);
//			c.gridx = 3;
//			centerPane.add(new JLabel(), c);
//			c.gridx = 4;
//			centerPane.add(new JLabel(), c);
			
			//超时时间
			c.gridx = 2;
			gridbag.setConstraints(taskTimeOutLabel, c);
			centerPane.add(taskTimeOutLabel);

			
			c.gridx = 3;
			gridbag.setConstraints(taskTimeOutTextField, c);
			centerPane.add(taskTimeOutTextField);
			
			//资源保存根目录
			c.gridx = 0;
			c.gridy = 1;
			gridbag.setConstraints(resSaveRootPathLabel, c);
			centerPane.add(resSaveRootPathLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(resSaveRootPathTextField, c);
			centerPane.add(resSaveRootPathTextField);
			
			//资源保存相对目录
			c.gridx = 2;
			gridbag.setConstraints(resSavePathLabel, c);
			centerPane.add(resSavePathLabel);
			
			c.gridx = 3;
			c.gridwidth = 1;
			gridbag.setConstraints(resSavePathTextField, c);
			centerPane.add(resSavePathTextField);
			
			//采集资源类型
			c.gridx = 0;
			c.gridy = 2;
			gridbag.setConstraints(extractResTypeLabel, c);
			centerPane.add(extractResTypeLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(extractResTypeTextField, c);
			centerPane.add(extractResTypeTextField);
			
			//采集媒体资源类型
			c.gridx = 2;
			gridbag.setConstraints(extractMediaResTypeLabel, c);
			centerPane.add(extractMediaResTypeLabel);
			
			c.gridx = 3;
			c.gridwidth = 1;
			gridbag.setConstraints(extractMediaResTypeTextField, c);
			centerPane.add(extractMediaResTypeTextField);
			
			//是否重命名
			c.gridx = 0;
			c.gridy = 3;
			gridbag.setConstraints(replaceResNameLabel, c);
			centerPane.add(replaceResNameLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(replaceResNameCheckBox, c);
			centerPane.add(replaceResNameCheckBox);
			
			//默认替换字符
			c.gridx = 2;
			gridbag.setConstraints(defaultWordsLabel, c);
			centerPane.add(defaultWordsLabel);
			
			c.gridx = 3;
			c.weightx = 1.0;
			c.gridwidth = 1;
			gridbag.setConstraints(defaultWordsTextField, c);
			centerPane.add(defaultWordsTextField);
			
			
			//代理地址集
			c.gridx = 0;
			c.gridy = 4;
			
			gridbag.setConstraints(proxyServerListLabel, c);
			centerPane.add(proxyServerListLabel);
			
			c.gridx = 1;
			c.weightx = 1.0;
			c.gridwidth = 3;
			c.gridheight = 3;
			gridbag.setConstraints(proxyServerListPanel, c);
			centerPane.add(proxyServerListPanel);
			
			
			//默认全局替换字符
			c.gridx = 0;
			c.gridy = 7;
			gridbag.setConstraints(defaultCommonReplaceWordsLabel, c);
			centerPane.add(defaultCommonReplaceWordsLabel);
			
			c.gridx = 1;
			c.weightx = 1.0;
			c.gridwidth = 3;
			gridbag.setConstraints(defaultCommonReplaceWordsPanel, c);
			centerPane.add(defaultCommonReplaceWordsPanel);
			

			c.gridwidth = 1;
			//是否重命名
			c.gridx = 0;
			c.gridy = 10;
			c.gridheight = 1;
			gridbag.setConstraints(acceptLabel, c);
			centerPane.add(acceptLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(acceptTextField, c);
			centerPane.add(acceptTextField);
			
			//默认替换字符
			c.gridx = 2;
			gridbag.setConstraints(acceptLanguageLabel, c);
			centerPane.add(acceptLanguageLabel);
			
			c.gridx = 3;
			c.weightx = 1.0;
			c.gridwidth = 1;
			gridbag.setConstraints(acceptLanguageTextField, c);
			centerPane.add(acceptLanguageTextField);
			
			
			
			//是否重命名
			c.gridx = 0;
			c.gridy = 11;
			gridbag.setConstraints(acceptCharsetLabel, c);
			centerPane.add(acceptCharsetLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(acceptCharsetTextField, c);
			centerPane.add(acceptCharsetTextField);
			
			//默认替换字符
			c.gridx = 2;
			gridbag.setConstraints(keepAliveLabel, c);
			centerPane.add(keepAliveLabel);
			
			c.gridx = 3;
			c.weightx = 1.0;
			c.gridwidth = 1;
			gridbag.setConstraints(keepAliveTextField, c);
			centerPane.add(keepAliveTextField);
			
			
			//是否重命名
			c.gridx = 0;
			c.gridy = 12;
			gridbag.setConstraints(connectionLabel, c);
			centerPane.add(connectionLabel);
			
			c.gridx = 1;
			gridbag.setConstraints(connectionTextField, c);
			centerPane.add(connectionTextField);
			
			//默认替换字符
			c.gridx = 2;
			gridbag.setConstraints(cacheControlLabel, c);
			centerPane.add(cacheControlLabel);
			
			c.gridx = 3;
			c.weightx = 1.0;
			c.gridwidth = 1;
			gridbag.setConstraints(cacheControlTextField, c);
			centerPane.add(cacheControlTextField);
			
			
			//默认全局替换字符
			c.gridx = 0;
			c.gridy = 13;
			c.gridheight = 3;
			gridbag.setConstraints(userAgentLabel, c);
			centerPane.add(userAgentLabel);
			
			c.gridx = 1;
			c.weightx = 1.0;
			c.gridwidth = 3;
			gridbag.setConstraints(userAgentPanel, c);
			centerPane.add(userAgentPanel);
			
			
			
			// 空白行
			c.gridx = 0;
			c.gridy = 16;
			c.weighty = 1.0;
			centerPane.add(new JLabel(), c);
		}
		return centerPane;
	}
	public void initData(GatherConfigSettingBean t){
		if(null == t){
			t = new GatherConfigSettingBean();
			t.setAccept(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT));
			t.setAcceptCharset(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_CHARSET));
			t.setAcceptLanguage(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_LANGUAGE));
			t.setCacheControl(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_CACHE_CONTROL));
			t.setConnection(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_CONNECTION));
			t.setDefaultCommonReplaceWords(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_COMMON_REPLACE_WORDS));
			t.setDefaultWords(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_WORDS));
			t.setExtractMediaResType(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_EXTRACT_MEDIA_RES_TYPE));
			t.setExtractResType(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_EXTRACT_RES_TYPE));
			t.setKeepAlive(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_KEEP_ALIVE));
			t.setProxyServerList(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_PROXY_SERVER_LIST));
			t.setReplaceResName(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_REPLACE_RES_NAME));
			t.setResSavePath(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_RES_SAVE_PATH));
			t.setResSaveRootPath(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_RES_SAVE_ROOT_PATH));
			t.setTaskTimeOut(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_TIME_OUT));
			t.setThreadNum(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_THREAD_NUM));
			t.setUserAgent(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_USER_AGENT));
		}
		fillComponentData(t);
	}
	@Override
	protected JComponent getBottomPane() {
		getButtonBar();
		buttonBar.add(saveButton);
		return buttonBar;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IPage#getPageName()
	 */
	@Override
	public String getPageName() {
		// TODO Auto-generated method stub
		return LanguageLoader.getString("Gather.config");
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentSettingPanel#populateData()
	 */
	@Override
	protected GatherConfigSettingBean populateData() {
		GatherConfigSettingBean t = new GatherConfigSettingBean();
		t.setAccept(acceptTextField.getText());
		t.setAcceptCharset(acceptLanguageTextField.getText());
		t.setAcceptLanguage(acceptLanguageTextField.getText());
		t.setCacheControl(cacheControlTextField.getText());
		t.setConnection(connectionTextField.getText());
		t.setUserAgent(userAgentTextArea.getText());
		t.setKeepAlive(keepAliveTextField.getText());
		
		t.setDefaultCommonReplaceWords(defaultCommonReplaceWordsTextArea.getText());
		t.setDefaultWords(defaultWordsTextField.getText());
		t.setExtractMediaResType(extractMediaResTypeTextField.getText());
		t.setExtractResType(extractResTypeTextField.getText());
		t.setProxyServerList(proxyServerListTextArea.getText());
		t.setReplaceResName(this.replaceResNameValue);
		t.setResSavePath(resSavePathTextField.getText());
		t.setResSaveRootPath(resSaveRootPathTextField.getText());
		t.setTaskTimeOut(taskTimeOutTextField.getText());
		t.setThreadNum(threadNumTextField.getText());
		
		return t;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentSettingPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		threadNumLabel = new JLabel(LanguageLoader.getString("Gather.threadNum"));
		threadNumTextField = new JTextField();
		taskTimeOutLabel = new JLabel(LanguageLoader.getString("Gather.taskTimeOut"));
		taskTimeOutTextField = new JTextField();
		resSaveRootPathLabel = new JLabel(LanguageLoader.getString("Gather.resSaveRootPath"));
		resSaveRootPathTextField = new JTextField();
		resSavePathLabel = new JLabel(LanguageLoader.getString("Gather.resSavePath"));
		resSavePathTextField = new JTextField();
		extractResTypeLabel = new JLabel(LanguageLoader.getString("Gather.extractResType"));
		extractResTypeTextField = new JTextField();
		extractMediaResTypeLabel = new JLabel(LanguageLoader.getString("Gather.extractMediaResType"));
		extractMediaResTypeTextField = new JTextField();
		replaceResNameLabel = new JLabel(LanguageLoader.getString("Gather.replaceResName"));
		replaceResNameCheckBox = new JCheckBox(LanguageLoader.getString("Yes"));
		defaultWordsLabel = new JLabel(LanguageLoader.getString("Gather.defaultWords"));
		defaultWordsTextField = new JTextField();
		

		proxyServerListLabel = new JLabel(LanguageLoader.getString("Gather.proxyServerList"));
		proxyServerListTextArea = new JTextArea(4,40);
		proxyServerListPanel = new JPanel(new BorderLayout()); 
		proxyServerListPanel.setPreferredSize(new Dimension(80, 60));
		proxyServerListTextArea.setLineWrap(true);
		proxyServerListTextArea.setWrapStyleWord(true);//激活断行不断字功能 
		proxyServerListPanel.add(new JScrollPane(proxyServerListTextArea));
		
		defaultCommonReplaceWordsLabel = new JLabel(LanguageLoader.getString("Gather.defaultCommonReplaceWords"));
		defaultCommonReplaceWordsTextArea = new JTextArea(4,40);
		
		defaultCommonReplaceWordsPanel = new JPanel(new BorderLayout()); 
		defaultCommonReplaceWordsTextArea.setLineWrap(true);
		defaultCommonReplaceWordsTextArea.setWrapStyleWord(true);//激活断行不断字功能 
		defaultCommonReplaceWordsPanel.add(new JScrollPane(defaultCommonReplaceWordsTextArea));
		
		
		acceptLabel = new JLabel(LanguageLoader.getString("Gather.accept"));
		acceptTextField = new JTextField();
		acceptLanguageLabel = new JLabel(LanguageLoader.getString("Gather.acceptLanguage"));
		acceptLanguageTextField = new JTextField();
		acceptCharsetLabel = new JLabel(LanguageLoader.getString("Gather.acceptCharset"));
		acceptCharsetTextField = new JTextField();
		keepAliveLabel = new JLabel(LanguageLoader.getString("Gather.keepAlive"));
		keepAliveTextField = new JTextField();
		connectionLabel = new JLabel(LanguageLoader.getString("Gather.connection"));
		connectionTextField = new JTextField();
		cacheControlLabel = new JLabel(LanguageLoader.getString("Gather.cacheControl"));
		cacheControlTextField = new JTextField();
		userAgentLabel = new JLabel(LanguageLoader.getString("Gather.userAgent"));
		userAgentTextArea = new JTextArea(4,40);
		
		userAgentPanel = new JPanel(new BorderLayout()); 
		userAgentTextArea.setLineWrap(true);
		userAgentTextArea.setWrapStyleWord(true);//激活断行不断字功能 
		userAgentPanel.add(new JScrollPane(userAgentTextArea));
		
		
		
		
		
		
		
		
		class GatherConfigSettingAction extends AbstractAction{
			private static final long serialVersionUID = 1L;
			public GatherConfigSettingAction(){
				super(LanguageLoader.getString("Common.finish"),ImageLoader.getImageIcon("CrawlerResource.commonSave"));
			}
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				GatherConfigSettingBean t = getData();
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_THREAD_NUM, t.getThreadNum());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_TIME_OUT, t.getTaskTimeOut());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_RES_SAVE_ROOT_PATH, t.getResSaveRootPath());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_RES_SAVE_PATH, t.getResSavePath());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_EXTRACT_RES_TYPE, t.getExtractResType());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_EXTRACT_MEDIA_RES_TYPE, t.getExtractMediaResType());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_REPLACE_RES_NAME, t.getReplaceResName());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_WORDS, t.getDefaultWords());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_PROXY_SERVER_LIST, t.getProxyServerList());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_DEFAULT_COMMON_REPLACE_WORDS, t.getDefaultCommonReplaceWords());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT, t.getAccept());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_CHARSET, t.getAcceptCharset());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_ACCEPT_LANGUAGE, t.getAcceptLanguage());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_CACHE_CONTROL, t.getCacheControl());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_CONNECTION, t.getConnection());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_USER_AGENT, t.getUserAgent());
				Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).put(Config.CRAWLER_CONFIG_KEY_CORE_KEEP_ALIVE, t.getKeepAlive());
				
				PropertiesHelper.writeMapProperties(Config.CRAWLER_CONFIG_RESOURCES_PATH_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE), Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE), "");
			}

		}
		saveButton = new JButton(new GatherConfigSettingAction());
		
		
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentSettingPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(GatherConfigSettingBean t) {
		threadNumTextField.setText(t.getThreadNum());
		taskTimeOutTextField.setText(t.getTaskTimeOut());
		resSaveRootPathTextField.setText(t.getResSaveRootPath());
		resSavePathTextField.setText(t.getResSavePath());
		extractResTypeTextField.setText(t.getExtractResType());
		extractMediaResTypeTextField.setText(t.getExtractMediaResType());
		if(Boolean.valueOf(t.getReplaceResName())){
			replaceResNameCheckBox.setSelected(true);
			this.replaceResNameValue = "true";
		}else{
			replaceResNameCheckBox.setSelected(false);
			this.replaceResNameValue = "false";
		}
		defaultWordsTextField.setText(t.getDefaultWords());
		proxyServerListTextArea.setText(t.getProxyServerList());
		defaultCommonReplaceWordsTextArea.setText(t.getDefaultCommonReplaceWords());
		
		acceptTextField.setText(t.getAccept());
		acceptLanguageTextField.setText(t.getAcceptLanguage());
		acceptCharsetTextField.setText(t.getAcceptCharset());
		keepAliveTextField.setText(t.getKeepAlive());
		connectionTextField.setText(t.getConnection());
		cacheControlTextField.setText(t.getCacheControl());
		userAgentTextArea.setText(t.getUserAgent());
		
	}

}
