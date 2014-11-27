package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.constant.GatherConstant;
import com.bjm.pms.crawler.plugin.gather.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.FileUtils;
import com.bjm.pms.crawler.view.base.utils.JsonUtils;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;
import com.google.gson.internal.StringMap;

/**
 * 导入采集规则
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-28 上午11:22:19
 * @version 1.0
 */
@Component("importRuleAction")
public class ImportRuleAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 采集规则列表页面
	 */
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
    public ImportRuleAction(){
    	super(LanguageLoader.getString("RuleList.import"),ImageLoader.getImageIcon("CrawlerResource.upload"));
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setDialogTitle(LanguageLoader.getString("RuleList.importFile"));
		jfc.addChoosableFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return GatherConstant.RULE_NAME_EXTENSION;
			}
			
			@Override
			public boolean accept(File f) {
				if(GatherConstant.RULE_NAME_EXTENSION.contains(FilenameUtils.getExtension(f.getName()))){
					return true;
				}
				return false;
			}
		});
		int result = jfc.showOpenDialog(null);
		if (result == 1) {
			return; // 撤销则返回
		} else {
			File f = jfc.getSelectedFile();// f为选择到的目录
			List<String> list = FileUtils.readFile(f.getAbsolutePath());
			StringBuilder rules = new StringBuilder();
			for(String str : list){
				rules.append(str);
			}
			if(StringUtils.isBlank(rules.toString())){
				logger.error("导入采集规则失败:规则文件内容为空");
				JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.importInfoError"),
						 LanguageLoader.getString("Common.alertTitle"),
						 JOptionPane.CLOSED_OPTION);
				return;
			}
			try{
				Object obj = JsonUtils.formatStringToObject(rules.toString(), Object.class);
				if(null != obj){
					List<StringMap> ruleList = (List<StringMap>)obj;
					CrawlerRuleBean crawlerRuleBean = null;
					for(StringMap str : ruleList){
						crawlerRuleBean = (CrawlerRuleBean) JsonUtils.formatStringToObject(JsonUtils.formatObjectToJsonString(str), CrawlerRuleBean.class);
						crawlerRuleBean.setCowSwingEvent(new CowSwingEvent(this,CowSwingEventType.RuleTableAddEvent));
						ruleListPage.getCrawlerRuleService().insert(crawlerRuleBean,GatherConstant.SQLMAP_ID_INSERT_CRAWLER_RULE);
					}
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.importInfoSuccess"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
				}else{
					logger.error("导入采集规则失败:规则文件已经损坏");
					JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.importInfoError"),
							 LanguageLoader.getString("Common.alertTitle"),
							 JOptionPane.CLOSED_OPTION);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error("导入采集规则失败:"+ex.getMessage());
				JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.importInfoError"),
						 LanguageLoader.getString("Common.alertTitle"),
						 JOptionPane.CLOSED_OPTION);
			}
		}
	}

}
