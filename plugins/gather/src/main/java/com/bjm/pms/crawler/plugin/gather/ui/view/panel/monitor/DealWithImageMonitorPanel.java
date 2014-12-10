/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.ui.view.panel.monitor;


import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;

/**
 * 图片处理监控
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-14 下午9:56:50
 * @version 1.0
 */
@Component("dealWithImageMonitorPanel")
public class DealWithImageMonitorPanel extends AbstractMonitorPanel {
	private static final long serialVersionUID = 1L;

	public DealWithImageMonitorPanel() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.ITabPanel#getTanPanelName()
	 */
	@Override
	public String getTanPanelName() {
		return LanguageLoader.getString("Monitor.dealWithImageTitle");
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
		if (CowSwingEventType.DealWithImageFinishedEvent == event.getEventType()
				|| CowSwingEventType.DealWithImageStartEvent == event.getEventType()) {
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
		eventTypeList.add(CowSwingEventType.DealWithImageStatusChangeEvent);
		return eventTypeList;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getMonitorType()
	 */
	@Override
	protected String getMonitorType() {
		return GatherConstant.TASK_TYPE_4;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getDestoryProgressEventType()
	 */
	@Override
	protected CowSwingEventType getDestoryProgressEventType() {
		return CowSwingEventType.DealWithImageFinishedEvent;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getUpadteProgressEventType()
	 */
	@Override
	protected CowSwingEventType getUpadteProgressEventType() {
		return CowSwingEventType.DealWithImageStatusChangeEvent;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.ITabPanel#getTabPanelIndex()
	 */
	@Override
	public int getTabPanelIndex() {
		// TODO Auto-generated method stub
		return 4;
	}

}
