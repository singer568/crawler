package org.javacoo.cowswing.plugin.gather.ui.view.panel.monitor;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;
import org.javacoo.cowswing.ui.view.panel.AbstractListPage;
import org.javacoo.crawler.core.CrawlerService;

/**
 * 采集监控panel
 * 
 * @author javacoo
 * @since 2012-11-22
 */
public class MonitorPage extends AbstractListPage implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * 采集规则服务类
	 */
	@Resource(name="crawlerTaskService")
	private ICrawlerService<CrawlerTaskBean,CrawlerTaskCriteria> crawlerTaskService;
	/**
	 * 采集服务类
	 */
	@Resource(name="crawlerService")
	private CrawlerService crawlerService;
//	@Resource(name="monitorDetail")
//	private MonitorDetail monitorDetail;
	
	private JPanel containerPanel;
	/**监控进度panel map*/
	private Map<Integer,ProgressPanel> progressPanelMap ;
	private MonitorPage() {
		super();
	}


	@Override
	protected Component getTopPane() {
		super.getTopPane();
		return buttonBar;
	}

	@Override
	protected Component getCenterPane() {
		logger.info("采集监控面板");
		containerPanel = new JPanel();
		GridLayout layout = new GridLayout(10, 1, 1, 1);
		containerPanel.setLayout(layout);
		return new JScrollPane(containerPanel);
	}

	public synchronized void initMonitor() {
		containerPanel.removeAll();
		getProgressPanelMap().clear();
		ProgressPanel progresspanel = null;
		CrawlerTaskCriteria criteria = new CrawlerTaskCriteria();
		criteria.setStatus(Constant.TASK_STATUS_RUN);
		List<CrawlerTaskBean> taskList = getCrawlerTaskService().getList(
				criteria, GatherConstant.SQLMAP_ID_LIST_CRAWLER_TASK);
		if (CollectionUtils.isNotEmpty(taskList)) {
			for (CrawlerTaskBean taskBean : taskList) {
//				if(!getProgressPanelMap().containsKey(taskBean.getTaskId())){
					progresspanel = new ProgressPanel(taskBean,crawlerService);
					getProgressPanelMap().put(taskBean.getRuleId(), progresspanel);
					containerPanel.add(progresspanel);
//				}
			}
		}
		containerPanel.repaint();
	}

	protected Component getBottomPane() {
		return null;
	}

	@Override
	public void update(CowSwingEvent event) {
		if (CowSwingEventType.TaskFinishedEvent == event.getEventType() || CowSwingEventType.TaskStartEvent == event.getEventType()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					initMonitor();
				}
			});
		}
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("CrawlerMainFrame.CrawlMonitor");
	}

	@Override
	public void disposePage() {
		super.disposePage();

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("ListSelectionEvent---响应事件");
	}
    
	public ICrawlerService<CrawlerTaskBean, CrawlerTaskCriteria> getCrawlerTaskService() {
		return crawlerTaskService;
	}


	public void setCrawlerTaskService(
			ICrawlerService<CrawlerTaskBean, CrawlerTaskCriteria> crawlerTaskService) {
		this.crawlerTaskService = crawlerTaskService;
	}


	public Map<Integer, ProgressPanel> getProgressPanelMap() {
		if(null == progressPanelMap){
			progressPanelMap = new ConcurrentHashMap<Integer,ProgressPanel>();
		}
		return progressPanelMap;
	}

	public void setProgressPanelMap(Map<Integer, ProgressPanel> progressPanelMap) {
		this.progressPanelMap = progressPanelMap;
	}


	public JPanel getContainerPanel() {
		return containerPanel;
	}

	public void setContainerPanel(JPanel containerPanel) {
		this.containerPanel = containerPanel;
	}
	
	
    
}
