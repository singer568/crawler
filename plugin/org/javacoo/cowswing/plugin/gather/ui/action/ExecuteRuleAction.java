package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.main.CowSwingMainFrame;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerRuleBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerRuleTabelModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.RuleListPage;
import org.javacoo.cowswing.ui.view.dialog.WaitingDialog;
import org.javacoo.crawler.core.CrawlerService;
import org.springframework.stereotype.Component;

/**
 * 执行任务
 *@author DuanYong
 *@since 2012-11-17下午9:09:54
 *@version 1.0
 */
@Component("executeRuleAction")
public class ExecuteRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;

	
	@Resource(name="crawlerService")
	private CrawlerService crawlerService;
	

	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame crawlerMainFrame;
	
	public ExecuteRuleAction(){
		super(LanguageLoader.getString("Task.run"),ImageLoader.getImageIcon("CrawlerResource.toolbarRuleQuery"));
		
		this.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRows().length > 0){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.executeInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				final ExecuteRule executeRule = new ExecuteRule();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						//开启线程
						Thread currThread = new Thread(executeRule);
						currThread.start();
						Thread waitingThread = new Thread(new WaitingDialog(crawlerMainFrame,currThread,LanguageLoader.getString("RuleList.executeInit")));
						waitingThread.start();
					}
				});
			
			}
		}
	}
	
	class ExecuteRule implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
			CrawlerRuleBean tempCrawlerRuleBean = null;
			for(Integer selectRow : ruleTable.getSelectedRows()){
				tempCrawlerRuleBean = crawlerRuleTabelModel.getRowObject(selectRow);
				crawlerService.start(tempCrawlerRuleBean.getRuleId(),false);
			}
		}
	}

}
