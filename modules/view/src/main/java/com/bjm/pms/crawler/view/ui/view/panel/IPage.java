package com.bjm.pms.crawler.view.ui.view.panel;

import com.bjm.pms.crawler.view.core.event.CowSwingListener;




/**
 * 页面接口
 *@author DuanYong
 *@since 2012-11-5下午3:20:22
 *@version 1.0
 */
public interface IPage extends CowSwingListener{
	
	public void init();
	
	public String getPageId();
	
	
	public String getPageName();
	
	
	public void disposePage();
	
	
	public boolean isDefaultPage();
	
	
	public void setDefaultPage(boolean bool);
	
	void showData(int pageNumber,int pageSize);
}
