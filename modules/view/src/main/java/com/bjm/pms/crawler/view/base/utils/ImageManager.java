/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.base.utils;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ResouceLoader;


/**
 * 图片资源管理工具类
 *@author DuanYong
 *@since 2012-11-4下午5:04:36
 *@version 1.0
 */
public class ImageManager {
	protected static Logger logger = Logger.getLogger(ImageManager.class);
	private static HashMap<String, ImageIcon> imageRegistry = new HashMap<String, ImageIcon>();

	public static HashMap<String, ImageIcon> getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new HashMap<String, ImageIcon>();
		}
		return imageRegistry;
	}

	public static ImageIcon getImageIcon(String imageName) {
		return getImageIcon(imageName, null);
	}

	public static ImageIcon getImageIcon(String imageName, String description) {
		ImageIcon getImageIcon = imageRegistry.get(imageName);
		if (getImageIcon == null) {
			getImageIcon = description == null ? new ImageIcon(ResouceLoader
					.getResouce(imageName)) : new ImageIcon(ResouceLoader
					.getResouce(imageName), description);
			imageRegistry.put(imageName, getImageIcon);
		}
		return getImageIcon;
	}

	public static Image getImage(String imageName) {
		return getImageIcon(imageName).getImage();
	}
	
	public static ImageIcon getImageIconByShortName(String imageName) {
		return getImageIcon(Constant.ICON_DIR + Constant.getSlash() + imageName);
	}
	
	public static Image getImageByShortName(String imageName) {
		return getImageIcon(Constant.ICON_DIR + Constant.getSlash() + imageName)
				.getImage();
	}
	
	public static String getImagePath(String imageName){
		return ResouceLoader.getResouce(Constant.PACKAGE_IMAGE_ICONS + "/" + imageName).getPath();
	}

}
