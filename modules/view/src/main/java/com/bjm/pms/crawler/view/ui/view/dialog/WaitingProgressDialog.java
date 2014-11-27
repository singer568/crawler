/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.dialog;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * 等待提示窗口
 * 
 * @author DuanYong
 * @since 2012-12-12上午10:03:33
 * @version 1.0
 */
public class WaitingProgressDialog implements Runnable ,CowSwingListener{
	protected Logger logger = Logger.getLogger(this.getClass());
	private Thread currentThread = null;// 实际调用时就是TestThread事务处理线程

	private String messages = "";// 提示框的提示信息

	private JFrame parentFrame = null;// 提示框的父窗体
	
	private Dialog dialogParentFrame = null;// 提示框的父窗体

	private JDialog clueDiag = null;// “线程正在运行”提示框
	
	private JLabel textLabel = null;
	
	private WaitingProgressDialog waitingProgressDialog;

	private Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();

	private int width = dimensions.width / 4, height = 80;
	
	private JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);

	public WaitingProgressDialog(JFrame parentFrame, Thread currentThread,
			String messages) {
		this.parentFrame = parentFrame;
		this.currentThread = currentThread;
		this.messages = messages;
		clueDiag = new JDialog(this.parentFrame, "正在执行，请等待...", true);
		initDiag();// 初始化提示框
	}
	public WaitingProgressDialog(Dialog dialogParentFrame, Thread currentThread,
			String messages) {
		this.dialogParentFrame = dialogParentFrame;
		this.currentThread = currentThread;
		this.messages = messages;
		clueDiag = new JDialog(this.dialogParentFrame, LanguageLoader.getString("ViewDetail.waitingTitle"), true);
		initDiag();// 初始化提示框
		
	}
	protected void initDiag() {
		waitingProgressDialog = this;
		CowSwingObserver.getInstance().addCrawlerListener(this);
		clueDiag.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		JPanel textPanel = new JPanel();
		textLabel = new JLabel(messages);
		// 创建一条水平不确定进度条
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// 设置在进度条中绘制完成百分比
				bar.setStringPainted(true);
				// 设置进度条的最大值和最小值,
				bar.setMinimum(0);
				clueDiag.getContentPane().add(bar, BorderLayout.CENTER);
				clueDiag.getContentPane().doLayout();
			}
		});
		clueDiag.getContentPane().add(textPanel, BorderLayout.SOUTH);
		textPanel.add(textLabel);
		(new Thread(new DisposeWaitingDialog())).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	@Override
	public void run() {
		// 显示提示框
		int left = (dimensions.width - width) / 2;

		int top = (dimensions.height - height) / 2;

		clueDiag.setSize(new Dimension(width, height));

		clueDiag.setLocation(left, top);

		clueDiag.setVisible(true);
	}

	class DisposeWaitingDialog implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				currentThread.join();// 等待事务处理线程结束
			} catch (InterruptedException e) {
				System.out.println("Exception:" + e);
			}
			clueDiag.dispose();// 关闭提示框
			//注销监听事件
			CowSwingObserver.getInstance().removeCrawlerListener(waitingProgressDialog);
		}

	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		if (event.getEventType().equals(CowSwingEventType.ProgressChangeEvent)) {
			final RunnableProgress runnableProgress = (RunnableProgress) event.getEventObject();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// 以总任务量作为进度条的最大值
					bar.setMaximum(runnableProgress.getTotal());
					// 以任务的当前完成量设置进度条的value
					bar.setValue(runnableProgress.getCurrentNum());
					textLabel.setText(messages+"-->"+runnableProgress.getCurrentMsg());
				}
			});
		}
	}

}
