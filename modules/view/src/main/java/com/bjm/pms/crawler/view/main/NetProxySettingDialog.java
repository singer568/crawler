/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.core.cache.UserCacheManager;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;

/**
 * 网络代理设置
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-3 上午9:50:23
 * @version 1.0
 */
public class NetProxySettingDialog extends AbstractDialog implements
		CowSwingListener {
	private static final long serialVersionUID = 1L;
	private static NetProxySettingDialog instance;
	private NetProxySettingBean netSettingBean;
	/** 用户缓存管理类 */
	protected UserCacheManager userCacheManager = UserCacheManager.getInstance();
	private NetProxySettingDialog(){
		super(300,280,true);
		this.setUndecorated(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				checkValue();
				save();
			}
		});
		getFinishButton().setText("保存");
		getCancelButton().setText("取消");
	}
	
	/**
	 * @return the instance
	 */
	public static NetProxySettingDialog getInstance() {
		if(null == instance){
			instance = new NetProxySettingDialog();
		}
		return instance;
	}

	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = NetProxySettingPanel.getInstance();
		}
		return centerPane;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.cowswing.ui.view.dialog.AbstractDialog#
	 * finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		if(checkValue()){
			save();
			this.dispose();
		}
	}
	/**
	 * 校验参数
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-9-12 下午2:20:30
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	private boolean checkValue(){
		netSettingBean = NetProxySettingPanel.getInstance().getData();
		
		return true;
	}	
	/**
	 * 建立连接
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-9-12 下午2:37:59
	 * @version 1.0
	 * @exception
	 */
	private void save(){
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_HOST,netSettingBean.getProxyHost());
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_PORT,netSettingBean.getProxyPort());
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_USERNAME,netSettingBean.getUserName());
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_PASSWORD,netSettingBean.getPassword());
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_DOMAIN,netSettingBean.getDomain());
		userCacheManager.setValue(Constant.NET_PROXY_SETTING_ISUSED,netSettingBean.getIsUsed());
	}
	protected void initData(String type) {
		netSettingBean = new NetProxySettingBean();
		netSettingBean.setProxyHost(userCacheManager.getValue(Constant.NET_PROXY_SETTING_HOST));
		netSettingBean.setProxyPort(userCacheManager.getValue(Constant.NET_PROXY_SETTING_PORT));
		netSettingBean.setUserName(userCacheManager.getValue(Constant.NET_PROXY_SETTING_USERNAME));
		netSettingBean.setPassword(userCacheManager.getValue(Constant.NET_PROXY_SETTING_PASSWORD));
		netSettingBean.setDomain(userCacheManager.getValue(Constant.NET_PROXY_SETTING_DOMAIN));
		if (StringUtils.isNotBlank(userCacheManager.getValue(Constant.NET_PROXY_SETTING_ISUSED))) {
			netSettingBean.setIsUsed(userCacheManager.getValue(Constant.NET_PROXY_SETTING_ISUSED));
		} else {
			netSettingBean.setIsUsed("false");
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}
	/**
	 * 填充JTabbedPane值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane(){
		logger.info("网络代理连接参数设置");
		NetProxySettingPanel.getInstance().initData(netSettingBean);
	}
	
	

	
}
