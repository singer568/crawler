package org.javacoo.cowswing.plugin.scheduler.core;
/**
 * 定时服务关联任务bean
 * @author javacoo
 * @since 2011-11-07
 */
public class SchedulerTaskBean {
	/**任务主键*/
	private Integer id;
	/**任务名称*/
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
