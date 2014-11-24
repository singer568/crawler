package org.javacoo.cowswing.ui.view.bar;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * 菜单栏对象
 *@author DuanYong
 *@since 2012-11-4下午4:24:10
 *@version 1.0
 */
@Component("appMenuBar")
public class AppMenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	
	public AppMenuBar() {
		super();
	}
	/**
	 * 初始化菜单
	 */
	public void initMenuBar(){
		
	}
	/**
	 * 加载插件中的菜单项
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午4:50:35
	 * @param menuList
	 * @return void
	 */
	public void loadPluginMenuBar(List<JMenu> menuList){
		if(CollectionUtils.isNotEmpty(menuList)){
			for(JMenu menu : menuList){
				this.add(menu);
			}
		}
	}

	
	
	
	
}
