/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.monitor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.service.ICrawlerService;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskCriteria;
import org.javacoo.cowswing.ui.view.panel.AbstractTabPanel;
import org.javacoo.crawler.core.CrawlerService;

/**
 * 监控面板抽象实现类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-30 下午1:49:29
 * @version 1.0
 */
public abstract class AbstractMonitorPanel extends AbstractTabPanel{
	private static final long serialVersionUID = 1L;
	/**中部面板*/
	private JPanel centerPanel;
	/**进度条面板*/
	protected JPanel progressPanel;
	/**分隔面板*/
	private JSplitPane splitPanel;
	/**
	 * 采集规则服务类
	 */
	@Resource(name = "crawlerTaskService")
	private ICrawlerService<CrawlerTaskBean, CrawlerTaskCriteria> crawlerTaskService;
	
	@Resource(name="crawlerService")
	private CrawlerService crawlerService;
	public AbstractMonitorPanel(){
		super();
	}
	
	protected Component getCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(getSplitPanel());
		return centerPanel;
	}
	
	private JSplitPane getSplitPanel(){
		if(splitPanel == null){
			splitPanel = new JSplitPane();
			splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPanel.setOneTouchExpandable(true);
			splitPanel.setTopComponent(getProgressPanel());
			splitPanel.setBottomComponent(getProgressDetailPanel());	
			this.addComponentListener(new ComponentAdapter(){
	            public void componentResized(ComponentEvent e) {
	            	splitPanel.setDividerLocation(0.7);
	            }
	        }); 
		}
		
		return splitPanel;
	}

	public void initOther() {
		progressPanel.removeAll();
		MonitorProgressPanel progresspanel = null;
		CrawlerTaskCriteria criteria = new CrawlerTaskCriteria();
		criteria.setType(getMonitorType());
		criteria.setStatus(Constant.TASK_STATUS_RUN);
		List<CrawlerTaskBean> taskList = crawlerTaskService.getList(criteria,GatherConstant.SQLMAP_ID_LIST_CRAWLER_TASK);
		if (CollectionUtils.isNotEmpty(taskList)) {
			for (CrawlerTaskBean taskBean : taskList) {
				progresspanel = new MonitorProgressPanel(taskBean,getMonitorType(),getDestoryProgressEventType(),getUpadteProgressEventType(),crawlerService);
				progressPanel.add(progresspanel);
			}
		}
		progressPanel.repaint();
	}

	/**
	 * 进度条详细面板
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午1:51:50
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	private Component getProgressDetailPanel() {
		JPanel progressDetailPanel = new JPanel();
		progressDetailPanel.setLayout(new BorderLayout());
		progressDetailPanel.add(new MonitorDetailPanel(getMonitorDetailEventTypeList()));
		return new JScrollPane(progressDetailPanel);
	}
	/**
	 * 取得详细信息显示事件集合
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午2:14:50
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	protected abstract List<CowSwingEventType> getMonitorDetailEventTypeList();
	/**
	 * 取得销毁进度条事件
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午4:25:04
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	protected abstract CowSwingEventType getDestoryProgressEventType();
	/**
	 * 取得更新进度条事件
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午4:25:19
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	protected abstract CowSwingEventType getUpadteProgressEventType();
    /**
     * 取得监控类型
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-4-30 下午2:24:51
     * @version 1.0
     * @exception 
     * @return
     */
	protected abstract String getMonitorType();

	/**
	 * 进度条面板
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 下午1:51:47
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	private Component getProgressPanel(){
		progressPanel = new JPanel();
		GridLayout layout = new GridLayout(10, 1, 1, 1);
		progressPanel.setLayout(layout);
		return new JScrollPane(progressPanel);
	}
	
	
}
