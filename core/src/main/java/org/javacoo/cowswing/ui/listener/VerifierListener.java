package org.javacoo.cowswing.ui.listener;
/**
 * 校验监听接口
 * @author javacoo
 * @since 2012-03-07
 */
public interface VerifierListener {
	/**
	 * 当发生错误时，调用此方法
	 * @param message
	 * @param component
	 */
	 void invalidData(String message, javax.swing.JComponent component);
	 /**
	  * 当成功时，调用此方法
	  * @param component
	  */
	 void validData(javax.swing.JComponent component);
}
