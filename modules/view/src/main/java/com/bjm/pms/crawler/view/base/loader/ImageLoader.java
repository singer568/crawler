package com.bjm.pms.crawler.view.base.loader;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.utils.ImageManager;

/**
 * 图标加载类
 *@author DuanYong
 *@since 2012-11-4下午3:41:35
 *@version 1.0
 */
public class ImageLoader {
	/**图标资源绑定对象*/
	private static ResourceBundle imageBundle;
	/**选择的图标资源*/
	private static Locale imageSelected;
	/**ClassLoader*/
	private static final ClassLoader WD_CLASS_LOADER;
	protected static Logger logger = Logger.getLogger(ImageLoader.class);
	static {
		WD_CLASS_LOADER = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
			@Override
			public ClassLoader run() {
				return new ClassLoader() {
					@Override
					public URL getResource(String name) {
						try {
							URL result = super.getResource(name);
							if (result != null) {
								return result;
							}
							return (new File(name)).toURI().toURL();
						} catch (MalformedURLException ex) {
							return null;
						}
					}
				};
			}
		});
	}
	/**
	 * 设置本地化语言
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:03:48
	 * @param locale
	 * @return void
	 */
	public static void setLanguage(Locale imageSelected) {
		logger.info("设置本地化语言:"+imageSelected);
		if (imageSelected == null) {
			imageBundle = ResourceBundle.getBundle(Constant.IMAGE_BUNDLE_NAME, Locale.getDefault(),WD_CLASS_LOADER);
		} else {
			imageBundle = ResourceBundle.getBundle(Constant.IMAGE_BUNDLE_NAME, imageSelected,WD_CLASS_LOADER);
		}
		imageSelected = imageBundle.getLocale();
	}
	/**
	 * 取得选择的图标资源类型
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:04:08
	 * @return
	 * @return Locale
	 */
	public static Locale getLanguageSelected() {
		return imageSelected;
	}
	/**
	 * 根据键，取图标资源
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:04:29
	 * @param key
	 * @return String
	 */
	public static ImageIcon getImageIcon(String key) {
		ImageIcon result = null;
		if (imageBundle != null) {
			try {
				String imagePath = imageBundle.getString(key);
				result = ImageManager.getImageIconByShortName(imagePath);
			} catch (MissingResourceException e) {
				System.out.println(e);
			}
		}
		return result;
	}
	/**
	 * 根据键，取图片资源
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:04:29
	 * @param key
	 * @return String
	 */
	public static Image getImage(String key) {
		Image result = null;
		if (imageBundle != null) {
			try {
				String imagePath = imageBundle.getString(key);
				result = ImageManager.getImageByShortName(imagePath);
			} catch (MissingResourceException e) {
				System.out.println(e);
			}
		}
		return result;
	}
	
	public static String getImagePath(String key){
		String imagePath = imageBundle.getString(key);
		return ImageManager.getImagePath(imagePath);
	}
}
