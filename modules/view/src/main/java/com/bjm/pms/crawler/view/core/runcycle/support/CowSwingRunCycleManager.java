/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.core.runcycle.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.bjm.pms.crawler.view.base.loader.ConfigLoader;
import com.bjm.pms.crawler.view.base.loader.ExceptionLoader;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.loader.PluginsLoader;
import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.core.launcher.ILauncher;
import com.bjm.pms.crawler.view.core.launcher.SpringLauncherImpl;
import com.bjm.pms.crawler.view.core.runcycle.ICowSwingRunCycle;
import com.bjm.pms.crawler.view.core.runcycle.schedule.Schedule;
import com.bjm.pms.crawler.view.core.runcycle.schedule.ScheduleEnum;




/**
 * CowSwing运行周期管理类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-1 下午8:26:28
 * @version 1.0
 */
public class CowSwingRunCycleManager implements ICowSwingRunCycle{
	protected Logger logger = Logger.getLogger(this.getClass());
	/** spring容器中注册了的所有Schedule */
    private TreeMap<Enum<ScheduleEnum>, Schedule> scheduleTreeMap;
    /** 当前运行周期的计划 */
    private Schedule currentSchedule = null;
    
    public CowSwingRunCycleManager(){
    	//设置自身引用到CowSwingContextData
    	CowSwingContextData.getInstance().setCowSwingRunCycle(this);
    }
    /**
     * 初始化计划MAP
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-2 上午11:00:26
	 * @version 1.0
	 * @exception 
	 */
	private void initScheduleMap() {
		Map<String, Schedule> map = CowSwingContextData.getInstance().getApplicationContext().getBeansOfType(Schedule.class);
		scheduleTreeMap = new TreeMap<Enum<ScheduleEnum>, Schedule>();
        if (!CollectionUtils.isEmpty(map)) {
            for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
            	Schedule schedule = map.get(iterator.next());
            	scheduleTreeMap.put(schedule.getSchedule(), schedule);
            }
        } else {
            logger.error("没有在Spring容器中找到P2Phase的bean!");
        }
	}
	/**
	 * 初始化Spring容器
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-6-2 上午11:00:22
	 * @version 1.0
	 * @exception 
	 */
	private void initSpringContext() {
		logger.info("开始初始化Spring容器.....");
	    long startDate = System.currentTimeMillis();
		ILauncher launcher = new SpringLauncherImpl();
		launcher.launch();
        logger.info("Spring容器初始化成功,消耗时间:" + (System.currentTimeMillis() - startDate));
	}
	/**
     * 初始化系统环境变量配置
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-6-2 上午10:59:16
     * @version 1.0
     * @exception
     */
    private void initEnv(){
    	//国际化
    	LanguageLoader.setLanguage(Locale.getDefault());
    	//图标
    	ImageLoader.setLanguage(Locale.getDefault());
    	//异常
    	ExceptionLoader.setLanguage(Locale.getDefault());
    	//加载插件
    	PluginsLoader.loadPlugins();
    	//加载配置参数
    	ConfigLoader.loadConfig();
    }
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.run.ICowRunCycle#start()
	 */
	@Override
	public void start() {
		initEnv();
		//启动spring容器
        initSpringContext();
	    //加载Schedulemap
	    initScheduleMap();
	    //执行计划
	  //执行计划
	    List<ScheduleEnum> states = new ArrayList<ScheduleEnum>();
		states.add(ScheduleEnum.RUN);
		states.add(ScheduleEnum.START);
	    executeSchedule(states);
		
	}
	/**
     * 执行计划
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-10-16 下午6:09:29
     * @version 1.0
     * @exception
     */
	private void executeSchedule(List<ScheduleEnum> states){
    	logger.info("执行计划计划:"+states.toString());
		for (Iterator<Enum<ScheduleEnum>> iterator = scheduleTreeMap.keySet().iterator(); iterator.hasNext();) {
            //取得运行计划
		Schedule schedule = scheduleTreeMap.get(iterator.next());
            try {
            	if(null != schedule && states.contains(schedule.getSchedule())){
            		logger.info("执行当前运行计划:"+schedule.getSchedule());
            		 //执行当前运行计划
	            	schedule.execute();
            	}
            } catch (Exception ex) {
            	ex.printStackTrace();
                //this.exceptionProcessor.handleException(phase, ex);
            }
            //将当前阶段赋予全局变量
            currentSchedule = schedule;
        }
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.run.ICowRunCycle#end()
	 */
	@Override
	public void end() {
		synchronized (CowSwingContextData.getInstance().getCowSwingTheadLock()) {
			CowSwingContextData.getInstance().getCowSwingTheadLock().notify();
        }
		//执行计划
		List<ScheduleEnum> states = new ArrayList<ScheduleEnum>();
		states.add(ScheduleEnum.SHUTDOWN);
	    executeSchedule(states);
	}
	 /**
     * 执行计划
     * <p>方法说明:</>
     * <li></li>
     * @author DuanYong
     * @since 2013-10-16 下午6:09:29
     * @version 1.0
     * @exception
     */
	private void executeSchedule(){
		for (Iterator<Enum<ScheduleEnum>> iterator = scheduleTreeMap.keySet().iterator(); iterator.hasNext();) {
            //取得运行计划
			Schedule schedule = scheduleTreeMap.get(iterator.next());
            try {
            	if(null != schedule){
            		 //执行当前运行计划
	            	schedule.execute();
            	}
            } catch (Exception ex) {
            	ex.printStackTrace();
                //this.exceptionProcessor.handleException(phase, ex);
            }
            //将当前阶段赋予全局变量
            currentSchedule = schedule;
        }
	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.core.run.ICowRunCycle#getCurrentRunSchedule()
	 */
	@Override
	public Schedule getCurrentSchedule() {
		return this.currentSchedule;
	}

}
