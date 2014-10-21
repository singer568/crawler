package org.javacoo.cowswing.plugin.scheduler.core.support;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.utils.DateUtil;
import org.javacoo.cowswing.plugin.scheduler.core.ScheduleIterator;

/**
 * 计划框架-时间生成器接口实现类
 * @author javacoo
 * @since 2013-05-09
 */
public class WeekScheduleIterator implements ScheduleIterator {
	protected Logger log = Logger.getLogger(this.getClass());
	private final ScheduleWeekParamBean scheduleParamBean;
	private final Calendar calendar = Calendar.getInstance();
	private final Calendar orginCalendar = Calendar.getInstance();
	public WeekScheduleIterator(final ScheduleWeekParamBean scheduleParamBean) {
		this(scheduleParamBean, new Date());
	}

	public WeekScheduleIterator(final ScheduleWeekParamBean scheduleParamBean, Date date) {
		this.scheduleParamBean = scheduleParamBean;
		
		orginCalendar.setTime(date);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(scheduleParamBean.getHour()));
		calendar.set(Calendar.MINUTE, Integer.valueOf(scheduleParamBean.getMinute()));
		calendar.set(Calendar.SECOND, Integer.valueOf(scheduleParamBean.getSecond()));
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public Date next() {
		orginCalendar.setTime(new Date());
		if(StringUtils.isNotBlank(scheduleParamBean.getWeek())){
			//是否小于当前时间
			boolean isBefore = !calendar.getTime().before(orginCalendar.getTime());
			log.info("任务时间："+DateUtil.dateToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
			log.info("当前时间："+DateUtil.dateToStr(orginCalendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
			log.info("是否大于当前时间："+isBefore);
			log.info("orginCalendar.get(Calendar.DAY_OF_WEEK)："+orginCalendar.get(Calendar.DAY_OF_WEEK));
			if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("1")){
				if(scheduleParamBean.getWeek().contains("1") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("2")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("1") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("2")){
				if(scheduleParamBean.getWeek().contains("2") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("2") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("3")){
				if(scheduleParamBean.getWeek().contains("3") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("2")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("3") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("4")){
				if(scheduleParamBean.getWeek().contains("4") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("2")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("4") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("5")){
				if(scheduleParamBean.getWeek().contains("5") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("2")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("5") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("6")){
				if(scheduleParamBean.getWeek().contains("6") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("7")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("2")){
					System.out.println("添加前时间："+calendar.get(Calendar.DAY_OF_WEEK));
					calendar.add(Calendar.DAY_OF_WEEK, 3);
					System.out.println("添加后时间："+calendar.get(Calendar.DAY_OF_WEEK));
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("6") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}else if(String.valueOf(orginCalendar.get(Calendar.DAY_OF_WEEK)).equals("7")){
				if(scheduleParamBean.getWeek().contains("7") && isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 0);
				}else if(scheduleParamBean.getWeek().contains("1")){
					calendar.add(Calendar.DAY_OF_WEEK, 1);
				}else if(scheduleParamBean.getWeek().contains("2")){
					calendar.add(Calendar.DAY_OF_WEEK, 2);
				}else if(scheduleParamBean.getWeek().contains("3")){
					calendar.add(Calendar.DAY_OF_WEEK, 3);
				}else if(scheduleParamBean.getWeek().contains("4")){
					calendar.add(Calendar.DAY_OF_WEEK, 4);
				}else if(scheduleParamBean.getWeek().contains("5")){
					calendar.add(Calendar.DAY_OF_WEEK, 5);
				}else if(scheduleParamBean.getWeek().contains("6")){
					calendar.add(Calendar.DAY_OF_WEEK, 6);
				}else if(scheduleParamBean.getWeek().contains("7") && !isBefore){
					calendar.add(Calendar.DAY_OF_WEEK, 7);
				}
			}
		}else{
			calendar.add(Calendar.HOUR_OF_DAY, 1);
		}
		log.info("下次执行时间:"+DateUtil.dateToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
		return calendar.getTime();
	}
	
	public static void main(String[] args) {  
		Calendar calendar = new GregorianCalendar();  
		Date trialTime = new Date();  
		calendar.setTime(trialTime);  
		// print out a bunch of interesting things  
		System.out.println("ERA: " + calendar.get(Calendar.ERA));  
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));  
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));  
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));  
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));  
		System.out.println("DATE: " + calendar.get(Calendar.DATE));  
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));  
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));  
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));  
		System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));  
		System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));  
		System.out.println("HOUR: " + calendar.get(Calendar.HOUR));  
		System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));  
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));  
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));  
		System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));  
		System.out.println("ZONE_OFFSET: " + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));  
		System.out.println("DST_OFFSET: " + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));  
		System.out.println("Current Time, with hour reset to 3");  
		calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override  
		calendar.set(Calendar.HOUR, 3);  
		System.out.println("ERA: " + calendar.get(Calendar.ERA));  
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));  
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));  
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));  
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));  
		System.out.println("DATE: " + calendar.get(Calendar.DATE));  
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));  
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));  
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));  
		System.out.println("DAY_OF_WEEK_IN_MONTH: " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
	}

}
