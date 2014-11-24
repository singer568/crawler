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
 * 保存至远程数据库监控
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-25 下午5:28:39
 * @version 1.0
 */
@Component("saveMonitorPanel")
public class SaveMonitorPanel extends AbstractMonitorPanel{

	private static final long serialVersionUID = 1L;
	public SaveMonitorPanel(){
		super();
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.ITabPanel#getTanPanelName()
	 */
	@Override
	public String getTanPanelName() {
		return LanguageLoader.getString("Monitor.saveTitle");
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
		if (CowSwingEventType.SaveFinishedEvent == event.getEventType()
				|| CowSwingEventType.SaveStartEvent == event.getEventType()) {
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
		eventTypeList.add(CowSwingEventType.SaveStatusChangeEvent);
		return eventTypeList;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getMonitorType()
	 */
	@Override
	protected String getMonitorType() {
		return GatherConstant.TASK_TYPE_3;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getDestoryProgressEventType()
	 */
	@Override
	protected CowSwingEventType getDestoryProgressEventType() {
		return CowSwingEventType.SaveFinishedEvent;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.plugin.gather.ui.view.panel.monitor.AbstractMonitorPanel#getUpadteProgressEventType()
	 */
	@Override
	protected CowSwingEventType getUpadteProgressEventType() {
		return CowSwingEventType.SaveStatusChangeEvent;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.ITabPanel#getTabPanelIndex()
	 */
	@Override
	public int getTabPanelIndex() {
		// TODO Auto-generated method stub
		return 3;
	}

}
