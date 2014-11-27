package com.bjm.pms.crawler.view.main;

import javax.swing.SwingUtilities;

import com.bjm.pms.crawler.view.core.runcycle.ICowSwingRunCycle;
import com.bjm.pms.crawler.view.core.runcycle.support.CowSwingRunCycleManager;
import com.bjm.pms.crawler.view.ui.style.LookAndFeelSelector;



/**
 * GUI界面入口类
 * 
 * @author javacoo
 * @since 2012-03-13
 */
public class CowSwingMain {
	public static void main(String[] args) {
		startCrawler();
	}

	/**
	 * 启动爬虫
	 */
	private static void startCrawler() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	LookAndFeelSelector.setLookAndFeel("OfficeBlue2007");
				com.bjm.pms.crawler.view.ui.style.ColorDefinitions.initColors();
				ICowSwingRunCycle cowSwingRunCycle = new CowSwingRunCycleManager();
				cowSwingRunCycle.start();
			}
		});
		
	}
}
