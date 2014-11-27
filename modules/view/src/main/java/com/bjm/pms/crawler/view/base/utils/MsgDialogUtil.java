/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.base.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;

/**
 * 消息提示帮助类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-7-23 下午3:31:21
 * @version 1.0
 */
public class MsgDialogUtil {
	/**
	 * 创建提示信息
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-7-17 上午10:53:45
	 * @version 1.0
	 * @exception 
	 * @param msg
	 */
	public static void createMessageDialog(String msg){
		JOptionPane.showMessageDialog(null,msg , LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
		return;
	}
	/**
	 * 创建下拉选择提示框
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-9-29 下午3:02:52
	 * @version 1.0
	 * @exception 
	 * @param parentComponent
	 * @param message
	 * @param selectionValues
	 * @param initialSelectionValue
	 */
	public static Object createSelectDialog(Component parentComponent,String message,Object[] selectionValues,Object initialSelectionValue){
		return JOptionPane.showInternalInputDialog(parentComponent, message, LanguageLoader.getString("Common.selectTitle"), JOptionPane.OK_CANCEL_OPTION, null, selectionValues, initialSelectionValue);
	}
	/**
	 * 创建文本输入提示框
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-9-29 下午3:10:23
	 * @version 1.0
	 * @exception 
	 * @param message 提示信息
	 * @return
	 */
	public static String createInputDialog(String message){
		return JOptionPane.showInputDialog(message);
	}
}
