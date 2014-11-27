package com.bjm.pms.crawler.view.ui.view.panel;

/**
 * 内容面板接口
 *@author DuanYong
 *@since 2012-11-15下午5:06:44
 *@version 1.0
 */
public interface IContentPanel<T> {
	/**
	 * 初始化数据
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 上午10:30:44
	 * @param t
	 * @return void
	 */
	void initData(T t);
	/**
	 * 取得内容面板值对象
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-15 下午5:07:51
	 * @return
	 * @return T
	 */
	T getData();
	/**
	 * 销毁对象
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-3 上午10:21:27
	 * @return void
	 */
	void dispose();
}
