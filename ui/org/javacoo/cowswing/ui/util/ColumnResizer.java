/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 根据内容调整列宽
 *@author DuanYong
 *@since 2012-12-6下午8:37:51
 *@version 1.0
 */
public class ColumnResizer {
	/**
	 * 根据内容调整列宽
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-6 下午9:17:35
	 * @param table
	 * @return void
	 */
	public static void adjustColumnPerferredWidths(JTable table){
		TableColumnModel columnModel = table.getColumnModel();
		for(int col=0;col<table.getColumnCount();col++){
			int maxWidth = 0;
			for(int row=0;row<table.getRowCount();row++){
				TableCellRenderer rend = table.getCellRenderer(row, col);
				Object value = table.getValueAt(row, col);
				Component comp = rend.getTableCellRendererComponent(table, value, false, false, row, col);
				maxWidth = Math.max(comp.getPreferredSize().width, maxWidth);
			}
			TableColumn column = columnModel.getColumn(col);
			TableCellRenderer headerRenderer = column.getHeaderRenderer();
			if(headerRenderer == null){
				headerRenderer = table.getTableHeader().getDefaultRenderer();
			}
			Object headerValue = column.getHeaderValue();
			Component headerComp = headerRenderer.getTableCellRendererComponent(table, headerValue, false, false, 0, col);
			maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width);
			column.setPreferredWidth(maxWidth);
		}
	}

}
