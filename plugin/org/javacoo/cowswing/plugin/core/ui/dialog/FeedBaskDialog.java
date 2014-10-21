/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.dialog;

import java.awt.event.ActionEvent;
import java.net.InetAddress;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.base.utils.MsgDialogUtil;
import org.javacoo.cowswing.core.context.CowSwingContextData;
import org.javacoo.cowswing.plugin.core.ui.view.panel.FeedBackPanel;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.javacoo.webservice.manager.ManagerService;
import org.javacoo.webservice.manager.beans.FeedBackBean;
import org.javacoo.webservice.manager.beans.PaginationBean;
import org.javacoo.webservice.manager.beans.UserBean;
import org.springframework.stereotype.Component;

/**
 * 意见反馈dialog
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-30 上午11:18:50
 * @version 1.0
 */
@Component("feedBaskDialog")
public class FeedBaskDialog extends AbstractDialog{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/** 意见反馈信息管理面板 */
	@Resource(name = "feedBackPanel")
	private FeedBackPanel feedBackPanel;
	
	private FeedBackBean feedBackBean;
	private ManagerService service = (ManagerService)CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_WEBSERVICE);
	public FeedBaskDialog(){
		super(500,350,true);
	}
	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = feedBackPanel;
		}
		return centerPane;
	}
	public JButton getCancelButton(){
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.cowswing.ui.view.dialog.AbstractDialog#
	 * finishButtonActionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		feedBackBean = feedBackPanel.getData();
		if(StringUtils.isBlank(feedBackBean.getTitle())){
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("Core.feedBack.titleIsEmpty"));
			return;
		}else if(StringUtils.isBlank(feedBackBean.getContent())){
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("Core.feedBack.contentIsEmpty"));
			return;
		}
		Object userObj = CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_USERANME);
		if(null != userObj){
			UserBean userBean = (UserBean)userObj;
			feedBackBean.setUserId(userBean.getId());
			feedBackBean.setUserName(userBean.getUsername());
		}
		try{
			feedBackBean.setIp(String.valueOf(InetAddress.getLocalHost().getHostAddress()));
			PaginationBean<FeedBackBean> p = service.getFeedBackList(feedBackBean);
			logger.info(p.getPageNo());
			service.feedBack(feedBackBean);
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("Core.feedBack.isSucc"));
		}catch(Exception e){
			e.printStackTrace();
			MsgDialogUtil.createMessageDialog(LanguageLoader.getString("Core.feedBack.isError")+e.getMessage());
			this.dispose();
		}
		this.dispose();
	}
	

	protected void initData(String type) {
		fillJTabbedPane();
	}
	public void dispose(){
		super.dispose();
		centerPane = null;
	}
	/**
	 * 填充JTabbedPane值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane(){
		feedBackPanel.initData(null);
	}
}
