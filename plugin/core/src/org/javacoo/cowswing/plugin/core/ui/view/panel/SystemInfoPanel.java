/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.utils.ImageManager;
import org.javacoo.cowswing.base.utils.PropertiesHelper;
import org.javacoo.cowswing.core.context.CowSwingContextData;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.plugin.core.constant.CoreConstant;
import org.javacoo.cowswing.ui.view.dialog.ViewDialog;
import org.javacoo.cowswing.ui.view.panel.AbstractTabPanel;
import org.javacoo.cowswing.ui.widget.ModifiedFlowLayout;
import org.springframework.stereotype.Component;

/**
 * 系统信息panel
 * 
 * @author DuanYong
 * @since 2013-3-4下午10:34:53
 * @version 1.0
 */
@Component("systemInfoPanel")
public class SystemInfoPanel extends AbstractTabPanel{
	
	private static final long serialVersionUID = 1L;
	/**中部面板*/
	private JPanel centerPanel;
	/**插件面板*/
	private JPanel pluginsPanel;
	/**信息面板*/
	private JEditorPane  infoPanel;
	/**分隔面板*/
	private JSplitPane splitPanel;
	/**
	 * 详细信息页面
	 */
	@Resource(name="viewDialog")
	private ViewDialog viewDialog;
	
	@Resource(name="msgListPanel")
    private MsgListPanel msgListPanel;
	private Map<String,String[]> requiresMap = new HashMap<String,String[]>();
	public SystemInfoPanel() {
		super();
	}
	
	protected JComponent getCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(getSplitPanel());
		return centerPanel;
	
	}
	private JSplitPane getSplitPanel(){
		if(splitPanel == null){
			splitPanel = new JSplitPane();
			splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPanel.setOneTouchExpandable(true);
			splitPanel.setTopComponent(getContainerPanelPanel());
			//splitPanel.setBottomComponent(getInfoPanel());	
			msgListPanel.init();
			splitPanel.setBottomComponent(msgListPanel);	
			
			this.addComponentListener(new ComponentAdapter(){
	            public void componentResized(ComponentEvent e) {
	            	splitPanel.setDividerLocation(0.3);
	            }
	        }); 
		}
		
		return splitPanel;
	}
	
	private JComponent getContainerPanelPanel(){
		pluginsPanel = new JPanel(new ModifiedFlowLayout(FlowLayout.LEADING));
		initContent();
		return new JScrollPane(pluginsPanel);
	}

	private void initContent() {
		initRequiresMap();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Map<String, Map<String, String>> plugins = CowSwingContextData.getInstance().getPlugins();
				String key = "";
				for(Iterator<String> it = plugins.keySet().iterator();it.hasNext();){
					key = it.next();
					JPanel pluginPanel = new JPanel();
					pluginPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
					pluginPanel.setPreferredSize(new Dimension(100, 120));
					JLabel imageLabel = new JLabel(ImageManager.getImageIcon(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_LOGOIMAGEPATH)));
					pluginPanel.add(imageLabel);
					JLabel label = new JLabel(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_NAME));
					pluginPanel.add(label);
					if(Boolean.valueOf(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE))){
						JButton disableButton = new JButton(ImageLoader.getImageIcon("CrawlerResource.pluginDisabled"));
						disableButton.setToolTipText(LanguageLoader.getString("Plugins.disabled"));
						pluginPanel.add(disableButton);
						disableButton.addActionListener(createActionListener(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_PATH),plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_ID),Constant.NO));
					}else{
						JButton activeButton = new JButton(ImageLoader.getImageIcon("CrawlerResource.plugin"));
						activeButton.setToolTipText(LanguageLoader.getString("Plugins.active"));
						pluginPanel.add(activeButton);
						activeButton.addActionListener(createActionListener(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_PATH),plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_ID),Constant.YES));
					}
					JButton infoButton = new JButton(ImageLoader.getImageIcon("CrawlerResource.pluginGo"));
					infoButton.setToolTipText(LanguageLoader.getString("Plugins.go"));
					pluginPanel.add(infoButton);
					infoButton.addActionListener(createShowInfoActionListener(plugins.get(key)));
					pluginsPanel.add(pluginPanel);
				}
				pluginsPanel.repaint();
				pluginsPanel.doLayout();
			}
		});
	}
	
	private void initRequiresMap(){
		Map<String, Map<String, String>> plugins = CowSwingContextData.getInstance().getPlugins();
		String key = "";
		String[] tempValue = null;
		for(Iterator<String> it = plugins.keySet().iterator();it.hasNext();){
			key = it.next();
			if(StringUtils.isNotBlank(plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_REQUIRES))){
				tempValue = new String[3];
				tempValue[0] = plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_REQUIRES);
				tempValue[1] = plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE);
				tempValue[2] = plugins.get(key).get(CoreConstant.PLUGIN_PROPERTIES_KY_NAME);
				requiresMap.put(key, tempValue);
			}
		}
	}
	/**
	 * 取得依赖此插件的插件名称
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-5-26 下午6:33:58
	 * @version 1.0
	 * @exception 
	 * @param pluginId
	 * @return
	 */
	private String getRequirePlugins(String pluginId){
		String key = "";
		for(Iterator<String> it = requiresMap.keySet().iterator();it.hasNext();){
			key = it.next();
			//如果有引用此插件，且为激活状态的插件
			if(requiresMap.get(key)[0].contains(pluginId) && Constant.YES.equals(requiresMap.get(key)[1])){
				return requiresMap.get(key)[2];
			}
		}
		return "";
	}
	/**
	 * 取得此插件依赖的插件名称
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-4 下午9:04:01
	 * @version 1.0
	 * @exception 
	 * @param pluginId
	 * @return
	 */
	private String getPluginRequire(String pluginId){
		Map<String, Map<String, String>> plugins = CowSwingContextData.getInstance().getPlugins();
		String key = "";
		StringBuilder plugReq = new StringBuilder();
		for(Iterator<String> it = requiresMap.keySet().iterator();it.hasNext();){
			key = it.next();
			//如果找到当前插件,且有依赖的插件
			if(key.equals(pluginId) && StringUtils.isNotBlank(requiresMap.get(key)[0])){
				String[] reqPlugins = requiresMap.get(key)[0].split(",");
				for(String reqPluid : reqPlugins){
					//如果插件为被激活
					if(null != plugins.get(reqPluid) && !Boolean.valueOf(plugins.get(reqPluid).get(CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE))){
						plugReq.append(plugins.get(reqPluid).get(CoreConstant.PLUGIN_PROPERTIES_KY_NAME)).append(",");
					}
				}
			}
		}
		return plugReq.toString();
	}
	private ActionListener createActionListener(final String path,final String pluginId,final String active){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isOk = true;
				if(Constant.NO.equals(active)){
					String requires = getRequirePlugins(pluginId);
					if(StringUtils.isNotBlank(requires)){
						isOk = false;
						JOptionPane.showMessageDialog(null, LanguageLoader.getString("Plugins.hasRequiresTitle")+requires+LanguageLoader.getString("Plugins.hasRequires"), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
					}
				}else if(Constant.YES.equals(active)){
					String reqPlu = getPluginRequire(pluginId);
					if(StringUtils.isNotBlank(reqPlu)){
						isOk = false;
						JOptionPane.showMessageDialog(null, LanguageLoader.getString("Plugins.hasRequiresTitle")+reqPlu+LanguageLoader.getString("Plugins.reqPlugs"), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
					}
				}
				if(isOk){
					int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("Plugins.confirm"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
					if(result == 0){
						PropertiesHelper.writeProperties(Constant.SYSTEM_ROOT_PATH+path, CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE, active, "");
						JOptionPane.showMessageDialog(null, LanguageLoader.getString("Plugins.info"), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);	
					}
				}
			}
		};
	}
	private ActionListener createShowInfoActionListener(final Map<String, String> infoMap){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StringBuilder tempInfo = new StringBuilder();
				tempInfo.append("插件ID：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_ID)).append("<br />");
				tempInfo.append("插件名称：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_NAME)).append("<br />");
				tempInfo.append("插件版本：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_VERSION)).append("<br />");
				tempInfo.append("插件提供者：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_PROVIDER)).append("<br />");
				tempInfo.append("插件配置文件路径：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_BEANXMLPATH)).append("<br />");
				tempInfo.append("插件依赖插件ID：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_REQUIRES)).append("<br />");
				tempInfo.append("插件是否激活：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_ACTIVE)).append("<br />");
				tempInfo.append("插件LOGO路径：" + infoMap.get(CoreConstant.PLUGIN_PROPERTIES_KY_LOGOIMAGEPATH)).append("<br />");
				viewDialog.setViewSize(400,200);
				viewDialog.showContent(tempInfo.toString());
				viewDialog.setVisible(true);
			}
		};
	}
	private JComponent getInfoPanel(){
		infoPanel = new JEditorPane();
		infoPanel.setEditable(false);
		infoPanel.setContentType("text/html");
		initInfo(infoPanel);
		return new JScrollPane(infoPanel);
	}
	
	private void initInfo(JEditorPane containerPanel){
		StringBuilder tempInfo = new StringBuilder();
		Properties props = System.getProperties();
		tempInfo.append("============================Java的运行环境==========================").append("<br />");
		tempInfo.append("Java的运行环境版本：" + props.getProperty("java.version")).append("<br />");
		tempInfo.append("Java的运行环境供应商：" + props.getProperty("java.vendor")).append("<br />");
		tempInfo.append("Java供应商的URL：" + props.getProperty("java.vendor.url")).append("<br />");
		tempInfo.append("Java的安装路径：" + props.getProperty("java.home")).append("<br />");
		tempInfo.append("Java的虚拟机规范版本："+ props.getProperty("java.vm.specification.version")).append("<br />");
		tempInfo.append("Java的虚拟机规范供应商："+ props.getProperty("java.vm.specification.vendor")).append("<br />");
		tempInfo.append("Java的虚拟机规范名称："+ props.getProperty("java.vm.specification.name")).append("<br />");
		tempInfo.append("Java的虚拟机实现版本：" + props.getProperty("java.vm.version")).append("<br />");
		tempInfo.append("Java的虚拟机实现供应商：" + props.getProperty("java.vm.vendor")).append("<br />");
		tempInfo.append("Java的虚拟机实现名称：" + props.getProperty("java.vm.name")).append("<br />");
		tempInfo.append("Java运行时环境规范版本："+ props.getProperty("java.specification.version")).append("<br />");
		tempInfo.append("Java运行时环境规范供应商："+ props.getProperty("java.specification.vender")).append("<br />");
		tempInfo.append("Java运行时环境规范名称：" + props.getProperty("java.specification.name")).append("<br />");
		tempInfo.append("Java的类格式版本号：" + props.getProperty("java.class.version")).append("<br />");
		//tempInfo.append("Java的类路径：" + props.getProperty("java.class.path")).append("<br />");
		//tempInfo.append("加载库时搜索的路径列表：" + props.getProperty("java.library.path")).append("<br />");
		//tempInfo.append("默认的临时文件路径：" + props.getProperty("java.io.tmpdir")).append("<br />");
		//tempInfo.append("一个或多个扩展目录的路径：" + props.getProperty("java.ext.dirs")).append("<br />");
		tempInfo.append("============================操作系统信息==========================").append("<br />");
		tempInfo.append("操作系统的名称：" + props.getProperty("os.name")).append("<br />");
		tempInfo.append("操作系统的构架：" + props.getProperty("os.arch")).append("<br />");
		tempInfo.append("操作系统的版本：" + props.getProperty("os.version")).append("<br />");
		tempInfo.append("用户的账户名称：" + props.getProperty("user.name")).append("<br />");
		tempInfo.append("用户的主目录：" + props.getProperty("user.home")).append("<br />");
		tempInfo.append("用户的当前工作目录：" + props.getProperty("user.dir"));
		containerPanel.setText(tempInfo.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.
	 * event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.ITabPanel#getTanPanelName()
	 */
	@Override
	public String getTanPanelName() {
		return LanguageLoader.getString("Home.pluginsInfo");
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.ITabPanel#getTabPanelIndex()
	 */
	@Override
	public int getTabPanelIndex() {
		// TODO Auto-generated method stub
		return 0;
	}


}
