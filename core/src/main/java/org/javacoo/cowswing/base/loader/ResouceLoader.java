package org.javacoo.cowswing.base.loader;

import java.net.MalformedURLException;
import java.net.URL;

import org.javacoo.cowswing.base.constant.Constant;



/**
 * 资源帮助类
 *@author DuanYong
 *@since 2012-11-4下午5:06:20
 *@version 1.0
 */
public class ResouceLoader {
	public static URL getFileURL(String url_name) {
		try {
			return new URL(Constant.PROTOCOL_FILE + url_name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Malformed URL " + url_name, e);
		}
	}

	public static String getFilePath(String filePath) {
		return getFileURL(filePath).getPath();
	}
	public static URL getResouce(String url_name) {
		//return getFileURL(url_name);
		return ClassLoader.getSystemResource(url_name);
	}

}
