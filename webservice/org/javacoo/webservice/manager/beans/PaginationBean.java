package org.javacoo.webservice.manager.beans;

import java.util.List;
/**
 * 分页对象
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-11-8上午9:26:34
 * @version 1.0
 */
public class PaginationBean<T> {
	/**
	 * 当前页的数据
	 */
	private List<T> list;
	private int totalCount = 0;
	private int pageSize = 20;
	private int pageNo = 1;
	private int startNum = 0;
	private int endNum = 0;
	private int showNum = 5;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getShowNum() {
		return showNum;
	}
	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}
	
	
	
}
