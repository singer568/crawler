package com.bjm.pms.crawler.plugin.gather.ui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.codec.Encoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.encoders.EncoderUtil;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.gather.ui.model.CrawlerRuleTabelModel;
import com.bjm.pms.crawler.plugin.gather.ui.view.panel.RuleListPage;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.constant.GatherConstant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.service.beans.CrawlerRuleBean;
import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.base.utils.JsonUtils;

/**
 * 导出采集规则
 * 
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-28 上午11:23:38
 * @version 1.0
 */
@Component("exportRuleAction")
public class ExportRuleAction extends AbstractAction{
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	
	private JTable ruleTable;
	
	private CrawlerRuleTabelModel crawlerRuleTabelModel;
	
	@Resource(name="ruleListPage")
	private RuleListPage ruleListPage;
	
	public ExportRuleAction(){
		super(LanguageLoader.getString("RuleList.export"),ImageLoader.getImageIcon("CrawlerResource.download"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ruleTable = ruleListPage.getCrawlerRuleTable();
		if(ruleTable.getSelectedRow()!= -1){
			int result = JOptionPane.showConfirmDialog(null, LanguageLoader.getString("RuleList.exportInfo"),LanguageLoader.getString("RuleList.confirm"), JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setDialogTitle(LanguageLoader.getString("RuleList.exportInfoDir"));
				int pathResult = jfc.showOpenDialog(null);
				if (pathResult == 1) {
					return; // 撤销则返回
				} else {
					crawlerRuleTabelModel = (CrawlerRuleTabelModel)ruleTable.getModel();
					File f = jfc.getSelectedFile();// f为选择到的目录
					String importRuleName = "RuleList"+DateUtil.dateToStrYYYMMDD(new Date());
					if(ruleTable.getSelectedRows().length == 1){
						importRuleName = crawlerRuleTabelModel.getRowObject(ruleTable.getSelectedRow()).getRuleName();
					}else{
						importRuleName = JOptionPane.showInputDialog(LanguageLoader.getString("RuleList.exportInfoRuleName")); 
					}
					if(StringUtils.isBlank(importRuleName)){
						importRuleName = "RuleList"+DateUtil.dateToStrYYYMMDD(new Date());
					}
					List<CrawlerRuleBean> ruleIdList = new ArrayList<CrawlerRuleBean>();
					CrawlerRuleBean tempCrawlerRuleBean = null;
					for(Integer selectRow : ruleTable.getSelectedRows()){
						tempCrawlerRuleBean = new CrawlerRuleBean();
						tempCrawlerRuleBean.setRuleName(crawlerRuleTabelModel.getRowObject(selectRow).getRuleName());
						tempCrawlerRuleBean.setRuleType(crawlerRuleTabelModel.getRowObject(selectRow).getRuleType());
						//tempCrawlerRuleBean.setImageSettingBean(crawlerRuleTabelModel.getRowObject(selectRow).getImageSettingBean());
						tempCrawlerRuleBean.setRuleBaseBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleBaseBean());
						tempCrawlerRuleBean.setRuleCommentBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleCommentBean());
						tempCrawlerRuleBean.setRuleContentBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleContentBean());
						tempCrawlerRuleBean.setRuleContentPageBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleContentPageBean());
						tempCrawlerRuleBean.setRuleDataBaseBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleDataBaseBean());
						tempCrawlerRuleBean.setRuleFieldsBean(crawlerRuleTabelModel.getRowObject(selectRow).getRuleFieldsBean());
						ruleIdList.add(tempCrawlerRuleBean);
					}
					String ruleStr = JsonUtils.formatObjectToJsonString(ruleIdList);
					try {
						FileUtils.writeByteArrayToFile(new File(f.getAbsolutePath()+Constant.SYSTEM_SEPARATOR+importRuleName+GatherConstant.RULE_NAME_EXTENSION), ruleStr.getBytes());
						JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.exportInfoSuccess"),
								 LanguageLoader.getString("Common.alertTitle"),
								 JOptionPane.CLOSED_OPTION);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.exportInfoError"),
								 LanguageLoader.getString("Common.alertTitle"),
								 JOptionPane.CLOSED_OPTION);
						logger.error("导出规则失败："+e1.getMessage());
					}
				}
				
			}
		}else{
			JOptionPane.showMessageDialog(null,LanguageLoader.getString("RuleList.exportInfoRuleIsEmpty"),
					 LanguageLoader.getString("Common.alertTitle"),
					 JOptionPane.CLOSED_OPTION);
		}
	}
	public static void main(String[] args){
		String ss = "[{\"ruleName\":\"51CTO-java基础\",\"ruleType\":\"false\",\"totalItem\":0,\"ruleBaseBean\":{\"ruleName\":\"51CTO-java基础\",\"pauseTime\":200,\"pageEncoding\":\"gb2312\",\"urlRepairUrl\":\"\",\"resourceRepairUrl\":\"\",\"paginationRepairUrl\":\"http://developer.51cto.com/\",\"replaceLinkFlag\":\"true\",\"repeatCheckFlag\":\"false\",\"saveResourceFlag\":\"true\",\"useProxyFlag\":\"false\",\"randomDateFlag\":\"false\",\"gatherOrderFlag\":\"false\",\"proxyAddress\":\"128.160.97.136\",\"proxyPort\":\"808\",\"replaceWords\":\"\",\"dateFormat\":\"yyyy\",\"startRandomDate\":\"\",\"endRandomDate\":\"\",\"gatherNum\":\"\"},\"ruleCommentBean\":{\"commentIndexStart\":\"\",\"commentIndexEnd\":\"\",\"commentAreaStart\":\"\",\"commentAreaEnd\":\"\",\"commentStart\":\"\",\"commentEnd\":\"\",\"commentLinkStart\":\"\",\"commentLinkEnd\":\"\"},\"ruleContentBean\":{\"planList\":\"\",\"dynamicAddr\":\"http://developer.51cto.com/col/1005/list_1005_[page].htm\",\"dynamicStart\":1,\"dynamicEnd\":2,\"linksetStart\":\"class\u003dfont20\",\"linksetEnd\":\"\",\"descriptionStart\":\"class\u003dbrieftext\",\"descriptionEnd\":\"class\u003dad\",\"contentStart\":\"id\u003dcontent\",\"contentEnd\":\"align\u003dright,class\u003dblank10|f12|blank1\"},\"ruleContentPageBean\":{\"paginationStart\":\"class\u003dpage\",\"paginationEnd\":\"class\u003dnext\"},\"ruleDataBaseBean\":{\"saveToDataBaseFlag\":\"true\",\"dataBaseId\":\"16\",\"primaryTable\":\"jc_content\",\"contentPageTag\":\"\u003cp\u003e[NextPage][/NextPage]\u003c/p\u003e\",\"hasSelectedValueMap\":{\"jc_content\":{\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"model_id\":{\"columnName\":\"model_id\",\"columnType\":\"INT\",\"columnValue\":\"1\",\"columnValueView\":\"1\",\"columnValueType\":\"static\",\"isNullable\":0},\"sort_date\":{\"columnName\":\"sort_date\",\"columnType\":\"DATETIME\",\"columnValue\":\"viewDate\",\"columnValueView\":\"显示时间\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"site_id\":{\"columnName\":\"site_id\",\"columnType\":\"INT\",\"columnValue\":\"1\",\"columnValueView\":\"1\",\"columnValueType\":\"static\",\"isNullable\":0},\"type_id\":{\"columnName\":\"type_id\",\"columnType\":\"INT\",\"columnValue\":\"1\",\"columnValueView\":\"1\",\"columnValueType\":\"static\",\"isNullable\":0},\"channel_id\":{\"columnName\":\"channel_id\",\"columnType\":\"INT\",\"columnValue\":\"68\",\"columnValueView\":\"68\",\"columnValueType\":\"static\",\"isNullable\":0},\"user_id\":{\"columnName\":\"user_id\",\"columnType\":\"INT\",\"columnValue\":\"[{\"value\":\"李英\",\"valueName\":\"2\"},{\"value\":\"谢龙君\",\"valueName\":\"3\"}]\",\"columnValueView\":\"用户ID集合\",\"columnValueType\":\"staticMapKey\",\"isNullable\":0}},\"jc_content_ext\":{\"title\":{\"columnName\":\"title\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentTitle\",\"columnValueView\":\"内容标题\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"description\":{\"columnName\":\"description\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentBrief\",\"columnValueView\":\"内容摘要\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"release_date\":{\"columnName\":\"release_date\",\"columnType\":\"DATETIME\",\"columnValue\":\"viewDate\",\"columnValueView\":\"显示时间\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"title_img\":{\"columnName\":\"title_img\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentTitleImg\",\"columnValueView\":\"标题图片\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"content_img\":{\"columnName\":\"content_img\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentTitleImg\",\"columnValueView\":\"标题图片\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"author\":{\"columnName\":\"author\",\"columnType\":\"VARCHAR\",\"columnValue\":\"[{\"value\":\"李英\",\"valueName\":\"2\"},{\"value\":\"谢龙君\",\"valueName\":\"3\"}]\",\"columnValueView\":\"用户ID集合\",\"columnValueType\":\"staticMapValue\",\"isNullable\":0}},\"jc_content_txt\":{\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"txt\":{\"columnName\":\"txt\",\"columnType\":\"VARCHAR\",\"columnValue\":\"content\",\"columnValueView\":\"采集内容\",\"columnValueType\":\"dynamic\",\"isNullable\":0}},\"jc_content_attachment\":{\"attachment_path\":{\"columnName\":\"attachment_path\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentResource\",\"columnValueView\":\"内容资源地址\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"filename\":{\"columnName\":\"filename\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentResourceName\",\"columnValueView\":\"内容资源名称\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"attachment_name\":{\"columnName\":\"attachment_name\",\"columnType\":\"VARCHAR\",\"columnValue\":\"contentResourceName\",\"columnValueView\":\"内容资源名称\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"priority\":{\"columnName\":\"priority\",\"columnType\":\"INT\",\"columnValue\":\"1\",\"columnValueView\":\"1\",\"columnValueType\":\"static\",\"isNullable\":0}},\"jc_content_channel\":{\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"channel_id\":{\"columnName\":\"channel_id\",\"columnType\":\"INT\",\"columnValue\":\"68\",\"columnValueView\":\"68\",\"columnValueType\":\"static\",\"isNullable\":0}},\"jc_content_check\":{\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0},\"check_step\":{\"columnName\":\"check_step\",\"columnType\":\"TINYINT\",\"columnValue\":\"2\",\"columnValueView\":\"2\",\"columnValueType\":\"static\",\"isNullable\":0},\"is_rejected\":{\"columnName\":\"is_rejected\",\"columnType\":\"TINYINT\",\"columnValue\":\"0\",\"columnValueView\":\"0\",\"columnValueType\":\"static\",\"isNullable\":0}},\"jc_content_count\":{\"content_id\":{\"columnName\":\"content_id\",\"columnType\":\"INT\",\"columnValue\":\"contentId\",\"columnValueView\":\"内容主键\",\"columnValueType\":\"dynamic\",\"isNullable\":0}}}},\"ruleFieldsBean\":{}}]";
		
		System.out.println(StringUtils.escape(ss));
	}
}
