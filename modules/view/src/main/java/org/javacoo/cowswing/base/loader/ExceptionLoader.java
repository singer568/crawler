/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.base.loader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.javacoo.cowswing.base.constant.Constant;

/**
 * 异常信息加载类
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-2 上午10:27:48
 * @version 1.0
 */
public class ExceptionLoader {
	/**异常信息资源绑定对象*/
	private static ResourceBundle exceptionBundle;
	/**选择的语言*/
	private static Locale languageSelected;
	/**ClassLoader*/
	private static final ClassLoader WD_CLASS_LOADER;
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
	public static void setLanguage(Locale locale) {
		if (locale == null) {
			exceptionBundle = ResourceBundle.getBundle(Constant.EXCEPTION_BUNDLE_NAME, Locale.getDefault(),WD_CLASS_LOADER);
		} else {
			exceptionBundle = ResourceBundle.getBundle(Constant.EXCEPTION_BUNDLE_NAME, locale,WD_CLASS_LOADER);
		}
		languageSelected = exceptionBundle.getLocale();
	}
	/**
	 * 取得选择的语言
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:04:08
	 * @return
	 * @return Locale
	 */
	public static Locale getLanguageSelected() {
		return languageSelected;
	}
	/**
	 * 根据键，取得值
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-4 下午4:04:29
	 * @param key
	 * @return String
	 */
	public static String getString(String key) {
		if (exceptionBundle != null) {
			String result;
			try {
				result = exceptionBundle.getString(key);
			} catch (MissingResourceException e) {
				return key;
			}
			return result;
		}
		return key;
	}
}
