/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.main;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-8 上午10:01:51
 * @version 1.0
 */
public interface Splash {
	public void splashOn();
	
	public void splashOff();
	
	public void increaseProgress(int value);
	
	public void increaseProgress(int value, String message);
	
	public void resetProgress();
}
