package org.javacoo.cowswing.plugin.scheduler.core.support;
/**
 * 时间计划参数bean
 * @author javacoo
 * @since 2011-11-04
 */
public class ScheduleParamBean {
	/**每个月的第几周,每周的第几天,每个月的第几天,小时(24小时制),分钟,秒*/
	private Integer weekOfMonth,dayOfWeek,dayOfMonth,hourOfDay, minute, second;

	public ScheduleParamBean(){
		
	}
	
	public ScheduleParamBean(Integer weekOfMonth, Integer dayOfWeek,
			Integer dayOfMonth, Integer hourOfDay, Integer minute,
			Integer second) {
		super();
		this.weekOfMonth = weekOfMonth;
		this.dayOfWeek = dayOfWeek;
		this.dayOfMonth = dayOfMonth;
		this.hourOfDay = hourOfDay;
		this.minute = minute;
		this.second = second;
	}

	public Integer getWeekOfMonth() {
		return weekOfMonth;
	}

	public void setWeekOfMonth(Integer weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Integer getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(Integer hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "ScheduleParamBean [dayOfMonth=" + dayOfMonth + ", dayOfWeek="
				+ dayOfWeek + ", hourOfDay=" + hourOfDay + ", minute=" + minute
				+ ", second=" + second + ", weekOfMonth=" + weekOfMonth + "]";
	}
	
	
	
	
}
