/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.service.init;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.core.init.InitService;
import org.springframework.stereotype.Component;

/**
 * 初始化核心插件
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-10-16 下午5:15:15
 * @version 1.0
 */
@Component("coreInitService")
public class CoreInitServiceImpl implements InitService{

	protected Logger logger = Logger.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.init.InitService#init()
	 */
	@Override
	public void init() {
		logger.info("初始化核心插件");
	}

}
