/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.webservice.manager.beans;

/**
 * 版本信息
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2014-9-18 上午11:35:17
 * @version 1.0
 */
public class VersionBean extends BaseBean{
	private Integer id;
	private double version;
	private String info;
	private String path;
	public VersionBean(){
		
	}
	public VersionBean(Integer id, double version, String info, String path) {
		super();
		this.id = id;
		this.version = version;
		this.info = info;
		this.path = path;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(double version) {
		this.version = version;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(version);
	}
 
	
	
}
