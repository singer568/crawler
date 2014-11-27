/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.dialog;

/**
 *@author DuanYong
 *@since 2013-1-27下午10:53:29
 *@version 1.0
 */
public interface RunnableProgress extends Runnable{
	int getTotal();
	int getCurrentNum();
	String getCurrentMsg();
}
