/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.view.navigator;

import java.awt.Component;

import javax.swing.Icon;

/**
 * 导航栏项目
 *@author DuanYong
 *@since 2012-12-13下午4:11:04
 *@version 1.0
 */
public class NavigatorItem {
	/**导航标题*/
	private String title;
	/**导航图标*/
	private Icon icon;
	/**组件*/
	private Component comp;
	
	/**
	 * @param title
	 * @param icon
	 * @param comp
	 */
	public NavigatorItem(String title, Icon icon, Component comp) {
		super();
		this.title = title;
		this.icon = icon;
		this.comp = comp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public Component getComp() {
		return comp;
	}
	public void setComp(Component comp) {
		this.comp = comp;
	}
	
	

}
