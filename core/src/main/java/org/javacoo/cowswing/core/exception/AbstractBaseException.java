/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.exception;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.ExceptionLoader;


/**
 * 抽象基础异常类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午7:16:35
 * @version 1.0
 */
public abstract class AbstractBaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	/**
	 * 异常类别
	 * <p>说明:</p>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-1 下午7:18:27
	 * @version 1.0
	 */
	public enum ExceptionType {
		BUSINESS, //业务异常
		USER, // 用户操作错误
		SYSTEM // 系统错误
	}
	/**异常类型*/
	protected ExceptionType exceptionType;
	/**异常代码*/
	protected String exceptionCode;
	/**异常信息*/
	protected String exceptionMessage;
	/**日志级别*/
	protected int exceptionLevel;
	
	public AbstractBaseException(String code) {
		super();
		setExceptionCode(code);
	}
	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public String getMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getExceptionLevel() {
		return exceptionLevel;
	}

	public void setExceptionLevel(int exceptionLevel) {
		this.exceptionLevel = exceptionLevel;
	}
	
	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	protected void buildException(ExceptionType type,String exceptionCode, String... para){
		String value = null;
		if(!StringUtils.isNotBlank(ExceptionLoader.getString(exceptionCode))){
			value = "系统发生错误,错误号为["+exceptionCode+"]未发现错误描述!|0";
		}else{
			value = ExceptionLoader.getString(exceptionCode);
		}
		
		try {
			int index = value.lastIndexOf("|");
			if (index >= 0) {
				String msg = value.substring(0,index);
				int level = Integer.parseInt(value.substring(index+1));
				this.setExceptionMessage(MessageFormat.format(msg, para));
				this.setExceptionLevel(level);
			}
		} catch (Exception e) {
			// exceptionCode不是纯数字，说明是文本消息的老异常格式定义
			if(!org.apache.commons.lang.StringUtils.isNumeric(exceptionCode)){
				this.setExceptionMessage(exceptionCode);
				this.setExceptionLevel(3);
			} else {
				// 如果从异常定义配置文件加载异常出错，则认为该异常尚未定义
				this.setExceptionMessage("[" + exceptionCode +"] 异常尚未定义，请联系管理员。");
				this.setExceptionLevel(5);
			}
		}
	}
}
