/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.webservice.manager.beans;

import java.util.Date;

/**
 * 消息BEAN
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-10-1 下午3:52:01
 * @version 1.0
 */
public class MsgBean extends BaseBean{
	private Integer id;
	private String msgStatus;
	private String msgTitle;
	private String msgContent;
	private Date sendTime;
	private Integer userId;
	private String userName;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the msgStatus
	 */
	public String getMsgStatus() {
		return msgStatus;
	}
	/**
	 * @param msgStatus the msgStatus to set
	 */
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	/**
	 * @return the msgTitle
	 */
	public String getMsgTitle() {
		return msgTitle;
	}
	/**
	 * @param msgTitle the msgTitle to set
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}
	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
