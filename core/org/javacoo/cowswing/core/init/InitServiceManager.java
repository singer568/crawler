/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-8-12 下午3:55:32
 * @version 1.0
 */
@Component("initServiceManager")
public class InitServiceManager {
	@Autowired
	private List<InitService> initServiceList;
	
	public void run(){
		if(!CollectionUtils.isEmpty(initServiceList)){
			for(InitService initService : initServiceList){
				initService.init();
			}
		}
	}
}
