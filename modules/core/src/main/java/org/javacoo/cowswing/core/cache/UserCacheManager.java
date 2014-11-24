/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.core.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javacoo.cowswing.base.constant.Constant;
import org.springframework.util.StringUtils;


/**
 * 用户缓存
 * <p>说明:</p>
 * <li>缓存用户登陆相关信息,最后一次登录柜员号及登陆方式，以及记录所有登录成功的柜员号</li>
 * @author DuanYong
 * @since 2013-5-7 下午2:40:12
 * @version 1.0
 */
public class UserCacheManager {
    /**缓存*/
	private Map<String, String> propertyCache = new ConcurrentHashMap<String, String>();
    /**路径*/
	private String filePath = null;
	private final Log logger = LogFactory.getLog(getClass());
    /**唯一实例*/
	private static volatile UserCacheManager instance = null;

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static synchronized UserCacheManager getInstance() {
		if (null == instance) {
			 instance = new UserCacheManager();
		} 
		return instance;
	}

	private UserCacheManager() {
		if(logger.isInfoEnabled()){
			logger.info("开始进入userinit;");
		}
		//在用户目录下生成登录的记录文件 记录登录人和最后登录方式
		String userDir = Constant.SYSTEM_ROOT_PATH +File.separator+ Constant.PACKAGE_DATA;
		File userProperties = new File(userDir);
		if (!userProperties.exists()) {
			try{
				userProperties.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		String resPath = userProperties.getAbsolutePath();
		if (StringUtils.hasText(resPath)) {
			String root = resPath;
			filePath = root +File.separator+ "users.properties";
			if(logger.isInfoEnabled()){
				logger.info("本地文件地址......"+filePath);
			}
			checkFile(filePath);
			init();
		}else{
			throw new RuntimeException("请检查"+resPath+"目录下是否存在配置文件users.properties");
		}
		
	}
	/**
	 * 检查配置文件是否存在，不存在则创建
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-7-11 下午6:28:54
	 * @version 1.0
	 * @exception 
	 * @param filePath
	 */
	private void checkFile(String filePath){
		File proFile = new File(filePath);
		if(!proFile.exists()){
			try {
				proFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("创建文件失败:"+filePath);
				throw new RuntimeException("创建文件失败:"+filePath);
			}
		}
	}

	private void init() {
		if(logger.isInfoEnabled()){
			logger.info("开始加载本地文件......"+filePath);
		}
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			PropertyResourceBundle bundle = new PropertyResourceBundle(
					fileInputStream);
			Enumeration<String> enumer = bundle.getKeys();
			while (enumer.hasMoreElements()) {
				String key = enumer.nextElement();
				if(logger.isInfoEnabled()){
					logger.info("开始加载本地文件属性......"+key);
				}
				propertyCache.put(key,((String) bundle.handleGetObject(key)).trim());
			}
		} catch (IOException e) {
			logger.error("无法找到 " + filePath + " 文件，请检查文件是否存在！", e);
		} finally {
			if (fileInputStream != null)
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 设置属性值
	 * <p>方法说明:</>
	 * 写入单个键,值
	 * @author DuanYong
	 * @since 2013-4-12 上午9:51:25
	 * @version 1.0
	 * @exception 
	 * @param key 键
	 * @param value 值
	 */
	public void setValue(String key,String value){
		if(StringUtils.hasText(key)){
			Map<String,String> propertiesMap = new HashMap<String,String>();
			propertiesMap.put(key, value);
			setValue(propertiesMap);
		}
	}
	/**
	 * 设置属性值
	 * <p>方法说明:</>
	 * 写入属性值到属性文件
	 * 刷新缓存
	 * @author DuanYong
	 * @since 2013-4-12 上午9:49:18
	 * @version 1.0
	 * @exception 
	 * @param propertiesMap 键,值对map
	 */
	public void setValue(Map<String, String> propertiesMap){
		if(propertiesMap != null && !propertiesMap.isEmpty()){
			writePropertyFile(propertiesMap);
			refresh();
		}
	}
	/**
	 * 刷新缓存
	 * <p>方法说明:</>
	 * 清空缓存
	 * 重新读取配置文件
	 * @author DuanYong
	 * @since 2013-4-12 上午9:45:09
	 * @version 1.0
	 * @exception
	 */
	private void refresh(){
		clear();
		init();
	}
	/**
	 * 清空缓存
	 * <p>方法说明:</>
	 * 清空缓存
	 * @author DuanYong
	 * @since 2013-4-12 上午9:48:42
	 * @version 1.0
	 * @exception
	 */
	private void clear(){
		propertyCache.clear();
	}
	/**
	 * 写入属性文件
	 * <p>方法说明:</>
	 * 将键,值对写入属性文件
	 * @author DuanYong
	 * @since 2013-4-12 上午9:34:55
	 * @version 1.0
	 * @exception 
	 * @param propertiesMap 键,值对map
	 */
	private void writePropertyFile(Map<String, String> propertiesMap){
		Properties prop = new Properties();
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 构建输出流
			fos = new FileOutputStream(filePath);
			String key = "";
			for(Iterator<String> it = propertiesMap.keySet().iterator();it.hasNext();){
				key = it.next();
				prop.setProperty(key, propertiesMap.get(key));
				if(logger.isInfoEnabled()){
					logger.info("写入属性配置文件:" + filePath + "成功,属性名称：" + key
							+ ",属性值：" + propertiesMap.get(key));
				}
			}
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "已经成功登陆过的用户ID");
		} catch (IOException e) {
			logger.error("写入属性配置文件:" + filePath + "失败");
			e.printStackTrace();
		}finally{
			if(fis !=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String getValue(String key) {
		if(propertyCache.containsKey(key)){
			return propertyCache.get(key);
		}
		return "";
	}
	/**
	 * 是否存在此值
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-7-22 下午3:08:34
	 * @version 1.0
	 * @exception 
	 * @param key 属性KEY
	 * @param value 待验证值
	 * @return 存在则返回TRUE
	 */
	public boolean hasValue(String key,String value){
		if(propertyCache.containsKey(key) && StringUtils.hasText(value)){
			String tempValue = propertyCache.get(key);
			if(StringUtils.hasText(tempValue)){
				if(tempValue.contains(",")){
					tempValue = tempValue.substring(0,tempValue.length() - 1);
					String[] tempValueArray = tempValue.split(",");
					for(String v : tempValueArray){
						if(value.contains(v)){
							return true;
						}
					}
				}else{
					if(value.contains(tempValue)){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 验证值是否与属性值相等
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-7-22 下午4:17:24
	 * @version 1.0
	 * @exception 
	 * @param key 属性KEY
	 * @param value 待验证值
	 * @return 相等返回TRUE
	 */
	public boolean equalsValue(String key,String value){
		if(propertyCache.containsKey(key) && StringUtils.hasText(value)){
			String tempValue = propertyCache.get(key);
			if(StringUtils.hasText(tempValue)){
				if(tempValue.contains(",")){
					tempValue = tempValue.substring(0,tempValue.length() - 1);
					String[] tempValueArray = tempValue.split(",");
					for(String v : tempValueArray){
						if(value.equals(v)){
							return true;
						}
					}
				}else{
					if(value.equals(tempValue)){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 删除属性值
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-7-22 下午4:40:17
	 * @version 1.0
	 * @exception 
	 * @param key 属性KEY
	 * @param value 待删除属性值
	 */
	public void removeValue(String key,String value){
		if(propertyCache.containsKey(key) && StringUtils.hasText(value)){
			String tempValue = propertyCache.get(key);
			if(StringUtils.hasText(tempValue) && equalsValue(key,value)){
				if(tempValue.contains(",")){
					tempValue = tempValue.replace(value+",", "");
				}else{
					tempValue = "";
				}
				setValue(key,tempValue);
				refresh();
			}
		}
	}
}
