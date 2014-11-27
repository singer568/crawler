

package com.bjm.pms.crawler.view.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bjm.pms.crawler.view.base.constant.Constant;


public class CalendarUtils {
	private String beginDay;
	private String endDay;
	/**
	 * @return the beginDay as String
	 */
	public String getBeginDay() {
		return beginDay;
	}
	/**
	 * @param beginDay the beginDay to set
	 */
	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}
	/**
	 * @return the endDay as String
	 */
	public String getEndDay() {
		return endDay;
	}
	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	
	public  CalendarUtils(String beginDay, String endDay){
		setBeginDay(beginDay);
		setEndDay(endDay);
	}
	
	/**
	 * Create an instance of this class.
	 * the property of beginDay(String): 1900-01-01.
	 * the property of endDay(String): 9999-12-31.
	 * */
	public static CalendarUtils getInstance(){    
        return new CalendarUtils(Constant.MIN_DAY, Constant.MAX_DAY);
	}
	
	/**
	 * Create an instance of this class.
	 * the property of beginDay(String): today(yyyy-MM-dd).
	 * the property of endDay(String): today(yyyy-MM-dd).
	 * */
	public static CalendarUtils getTodayInstance(){
		
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   // eg. 2009－04－01
        String  begin = sdf.format(now.getTime());
        
        return new CalendarUtils(begin, begin);
	}
	
	/**
	 * Create an instance of this class.
	 * the property of beginDay(String): Current month first day.(yyyy-MM-dd).
	 * the property of endDay(String): Current month last day.(yyyy-MM-dd).
	 * */
	public static CalendarUtils getCurrentMonthInstance(){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String begin = sdf.format(now.getTime()) + "-01";   // eg.2009－04－01
        
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        now.add(Calendar.MONTH, 1);  //next month
        now.add(Calendar.DATE, -1-now.get(Calendar.DAY_OF_MONTH)); //current month last day.
        String end = sdf2.format(now.getTime());    //eg. 2009－04－30
        
        return new CalendarUtils(begin, end);
	}
	
	/**
	 * Create an instance of this class.
	 * the property of beginDay(String): Current year first day.(yyyy-01-01).
	 * the property of endDay(String): Current year last day.(yyyy-12-31).
	 * */
	public static CalendarUtils getCurrentYearInstance(){
        Calendar now = Calendar.getInstance();
        
        int year = now.get(Calendar.YEAR);
        String begin = year + "-01-01";
        String end = year + "-12-31";
        
        return new CalendarUtils(begin, end);
	}
}
