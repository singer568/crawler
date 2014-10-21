/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.env;

import java.io.Serializable;

import org.javacoo.cowswing.core.runcycle.ICowSwingRunCycle;

/**
 * CowSwing运行时环境信息对象
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 上午9:36:28
 * @version 1.0
 */
public class CowSwingEnv implements Serializable{
	private static final long serialVersionUID = 1L;
	/**CowSwing运行时环境信息对象实例*/
	private static CowSwingEnv instance = new CowSwingEnv();
    
	private CowSwingEnv(){}
	/**
	 * 取得CowSwing运行时环境信息对象实例
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-2 上午9:40:14
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	public static CowSwingEnv getInstance() {
		return instance;
	}

	
}
