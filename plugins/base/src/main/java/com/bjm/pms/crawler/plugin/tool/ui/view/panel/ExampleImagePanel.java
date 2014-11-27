/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.tool.ui.view.panel;

import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageSettingBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.ui.util.ImageUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 案例图片容器panel
 * @author DuanYong
 * @since 2012-12-22下午5:30:54
 * @version 1.0
 */
public class ExampleImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage bufferedImage;
	private static final String DEFAULT_FORMAT_NAME = "JPEG";
	/**参数bean*/
	private ImageSettingBean imageSettingBean;
    /**
     * 初始化
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-12-26 下午5:55:02
     * @param imageSettingBean
     * @return void
     */
    public void initExampleImagePanel(ImageSettingBean imageSettingBean){
    	this.imageSettingBean = imageSettingBean;
    	try {
			bufferedImage = ImageUtils.getBufferedImageByImagePath(imageSettingBean.getExampleImagePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	this.setBounds(0, 0, bufferedImage.getWidth(),bufferedImage.getHeight());
    	repaint();
    } 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/** 缩放方式：true为按比例,false为按分辨率 */
		if(Constant.YES.equals(this.imageSettingBean.getResizeValue())){
			bufferedImage = ImageUtils.scaleByScale(imageSettingBean.getExampleImagePath(),this.imageSettingBean.getResizeByScaleValue());
		}else{
			//是否等比例
			if(Constant.YES.equals(this.imageSettingBean.getEqualScaleValue())){
				bufferedImage = ImageUtils.scaleByWH(this.imageSettingBean.getExampleImagePath(),this.imageSettingBean.getResizeByPixelWidth(),this.imageSettingBean.getResizeByPixelHeight(),true);
			}else{
				bufferedImage = ImageUtils.scaleByWH(this.imageSettingBean.getExampleImagePath(),this.imageSettingBean.getResizeByPixelWidth(),this.imageSettingBean.getResizeByPixelHeight(),false);
			}
		}
		//保存质量
		String fullSaveImagePath = this.imageSettingBean.getSavePath()+System.getProperty("file.separator")+FilenameUtils.getName(this.imageSettingBean.getExampleImagePath());
		String formatName = DEFAULT_FORMAT_NAME;
		if(Constant.YES.equals(this.imageSettingBean.getSaveFormatValue())){
			formatName = this.imageSettingBean.getSaveFormatType();
			fullSaveImagePath = fullSaveImagePath.substring(0, fullSaveImagePath.lastIndexOf(".")+1)+ImageSettingBean.FORMAT_TYPE_MAP.get(formatName);
			bufferedImage = ImageUtils.scaleByQuality(bufferedImage,fullSaveImagePath,this.imageSettingBean.getSaveImageQualityValue());
		}else{
			bufferedImage = ImageUtils.scaleByQuality(bufferedImage,fullSaveImagePath,100);
		}
		//亮度
		if(Constant.YES.equals(this.imageSettingBean.getImageLightenessCheckValue())){
			bufferedImage = ImageUtils.lightenessContrast(bufferedImage,this.imageSettingBean.getImageContrast(),this.imageSettingBean.getImageLighteness());
		}
		//对比度
		if(Constant.YES.equals(this.imageSettingBean.getImageContrastValue())){
			bufferedImage = ImageUtils.lightenessContrast(bufferedImage,this.imageSettingBean.getImageContrast(),this.imageSettingBean.getImageLighteness());
		}
		//黑白
		if(Constant.YES.equals(this.imageSettingBean.getBlackAndwhiteCheckValue())){
			bufferedImage = ImageUtils.gray(bufferedImage);
		}
		//锐化
		if(Constant.YES.equals(this.imageSettingBean.getSharpCheckValue())){
			bufferedImage = ImageUtils.sharpen(bufferedImage);
		}
		//模糊
		if(Constant.YES.equals(this.imageSettingBean.getBlurCheckValue())){
			bufferedImage = ImageUtils.blur(bufferedImage);
		}
		//旋转
		if(Constant.YES.equals(this.imageSettingBean.getCirCehckValue())){
			int xScale = 1;
			int yScale = 1;
			if(Constant.YES.equals(this.imageSettingBean.getCirCehckTypeHorizontalValue())){
				xScale = -1;
			}
			if(Constant.YES.equals(this.imageSettingBean.getCirCehckTypeVerticalValue())){
				yScale = -1;
			}
			this.setBounds(0, 0, 1000,1000);
			ImageUtils.gyral(g,bufferedImage,xScale,yScale,this.imageSettingBean.getCirValue());
		}else{
			this.setBounds(0, 0, bufferedImage.getWidth(),bufferedImage.getHeight());
			g.drawImage(bufferedImage, 0, 0,bufferedImage.getWidth(),bufferedImage.getHeight(), this);
		}
		//drawTransImage(g, bufferedImage.getWidth(this),bufferedImage.getHeight(this), zoomLevel);
	}

	/**
	 * 根据参数重新绘制图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-26 下午5:53:06
	 * @param imageSettingBean
	 * @return void
	 */
	public void repaintImage(ImageSettingBean imageSettingBean){
		this.imageSettingBean = imageSettingBean;
		this.repaint();
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	


}
