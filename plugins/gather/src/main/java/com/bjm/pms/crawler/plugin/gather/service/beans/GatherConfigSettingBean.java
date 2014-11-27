/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.beans;

/**
 * 采集基本配置bean
 *@author DuanYong
 *@since 2013-3-13上午10:25:43
 *@version 1.0
 */
public class GatherConfigSettingBean {
	/**开启线程数,一般留空,默认为CPU数量*/
	private String threadNum;
	/**一个任务的超时时间，单位为毫秒*/
	private String taskTimeOut;
	/**资源保存根路径*/
	private String resSaveRootPath;
	/**资源保存相对路径,一般留空,默认按照年月生成文件夹，然后再按照当天时间生成子文件夹如：201202/29*/
	private String resSavePath;
	/**允许采集进来的外部资源类型*/
	private String extractResType;
	/**允许采集进来的外部媒体资源类型*/
	private String extractMediaResType;
	/**资源是否重命名*/
	private String replaceResName;
	/**代理服务器MAP列表,key为服务器地址,value为端口，配置格式为:address:port,address:port,.....*/
	private String proxyServerList;
	/**默认替换字符*/
	private String defaultWords;
	/**默认全局替换字符,格式:待替换字符1=替换后字符1,待替换字符2=替换后字符2,待替换字符3,待替换字符4...*/
	private String defaultCommonReplaceWords;
	private String accept;
	private String acceptLanguage;
	private String userAgent;
	private String acceptCharset;
	private String keepAlive;
	private String connection;
	private String cacheControl;
	public String getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}
	public String getTaskTimeOut() {
		return taskTimeOut;
	}
	public void setTaskTimeOut(String taskTimeOut) {
		this.taskTimeOut = taskTimeOut;
	}
	public String getResSaveRootPath() {
		return resSaveRootPath;
	}
	public void setResSaveRootPath(String resSaveRootPath) {
		this.resSaveRootPath = resSaveRootPath;
	}
	public String getResSavePath() {
		return resSavePath;
	}
	public void setResSavePath(String resSavePath) {
		this.resSavePath = resSavePath;
	}
	public String getExtractResType() {
		return extractResType;
	}
	public void setExtractResType(String extractResType) {
		this.extractResType = extractResType;
	}
	public String getExtractMediaResType() {
		return extractMediaResType;
	}
	public void setExtractMediaResType(String extractMediaResType) {
		this.extractMediaResType = extractMediaResType;
	}
	public String getReplaceResName() {
		return replaceResName;
	}
	public void setReplaceResName(String replaceResName) {
		this.replaceResName = replaceResName;
	}
	public String getProxyServerList() {
		return proxyServerList;
	}
	public void setProxyServerList(String proxyServerList) {
		this.proxyServerList = proxyServerList;
	}
	public String getDefaultWords() {
		return defaultWords;
	}
	public void setDefaultWords(String defaultWords) {
		this.defaultWords = defaultWords;
	}
	public String getDefaultCommonReplaceWords() {
		return defaultCommonReplaceWords;
	}
	public void setDefaultCommonReplaceWords(String defaultCommonReplaceWords) {
		this.defaultCommonReplaceWords = defaultCommonReplaceWords;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getAcceptCharset() {
		return acceptCharset;
	}
	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}
	public String getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getCacheControl() {
		return cacheControl;
	}
	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}
	
	

}
