package org.javacoo.webservice.manager.beans;
/**
 * 用户信息值对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-26 上午10:53:23
 * @version 1.0
 */
public class UserBean extends BaseBean{
	// primary key
	private java.lang.Integer id;

	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String email;
	private java.util.Date registerTime;
	private java.lang.String registerIp;
	private java.util.Date lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.Integer loginCount;
	private java.lang.Integer rank;
	private java.lang.Long uploadTotal;
	private java.lang.Integer uploadSize;
	private java.lang.Boolean admin;
	private java.lang.Boolean viewonlyAdmin;
	private java.lang.Boolean selfAdmin;
	private java.lang.Boolean disabled;
	private Boolean isLoginSuccess;
	private String errorMsg;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public java.lang.String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.util.Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}
	public java.lang.String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(java.lang.String registerIp) {
		this.registerIp = registerIp;
	}
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public java.lang.Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}
	public java.lang.Integer getRank() {
		return rank;
	}
	public void setRank(java.lang.Integer rank) {
		this.rank = rank;
	}
	public java.lang.Long getUploadTotal() {
		return uploadTotal;
	}
	public void setUploadTotal(java.lang.Long uploadTotal) {
		this.uploadTotal = uploadTotal;
	}
	public java.lang.Integer getUploadSize() {
		return uploadSize;
	}
	public void setUploadSize(java.lang.Integer uploadSize) {
		this.uploadSize = uploadSize;
	}
	
	public java.lang.Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(java.lang.Boolean admin) {
		this.admin = admin;
	}
	public java.lang.Boolean getViewonlyAdmin() {
		return viewonlyAdmin;
	}
	public void setViewonlyAdmin(java.lang.Boolean viewonlyAdmin) {
		this.viewonlyAdmin = viewonlyAdmin;
	}
	public java.lang.Boolean getSelfAdmin() {
		return selfAdmin;
	}
	public void setSelfAdmin(java.lang.Boolean selfAdmin) {
		this.selfAdmin = selfAdmin;
	}
	public java.lang.Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(java.lang.Boolean disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return the isLoginSuccess
	 */
	public Boolean getIsLoginSuccess() {
		return isLoginSuccess;
	}
	/**
	 * @param isLoginSuccess the isLoginSuccess to set
	 */
	public void setIsLoginSuccess(Boolean isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
