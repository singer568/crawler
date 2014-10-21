package org.javacoo.cowswing.ui.widget;

import java.awt.Component;

import javax.swing.JFrame;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;



/**
 * 窗口基类
 *@author DuanYong
 *@since 2012-11-5上午9:57:20
 *@version 1.0
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public MainFrame(){
		super(LanguageLoader.getString("CrawlerMainFrame.title"));
		setSize(Constant.FRAME_DEFAULT_WIDTH,Constant.FRAME_DEFAULT_HEIGHT);
		setIconImage(ImageLoader.getImage("CrawlerResource.logo"));
	} 
	public MainFrame(String title){
		super(title);
		setSize(Constant.FRAME_DEFAULT_WIDTH,Constant.FRAME_DEFAULT_HEIGHT);
		setIconImage(ImageLoader.getImage("CrawlerResource.logo"));
	}
	public MainFrame(String title,Integer width, Integer height){
		super(title);
		setSize(width,height);
		setIconImage(ImageLoader.getImage("CrawlerResource.logo"));
	}
	public MainFrame(String title, Integer width, Integer height, Component owner){
		super(title);
		setSize((null == width) ? Constant.FRAME_DEFAULT_WIDTH : width, (null == height) ? Constant.FRAME_DEFAULT_HEIGHT : height);
		setLocationRelativeTo(owner);
		setIconImage(ImageLoader.getImage("CrawlerResource.logo"));
	}
	
	

}
