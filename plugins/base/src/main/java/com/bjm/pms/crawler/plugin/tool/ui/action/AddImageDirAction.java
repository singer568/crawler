/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.tool.ui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageBean;
import com.bjm.pms.crawler.plugin.tool.ui.view.panel.ImageListPanel;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;


/**
 * 添加图片目录
 *@author DuanYong
 *@since 2012-12-14下午11:10:34
 *@version 1.0
 */
@Component("addImageDirAction")
public class AddImageDirAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	
	
	
	private JFileChooser jfc = new JFileChooser();// 文件选择器  
	
	public AddImageDirAction(){
		super(LanguageLoader.getString("ToolImage.imageListAddDir"),ImageLoader.getImageIcon("CrawlerResource.toolImageListAddDir"));
		jfc.setCurrentDirectory(new File(Constant.DEFAULT_SELECT_DIR));// 文件选择器的初始目录定为系统安装目录下doanload目录 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设定只能选择到文件夹  
        int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
        if (state == 1) {  
            return;  // 撤销则返回  
        } else {  
            File f = jfc.getSelectedFile();// f为选择到的目录 
            List<ImageBean> imageList = new ArrayList<ImageBean>();
            ImageBean bean = null;
            for(File tempFile : f.listFiles(new ImageFilter("jpg","jpeg","bmp","gif","png"))){
            	bean = new ImageBean();
            	bean.setName(FilenameUtils.getName(tempFile.getName()));
            	bean.setPath(tempFile.getAbsolutePath());
            	bean.setSize(tempFile.length());
            	bean.setType(FilenameUtils.getExtension(tempFile.getName()));
            	imageList.add(bean);
            } 
            imageListPanel.loadData(imageList);
            imageListPanel.changeState();
        } 
	}

}
