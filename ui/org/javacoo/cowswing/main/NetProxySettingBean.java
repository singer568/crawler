/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.main;

/**
 * 代理网络设置值对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-3 上午9:34:44
 * @version 1.0
 */
public class NetProxySettingBean {
	private String proxyHost;// 代理地址
	private String proxyPort;//代理端口
	private String domain;//域
	private String userName;//用户名
	private String password;//密码
	private String isUsed;//是否启用
	/**
	 * @return the proxyHost
	 */
	public String getProxyHost() {
		return proxyHost;
	}
	/**
	 * @param proxyHost the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	/**
	 * @return the proxyPort
	 */
	public String getProxyPort() {
		return proxyPort;
	}
	/**
	 * @param proxyPort the proxyPort to set
	 */
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
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
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the isUsed
	 */
	public String getIsUsed() {
		return isUsed;
	}
	/**
	 * @param isUsed the isUsed to set
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	
	

}
