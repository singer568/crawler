/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.scheduler.ui.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.scheduler.service.beans.SchedulerBean;
import com.bjm.pms.crawler.plugin.scheduler.ui.model.RuleComboBoxModel;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.core.cache.ICowSwingCacheManager;
import com.bjm.pms.crawler.view.core.cache.support.CacheKeyConstant;
import com.bjm.pms.crawler.view.ui.listener.IntegerVerifier;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;

/**
 * 定时任务设置面板
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-5-5 下午4:38:00
 * @version 1.0
 */
@Component("schedulerSettingPanel")
public class SchedulerSettingPanel extends AbstractContentPanel<SchedulerBean>{

	private static final long serialVersionUID = 1L;
	/**缓存管理*/
	@Resource(name="cowSwingCacheManager")
	private ICowSwingCacheManager crawlerCacheManager;
	/**定时任务名称输入框*/
	private javax.swing.JTextField nameField;
	/**定时任务名称标签*/
	private javax.swing.JLabel nameLabel;
	/**定时任务关联标签*/
	private javax.swing.JLabel taskLabel;
	/**定时任务关联下拉*/
	private JComboBox taskCombo;
	/***定时任务默认ComboBoxModel*/
	private DefaultComboBoxModel taskComboComboBoxModel;
	/**周期名称标签*/
	private javax.swing.JLabel weekLabel;
	/**星期一*/
	private javax.swing.JCheckBox mondayCheckBox;
	/**星期二*/
	private javax.swing.JCheckBox tuesdayCheckBox;
	/**星期三*/
	private javax.swing.JCheckBox wednesdayCheckBox;
	/**星期四*/
	private javax.swing.JCheckBox thursdayCheckBox;
	/**星期五*/
	private javax.swing.JCheckBox fridayCheckBox;
	/**星期六*/
	private javax.swing.JCheckBox saturdayCheckBox;
	/**星期天*/
	private javax.swing.JCheckBox sundayCheckBox;
	
	/**时间名称标签*/
	private javax.swing.JLabel timeLabel;
	/**小时名称标签*/
	private javax.swing.JLabel hourLabel;
	/**时间关联下拉*/
	private JComboBox timeCombo;
	/**分钟名称标签*/
	private javax.swing.JLabel minuteLabel;
	/**分钟输入框*/
	private javax.swing.JTextField minuteField;
	/**秒名称标签*/
	private javax.swing.JLabel secondLabel;
	/**秒输入框*/
	private javax.swing.JTextField secondField;
	
	/**周期值*/
	private String week = "";
	/**服务名称,与spring配置一致*/
	private String serviceName = "schedulerRuleSvc";
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected SchedulerBean populateData() {
		SchedulerBean schedulerBean = new SchedulerBean();
		schedulerBean.setName(nameField.getText());
		if(null != taskCombo.getSelectedItem()){
			CrawlerRuleBean crawlerRuleBean = (CrawlerRuleBean) taskCombo.getSelectedItem();
			schedulerBean.setAssociateId(crawlerRuleBean.getRuleId());
		}else{
			JOptionPane.showMessageDialog(null, LanguageLoader.getString("Scheduler.isNotBlank"), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
			return null;
		}
		schedulerBean.setServiceName(serviceName);
		schedulerBean.setWeek(week);
		schedulerBean.setHour(timeCombo.getSelectedItem().toString());
		schedulerBean.setMinute(minuteField.getText());
		schedulerBean.setSecond(secondField.getText());
		return schedulerBean;
	}

	
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		nameLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.name"));
		nameLabel.setBounds(20, 15, 80, 15);
		add(nameLabel);
		nameField = new javax.swing.JTextField();
		nameField.setBounds(100, 15, 200, 21);
		add(nameField);
		
		taskLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.task"));
		taskLabel.setBounds(20, 45, 80, 15);
		add(taskLabel);
		taskComboComboBoxModel = new DefaultComboBoxModel();
		taskCombo = new JComboBox(taskComboComboBoxModel);
		taskCombo.setBounds(100, 45, 200, 21);
		add(taskCombo);
		
		weekLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.week"));
		weekLabel.setBounds(20, 75, 80, 15);
		add(weekLabel);
		mondayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.monday"));
		mondayCheckBox.setBounds(100, 75, 70, 21);
		add(mondayCheckBox);
		tuesdayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.tuesday"));
		tuesdayCheckBox.setBounds(170, 75, 70, 21);
		add(tuesdayCheckBox);
		wednesdayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.wednesday"));
		wednesdayCheckBox.setBounds(240, 75, 70, 21);
		add(wednesdayCheckBox);
		thursdayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.thursday"));
		thursdayCheckBox.setBounds(100, 105, 70, 21);
		add(thursdayCheckBox);
		fridayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.friday"));
		fridayCheckBox.setBounds(170, 105, 70, 21);
		add(fridayCheckBox);
		saturdayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.saturday"));
		saturdayCheckBox.setBounds(240, 105, 70, 21);
		add(saturdayCheckBox);
		sundayCheckBox = new javax.swing.JCheckBox(LanguageLoader.getString("Scheduler.sunday"));
		sundayCheckBox.setBounds(100, 135, 70, 21);
		add(sundayCheckBox);
		
		timeLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.time"));
		timeLabel.setBounds(20, 165, 80, 15);
		add(timeLabel);
		
		
		hourLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.hour"));
		hourLabel.setBounds(100, 165, 30, 15);
		add(hourLabel);
		
		String[] timeData = new String[]{"0","1","2","3","4","5","6","7","8","9","10"
				,"11","12","13","14","15","16","17","18","19","20","21","22","23"};
		timeCombo = new JComboBox(timeData);
		timeCombo.setBounds(130, 165, 45, 21);
		add(timeCombo);
		
		minuteLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.minute"));
		minuteLabel.setBounds(180, 165, 30, 15);
		add(minuteLabel);
		minuteField = new javax.swing.JTextField();
		minuteField.setBounds(210, 165, 30, 21);
		minuteField.setInputVerifier(new IntegerVerifier(this, true, 0, 59));
		add(minuteField);
		
		secondLabel = new javax.swing.JLabel(LanguageLoader.getString("Scheduler.second"));
		secondLabel.setBounds(245, 165, 20, 15);
		add(secondLabel);
		secondField = new javax.swing.JTextField();
		secondField.setBounds(265, 165, 30, 21);
		secondField.setInputVerifier(new IntegerVerifier(this, true, 0, 59));
		add(secondField);
		
	}
	protected void initActionListener(){
		class CheckBoxActionAdapter implements ActionListener{
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getWeek();
				System.out.println(week);
			}
		}
		mondayCheckBox.addActionListener(new CheckBoxActionAdapter());
		tuesdayCheckBox.addActionListener(new CheckBoxActionAdapter());
		wednesdayCheckBox.addActionListener(new CheckBoxActionAdapter());
		thursdayCheckBox.addActionListener(new CheckBoxActionAdapter());
		fridayCheckBox.addActionListener(new CheckBoxActionAdapter());
		saturdayCheckBox.addActionListener(new CheckBoxActionAdapter());
		sundayCheckBox.addActionListener(new CheckBoxActionAdapter());
	}
	private void getWeek(){
		StringBuilder weekStr = new StringBuilder();
		if(mondayCheckBox.isSelected()){
			weekStr.append("2");
		}
		if(tuesdayCheckBox.isSelected()){
			weekStr.append("3");
		}
		if(wednesdayCheckBox.isSelected()){
			weekStr.append("4");
		}
		if(thursdayCheckBox.isSelected()){
			weekStr.append("5");
		}
		if(fridayCheckBox.isSelected()){
			weekStr.append("6");
		}
		if(saturdayCheckBox.isSelected()){
			weekStr.append("7");
		}
		if(sundayCheckBox.isSelected()){
			weekStr.append("1");
		}
		week = weekStr.toString();
	}
	public void initData(SchedulerBean t){
		if(t == null){
			t = new SchedulerBean();
		}
		fillComponentData(t);
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	protected void fillComponentData(SchedulerBean t) {
		List<CrawlerRuleBean> resultList = (List<CrawlerRuleBean>) this.crawlerCacheManager.getValue(CacheKeyConstant.CACHE_KEY_RULE);
		if(CollectionUtils.isEmpty(resultList)){
			resultList = (List<CrawlerRuleBean>)crawlerCacheManager.loadCacheByKey(CacheKeyConstant.CACHE_KEY_RULE);
		}
		taskCombo.setModel(new RuleComboBoxModel(resultList));
		taskCombo.repaint();
		
		if(null != t && null != t.getAssociateId()){
			CrawlerRuleBean crawlerRuleBean = null;
			for(CrawlerRuleBean tempCrawlerRuleBean : resultList){
				if(tempCrawlerRuleBean.getRuleId().intValue() == t.getAssociateId().intValue()){
					crawlerRuleBean = tempCrawlerRuleBean;
					break;
				}
			}
			if(null != crawlerRuleBean){
				taskCombo.setSelectedItem(crawlerRuleBean);
			}else{
				taskCombo.setSelectedItem("");
			}
		}else{
			taskCombo.setSelectedItem("");
		}
		if(StringUtils.isNotBlank(t.getName())){
			nameField.setText(t.getName());
		}else{
			nameField.setText("");
		}
		if(StringUtils.isNotBlank(t.getWeek())){
			mondayCheckBox.setSelected(t.getWeek().contains("2") ? true : false);
			tuesdayCheckBox.setSelected(t.getWeek().contains("3") ? true : false);
			wednesdayCheckBox.setSelected(t.getWeek().contains("4") ? true : false);
			thursdayCheckBox.setSelected(t.getWeek().contains("5") ? true : false);
			fridayCheckBox.setSelected(t.getWeek().contains("6") ? true : false);
			saturdayCheckBox.setSelected(t.getWeek().contains("7") ? true : false);
			sundayCheckBox.setSelected(t.getWeek().contains("1") ? true : false);
		}else{
			mondayCheckBox.setSelected(false);
			tuesdayCheckBox.setSelected(false);
			wednesdayCheckBox.setSelected(false);
			thursdayCheckBox.setSelected(false);
			fridayCheckBox.setSelected(false);
			saturdayCheckBox.setSelected(false);
			sundayCheckBox.setSelected(false);
		}
		timeCombo.setSelectedItem(t.getHour());
		if(StringUtils.isNotBlank(t.getMinute())){
			minuteField.setText(t.getMinute());
		}else{
			minuteField.setText("");
		}
		if(StringUtils.isNotBlank(t.getSecond())){
			secondField.setText(t.getSecond());
		}else{
			secondField.setText("");
		}
		
	}

}
