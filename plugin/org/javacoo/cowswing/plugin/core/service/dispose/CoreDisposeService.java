/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.service.dispose;

import org.apache.log4j.Logger;
import org.javacoo.cowswing.core.dispose.DisposeService;
import org.springframework.stereotype.Component;

/**
 *核心插件销毁
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-7-9 下午4:29:12
 * @version 1.0
 */
@Component("coreDisposeService")
public class CoreDisposeService implements DisposeService{
	protected Logger logger = Logger.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.dispose.DisposeService#dispose()
	 */
	@Override
	public void dispose() {
		logger.info("核心插件销毁");
	}

}
