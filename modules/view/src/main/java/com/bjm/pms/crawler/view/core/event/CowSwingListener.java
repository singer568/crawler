/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.event;

import java.util.EventListener;


/**
 * 监听接口
 *@author DuanYong
 *@since 2012-11-4下午10:30:59
 *@version 1.0
 */
public interface CowSwingListener extends EventListener{
	/**
	 * 监听事件
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-4-25 下午3:01:06
	 * @version 1.0
	 * @exception 
	 * @param event
	 */
	void update(CowSwingEvent event);
}
