/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.bean;

/**
 * 图片值对象
 *@author DuanYong
 *@since 2012-12-14下午10:54:03
 *@version 1.0
 */
public class ImageBean {
	/**路径*/
	private String path;
	/**名称*/
	private String name;
	/**类型*/
	private String type;
	/**类型*/
	private long size;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
//		if(0 != size){
//			size = size / 1024;
//		}
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	

}
