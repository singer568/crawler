/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.persistence.util;
import java.sql.*;
/**
 * 
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-4-20 下午11:16:43
 * @version 1.0
 */
public class DB {
	Connection con;
	Statement stt;

	private void begin() throws ClassNotFoundException, SQLException {
		if (con != null || stt != null) {
			this.closs();
		}
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://27.54.193.108:3306/meishih","meishih","duan1982");
		stt = con.createStatement();

	}

	

	public String cun(String sql) throws ClassNotFoundException, SQLException {
		this.begin();
		DatabaseMetaData dbmd = con.getMetaData();
		ResultSet tSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE","VIEW"});
		StringBuilder tempInfo = new StringBuilder();
		tempInfo.append("数据库产品名: " + dbmd.getDatabaseProductName()).append("<br />");
		tempInfo.append("数据库是否支持事务: " + dbmd.supportsTransactions()).append("<br />");
		tempInfo.append("数据库产品的版本号:"+ dbmd.getDatabaseProductVersion()).append("<br />");
		while (tSet.next()) {
			tempInfo.append(tSet.getRow()+"==表类别:"+tSet.getString("TABLE_CAT")).append("<br />");
			tempInfo.append(tSet.getRow()+"==表模式:"+tSet.getString("TABLE_SCHEM")).append("<br />");
			tempInfo.append(tSet.getRow()+"==表名称:"+tSet.getString("TABLE_NAME")).append("<br />");
			
            //2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE

//            String tableName = tSet.getString(3);
//            String sql1 = "select * from " + tableName;
//            ResultSet rsSet = con.createStatement().executeQuery(sql1);
//            ResultSetMetaData rsData = rsSet.getMetaData();
//            for (int i = 1; i <= rsData.getColumnCount(); i++) {
//            	tempInfo.append(tSet.getRow()+"===列名:"+rsData.getColumnName(i)+"("+rsData.getColumnLabel(i)+","+rsData.getColumnType(i)+","+rsData.getColumnClassName(i)+")"
//                        +",列宽:"+rsData.getPrecision(i)+",大小写敏感:"+rsData.isCaseSensitive(i)+",isReadOnly:"+rsData.isReadOnly(i)).append("<br />");
//                //==列的信息:获取SQL语句的列名:LIMITLEVER(LIMITLEVER,5,java.lang.Short) 列宽5 大小写敏感true isReadOnly:false
//
//            }
        }
        tSet.close();
		return tempInfo.toString();
	}

	public void closs() throws SQLException {
		stt.close();
		con.close();
	}
	public static void main(String[] args){
		DB a = new DB();
		try {
			String b = a.cun("select * from crawler_rule");
			System.out.println(b);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
