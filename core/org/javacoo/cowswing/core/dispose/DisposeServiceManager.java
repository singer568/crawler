/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.dispose;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-7-9 下午2:38:00
 * @version 1.0
 */
@Component("disposeServiceManager")
public class DisposeServiceManager {
	@Autowired
	private List<DisposeService> disposeServiceList;
	
	public void run(){
		if(!CollectionUtils.isEmpty(disposeServiceList)){
			for(DisposeService service : disposeServiceList){
				service.dispose();
			}
		}
	}
}
