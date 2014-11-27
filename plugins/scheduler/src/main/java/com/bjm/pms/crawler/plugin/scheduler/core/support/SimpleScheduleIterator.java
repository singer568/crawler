package com.bjm.pms.crawler.plugin.scheduler.core.support;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.bjm.pms.crawler.plugin.scheduler.core.ScheduleIterator;

/**
 * 计划框架-时间生成器接口实现类
 * <li>返回 月/周/天/小时/分钟/秒 计划的下一次执行时间</li>
 * <li>约定：参数以逗号分隔,*号表示无值</li>
 * <li>参数解释：
 * <br>第一位：每个月的第几周</br>
 * <br>第二位：每周的第几天</br>
 * <br>第三位：天(几号)</br>
 * <br>第四位：小时(24小时制)</br>
 * <br>第五位：分钟</br>
 * <br>第六位：秒</br>
 * </li>
 * <li>参数样例：
 *  <br> 1,6,4,15,20,30       表示 从今天的15:20:30开始，每隔一个月执行一次,即下次执行时间是  下个月的第一周的第6天的15:20:30</br>
 *  <br> *,6,4,15,20,30       表示 从今天的15:20:30开始，每隔一周执行一次,即下次执行时间是  下一周的第6天的15:20:30</br>
 *  <br> *,*,4,15,20,30       表示 从今天的15:20:30开始，每隔一天执行一次,即下次执行时间是  下一天的15:20:30</br> 
 *  <br> *,*,*,15,20,30       表示 从今天的15:20:30开始，每隔一小时执行一次,即下次执行时间是  16:20:30</br>
 *  <br> *,*,*,*,20,30        表示 从这个小时的20:30开始，每隔一分钟执行一次,即下次执行时间是  *:21:30</br>
 *  <br> *,*,*,*,*,30         表示 从当前时间的30秒开始，每隔一秒执行一次,即下次执行时间是  *:*:31</br>
 * </li>
 * @author javacoo
 * @since 2011-11-03
 */
public class SimpleScheduleIterator implements ScheduleIterator {
	private final ScheduleParamBean scheduleParamBean;
	private final Calendar calendar = Calendar.getInstance();
	private final Calendar orginCalendar = Calendar.getInstance();
	public SimpleScheduleIterator(final ScheduleParamBean scheduleParamBean) {
		this(scheduleParamBean, new Date());
	}

	public SimpleScheduleIterator(final ScheduleParamBean scheduleParamBean, Date date) {
		this.scheduleParamBean = scheduleParamBean;
		
		orginCalendar.setTime(date);
		calendar.setTime(date);
		if(null != scheduleParamBean.getWeekOfMonth()){
			calendar.set(Calendar.WEEK_OF_MONTH, scheduleParamBean.getWeekOfMonth());
		}
		//如果设置了每周的第几天和一个月的第几天，则忽略一个月的第几天
		if(null != scheduleParamBean.getDayOfWeek()){
			calendar.set(Calendar.DAY_OF_WEEK, scheduleParamBean.getDayOfWeek());
		}else if(null != scheduleParamBean.getDayOfMonth()){
			calendar.set(Calendar.DAY_OF_MONTH, scheduleParamBean.getDayOfMonth());
		}
		if(null != scheduleParamBean.getHourOfDay()){
			calendar.set(Calendar.HOUR_OF_DAY, scheduleParamBean.getHourOfDay());
		}
		if(null != scheduleParamBean.getMinute()){
			calendar.set(Calendar.MINUTE, scheduleParamBean.getMinute());
		}
		if(null != scheduleParamBean.getSecond()){
			calendar.set(Calendar.SECOND, scheduleParamBean.getSecond());
		}
		calendar.set(Calendar.MILLISECOND, 0);
		//如果设置时间 大于当前时间
		if (!calendar.getTime().before(date)) {
			System.out.println(calendar.getTime() +"大于当前时间："+date);
			if(null != scheduleParamBean.getWeekOfMonth()){
				calendar.add(Calendar.MONTH, -1);
			}else if(null != scheduleParamBean.getDayOfWeek()){
				calendar.add(Calendar.DAY_OF_WEEK, -6);
			}else if(null != scheduleParamBean.getDayOfMonth()){
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}else if(null != scheduleParamBean.getHourOfDay()){
				calendar.add(Calendar.HOUR_OF_DAY, -1);
			}else if(null != scheduleParamBean.getMinute()){
				calendar.add(Calendar.MINUTE, -1);
			}else if(null != scheduleParamBean.getSecond()){
				calendar.add(Calendar.SECOND, -1);
			}
		}else{//如果小于，则会一下执行多次，所以在天,小时,分钟,秒 都加上相应时间差
			System.out.println(calendar.getTime() +"小于当前时间："+date);
			if(null != scheduleParamBean.getDayOfMonth()){
				calendar.add(Calendar.DAY_OF_MONTH, orginCalendar.get(Calendar.DAY_OF_MONTH) - scheduleParamBean.getDayOfMonth());
			}else if(null != scheduleParamBean.getHourOfDay()){
				calendar.add(Calendar.HOUR_OF_DAY, orginCalendar.get(Calendar.HOUR_OF_DAY) - scheduleParamBean.getHourOfDay());
			}else if(null != scheduleParamBean.getMinute()){
				calendar.add(Calendar.MINUTE, orginCalendar.get(Calendar.MINUTE) - scheduleParamBean.getMinute());
			}else if(null != scheduleParamBean.getSecond()){
				calendar.add(Calendar.SECOND, orginCalendar.get(Calendar.SECOND) - scheduleParamBean.getSecond());
			}
		}
	}

	public Date next() {
		if(null != scheduleParamBean.getWeekOfMonth()){
			calendar.add(Calendar.MONTH, 1);
		}else if(null != scheduleParamBean.getDayOfWeek()){
			calendar.add(Calendar.DAY_OF_WEEK, 6);
		}else if(null != scheduleParamBean.getDayOfMonth()){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}else if(null != scheduleParamBean.getHourOfDay()){
			calendar.add(Calendar.HOUR_OF_DAY, 1);
		}else if(null != scheduleParamBean.getMinute()){
			calendar.add(Calendar.MINUTE, 1);
		}else if(null != scheduleParamBean.getSecond()){
			calendar.add(Calendar.SECOND, 1);
		}
		System.out.println("下次执行时间:"+calendar.getTime());
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
