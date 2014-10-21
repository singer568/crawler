/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.monitor;


import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.gather.constant.GatherConstant;
import org.springframework.stereotype.Component;

/**
 * FTP上传监控
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-4-25 下午5:05:37
 * @version 1.0
 */
@Component("ftpMonitorPanel")
public class FtpMonitorPanel extends AbstractMonitorPanel {
	private static final long serialVersionUID = 1L;

	public FtpMonitorPanel() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.ITabPanel#getTanPanelName()
	 */
	@Override
	public String getTanPanelName() {
		return LanguageLoader.getString("Monitor.ftpTitle");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.
	 * event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		if (CowSwingEventType.FtpFinishedEvent == event.getEventType()
				|| CowSwingEventType.FtpStartEvent == event.getEventType()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					initOther();
				}
			});
		}
	}


	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getMonitorDetailEventType()
	 */
	@Override
	protected List<CowSwingEventType> getMonitorDetailEventTypeList() {
		List<CowSwingEventType> eventTypeList = new ArrayList<CowSwingEventType>();
		eventTypeList.add(CowSwingEventType.FtpStatusChangeEvent);
		return eventTypeList;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getMonitorType()
	 */
	@Override
	protected String getMonitorType() {
		return GatherConstant.TASK_TYPE_2;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getDestoryProgressEventType()
	 */
	@Override
	protected CowSwingEventType getDestoryProgressEventType() {
		return CowSwingEventType.FtpFinishedEvent;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getUpadteProgressEventType()
	 */
	@Override
	protected CowSwingEventType getUpadteProgressEventType() {
		return CowSwingEventType.FtpStatusChangeEvent;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.ITabPanel#getTabPanelIndex()
	 */
	@Override
	public int getTabPanelIndex() {
		// TODO Auto-generated method stub
		return 5;
	}

}
