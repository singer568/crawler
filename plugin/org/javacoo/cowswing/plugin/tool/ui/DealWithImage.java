/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageBean;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageSettingBean;
import org.javacoo.cowswing.ui.util.ImageUtils;
import org.javacoo.cowswing.ui.view.dialog.RunnableProgress;

/**
 * 图片处理
 * 
 * @author DuanYong
 * @since 2013-1-26上午10:59:48
 * @version 1.0
 */
public class DealWithImage implements RunnableProgress {
	protected static Logger logger = Logger.getLogger(DealWithImage.class);
	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 50;
	private static final String DEFAULT_FORMAT_NAME = "JPEG";
	private List<ImageBean> imageList;
	private ImageSettingBean imageSettingBean;
	/**处理图片总数*/
	private int total;
	/**当前数量*/
	private int currentNum;
	/**当前信息*/
	private String currentMsg;

	public DealWithImage(List<ImageBean> imageList,
			ImageSettingBean imageSettingBean) {
		this.imageList = imageList;
		this.imageSettingBean = imageSettingBean;
		this.setTotal(imageList.size());
	}

	private void doRun() {
		for (int i=0;i<imageList.size();i++) {
			this.imageSettingBean.setExampleImagePath(imageList.get(i).getPath());
			this.setCurrentNum(i+1);
			this.setCurrentMsg(imageList.get(i).getName());
			dealWith(this.imageSettingBean);
			//通知监控
			CowSwingObserver.getInstance().notifyEvents(new CowSwingEvent(this,CowSwingEventType.ProgressChangeEvent,this));
			logger.info(imageList.get(i).getPath());
		}
	}

	public static void dealWith(ImageSettingBean imageSettingBean) {

		BufferedImage bufferedImage = null;
		BufferedImage waterBufferedImage = null;
		BufferedImage waterTextBufferedImage = null;
		// 保存质量
		String fullSaveImagePath = imageSettingBean.getExampleImagePath();
		if(StringUtils.isNotBlank(imageSettingBean.getSavePath())){
			fullSaveImagePath = imageSettingBean.getSavePath()
					+ System.getProperty("file.separator")
					+ FilenameUtils.getName(imageSettingBean
							.getExampleImagePath());
		}
		logger.info("图片处理保存路径："+fullSaveImagePath);
		String formatName = DEFAULT_FORMAT_NAME;
		
		/** 缩放方式：true为按比例,false为按分辨率 */
		if (Constant.YES.equals(imageSettingBean.getResizeValue())) {
			bufferedImage = ImageUtils.scaleByScale(
					imageSettingBean.getExampleImagePath(),
					imageSettingBean.getResizeByScaleValue());
		} else {
			// 是否等比例
			if (Constant.YES.equals(imageSettingBean.getEqualScaleValue())) {
				bufferedImage = ImageUtils.scaleByWH(
						imageSettingBean.getExampleImagePath(),
						imageSettingBean.getResizeByPixelWidth(),
						imageSettingBean.getResizeByPixelHeight(), true);
			} else {
				bufferedImage = ImageUtils.scaleByWH(
						imageSettingBean.getExampleImagePath(),
						imageSettingBean.getResizeByPixelWidth(),
						imageSettingBean.getResizeByPixelHeight(), false);
			}
		}
		if (Constant.YES.equals(imageSettingBean.getSaveFormatValue())) {
			formatName = imageSettingBean.getSaveFormatType();
			fullSaveImagePath = fullSaveImagePath.substring(0, fullSaveImagePath.lastIndexOf(".")+1)+ImageSettingBean.FORMAT_TYPE_MAP.get(formatName);
			bufferedImage = ImageUtils.scaleByQuality(bufferedImage,
					fullSaveImagePath,
					imageSettingBean.getSaveImageQualityValue());
		} else {
			bufferedImage = ImageUtils.scaleByQuality(bufferedImage,
					fullSaveImagePath, 100);
		}
		// 亮度
		if (Constant.YES.equals(imageSettingBean
				.getImageLightenessCheckValue())) {
			bufferedImage = ImageUtils.lightenessContrast(bufferedImage,
					imageSettingBean.getImageContrast(),
					imageSettingBean.getImageLighteness());
		}
		// 对比度
		if (Constant.YES.equals(imageSettingBean.getImageContrastValue())) {
			bufferedImage = ImageUtils.lightenessContrast(bufferedImage,
					imageSettingBean.getImageContrast(),
					imageSettingBean.getImageLighteness());
		}
		// 黑白
		if (Constant.YES.equals(imageSettingBean
				.getBlackAndwhiteCheckValue())) {
			bufferedImage = ImageUtils.gray(bufferedImage);
		}
		// 锐化
		if (Constant.YES.equals(imageSettingBean.getSharpCheckValue())) {
			bufferedImage = ImageUtils.sharpen(bufferedImage);
		}
		// 模糊
		if (Constant.YES.equals(imageSettingBean.getBlurCheckValue())) {
			bufferedImage = ImageUtils.blur(bufferedImage);
		}
		// 旋转
		if (Constant.YES.equals(imageSettingBean.getCirCehckValue())) {
			int xScale = 1;
			int yScale = 1;
			if (Constant.YES.equals(imageSettingBean
					.getCirCehckTypeHorizontalValue())) {
				xScale = -1;
			}
			if (Constant.YES.equals(imageSettingBean
					.getCirCehckTypeVerticalValue())) {
				yScale = -1;
			}
			bufferedImage = ImageUtils.gyral(bufferedImage, xScale, yScale,
					imageSettingBean.getCirValue());
		} 

		// 剪切
		if (Constant.YES.equals(imageSettingBean.getCutCehckValue())) {
			bufferedImage = ImageUtils.cutByXYWH(bufferedImage,
					imageSettingBean.getCutPanelX(),
					imageSettingBean.getCutPanelY(),
					imageSettingBean.getCutWidth(),
					imageSettingBean.getCutHeight());
		}

		// 设置水印图片
		if (Constant.YES
				.equals(imageSettingBean.getWaterImageCehckValue())) {
			try {
				waterBufferedImage = ImageUtils
						.getBufferedImageByImagePath(imageSettingBean
								.getWaterImagePath());
				// if(Constant.YES.equals(imageSettingBean.getWaterImageCancelBgValue())){
				// bufferedImage = ImageUtils.backGround(g,bufferedImage);
				// }
				// 设置透明度
				if (Constant.YES.equals(imageSettingBean
						.getWaterImageDiaphaneityCehckValue())) {
					bufferedImage = ImageUtils.pressImage(waterBufferedImage,
							bufferedImage, imageSettingBean
									.getWaterImageX(), imageSettingBean
									.getWaterImageY(), imageSettingBean
									.getWaterImageDiaphaneityValue()/100f);

				} else {
					bufferedImage = ImageUtils.pressImage(waterBufferedImage,
							bufferedImage,
							imageSettingBean.getWaterImageX(),
							imageSettingBean.getWaterImageY(), 1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// 设置水印文字
		if (Constant.YES.equals(imageSettingBean.getWaterTextCehckValue())) {
			waterTextBufferedImage = new BufferedImage(getWaterTextPanelWidth(imageSettingBean),getWaterTextPanelHeight(imageSettingBean),BufferedImage.TYPE_INT_RGB);
			ImageUtils.waterText(waterTextBufferedImage,imageSettingBean.getWaterText(),imageSettingBean.getWaterTextDate(),imageSettingBean.getWaterTextFontValue(),imageSettingBean.getWaterTextColorValue(),Boolean.valueOf(imageSettingBean.getWaterTextBgColorCehckValue()),imageSettingBean.getWaterTextBgColor(),Boolean.valueOf(imageSettingBean.getWaterTextBorderCehckValue()),imageSettingBean.getWaterTextBorderColor(),0,imageSettingBean.getWaterTextFontValue().getSize());
            
			//设置透明度
			if(Constant.YES.equals(imageSettingBean.getWaterTextDiaphaneityCehckValue())){
				bufferedImage = ImageUtils.pressImage(waterTextBufferedImage,
						bufferedImage, imageSettingBean.getWaterTextX(), imageSettingBean
								.getWaterTextY(), imageSettingBean.getWaterTextDiaphaneityValue()/100f);
			}else{
				bufferedImage = ImageUtils.pressImage(waterTextBufferedImage,
						bufferedImage, imageSettingBean.getWaterTextX(), imageSettingBean
								.getWaterTextY(),1);	
			}
		}
		ImageUtils.saveAsImage(bufferedImage, fullSaveImagePath,formatName);
		bufferedImage.flush();
		waterBufferedImage.flush();
		waterTextBufferedImage.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.doRun();
	}
	private static int getWaterTextPanelWidth(ImageSettingBean imageSettingBean){
		int textMaxWidth = getMaxLength(imageSettingBean);
		if(textMaxWidth > DEFAULT_WIDTH){
			return textMaxWidth;
		}
		return DEFAULT_WIDTH;
	}
	private static int getMaxLength(ImageSettingBean imageSettingBean){
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
	private static int getWaterTextPanelHeight(ImageSettingBean imageSettingBean){
		int textMaxHeight = getTextHeight(imageSettingBean);
		//如果文字总高度 大于边框高度,则改变边框高度为现有文字高度+2倍文字大小
		if(textMaxHeight > DEFAULT_HEIGHT){
			return textMaxHeight;
		}
		return DEFAULT_HEIGHT;
	}
	private static int getTextHeight(ImageSettingBean imageSettingBean){
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

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.Progress#getTotal()
	 */
	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return this.total;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.Progress#getCurrentNum()
	 */
	@Override
	public int getCurrentNum() {
		// TODO Auto-generated method stub
		return this.currentNum;
	}
	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.dialog.Progress#getCurrentMsg()
	 */
	@Override
	public String getCurrentMsg() {
		// TODO Auto-generated method stub
		return this.currentMsg;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	public void setCurrentMsg(String currentMsg) {
		this.currentMsg = currentMsg;
	}
}
