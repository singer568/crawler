package org.javacoo.cowswing.plugin.scheduler.core.support;
/**
 * 时间计划参数bean
 * @author javacoo
 * @since 2013-05-09
 */
public class ScheduleWeekParamBean {
	/**周期*/
	private String week;
	/**小时*/
	private String hour;
	/**分钟*/
	private String minute;
	/**秒*/
	private String second;

	public ScheduleWeekParamBean(){
		
	}
	
	public ScheduleWeekParamBean(String week, String hour, String minute, String second) {
		super();
		this.week = week;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public String getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(String minute) {
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public String getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(String second) {
		this.second = second;
	}
    
	
	
	
}
