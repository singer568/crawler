/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.persist;

import java.io.Serializable;

/**
 * 分页支持的规则定义
 * @author DuanYong
 * @since 2012-12-8下午1:59:58
 * @version 1.0
 */
public class PaginationCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 跳转页面命令 */
	private final static String GOTO_PAGE_COMMAND = "GoToPageCommand";
	/** 页面查询命令 */
	private final static String QUERY_COMMAND = "QueryCommand";
	/** 默认命令 */
	private static final String DEFAULT_COMMAND = "Pagination";
	/** 每页记录数 */
	public final static int PAGESIZE = 20;
	/** 开始页数 */
	private int startIndex;
	/** 每页记录数 */
	private int pageSize = PAGESIZE;
	/** 跳转页数 */
	private int gotoPageIndex;
	/** 命令 */
	private String command = DEFAULT_COMMAND;

	public PaginationCriteria() {
		this.startIndex = 0;
		this.pageSize = PAGESIZE;
		this.gotoPageIndex = -1;
	}

	public int getStartIndex() {
		if (gotoPageIndex < 1)
			gotoPageIndex = 1;
		// 计算查询记录起始位置
		if (GOTO_PAGE_COMMAND.equalsIgnoreCase(getCommand())) {
			this.startIndex = (gotoPageIndex - 1) * pageSize;
		} else if (QUERY_COMMAND.equalsIgnoreCase(getCommand())) {
			this.startIndex = 0;
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getGotoPageIndex() {
		return gotoPageIndex;
	}

	public void setGotoPageIndex(int gotoPageIndex) {
		this.gotoPageIndex = gotoPageIndex;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
