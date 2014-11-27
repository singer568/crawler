/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.util;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.lang.StringUtils;

import com.bjm.pms.crawler.view.base.constant.Constant;

/**
 * 菜单工具类
 *@author DuanYong
 *@since 2012-12-13下午4:31:42
 *@version 1.0
 */
public class MenuUtil {
	/**
	 * 添加主菜单
	 * @param muneLabel 主菜单标签
	 * @param muneShortKey 主菜单快捷键
	 * @param menuItemActionMap  子菜单Action，KeyStroke map对象
	 * Action 包含子菜单标签及小图标
	 * KeyStroke 加速键
	 */
	public static JMenu createMenu(String muneLabel,String muneShortKey,Map<Action,KeyStroke> menuItemActionMap){
		JMenu menu = new JMenu(muneLabel);
		if(StringUtils.isNotBlank(muneShortKey)){
			menu.setMnemonic(muneShortKey.charAt(0));
		}
		if(null != menuItemActionMap){
			Action tempAction = null;
			for(Iterator<Action> it = menuItemActionMap.keySet().iterator();it.hasNext();){
				tempAction = it.next();
				addItem(menu,tempAction,menuItemActionMap.get(tempAction));
			}
		}
		return menu;
	}
	/**
	 * 添加子菜单
	 * @param menu 主菜单
	 * @param itemAction 子菜单Action对象
	 * @param itemKeyStroke 子菜单KeyStroke对象
	 */
	private static void addItem(JMenu menu,Action itemAction,KeyStroke itemKeyStroke){
		JMenuItem menuItem = new JMenuItem(itemAction);
		if(null != itemKeyStroke){
			menuItem.setAccelerator(itemKeyStroke);
		}
		if(null != itemAction.getValue(Constant.ACTION_LISTENER) && itemAction.getValue(Constant.ACTION_LISTENER) instanceof ActionListener){
			menuItem.addActionListener((ActionListener)itemAction.getValue(Constant.ACTION_LISTENER));
		}
		menu.add(menuItem);
		menu.addSeparator();
	}

}
