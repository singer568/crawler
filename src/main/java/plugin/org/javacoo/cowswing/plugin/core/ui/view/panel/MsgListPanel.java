/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.view.panel;

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

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.context.CowSwingContextData;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.plugin.core.ui.action.ViewMsgAction;
import org.javacoo.cowswing.plugin.core.ui.model.MsgTabelModel;
import org.javacoo.cowswing.ui.util.ColumnResizer;
import org.javacoo.cowswing.ui.view.panel.AbstractBaseListPage;
import org.javacoo.persistence.PaginationSupport;
import org.javacoo.webservice.manager.ManagerService;
import org.javacoo.webservice.manager.beans.MsgBean;
import org.javacoo.webservice.manager.beans.PaginationBean;
import org.javacoo.webservice.manager.beans.UserBean;
import org.springframework.stereotype.Component;

/**
 * 消息列表
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-10-1 下午4:48:22
 * @version 1.0
 */
@Component("msgListPanel")
public class MsgListPanel  extends AbstractBaseListPage implements ListSelectionListener{
	private static final long serialVersionUID = 1L;

	/**
	 * 查看按钮
	 */
	private JButton viewButton;
	/**
	 * 查看Action
	 */
	@Resource(name="viewMsgAction")
	private ViewMsgAction viewMsgAction;
	
	/**
	 * 浏览信息Table
	 */
	private JTable msgTable;
	
    private ManagerService service = (ManagerService)CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_WEBSERVICE);
	
    @Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getViewButton());
		return buttonBar;
	}

	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton(viewMsgAction);
		}
		return viewButton;
	}
	

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getMsgTable());
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
		return LanguageLoader.getString("Core.msg.list");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}
	
	/**
	 * @return crawlerRuleTable
	 * */
	public JTable getMsgTable() {
		if (msgTable == null) {
			msgTable = new JTable();
			final MsgTabelModel dataModel = new MsgTabelModel(
					getColumnNames());
			msgTable.setModel(dataModel);
			msgTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			msgTable.setFillsViewportHeight(true);

			msgTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (msgTable.getSelectedRow() != -1) {
										if (msgTable.getSelectedRows().length > 1) {
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
			msgTable.setAutoCreateRowSorter(true);
		}
		return msgTable;
	}
	
	public void showData(int startIndex,int pageSize) {
		((MsgTabelModel) getMsgTable().getModel())
				.setData(getData(startIndex, pageSize));
		final JTable table = getMsgTable();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(table);
			}
		});
	}
	public void getList(String typeCode){
		showData(0,Constant.PAGE_LIMIT);
	}
	public List<MsgBean> getData(int startIndex,int pageSize) {
		MsgBean msgBean = new MsgBean();
		Object userObj = CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_USERANME);
		if(null != userObj){
			UserBean userBean = (UserBean)userObj;
			msgBean.setUserId(userBean.getId());
			msgBean.setUserName(userBean.getUsername());
		}
		try{
			PaginationBean<MsgBean> paginationBean = service.getMsgList(msgBean);
			PaginationSupport<MsgBean> resultList = new PaginationSupport(paginationBean.getList(),paginationBean.getTotalCount(),paginationBean.getPageNo(),paginationBean.getPageSize());
			paginationBar.setPaginationSupport(resultList);
			paginationBar.setListPage(this);
			paginationBar.loadData();
			return (List<MsgBean>) resultList.getData();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new ArrayList<MsgBean>();
	}
	
	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("Core.msg.list.title"));
		columnNames.add(LanguageLoader.getString("Core.msg.list.content"));
		columnNames.add(LanguageLoader.getString("Core.msg.list.time"));
		return columnNames;
	}
	

}