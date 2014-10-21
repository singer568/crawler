/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.gather.ui.view.dialog;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.plugin.gather.service.beans.CrawlerDataBaseBean;
import org.javacoo.cowswing.plugin.gather.ui.model.CrawlerDataBaseTableModel;
import org.javacoo.cowswing.plugin.gather.ui.view.panel.DataBaseListPage;
import org.javacoo.cowswing.ui.view.dialog.AbstractDialog;
import org.javacoo.cowswing.ui.view.panel.ViewDeatilPanel;
import org.springframework.stereotype.Component;

/**
 * 数据库信息
 * 
 * @author DuanYong
 * @since 2013-2-19下午2:14:48
 * @version 1.0
 */
@Component("dataBaseInfoDialog")
public class DataBaseInfoDialog extends AbstractDialog implements CowSwingListener {
	private static final long serialVersionUID = 1L;
	@Resource(name = "dataBaseListPage")
	private DataBaseListPage dataBaseListPage;
	@Resource(name = "viewDeatilPanel")
	private ViewDeatilPanel viewDeatilPanel;
	private JTable dataBaseTable;
	private CrawlerDataBaseTableModel crawlerDataBaseTableModel;
	private String title = "";
	private String content = "";
	
	

	@Override
	public JComponent getCenterPane() {
		if (centerPane == null) {
			JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP,
					JTabbedPane.SCROLL_TAB_LAYOUT);
			logger.info("初始化内容面板");
			jTabbedPane.addTab(
					LanguageLoader.getString("ContentList.contentDetail"),
					viewDeatilPanel);
			centerPane = jTabbedPane;
		}
		return centerPane;
	}

	/**
	 * 填充JTabbedPane值
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-3 下午12:20:32
	 * @return void
	 */
	private void fillJTabbedPane() {
		logger.info("填充JTabbedPane值");
		if (StringUtils.isNotBlank(title)) {
			setTitle(title);
		}
		viewDeatilPanel.showContent(content);
	}

	@Override
	public void update(CowSwingEvent event) {

	}

	protected void initData(String type) {
		dataBaseTable = dataBaseListPage.getCrawlerDataBaseTable();
		if (dataBaseTable.getSelectedRow() != -1) {
			crawlerDataBaseTableModel = (CrawlerDataBaseTableModel) dataBaseTable
					.getModel();
			CrawlerDataBaseBean crawlerDataBaseBean = crawlerDataBaseTableModel
					.getRowObject(dataBaseTable.getSelectedRow());
			Connection conn = dataBaseListPage.getConnectionManager()
					.getConnection(crawlerDataBaseBean.getDataBaseId() + "");
			if (null == conn) {
				title = LanguageLoader.getString("System.DataBaseSettingError");
				content = LanguageLoader
						.getString("System.DataBaseSettingErrorMessage");
				// JOptionPane.showMessageDialog(null,
				// LanguageLoader.getString("System.DataBaseSettingErrorMessage"),
				// LanguageLoader.getString("System.DataBaseSettingError"),
				// JOptionPane.CLOSED_OPTION);
			} else {
				title = LanguageLoader.getString("System.DataBaseInfo");
				// JOptionPane.showMessageDialog(null, LanguageLoader
				// .getString("System.DataBaseSettingSuccessMessage"),
				// LanguageLoader.getString("System.DataBaseSetting"),
				// JOptionPane.CLOSED_OPTION);
				StringBuilder tempInfo = new StringBuilder();

				try {
					DatabaseMetaData dbmd = conn.getMetaData();
					tempInfo.append("########## DatabaseMetaData关于数据库的整体综合信息 ##########").append("<br />");
					tempInfo.append("数据库产品名: " + dbmd.getDatabaseProductName()).append("<br />");
					tempInfo.append("数据库是否支持事务: " + dbmd.supportsTransactions()).append("<br />");
					tempInfo.append("数据库产品的版本号:"+ dbmd.getDatabaseProductVersion()).append("<br />");
					tempInfo.append("数据库的默认事务隔离级别:"+ dbmd.getDefaultTransactionIsolation()).append("<br />");
					tempInfo.append("支持批量更新:" + dbmd.supportsBatchUpdates()).append("<br />");
					tempInfo.append("DBMS 的 URL:" + dbmd.getURL()).append("<br />");
					tempInfo.append("数据库的已知的用户名称:" + dbmd.getUserName()).append("<br />");
					tempInfo.append("数据库是否处于只读模式:" + dbmd.isReadOnly()).append("<br />");
					tempInfo.append("数据库是否支持为列提供别名:"+ dbmd.supportsColumnAliasing()).append("<br />");
					tempInfo.append("是否支持指定 LIKE 转义子句:"+ dbmd.supportsLikeEscapeClause()).append("<br />");
					tempInfo.append("是否为外连接提供受限制的支持:"+ dbmd.supportsLimitedOuterJoins()).append("<br />");
					tempInfo.append("是否允许一次打开多个事务:"+ dbmd.supportsMultipleTransactions()).append("<br />");
					tempInfo.append("是否支持 EXISTS 表达式中的子查询:"+ dbmd.supportsSubqueriesInExists()).append("<br />");
					tempInfo.append("是否支持 IN 表达式中的子查询:"+ dbmd.supportsSubqueriesInIns()).append("<br />");
					tempInfo.append("是否支持给定事务隔离级别:"+ dbmd.supportsTransactionIsolationLevel(1)).append("<br />");
					tempInfo.append("此数据库是否支持事务:" + dbmd.supportsTransactions()).append("<br />");
					tempInfo.append("此数据库是否支持 SQL UNION:" + dbmd.supportsUnion()).append("<br />");
					tempInfo.append("此数据库是否支持 SQL UNION ALL:" + dbmd.supportsUnionAll()).append("<br />");
					tempInfo.append("此数据库是否为每个表使用一个文件:" + dbmd.usesLocalFilePerTable()).append("<br />");
					tempInfo.append("此数据库是否将表存储在本地文件中:" + dbmd.usesLocalFiles()).append("<br />");
					tempInfo.append("底层数据库的主版本号:" + dbmd.getDatabaseMajorVersion()).append("<br />");
					tempInfo.append("底层数据库的次版本号:" + dbmd.getDatabaseMinorVersion()).append("<br />");

					tempInfo.append("JDBC 驱动程序的主版本号:" + dbmd.getJDBCMajorVersion()).append("<br />");
					tempInfo.append("JDBC 驱动程序的次版本号:" + dbmd.getJDBCMinorVersion()).append("<br />");
					tempInfo.append("JDBC 驱动程序的名称:" + dbmd.getDriverName()).append("<br />");
					tempInfo.append("JDBC 驱动程序的 String 形式的版本号:" + dbmd.getDriverVersion()).append("<br />");

					tempInfo.append("可以在不带引号的标识符名称中使用的所有“额外”字符:" + dbmd.getExtraNameCharacters()).append("<br />");
					tempInfo.append("用于引用 SQL 标识符的字符串:" + dbmd.getIdentifierQuoteString()).append("<br />");
					tempInfo.append("允许用于类别名称的最大字符数:" + dbmd.getMaxCatalogNameLength()).append("<br />");
					tempInfo.append("允许用于列名称的最大字符数:" + dbmd.getMaxColumnNameLength()).append("<br />");
					tempInfo.append("允许在 GROUP BY 子句中使用的最大列数:" + dbmd.getMaxColumnsInGroupBy()).append("<br />");
					tempInfo.append("允许在 SELECT 列表中使用的最大列数:" + dbmd.getMaxColumnsInSelect()).append("<br />");
					tempInfo.append("允许在表中使用的最大列数:" + dbmd.getMaxColumnsInTable()).append("<br />");
					tempInfo.append("数据库的并发连接的可能最大数:" + dbmd.getMaxConnections()).append("<br />");
					tempInfo.append("允许用于游标名称的最大字符数:" + dbmd.getMaxCursorNameLength()).append("<br />");
					tempInfo.append("在同一时间内可处于开放状态的最大活动语句数:" + dbmd.getMaxStatements()).append("<br />");
					tempInfo.append("########## 获取表的信息 ##########").append("<br />");
					ResultSet tSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE","VIEW"});
					while (tSet.next()) {
						tempInfo.append(tSet.getRow()+"==表类别:"+tSet.getString("TABLE_CAT")).append("<br />");
						tempInfo.append(tSet.getRow()+"==表模式:"+tSet.getString("TABLE_SCHEM")).append("<br />");
						tempInfo.append(tSet.getRow()+"==表名称:"+tSet.getString("TABLE_NAME")).append("<br />");
						
		                //2_表类别:MANOR_表模式:PUBLIC_表名称:SYS_RESOURCE_表类型:TABLE

//		                String tableName = tSet.getString(3);
//		                String sql = "select * from " + tableName;
//		                ResultSet rsSet = conn.createStatement().executeQuery(sql);
//		                ResultSetMetaData rsData = rsSet.getMetaData();
//		                for (int i = 1; i <= rsData.getColumnCount(); i++) {
//		                	tempInfo.append(tSet.getRow()+"===列名:"+rsData.getColumnName(i)+"("+rsData.getColumnLabel(i)+","+rsData.getColumnType(i)+","+rsData.getColumnClassName(i)+")"
//		                            +",列宽:"+rsData.getPrecision(i)+",大小写敏感:"+rsData.isCaseSensitive(i)+",isReadOnly:"+rsData.isReadOnly(i)).append("<br />");
//		                    //==列的信息:获取SQL语句的列名:LIMITLEVER(LIMITLEVER,5,java.lang.Short) 列宽5 大小写敏感true isReadOnly:false
//
//		                }
		            }
		            tSet.close();
		              
		            tempInfo.append("########## 获取当前数据库所支持的SQL数据类型 ##########").append("<br />");
		            ResultSet tableType = dbmd.getTypeInfo();
		            while(tableType.next()){
		            	tempInfo.append("数据类型名:"+tableType.getString(1)
		                     +",短整型的数:"+tableType.getString(2)
		                     +",整型的数:"+tableType.getString(3)
		                     +",最小精度:"+tableType.getString(14)
		                     +",最大精度:"+tableType.getString(15)).append("<br />");
		                //数据类型名:TIMESTAMP,短整型的数:93,整型的数:23,最小精度:0,最大精度:10

		                //数据类型名:VARCHAR,短整型的数:12,整型的数:2147483647,最小精度:0,最大精度:0

		            }
//		               
//		            tempInfo.append("######################## 表的主键列信息 ########################");
//		            ResultSet primaryKey = dbmd.getPrimaryKeys("MANOR","PUBLIC","SYS_ROLE_RES");
//		            while(primaryKey.next()){
//		            	tempInfo.append("表名:"+primaryKey.getString("TABLE_NAME")+",列名:"+primaryKey.getString("COLUMN_NAME")
//		                     +" 主键名:"+primaryKey.getString("PK_NAME"));
//		             //表名:SYS_ROLE_RES,列名:SYS_RES_ID 主键名:CONSTRAINT_9
//
//		             //表名:SYS_ROLE_RES,列名:SYS_ROLE_ID 主键名:CONSTRAINT_9
//
//		            }
//		            tempInfo.append("######################## 表的外键列信息 ########################"); 
//		            ResultSet foreinKey = dbmd.getImportedKeys("MANOR","PUBLIC","SYS_ROLE_RES");
//		            while(foreinKey.next()){
//		            	tempInfo.append("主键名:"+foreinKey.getString("PK_NAME")+",外键名:"+foreinKey.getString("FKCOLUMN_NAME")
//		                        +",主键表名:"+foreinKey.getString("PKTABLE_NAME")+",外键表名:"+foreinKey.getString("FKTABLE_NAME")
//		                        +",外键列名:"+foreinKey.getString("PKCOLUMN_NAME")+",外键序号:"+foreinKey.getString("KEY_SEQ"));
//		                //主键名:PRIMARY_KEY_95,外键名:SYS_RES_ID,主键表名:SYS_RESOURCE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
//
//		                //主键名:PRIMARY_KEY_A,外键名:SYS_ROLE_ID,主键表名:SYS_ROLE,外键表名:SYS_ROLE_RES,外键列名:ID,外键序号:1
//
//		            }
//		              
//		            tempInfo.append("######################## 获取数据库中允许存在的表类型 ########################");
//		            ResultSet tableTypes = dbmd.getTableTypes();
//		            while(tableTypes.next()){
//		            	tempInfo.append("类型名:"+tableTypes.getString(1));
//		                /** H2
//		                 类型名:SYSTEM TABLE
//		                 类型名:TABLE
//		                 类型名:TABLE LINK
//		                 类型名:VIEW
//		                 */
//		            }
                   content = tempInfo.toString();
				} catch (Exception ex) {
					ex.printStackTrace();
				}finally{
					dataBaseListPage.getConnectionManager().freeConnection(
							crawlerDataBaseBean.getDataBaseId() + "", conn);
				}
			}
		}
		fillJTabbedPane();
	}

	public void dispose() {
		super.dispose();
		centerPane = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.crawler.ui.view.dialog.AbstractDialog#finishButtonActionPerformed
	 * (java.awt.event.ActionEvent)
	 */
	@Override
	protected void finishButtonActionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub

	}

	public JButton getHelpButton() {
		return null;
	}

	public JButton getFinishButton() {
		return null;
	}
}
