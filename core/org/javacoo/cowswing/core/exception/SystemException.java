/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.exception;

/**
 * 系统异常
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 上午10:02:27
 * @version 1.0
 */
public class SystemException extends AbstractBaseException{
	private static final long serialVersionUID = 1L;
	/**
	 * @param code
	 */
	public SystemException(String code, String... para) {
		super(code);
		setExceptionType(ExceptionType.SYSTEM);
		buildException(ExceptionType.SYSTEM, exceptionCode,para);
	}

}
