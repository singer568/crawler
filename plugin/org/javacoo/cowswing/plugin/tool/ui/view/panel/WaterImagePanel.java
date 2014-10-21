/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.view.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageSettingBean;
import org.javacoo.cowswing.ui.util.ImageUtils;

/**
 * 水印图片panel
 *@author DuanYong
 *@since 2012-12-27下午9:46:47
 *@version 1.0
 */
public class WaterImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage bufferedImage;
	private String waterImagePath;
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
    private void initExampleImagePanel(ImageSettingBean imageSettingBean){
    	try {
    		BufferedImage tempBufferedImage = ImageUtils.getBufferedImageByImagePath(imageSettingBean.getWaterImagePath());
			if(tempBufferedImage.getHeight() > 200 || tempBufferedImage.getWidth() > 200){
				JOptionPane.showMessageDialog(null, LanguageLoader.getString("ToolImage.imageSetWaterImageMaxMessage"), LanguageLoader.getString("ToolImage.imageSetWaterImageMaxTitle"), JOptionPane.CLOSED_OPTION);
				return;
			}else{
				waterImagePath = imageSettingBean.getWaterImagePath();
				bufferedImage = tempBufferedImage;
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	this.setBounds(0, 0, bufferedImage.getWidth(),bufferedImage.getHeight());
    	repaint();
    } 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//设置水印图片
		if(Constant.YES.equals(this.imageSettingBean.getWaterImageCehckValue())){
			
			this.setBounds(this.imageSettingBean.getWaterImageX(), this.imageSettingBean.getWaterImageY(), bufferedImage.getWidth(),bufferedImage.getHeight());
			
//			if(Constant.YES.equals(this.imageSettingBean.getWaterImageCancelBgValue())){
//				bufferedImage = ImageUtils.backGround(g,bufferedImage);
//			}
			//设置透明度
			if(Constant.YES.equals(this.imageSettingBean.getWaterImageDiaphaneityCehckValue())){
				ImageUtils.opacity(g,this.imageSettingBean.getWaterImageDiaphaneityValue());
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
	public void repaintWaterImage(ImageSettingBean imageSettingBean){
		this.imageSettingBean = imageSettingBean;
		if((null == bufferedImage && StringUtils.isNotBlank(imageSettingBean.getWaterImagePath()))|| (StringUtils.isNotBlank(imageSettingBean.getWaterImagePath()) && !imageSettingBean.getWaterImagePath().equals(waterImagePath))){
			initExampleImagePanel(imageSettingBean);
		}
		this.repaint();
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	


}
