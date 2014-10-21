/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.view.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageSettingBean;
import org.javacoo.cowswing.ui.util.ImageUtils;

/**
 * 水印文字panel
 *@author DuanYong
 *@since 2012-12-29下午10:15:10
 *@version 1.0
 */
public class WaterTextPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage bufferedImage;
	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 50;
	private String[] waterText = new String[]{};
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
    private void initWaterTextPanel(ImageSettingBean imageSettingBean){
    	waterText = imageSettingBean.getWaterText();
    	bufferedImage = new BufferedImage(getWaterTextPanelWidth(imageSettingBean),getWaterTextPanelHeight(imageSettingBean),BufferedImage.TYPE_INT_RGB);
    	this.setBounds(this.imageSettingBean.getWaterTextX(), this.imageSettingBean.getWaterTextY(), bufferedImage.getWidth(),bufferedImage.getHeight());
    	repaint();
    } 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//设置水印文字
		if(Constant.YES.equals(this.imageSettingBean.getWaterTextCehckValue())){
			
			this.setBounds(this.imageSettingBean.getWaterTextX(), this.imageSettingBean.getWaterTextY(), bufferedImage.getWidth(),bufferedImage.getHeight());
			ImageUtils.waterText(bufferedImage,waterText,this.imageSettingBean.getWaterTextDate(),this.imageSettingBean.getWaterTextFontValue(),this.imageSettingBean.getWaterTextColorValue(),Boolean.valueOf(this.imageSettingBean.getWaterTextBgColorCehckValue()),this.imageSettingBean.getWaterTextBgColor(),Boolean.valueOf(this.imageSettingBean.getWaterTextBorderCehckValue()),this.imageSettingBean.getWaterTextBorderColor(),0,imageSettingBean.getWaterTextFontValue().getSize());
            
			//设置透明度
			if(Constant.YES.equals(this.imageSettingBean.getWaterTextDiaphaneityCehckValue())){
				ImageUtils.opacity(g,this.imageSettingBean.getWaterTextDiaphaneityValue());
			}
			g.drawImage(bufferedImage, 0, 0,bufferedImage.getWidth(),bufferedImage.getHeight(), this);

			g.dispose();
		}else{
			this.setBounds(0, 0, 0,0);
			bufferedImage = null;
		}
	}

	/**
	 * 根据参数重新绘制图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-26 下午5:53:06
	 * @param imageSettingBean
	 * @return void
	 */
	public void repaintWaterText(ImageSettingBean imageSettingBean){
		this.imageSettingBean = imageSettingBean;
		if((null == bufferedImage && null != imageSettingBean.getWaterText() && imageSettingBean.getWaterText().length > 0) || (null != imageSettingBean.getWaterText() && imageSettingBean.getWaterText().length > 0 && !imageSettingBean.getWaterText().equals(waterText))){
			initWaterTextPanel(imageSettingBean);
		}
		this.repaint();
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	private int getWaterTextPanelWidth(ImageSettingBean imageSettingBean){
		int textMaxWidth = getMaxLength(imageSettingBean);
		if(textMaxWidth > DEFAULT_WIDTH){
			return textMaxWidth;
		}
		return DEFAULT_WIDTH;
	}
	private int getMaxLength(ImageSettingBean imageSettingBean){
		int maxLength = 0;
		int tempLength = 0;
		for(String text : imageSettingBean.getWaterText()){
			tempLength = ImageUtils.getLength(text) * imageSettingBean.getWaterTextFontValue().getSize();
			if(tempLength > maxLength){
				maxLength = tempLength;
			}
		}
		if(StringUtils.isNotBlank(imageSettingBean.getWaterTextDate())){
			tempLength = ImageUtils.getLength(imageSettingBean.getWaterTextDate()) * imageSettingBean.getWaterTextFontValue().getSize();
			if(tempLength > maxLength){
				maxLength = tempLength;
			}
		}
		
		return maxLength;
	}
	private int getWaterTextPanelHeight(ImageSettingBean imageSettingBean){
		int textMaxHeight = getTextHeight(imageSettingBean);
		//如果文字总高度 大于边框高度,则改变边框高度为现有文字高度+2倍文字大小
		if(textMaxHeight > DEFAULT_HEIGHT){
			return textMaxHeight;
		}
		return DEFAULT_HEIGHT;
	}
	private int getTextHeight(ImageSettingBean imageSettingBean){
		//文字高度
		int textHeight = 0;
		if(null != imageSettingBean.getWaterText() && imageSettingBean.getWaterText().length > 0){
			textHeight = imageSettingBean.getWaterText().length * imageSettingBean.getWaterTextFontValue().getSize();
		}
		if(StringUtils.isNotBlank(imageSettingBean.getWaterTextDate())){
			textHeight = textHeight + imageSettingBean.getWaterTextFontValue().getSize();
		}
		textHeight = textHeight + imageSettingBean.getWaterTextFontValue().getSize();
		return textHeight;
	}
	


}
