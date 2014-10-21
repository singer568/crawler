package org.javacoo.webservice.manager;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.javacoo.webservice.manager.beans.FeedBackBean;
import org.javacoo.webservice.manager.beans.MsgBean;
import org.javacoo.webservice.manager.beans.PaginationBean;
import org.javacoo.webservice.manager.beans.UserBean;
import org.javacoo.webservice.manager.beans.VersionBean;

@WebService  
@SOAPBinding(style = Style.RPC)
public interface ManagerService {
	/**
	 * 登录
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-10-26 上午10:57:32
	 * @version 1.0
	 * @exception 
	 * @param userBean 用户值对象
	 * @return 用户值对象
	 */
	UserBean login(@WebParam(name = "userBean")UserBean userBean);
	/**
	 * 退出登录
	 * 
	 * <p>说明:</p>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-7-9上午10:20:52
	 * @version 1.0
	 * @param userBean 用户值对象
	 * @return 用户值对象
	 */
	UserBean logout(@WebParam(name = "userBean")UserBean userBean);
	
	/**
	 * 取得版本列表
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-18 下午2:18:43
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	VersionBean getVersion(@WebParam(name = "versionBean")VersionBean versionBean);
	/**
	 * 留言反馈
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-30 下午3:04:23
	 * @version 1.0
	 * @exception 
	 * @param feedBackBean
	 * @return
	 */
	FeedBackBean feedBack(@WebParam(name = "feedBackBean")FeedBackBean feedBackBean);
	/**
	 * 取得返回信息列表
	 * 
	 * <p>说明:</p>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-9-30下午3:59:08
	 * @version 1.0
	 * @param feedBackBean
	 * @return
	 */
	PaginationBean<FeedBackBean> getFeedBackList(@WebParam(name = "feedBackBean")FeedBackBean feedBackBean);
	/**
	 * 取得系统消息列表
	 * 
	 * <p>说明:</p>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-10-1下午4:08:11
	 * @version 1.0
	 * @param msgBean
	 * @return
	 */
	PaginationBean<MsgBean> getMsgList(@WebParam(name = "msgBean")MsgBean msgBean);
}
