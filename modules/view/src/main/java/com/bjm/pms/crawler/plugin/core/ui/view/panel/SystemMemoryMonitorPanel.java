/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.view.panel;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractTabPanel;


/**
 * 系统信息panel
 * 
 * @author DuanYong
 * @since 2013-3-4下午10:34:53
 * @version 1.0
 */
@Component("systemMemoryMonitorPanel")
public class SystemMemoryMonitorPanel extends AbstractTabPanel{
	private static final long serialVersionUID = 1L;
	private JPanel containerPanel;
	
	public SystemMemoryMonitorPanel() {
		super();
	}
	
	protected JComponent getCenterPanel() {
		containerPanel = new JPanel();
		containerPanel.setLayout(new BorderLayout());
        
        MemoryUsagePanel memoryUsagePanel = new MemoryUsagePanel(30000);
        containerPanel.add(memoryUsagePanel);
        memoryUsagePanel.start(100);
		return containerPanel;
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
		return LanguageLoader.getString("Home.memoryInfo");
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.ITabPanel#getTabPanelIndex()
	 */
	@Override
	public int getTabPanelIndex() {
		// TODO Auto-generated method stub
		return 1;
	}
	


}
