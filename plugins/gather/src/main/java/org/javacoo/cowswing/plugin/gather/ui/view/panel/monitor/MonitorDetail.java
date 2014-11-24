/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.panel.monitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.core.event.CowSwingObserver;

/**
 * 监控详细信息
 * 
 * @author DuanYong
 * @since 2013-3-9下午12:22:10
 * @version 1.0
 */
public class MonitorDetail extends JDialog implements CowSwingListener {
	private static final long serialVersionUID = 1L;
	private JTextArea taskOutput;
	private int ruleId = 0;
	// 窗体高度大小
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 500;

	public MonitorDetail() {
		setTitle(LanguageLoader.getString("CrawlerMainFrame.CrawlMonitor"));
		setLayout(new BorderLayout());
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setModalityType(ModalityType.MODELESS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(getPreferredSize());
		init();
	}
	public MonitorDetail(String title,int ruleId) {
		setTitle(LanguageLoader.getString("CrawlerMainFrame.CrawlMonitor")+"-"+title);
		setLayout(new BorderLayout());
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setModalityType(ModalityType.MODELESS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(getPreferredSize());
		this.ruleId = ruleId;
		init();
	}

	private void initListener() {
		// 添加监听事件
		CowSwingObserver.getInstance().addCrawlerListener(this);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				taskOutput.removeAll();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void init() {
		taskOutput = new JTextArea();
		taskOutput.setMargin(new Insets(2, 2, 2, 2));
		taskOutput.setEditable(false);
		taskOutput.setAutoscrolls(true);
		// 不显示滚动条
		JScrollPane scrollPane = new JScrollPane(taskOutput,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		initListener();
	}

	private void outPutInfo(String str) {
		if (taskOutput.getRows() > 28) {
			taskOutput.remove(28);
		}
		taskOutput.append(str + ".\n");
		// 定位到控件底部
		taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
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
		if (event.getEventType().equals(CowSwingEventType.ContentTableAddEvent)
				|| event.getEventType().equals(
						CowSwingEventType.ContentCommentTableAddEvent)
				|| event.getEventType().equals(
						CowSwingEventType.ContentPaginationTableAddEvent)
				|| event.getEventType().equals(
						CowSwingEventType.ContentResourceTableAddEvent)
				|| event.getEventType().equals(
								CowSwingEventType.ContentExtendFieldAddEvent)) {
			if (null != event.getEventObject()
					&& StringUtils.isNotBlank(event.getEventObject().toString())) {
				if(ruleId == Integer.valueOf(event.getEventObject().toString().split(":")[0])){
					outPutInfo(event.getEventObject().toString());
				}
			}
		}
	}
}
