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
import org.javacoo.cowswing.plugin.gather.constant.SystemConstant;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerDataBaseTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.DataBaseListPage;
import org.springframework.stereotype.Component;

/**
 * 删除数据库信息
 *@author DuanYong
 *@since 2013-2-14上午9:50:42
 *@version 1.0
 */
@Component("deleteDataBaseAction")
public class DeleteDataBaseAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="dataBaseListPage")
    private DataBaseListPage dataBaseListPage;
	private JTable dataBaseTable;
	private CrawlerDataBaseTableModel crawlerDataBaseTableModel;
	public DeleteDataBaseAction(){
    	super(LanguageLoader.getString("System.DataBaseDelete"),ImageLoader.getImageIcon("CrawlerResource.systemDataBaseDelete"));
    	this.setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dataBaseTable = dataBaseListPage.getCrawlerDataBaseTable();
		if(dataBaseTable.getSelectedRow() != -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.deleteInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				crawlerDataBaseTableModel = (CrawlerDataBaseTableModel)dataBaseTable.getModel();
				List<Integer> dataBaseIdList = new ArrayList<Integer>();
				CrawlerDataBaseBean tempCrawlerDataBaseBean = null;
				for(Integer selectRow : dataBaseTable.getSelectedRows()){
					tempCrawlerDataBaseBean = crawlerDataBaseTableModel.getRowObject(selectRow);
					dataBaseIdList.add(tempCrawlerDataBaseBean.getDataBaseId());
					dataBaseListPage.getConnectionManager().removeConnConfig(tempCrawlerDataBaseBean.getDataBaseId()+"");
				}
				//根据数据库ID删除规则
				CrawlerDataBaseBean crawlerDataBase = new CrawlerDataBaseBean();
				crawlerDataBase.setDataBaseIdList(dataBaseIdList);
				crawlerDataBase.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.DataBaseTableDeleteEvent));
				dataBaseListPage.getCrawlerDataBaseService().delete(crawlerDataBase,SystemConstant.SQLMAP_ID_DELETE_CRAWLER_DATABASE);
			}
		}
	}

}
