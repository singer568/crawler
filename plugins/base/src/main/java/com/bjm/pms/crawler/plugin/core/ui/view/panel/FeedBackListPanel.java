/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.core.ui.view.panel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.plugin.core.ui.action.FeedBackAction;
import com.bjm.pms.crawler.plugin.core.ui.action.ViewFeedBackAction;
import com.bjm.pms.crawler.plugin.core.ui.model.FeedBackTabelModel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;
import com.bjm.pms.crawler.view.ui.view.panel.ViewDeatilPanel;
import com.bjm.pms.crawler.webservice.manager.ManagerService;
import com.bjm.pms.crawler.webservice.manager.beans.FeedBackBean;
import com.bjm.pms.crawler.webservice.manager.beans.PaginationBean;
import com.bjm.pms.crawler.webservice.manager.beans.UserBean;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-10 下午9:04:23
 * @version 1.0
 */
@Component("feedBackListPanel")
public class FeedBackListPanel  extends AbstractListPage implements ListSelectionListener{
	private static final long serialVersionUID = 1L;
	/**
	 * 反馈按钮
	 */
	private JButton feedBackButton;
	/**
	 * 查看按钮
	 */
	private JButton viewButton;
	/**
	 * 查看Action
	 */
	@Resource(name="viewFeedBackAction")
	private ViewFeedBackAction viewFeedBackAction;
	/**
	 * 反馈Action
	 */
	@Resource(name="feedBackAction")
	private FeedBackAction feedBackAction;
	/**
	 * 浏览信息Table
	 */
	private JTable feedBackTable;
	
    private ViewDeatilPanel viewDeatilPanel;
    private ManagerService service = (ManagerService)CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_WEBSERVICE);
	
    @Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getFeedBackButton());
		buttonBar.add(getViewButton());
		return buttonBar;
	}
    private JButton getFeedBackButton() {
		if (feedBackButton == null) {
			feedBackButton = new JButton(feedBackAction);
		}
		return feedBackButton;
	}
	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewFeedBackAction);
		}
		return viewButton;
	}
	

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getFeedBackTable());
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.event.CowSwingListener#update(org.javacoo.cowswing.core.event.CowSwingEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getPageName() {
		return LanguageLoader.getString("Core.feedBack.list");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}
	
	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getFeedBackTable() {
		if (feedBackTable == null) {
			feedBackTable = new JTable();
			final FeedBackTabelModel dataModel = new FeedBackTabelModel(
					getColumnNames());
			feedBackTable.setModel(dataModel);
			feedBackTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			feedBackTable.setFillsViewportHeight(true);

			feedBackTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (feedBackTable.getSelectedRow() != -1) {
										if (feedBackTable.getSelectedRows().length > 1) {
											viewButton.setEnabled(false);
										} else {
											viewButton.setEnabled(true);
										}
									} else {
										viewButton.setEnabled(false);
									}
								}
							});
						}
					});
			feedBackTable.setAutoCreateRowSorter(true);
		}
		return feedBackTable;
	}
	
	public void showData(int startIndex,int pageSize) {
		((FeedBackTabelModel) getFeedBackTable().getModel())
				.setData(getData(startIndex, pageSize));
		final JTable table = getFeedBackTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}
	public void getList(String typeCode){
		showData(0,Constant.PAGE_LIMIT);
	}
	public List<FeedBackBean> getData(int startIndex,int pageSize) {
		FeedBackBean feedBackBean = new FeedBackBean();
		Object userObj = CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_USERANME);
		if(null != userObj){
			UserBean userBean = (UserBean)userObj;
			feedBackBean.setUserId(userBean.getId());
			feedBackBean.setUserName(userBean.getUsername());
		}
		PaginationBean<FeedBackBean> paginationBean = service.getFeedBackList(feedBackBean);
		PaginationSupport<FeedBackBean> resultList = new PaginationSupport(paginationBean.getList(),paginationBean.getTotalCount(),paginationBean.getPageNo(),paginationBean.getPageSize());
		paginationBar.setPaginationSupport(resultList);
		paginationBar.setListPage(this);
		paginationBar.loadData();
		return (List<FeedBackBean>) resultList.getData();
	}
	
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("Core.feedBack.list.id"));
		columnNames.add(LanguageLoader.getString("Core.feedBack.list.title"));
		columnNames.add(LanguageLoader.getString("Core.feedBack.list.reply"));
		columnNames.add(LanguageLoader.getString("Core.feedBack.list.ip"));
		return columnNames;
	}
	/**
	 * @return the viewDeatilPanel
	 */
	public ViewDeatilPanel getViewDeatilPanel() {
		return viewDeatilPanel;
	}
	/**
	 * @param viewDeatilPanel the viewDeatilPanel to set
	 */
	public void setViewDeatilPanel(ViewDeatilPanel viewDeatilPanel) {
		this.viewDeatilPanel = viewDeatilPanel;
	}
	

}
