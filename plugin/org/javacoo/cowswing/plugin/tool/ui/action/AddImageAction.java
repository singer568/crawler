/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageBean;
import org.javacoo.cowswing.plugin.tool.ui.view.panel.ImageListPanel;
import org.springframework.stereotype.Component;


/**
 * 添加图片
 *@author DuanYong
 *@since 2012-12-14下午11:10:34
 *@version 1.0
 */
@Component("addImageAction")
public class AddImageAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JFileChooser jfc = new JFileChooser();// 文件选择器  
	@Resource(name="imageListPanel")
	private ImageListPanel imageListPanel;
	public AddImageAction(){
		super(LanguageLoader.getString("ToolImage.imageListAdd"),ImageLoader.getImageIcon("CrawlerResource.toolImageListAdd"));
		jfc.setCurrentDirectory(new File(Constant.DEFAULT_SELECT_DIR));// 文件选择器的初始目录定为系统安装目录下doanload目录 
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(new FileNameExtensionFilter("All(*.jpg,*.jpeg,*.bmp)","jpg","jpeg","bmp"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设定只能选择到文件
		jfc.setMultiSelectionEnabled(true);
        int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
        if (state == 1) {  
            return;  // 撤销则返回  
        } else {  
            File[] files = jfc.getSelectedFiles();
            List<ImageBean> imageList = new ArrayList<ImageBean>();
            ImageBean bean = null;
            for(File f : files){
            	bean = new ImageBean();
            	bean.setName(FilenameUtils.getName(f.getName()));
            	bean.setPath(f.getAbsolutePath());
            	bean.setSize(f.length());
            	bean.setType(FilenameUtils.getExtension(f.getName()));
            	imageList.add(bean);
            } 
            imageListPanel.loadData(imageList);
            imageListPanel.changeState();
        }  
	}

}
