package org.javacoo.cowswing.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerContentBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentResourceTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerContentTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.ContentListPage;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentCommentListPanel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentExtendFieldListPanel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentPaginationListPanel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.content.ContentResourceListPanel;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.javacoo.cowswing.ui.view.dialog.WaitingDialog;
import org.javacoo.cowswing.ui.view.panel.ViewDeatilPanel;
import org.springframework.stereotype.Component;


/**
 * 爬虫采集规则参数设置窗口
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("contentDialog")
public class ContentDialog extends AbstractDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;
	/**
	 * 内容列表页面
	 */
	@Resource(name="contentListPage")
	private ContentListPage contentListPage;
	
	@Resource(name="viewDeatilPanel")
	private ViewDeatilPanel viewDeatilPanel;
	
	@Resource(name="contentPaginationListPanel")
	private ContentPaginationListPanel contentPaginationListPanel;
	
	@Resource(name="contentCommentListPanel")
	private ContentCommentListPanel contentCommentListPanel;
	
	@Resource(name="contentResourceListPanel")
	private ContentResourceListPanel contentResourceListPanel;
	
	@Resource(name="contentExtendFieldListPanel")
	private ContentExtendFieldListPanel contentExtendFieldListPanel;
	
	
	private CrawlerContentBean crawlerContentBean;
	public ContentDialog(){
		super();
	}
	
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化内容面板");
			jTabbedPane.addTab(LanguageLoader.getString("ContentList.contentDetail"), viewDeatilPanel);
			contentPaginationListPanel.init();
			jTabbedPane.addTab(LanguageLoader.getString("ContentList.contentPagination"), contentPaginationListPanel);
			contentCommentListPanel.init();
			jTabbedPane.addTab(LanguageLoader.getString("ContentList.contentComment"), contentCommentListPanel);
			contentResourceListPanel.init();
			jTabbedPane.addTab(LanguageLoader.getString("ContentList.contentResource"), contentResourceListPanel);
			contentExtendFieldListPanel.init();
			jTabbedPane.addTab(LanguageLoader.getString("ContentList.contentExtendField"), contentExtendFieldListPanel);
			centerPane = jTabbedPane;
		}
		return centerPane;
	}
	/**
	 * 填充JTabbedPane值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane(){
		logger.info("填充JTabbedPane值");
		if(StringUtils.isNotBlank(crawlerContentBean.getTitle())){
			setTitle(crawlerContentBean.getTitle());
		}
		viewDeatilPanel.showContent(crawlerContentBean.getContent());
		contentPaginationListPanel.showContent(crawlerContentBean.getContentId());
		contentCommentListPanel.showContent(crawlerContentBean.getContentId());
		contentResourceListPanel.showContent(crawlerContentBean.getContentId());
		contentExtendFieldListPanel.showContent(crawlerContentBean.getContentId());
		final ContentDialog contentDialog = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			//开启线程加载图片
			Thread currThread = new Thread((CrawlerContentResourceTabelModel)contentResourceListPanel.getCrawlerContentResourceTable().getModel());
			currThread.start();
			Thread waitingThread = new Thread(new WaitingDialog(contentDialog,currThread,LanguageLoader.getString("ViewDetail.loadImageList")));
			waitingThread.start();
			}
		});
	}
	@Override
	public void update(CowSwingEvent event) {
		
	}
	
	protected void initData(String type) {
		JTable ruleTable = contentListPage.getCrawlerContentTable();
		if(ruleTable.getSelectedRow() != -1){
			CrawlerContentTabelModel crawlerContentTabelModel = (CrawlerContentTabelModel)ruleTable.getModel();
			crawlerContentBean = crawlerContentTabelModel.getRowObject(ruleTable.getSelectedRow());
		}else{
			crawlerContentBean = new CrawlerContentBean();
		}
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	public JButton getHelpButton() {
		return null;
	}
	public JButton getFinishButton() {
		return null;
	}
	
}
