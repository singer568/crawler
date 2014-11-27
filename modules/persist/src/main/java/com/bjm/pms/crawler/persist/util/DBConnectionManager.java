package com.bjm.pms.crawler.persist.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * 管理类DBConnectionManager支持对一个或多个由属性文件定义的数据库连接
 * 池的访问.客户程序可以调用getInstance()方法访问本类的唯一实例.
 */
public class DBConnectionManager {
	protected Logger logger = Logger.getLogger(this.getClass());
	static private DBConnectionManager instance; // 唯一实例
	static private int clients;
	static private String defaultDbName;
	static private long defaultWaitTime;
	private Vector<Driver> drivers = new Vector<Driver>();
	private Hashtable<String,DBConnectionPool> pools = new Hashtable<String,DBConnectionPool>();

	/**
	 * 返回唯一实例.如果是第一次调用此方法,则创建实例
	 * 
	 * @return DBConnectionManager 唯一实例
	 */
	static synchronized public DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		clients++;
		return instance;
	}

	/**
	 * 建构函数私有以防止其它对象创建本类实例
	 */
	private DBConnectionManager() {
	}
    
	
	public void addConnConfig(ConnectionConfig config){
		if(!pools.containsKey(config.getId())){
			loadDriver(config.getClassName());
			createPool(config);
		}
	}
	public void updateConnConfig(ConnectionConfig config){
		removeConnConfig(config.getId());
		addConnConfig(config);
	}
	public void removeConnConfig(String id){
		if(null != pools.get(id)){
			pools.remove(id);
		}
	}
	/**
	 * 将连接对象返回给由名字指定的连接池
	 * 
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @param con
	 *            连接对象
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}
	/**
	 * 将连接对象返回给默认连接池
	 * @param con
	 */
	public void freeConnection(Connection con) {
		freeConnection(defaultDbName,con);
	}

	/**
	 * 获得一个可用的(空闲的)连接.如果没有可用连接,且已有连接数小于最大连接数 限制,则创建并返回新连接
	 * 
	 * @param name
	 *            在属性文件中定义的连接池名字
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = pools.get(name);
		if (pool != null) {
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * 获得一个可用连接.若没有可用连接,且已有连接数小于最大连接数限制, 则创建并返回新连接.否则,在指定的时间内等待其它线程释放连接.
	 * 
	 * @param name
	 *            连接池名字
	 * @param time
	 *            以毫秒计的等待时间
	 * @return Connection 可用连接或null
	 */
	public Connection getConnection(String name, long time) {
		DBConnectionPool pool = pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}
	/**
	 * 得到默认数据库连接
	 * 配置文件中配置的默认数据库名称及等待时间
	 * @return
	 */
	public Connection getConnection(){
		return getConnection(defaultDbName,defaultWaitTime);
	}

	public int getClient() {
		return clients;
	}

	/**
	 * 关闭所有连接,撤销驱动程序的注册
	 */
	public synchronized void release() {
		// 等待直到最后一个客户程序调用
		if (--clients != 0) {
			return;
		}

		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = allPools.nextElement();
			pool.release();
		}
		Enumeration<Driver> allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.info("撤销JDBC驱动程序 " + driver.getClass().getName() + "的注册");
			} catch (SQLException e) {
				logger.info("无法撤销下列JDBC驱动程序的注册: " + driver.getClass().getName()+":"+e);
			}
		}
	}

	/**
	 * 创建连接池实例
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-17 下午3:03:42
	 * @param config
	 * @return void
	 */
    private void createPool(ConnectionConfig config){
		int max;
		try {
			max = Integer.valueOf(config.getMaxconn()).intValue();
		} catch (NumberFormatException e) {
			logger.info("错误的最大连接数限制: " + config.getMaxconn() + " .连接池: " + config.getId());
			max = 0;
		}
		DBConnectionPool pool = new DBConnectionPool(config.getId(), config.getUrl(),config.getUserName(), config.getPassword(), max);
		pools.put(config.getId(), pool);
		logger.info("成功创建连接池" + config.getId());
    }

	/**
	 * 注册JDBC驱动程序
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-17 下午2:59:27
	 * @param driverClassName
	 * @return void
	 */
	private void loadDriver(String driverClassName){
		try {
			Driver driver = (Driver) Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(driver);
			drivers.addElement(driver);
			logger.info("成功注册JDBC驱动程序" + driverClassName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("无法注册JDBC驱动程序: " + driverClassName + ", 错误: " + e);
		}
	}


	/**
	 * 此内部类定义了一个连接池.它能够根据要求创建新连接,直到预定的最 大连接数为止.在返回连接给客户程序之前,它能够验证连接的有效性.
	 */
	class DBConnectionPool {
		private int checkedOut;
		private Vector<Connection> freeConnections = new Vector<Connection>();
		private int maxConn;
		private String name;
		private String password;
		private String URL;
		private String user;

		/**
		 * 创建新的连接池
		 * 
		 * @param name
		 *            连接池名字
		 * @param URL
		 *            数据库的JDBC URL
		 * @param user
		 *            数据库帐号,或 null
		 * @param password
		 *            密码,或 null
		 * @param maxConn
		 *            此连接池允许建立的最大连接数
		 */
		public DBConnectionPool(String name, String URL, String user,
				String password,int maxConn) {
			this.name = name;
			this.URL = URL;
			this.user = user;
			this.password = password;
			this.maxConn = maxConn;
		}

		/**
		 * 将不再使用的连接返回给连接池
		 * 
		 * @param con
		 *            客户程序释放的连接
		 */
		public synchronized void freeConnection(Connection con) {
			// 将指定连接加入到向量末尾
			freeConnections.addElement(con);
			checkedOut--;
			notifyAll();
		}

		/**
		 * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
		 * 然后递归调用自己以尝试新的可用连接.
		 */
		public synchronized Connection getConnection() {
			Connection con = null;
			if (freeConnections.size() > 0) {
				// 获取向量中第一个可用连接
				con = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);
				try {
					if (con.isClosed()) {
						logger.info("从连接池" + name + "删除一个无效连接");
						// 递归调用自己,尝试再次获取可用连接
						con = getConnection();
					}
				} catch (SQLException e) {
					logger.info("从连接池" + name + "删除一个无效连接");
					// 递归调用自己,尝试再次获取可用连接
					con = getConnection();
				}
			} else if (maxConn == 0 || checkedOut < maxConn) {
				con = newConnection();
			}
			if (con != null) {
				checkedOut++;
			}
			return con;
		}

		/**
		 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
		 * 
		 * @param timeout
		 *            以毫秒计的等待时间限制
		 */
		public synchronized Connection getConnection(long timeout) {
			long startTime = new Date().getTime();
			Connection con;
			while ((con = getConnection()) == null) {
				try {
					wait(timeout);
				} catch (InterruptedException e) {
				}
				if ((new Date().getTime() - startTime) >= timeout) {
					// wait()返回的原因是超时
					return null;
				}
			}
			return con;
		}

		/**
		 * 关闭所有连接
		 */
		public synchronized void release() {
			Enumeration<Connection> allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection con = allConnections.nextElement();
				try {
					con.close();
					logger.info("关闭连接池" + name + "中的一个连接");
				} catch (SQLException e) {
					logger.info("无法关闭连接池" + name + "中的连接");
				}
			}
			freeConnections.removeAllElements();
		}

		/**
		 * 创建新的连接
		 */
		private Connection newConnection() {
			Connection con = null;
			try {
				if (user == null) {
					con = DriverManager.getConnection(URL);
				} else {
					con = DriverManager.getConnection(URL, user, password);
				}
				logger.info("连接池" + name + "创建一个新的连接");
			} catch (SQLException e) {
				logger.info("无法创建下列URL的连接: " + URL);
				logger.info(e);
				return null;
			}
			return con;
		}
	}
}
