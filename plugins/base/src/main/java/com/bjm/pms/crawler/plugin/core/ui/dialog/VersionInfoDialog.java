/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.dialog;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.core.ui.view.panel.VersionInfoPanel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.BaseHttpClientHelper;
import com.bjm.pms.crawler.view.base.utils.FileUtils;
import com.bjm.pms.crawler.view.base.utils.MsgDialogUtil;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;
import com.bjm.pms.crawler.view.ui.view.dialog.AbstractDialog;
import com.bjm.pms.crawler.view.ui.view.dialog.WaitingDialog;
import com.bjm.pms.crawler.webservice.manager.beans.VersionBean;

/**
 * 版本信息dialog
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-1-2 下午2:56:48
 * @version 1.0
 */
@Component("versionInfoDialog")
public class VersionInfoDialog extends AbstractDialog{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/** 版本信息管理面板 */
	@Resource(name = "versionInfoPanel")
	private VersionInfoPanel versionInfoPanel;

	private VersionInfoDialog versionInfoDialog;
	/**主窗体*/
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	
	private VersionBean msgBean;
	/**HttpClient对象*/
	private  CloseableHttpClient httpClient = BaseHttpClientHelper.createHttpClient();
	
	public VersionInfoDialog(){
		super(500,300,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = versionInfoPanel;
		}
		return centerPane;
	}
	public JButton getCancelButton(){
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.cowswing.ui.view.dialog.AbstractDialog#
	 * finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		msgBean = versionInfoPanel.getData();
		versionInfoDialog = this;
		if(null == msgBean){
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("Core.version_select_isEmpty"));
		}else{
			int result = JOptionPane.showConfirmDialog(versionInfoDialog, LanguageLoader.getString("Core.version_select_Confirm"),LanguageLoader.getString("Common.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				final DownLoadVersion downLoadVersion = new DownLoadVersion(msgBean.getPath());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						//开启线程加载图片
						Thread currThread = new Thread(downLoadVersion);
						currThread.start();
						Thread waitingThread = new Thread(new WaitingDialog(crawlerMainFrame,currThread,LanguageLoader.getString("Core.version_download_info")));
						waitingThread.start();
					}
				});
				versionInfoDialog.setVisible(false);
			}else{
				this.dispose();
			}
		}
	}
	

	protected void initData(String type) {
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
		versionInfoPanel.initData(null);
	}
	class DownLoadVersion implements Runnable{
        private String path;
        public DownLoadVersion(String path){
        	this.path = path;
        }
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			download();
		}
		
		private void download(){
			FileUtils.deleteAllFile(Constant.UPDATE_DIR);
			HttpGet httpGet = null;
			HttpHost target = null;
			HttpClientContext context = null;
			try {
				target = URIUtils.extractHost(new URI(this.path));
				httpGet = BaseHttpClientHelper.getHttpGet(this.path);
				context = BaseHttpClientHelper.getHttpClientContext();
				HttpResponse response = httpClient.execute(target, httpGet, context);
				HttpEntity entity = response.getEntity();
				String rootPath = Constant.UPDATE_DIR+Constant.SYSTEM_SEPARATOR;
				String savePath = rootPath+FilenameUtils.getName(this.path);
				if (entity != null) {
					FileUtils.saveFile(entity.getContent(), savePath);
					FileUtils.unZip(rootPath, savePath);
					FileUtils.deleteFile(savePath,true);
					updateComplate();
				}
			}  catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}finally{
				if(null != httpGet){
					httpGet.abort();
				}
			}
		}
		/**
		 * 更新成功
		 * <p>方法说明:</>
		 * <li></li>
		 * @author DuanYong
		 * @since 2014-9-18 下午3:19:05
		 * @version 1.0
		 * @exception
		 */
		private void updateComplate(){
			versionInfoPanel.setVersion(msgBean.getVersion());
			//创建待更新标志文件
			try {
				FileUtils.createFile(Constant.UPDATE_FILE);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("创建待更新标志文件失败："+e.getMessage());
			}
			versionInfoDialog.dispose();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					int result = JOptionPane.showConfirmDialog(
							crawlerMainFrame, LanguageLoader.getString("Core.version_update_complete"),
							LanguageLoader.getString("Common.confirm"),
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						crawlerMainFrame.dispose();
					}
				}
			});
		}
	}
	
}
