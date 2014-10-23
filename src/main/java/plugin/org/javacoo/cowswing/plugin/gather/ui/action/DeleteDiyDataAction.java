/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.plugin.core.service.beans.CrawlerConfigBean;
import org.javacoo.cowswing.plugin.gather.constant.SystemConstant;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerConfigDiyDataTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.DiyDataListPage;
import org.springframework.stereotype.Component;

/**
 * 删除FTP配置
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-4-22 下午5:07:37
 * @version 1.0
 */
@Component("deleteDiyDataAction")
public class DeleteDiyDataAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="diyDataListPage")
	private DiyDataListPage diyDataListPage;
	private JTable configTable;
	private CrawlerConfigDiyDataTableModel crawlerDiyDataConfigTableModel;
	public DeleteDiyDataAction(){
    	super(LanguageLoader.getString("System.DiyDelete"),ImageLoader.getImageIcon("CrawlerResource.toolbarDiydataDelete"));
    	this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		configTable = diyDataListPage.getDiyDataConfigTable();
		if(configTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerDiyDataConfigTableModel = (CrawlerConfigDiyDataTableModel)configTable.getModel();
				List<Integer> configIdList = new ArrayList<Integer>();
				CrawlerConfigBean tempCrawlerConfigBean = null;
				for(Integer selectRow : configTable.getSelectedRows()){
					tempCrawlerConfigBean = crawlerDiyDataConfigTableModel.getRowObject(selectRow);
					configIdList.add(tempCrawlerConfigBean.getConfigId());
				}
				//根据数据库ID删除配置
				CrawlerConfigBean crawlerConfig = new CrawlerConfigBean();
				crawlerConfig.setConfigIdList(configIdList);
				crawlerConfig.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.ConfigTableDeleteEvent));
				diyDataListPage.getCrawlerConfigService().delete(crawlerConfig,SystemConstant.SQLMAP_ID_DELETE_CRAWLER_CINFIG);
			}
		}
	}

}
