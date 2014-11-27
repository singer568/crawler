/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.gather.service.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjm.pms.crawler.view.base.service.beans.CrawlerBaseBean;

/**
 * 数据库信息值对象
 *@author DuanYong
 *@since 2013-2-14上午9:24:45
 *@version 1.0
 */
public class CrawlerDataBaseBean extends CrawlerBaseBean{
	/** 数据库类型对照MAP*/
	public final static Map<String,String> DATABASE_TYPE_MAP = new HashMap<String,String>();
	/** 数据库类型URL对照MAP*/
	public final static Map<String,String> DATABASE_TYPE_URL_MAP = new HashMap<String,String>();
	/** 数据库类型CLASSNAME对照MAP*/
	public final static Map<String,String> DATABASE_TYPE_CLASSNAME_MAP = new HashMap<String,String>();
	static{
		DATABASE_TYPE_MAP.put("Oracle(thin)", "Oracle-thin");
		DATABASE_TYPE_MAP.put("MySQL", "MySQL");
		DATABASE_TYPE_MAP.put("DB2", "DB2");
		DATABASE_TYPE_MAP.put("Imformix", "Imformix");
		DATABASE_TYPE_MAP.put("Derby", "Derby");
		DATABASE_TYPE_MAP.put("PostgreSQL", "PostgreSQL");
		DATABASE_TYPE_MAP.put("JDBC-ODBC", "JDBC-ODBC");
		DATABASE_TYPE_MAP.put("Microsoft SQL Server 2000", "Microsoft-SQL-Server-2000");
		DATABASE_TYPE_MAP.put("Microsoft SQL Server 2005", "Microsoft-SQL-Server-2005");
		DATABASE_TYPE_MAP.put("Sybase", "Sybase");
		DATABASE_TYPE_MAP.put("Firebird", "Firebird");
		DATABASE_TYPE_MAP.put("HSQLDB", "HSQLDB");
		DATABASE_TYPE_MAP.put("SqlLite", "SqlLite");
		
		DATABASE_TYPE_URL_MAP.put("Oracle(thin)", "jdbc:oracle:thin:@<server>[:<1521>]:<database_name>");
		DATABASE_TYPE_URL_MAP.put("MySQL", "jdbc:mysql://<hostname>[<:3306>]/<dbname>");
		DATABASE_TYPE_URL_MAP.put("DB2", "jdbc:db2://<host_name>:<port>/<dbname>");
		DATABASE_TYPE_URL_MAP.put("Imformix", "jdbc:informix-sqli://<host_name>:<1533>/<dbname>:INFORMIXSERVER=infserver");
		DATABASE_TYPE_URL_MAP.put("Derby", "jdbc:derby://<server_name>:<port>/<databaseName>[;create=true]");
		DATABASE_TYPE_URL_MAP.put("PostgreSQL", "jdbc:postgresql://<host_name>/<dbname>");
		DATABASE_TYPE_URL_MAP.put("JDBC-ODBC", "jdbc:odbc:<dbsource>");
		DATABASE_TYPE_URL_MAP.put("Microsoft SQL Server 2000", "jdbc:microsoft:sqlserver://<server_name>:<1433>");
		DATABASE_TYPE_URL_MAP.put("Microsoft SQL Server 2005", "jdbc:sqlserver://<server_name>:<port>[;databaseName=<dbname>]");
		DATABASE_TYPE_URL_MAP.put("Sybase", "jdbc:jtds:sybase://<hostname>[:<port>]/<dbname>");
		DATABASE_TYPE_URL_MAP.put("Firebird", "jdbc:firebirdsql:[//host[:port]/]<database>");
		DATABASE_TYPE_URL_MAP.put("HSQLDB", "jdbc:hsqldb:hsql://<server>[:<1476>]");
		DATABASE_TYPE_URL_MAP.put("SqlLite", "jdbc:sqlite:<dbname>");
		
		DATABASE_TYPE_CLASSNAME_MAP.put("Oracle(thin)", "oracle.jdbc.dirver.OracleDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("MySQL", "com.mysql.jdbc.Driver");
		DATABASE_TYPE_CLASSNAME_MAP.put("DB2", "com.ibm.db2.jdbc.app.DB2Driver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Imformix", "com.informix.jdbc.IfxDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Derby", "org.apache.derby.jdbc.EmbeddedDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("PostgreSQL", "org.postgresql.Driver");
		DATABASE_TYPE_CLASSNAME_MAP.put("JDBC-ODBC", "sun.jdbc.odbc.JdbcOdbcDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Microsoft SQL Server 2000", "com.microsoft.jdbc.sqlserver.SQLServerDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Microsoft SQL Server 2005", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Sybase", "com.sybase.jdbc.SybDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("Firebird", "org.firebirdsql.jdbc.FBDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("HSQLDB", "org.hsqldb.jdbcDriver");
		DATABASE_TYPE_CLASSNAME_MAP.put("SqlLite", "org.sqlite.JDBC");
	}
	/**主键*/
	private Integer dataBaseId;
	private List<Integer> dataBaseIdList;
	/**描述*/
	private String description;
	/**URL*/
	private String url;
	/**用户名*/
	private String userName;
	/**密码*/
	private String password;
	/**驱动名称*/
	private String className;
	/**数据库类型*/
	private String type;
	
	public CrawlerDataBaseBean(){
	}
	
	public CrawlerDataBaseBean(Integer dataBaseId){
		this.dataBaseId = dataBaseId;
	}
	
	public Integer getDataBaseId() {
		return dataBaseId;
	}
	public void setDataBaseId(Integer dataBaseId) {
		this.dataBaseId = dataBaseId;
	}
	
	public List<Integer> getDataBaseIdList() {
		return dataBaseIdList;
	}
	public void setDataBaseIdList(List<Integer> dataBaseIdList) {
		this.dataBaseIdList = dataBaseIdList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return  description;
	}
	
	

}
