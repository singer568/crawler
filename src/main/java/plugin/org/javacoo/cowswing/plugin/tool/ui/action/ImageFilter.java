/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.action;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FilenameUtils;

/**
 * 图片文件过滤器
 * 
 * @author DuanYong
 * @since 2012-12-15下午11:21:21
 * @version 1.0
 */
public class ImageFilter implements FileFilter {
	/***
	 * 扩展名数组 
	 */
	private final String[] extensions;

	public ImageFilter(String... extensions) {
		if (extensions == null || extensions.length == 0) {
            throw new IllegalArgumentException(
                    "扩展名不能为空");
        }
		this.extensions = new String[extensions.length];
		for (int i = 0; i < extensions.length; i++) {
			if (extensions[i] == null || extensions[i].length() == 0) {
				throw new IllegalArgumentException(
						"每个扩展名不能为空");
			}
			this.extensions[i] = extensions[i];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File pathname) {
		if (null != pathname && pathname.exists() && pathname.isFile()) {
			if (FilenameUtils.isExtension(pathname.getName(), extensions)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
