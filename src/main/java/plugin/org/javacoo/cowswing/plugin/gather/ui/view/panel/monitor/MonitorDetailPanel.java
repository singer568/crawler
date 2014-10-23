/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.monitor;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.monitor.MonitorStateBean;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerTaskBean;

/**
 * 监控详细信息
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-30 上午11:55:46
 * @version 1.0
 */
public class MonitorDetailPanel extends JPanel implements CowSwingListener{

	private static final long serialVersionUID = 1L;
	private JTextArea taskOutput;
	private List<CowSwingEventType> CowSwingEventTypeList;
	
	/**
	 * @param taskOutput
	 * @param ruleId
	 */
	public MonitorDetailPanel(List<CowSwingEventType> CowSwingEventTypeList) {
		super();
		setLayout(new BorderLayout());
		this.CowSwingEventTypeList = CowSwingEventTypeList;
		init();
	}
	private void init() {
		taskOutput = new JTextArea();
		taskOutput.setMargin(new Insets(1, 1, 1, 1));
		taskOutput.setEditable(false);
		taskOutput.setAutoscrolls(true);
		// 不显示滚动条
		JScrollPane scrollPane = new JScrollPane(taskOutput,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);
		initListener();
	}
	
	private void outPutInfo(String str) {
		if (taskOutput.getRows() > 18) {
			taskOutput.remove(18);
		}
		taskOutput.append(str + ".\n");
		// 定位到控件底部
		taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
	}

	/**
	 * 初始化监听事件
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-30 上午11:58:34
	 * @version 1.0
	 * @exception 
	 */
	private void initListener() {
		CowSwingObserver.getInstance().addCrawlerListener(this);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		if (this.CowSwingEventTypeList.contains(event.getEventType())) {
			if(null != event.getEventObject() && event.getEventObject() instanceof CrawlerTaskBean){
				CrawlerTaskBean crawlerTaskBean = (CrawlerTaskBean)event.getEventObject();
				outPutInfo(crawlerTaskBean.getDesc());
			}else if (null != event.getEventObject()
					&& event.getEventObject() instanceof MonitorStateBean) {
				MonitorStateBean monitorStateBean = (MonitorStateBean)event.getEventObject();
				outPutInfo(monitorStateBean.getDesc());
			}else if (null != event.getEventObject()
					&& event.getEventObject() instanceof String
					&& StringUtils.isNotBlank(event.getEventObject().toString())) {
				outPutInfo(event.getEventObject().toString());
			}
		}
	}

}
