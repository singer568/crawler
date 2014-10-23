package org.javacoo.cowswing.main;

import javax.swing.SwingUtilities;

import org.javacoo.cowswing.core.runcycle.ICowSwingRunCycle;
import org.javacoo.cowswing.core.runcycle.support.CowSwingRunCycleManager;
import org.javacoo.cowswing.ui.style.LookAndFeelSelector;

/**
 * GUI界面入口类
 * 
 * @author javacoo
 * @since 2012-03-13
 */
public class CowSwingMain {
	
	
	private static final Runnable doHelloWorld = new Runnable() {
	     public void run() {
	         System.out.println("Hello World on " + Thread.currentThread());
	     }
	 };

	 private static final Thread appThread = new Thread() {
	     public void run() {
	         try {
	             SwingUtilities.invokeAndWait(doHelloWorld);
	         }
	         catch (Exception e) {
	             e.printStackTrace();
	         }
	         System.out.println("Finished on " + Thread.currentThread());
	     }
	 };


	public static void main(String[] args) {
//		appThread.start();
		startCrawler();
	}

	/**
	 * 启动爬虫
	 */
	private static void startCrawler() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LookAndFeelSelector.setLookAndFeel("OfficeBlue2007");
				org.javacoo.cowswing.ui.style.ColorDefinitions.initColors();
				ICowSwingRunCycle cowSwingRunCycle = new CowSwingRunCycleManager();
				cowSwingRunCycle.start();
			}
		});
	}

}
