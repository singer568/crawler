package com.bjm.pms.crawler.core.util.File;

import java.io.InputStream;

/**
 * 文件操作工具类接口
 * @author javacoo
 * @since 2011-11-15
 */
public interface FileHelper {
	/**
	 * 保存文件
	 * @param in InputStream
	 * @param savePath 文件全路径
	 */
	void saveFile(InputStream in,String savePath);
}
