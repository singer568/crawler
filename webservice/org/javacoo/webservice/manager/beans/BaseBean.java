package org.javacoo.webservice.manager.beans;
/**
 * 基类
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-8上午10:58:35
 * @version 1.0
 */
public class BaseBean {
	private int pageNo = 1;
	private int pageSize = 15;
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
