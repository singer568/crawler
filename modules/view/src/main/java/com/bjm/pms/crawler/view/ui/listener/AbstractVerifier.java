package com.bjm.pms.crawler.view.ui.listener;

import java.awt.Color;


/**
 * 抽象校验类
 * 处理显示出错提示,改变出错组件背景,通知Form窗体等
 *@author DuanYong
 *@since 2012-11-17上午11:46:26
 *@version 1.0
 */
public abstract class AbstractVerifier extends javax.swing.InputVerifier{
	/** 校验监听接口 */
	private VerifierListener listener = null;
	/**消息*/
	protected String message = "";
	public AbstractVerifier(VerifierListener listener){
		this.listener = listener;
	}
	public boolean verify(javax.swing.JComponent jComponent) {
		if(!this.doVerify(jComponent)){
			//设置背景颜色
			jComponent.setBackground(Color.PINK);
			reportError(jComponent,message);
			return false;
		}
		jComponent.setForeground(Color.black);
		//设置背景颜色
		jComponent.setBackground(null);
		jComponent.setToolTipText("");
		if (listener != null){
			listener.validData(jComponent);
		}
		return true;
	}
	/**
	 * 验证失败
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-17 下午12:43:14
	 * @param jComponent
	 * @param message
	 * @return void
	 */
	private void reportError(javax.swing.JComponent jComponent, String message) {
		jComponent.setForeground(Color.red);
		jComponent.setToolTipText(message);
		if (listener != null){
			listener.invalidData(message, jComponent);
		}
	}
	/**
	 * 具体校验规则有子类实现
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-17 上午11:50:42
	 * @param jComponent
	 * @return boolean 验证通过返回true
	 */
	protected abstract boolean doVerify(javax.swing.JComponent jComponent);
	/**
	 * 设置消息
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-17 下午12:41:04
	 * @param message
	 * @return void
	 */
	public void setMessage(String message){
		this.message = message;
	}
    
}
