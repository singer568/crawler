/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.view.panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.core.constants.Config;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.PropertiesHelper;
import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
import com.bjm.pms.crawler.webservice.manager.ManagerService;
import com.bjm.pms.crawler.webservice.manager.beans.VersionBean;

/**
 * 版本信息列表
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-1-2 下午2:45:36
 * @version 1.0
 */
@Component("versionInfoPanel")
public class VersionInfoPanel extends AbstractContentPanel<VersionBean>{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/**操作说明标签*/
	private javax.swing.JLabel operDescLabel;
	/**选择的模块类别标签*/
	private javax.swing.JLabel selectFileLabel;
	/**拖拽说明标签*/
	private javax.swing.JLabel dropLabel;
	/**版本信息列表*/
	private JList versionJList;
	/**版本信息Model*/
	private DefaultListModel versionListModel;
	/**版本信息*/
	private double version = Double.parseDouble(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_VERSION));

	/**操作说明标签*/
	private javax.swing.JLabel versionInfoLabel;
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected VersionBean populateData() {
		if(null != versionJList.getSelectedValue()){
			return (VersionBean)versionJList.getSelectedValue();
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		operDescLabel = new javax.swing.JLabel();
		operDescLabel.setText(LanguageLoader.getString("Core.version_operDesc"));
		add(operDescLabel);
		operDescLabel.setBounds(20, 15, 80, 15);
		
		
		dropLabel = new javax.swing.JLabel();
		dropLabel.setText(LanguageLoader.getString("Core.version_info"));
		add(dropLabel);
		dropLabel.setBounds(110, 15, 320, 15);
		
		
		selectFileLabel = new javax.swing.JLabel();
		selectFileLabel.setText(LanguageLoader.getString("Core.version_list"));
		add(selectFileLabel);
		selectFileLabel.setBounds(20, 45, 80, 15);
		
		
		versionListModel = new DefaultListModel();
		versionJList = new JList(versionListModel);
		JScrollPane fileListJScrollPane = new JScrollPane(versionJList);
		add(fileListJScrollPane);
		fileListJScrollPane.setBounds(110, 45, 320, 130);
		
		
		versionInfoLabel = new javax.swing.JLabel();
		versionInfoLabel.setText("");
		add(versionInfoLabel);
		versionInfoLabel.setBounds(110, 185, 320, 15);
	}
	protected void initActionListener(){
		versionJList.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e) {
				 if(null != versionJList.getSelectedValue()){
					 VersionBean msgBean =  (VersionBean)versionJList.getSelectedValue();
						versionInfoLabel.setText(msgBean.getInfo());
					}
			 }
		});
	}
	public void initData(VersionBean t){
		fillComponentData(t);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(VersionBean t) {
		versionListModel.clear();
		versionInfoLabel.setText("");
		try{
			ManagerService service = (ManagerService)CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_WEBSERVICE);
			//List<VersionBean> versionList = service.getVersionList();
			VersionBean v = service.getVersion(new VersionBean());
			//if(CollectionUtils.isNotEmpty(versionList)){
				//for(VersionBean v:versionList){
					logger.info("=====================当前版本："+v.toString());
					int result = Double.compare(version, v.getVersion());
					logger.info("=====================版本对比结果："+result);
					//如果当前版本小于其他客户端版本
					if(result < 0){
						if(!versionListModel.contains(v)){
							versionListModel.addElement(v);
						}
					}
				//}
			//}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(double version) {
		this.version = version;
		Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).put(Config.CRAWLER_CONFIG_KEY_INIT_VERSION, String.valueOf(version));
		
		PropertiesHelper.writeMapProperties(Config.CRAWLER_CONFIG_RESOURCES_PATH_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT), Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT), "");
	
	}
	
}
