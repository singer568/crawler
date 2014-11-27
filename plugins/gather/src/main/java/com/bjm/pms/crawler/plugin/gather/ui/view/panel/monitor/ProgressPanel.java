package com.bjm.pms.crawler.plugin.gather.ui.view.panel.monitor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.core.CrawlerService;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerTaskBean;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * 进度条panel
 * 
 * @author DuanYong
 * @since 2012-11-23上午11:01:43
 * @version 1.0
 */
public class ProgressPanel extends JPanel implements CowSwingListener {
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	private String runInfo = LanguageLoader.getString("Task.runInfo");
	private String completeInfo = LanguageLoader.getString("Task.completeInfo");
	public JLabel label;
	public JProgressBar progressBar;
	private JButton stopButton;
	private JButton pauseButton;
	private JButton runButton;
	private JButton viewDetailButton;
	private CrawlerTaskBean crawlerTaskBean;
	// 创建一条水平进度条
	private JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
    private ProgressPanel progressPanel;
    private MonitorDetail monitorDetail;
	public ProgressPanel(final CrawlerTaskBean crawlerTaskBean,final CrawlerService crawlerService) {
		super();
		progressPanel = this;
		this.crawlerTaskBean = crawlerTaskBean;
		CowSwingObserver.getInstance().addCrawlerListener(this);
		setLayout(new GridLayout(2, 1));
		
		runButton = new JButton(LanguageLoader.getString("Task.run"),ImageLoader.getImageIcon("CrawlerResource.controlPlay"));
		stopButton = new JButton(LanguageLoader.getString("Task.stop"),ImageLoader.getImageIcon("CrawlerResource.controlStop"));
		pauseButton = new JButton(LanguageLoader.getString("Task.pause"),ImageLoader.getImageIcon("CrawlerResource.controlPause"));
		viewDetailButton = new JButton(LanguageLoader.getString("Task.detail"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleQuery"));
		runButton.setEnabled(false);
		stopButton.setEnabled(true);
		pauseButton.setEnabled(true);
		
		label = new JLabel();
		label.setText(" "+runInfo+ crawlerTaskBean.getRuleName());
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(runButton);
		box.add(pauseButton);
		box.add(stopButton);
		box.add(viewDetailButton);
		box.add(label);
		add(box);
		add(bar);
		// 设置在进度条中绘制完成百分比
		bar.setStringPainted(true);
		// 设置进度条的最大值和最小值,
		bar.setMinimum(0);
		
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				pauseButton.setEnabled(true);
				viewDetailButton.setEnabled(true);
				crawlerService.resume(crawlerTaskBean.getRuleId());
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				pauseButton.setEnabled(false);
				viewDetailButton.setEnabled(false);
				crawlerService.stop(crawlerTaskBean.getRuleId());
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runButton.setEnabled(true);
				stopButton.setEnabled(true);
				pauseButton.setEnabled(false);
				viewDetailButton.setEnabled(false);
				crawlerService.pause(crawlerTaskBean.getRuleId());
			}
		});
		viewDetailButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMonitorDetail().setVisible(true);
			}
		});
		
	}
	
	public void refushProgress(){
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.crawler.ui.event.CrawlerListener#update(org.javacoo.crawler
	 * .ui.event.CrawlerEvent)
	 */
	@Override
	public void update(final CowSwingEvent event) {
		
		if (CowSwingEventType.TaskFinishedEvent == event.getEventType()) {
			logger.info("---------------------------------采集完成，注销此对象的监听");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					CowSwingObserver.getInstance().removeCrawlerListener(progressPanel);
					progressPanel = null;
				}
			});
		}else if (CowSwingEventType.TaskStatusChangeEvent == event.getEventType()) {
			logger.info("---------------------------------正在采集，更新进度");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//CrawlerTaskBean target = getMonitorPage().getCrawlerTaskService().get(crawlerTaskBean,Constant.SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK);
					if(null != event.getEventObject()){
						CrawlerTaskBean target = (CrawlerTaskBean) event.getEventObject();
						if(crawlerTaskBean.getRuleId().intValue() == target.getRuleId().intValue()){
							// 以总任务量作为进度条的最大值
							bar.setMaximum(target.getTotal());
							// 以任务的当前完成量设置进度条的value
							bar.setValue(target.getComplete());
							label.setText(" "+runInfo+ crawlerTaskBean.getRuleName()+","+completeInfo+target.getComplete()+"/"+target.getTotal());
						}
					}
					
				}
			});
		}
	}


	

	public MonitorDetail getMonitorDetail() {
		if(null == monitorDetail){
			monitorDetail = new MonitorDetail(this.crawlerTaskBean.getRuleName(),this.crawlerTaskBean.getRuleId());
		}
		return monitorDetail;
	}

	public void setMonitorDetail(MonitorDetail monitorDetail) {
		this.monitorDetail = monitorDetail;
	}
    
	
	
	
}
