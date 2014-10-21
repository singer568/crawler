package org.javacoo.cowswing.ui.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;


/**
 * 模型抽象类
 *@author DuanYong
 *@since 2012-11-5下午9:27:31
 *@version 1.0
 */
public abstract class AbstractCrawlerTableModel<T> extends AbstractTableModel{
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	protected List<String> columnNames;
	
	protected List<T> dataList;
	
	public AbstractCrawlerTableModel(List<String> columnNames){
		this.dataList = new CopyOnWriteArrayList<T>();
		this.columnNames = new CopyOnWriteArrayList<String>();
		this.columnNames.addAll(columnNames);
	}
	
	public AbstractCrawlerTableModel(List<String> columnNames,List<T> dataList){
		this.dataList = new CopyOnWriteArrayList<T>();
		this.dataList.addAll(dataList);
		this.columnNames = new CopyOnWriteArrayList<String>();
		this.columnNames.addAll(columnNames);
	}
	
	public List<T> getData(){
		return this.dataList;
	}
	public void setData(List<T> entityList) {
		dataList = entityList;
		this.fireTableDataChanged();
	}
    
	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public int getRowCount() {
		return this.dataList.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.size();
	}
	public String getColumnName(int col) {
		return this.columnNames.get(col);
	}

	@Override
	public abstract Object getValueAt(int rowIndex, int columnIndex);
	
	public T getRowObject(int rowIndex) {
		return dataList.get(rowIndex);
	}

	public void addRow(T rowData) {
		insertRow(getRowCount(), rowData);
	}

	public void addRow(List<T> rowData) {
		insertRow(getRowCount(), rowData);
	}

	public void insertRow(int row, T rowData) {
		dataList.add(row, rowData);
		fireTableRowsInserted(row, row);
	}

	public void insertRow(int row, List<T> rowData) {
		for (int i = 0; i < rowData.size(); i++) {
			dataList.add(row + i, rowData.get(i));
		}
		fireTableRowsInserted(row, row + rowData.size() - 1);
	}

	public void removeRow(int row) {
		dataList.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void rowsRemoved(TableModelEvent event) {
		fireTableChanged(event);
	}
	public void updateRow(int row, T newRowData) {
		dataList.remove(row);
		dataList.add(row, newRowData);
		fireTableRowsUpdated(row, row);
	}
	public void moveRow(int start, int end, int to) {
		int shift = to - start;
		int first, last;
		if (shift < 0) {
			first = to;
			last = end;
		} else {
			first = start;
			last = to + end - start;
		}
		rotate(dataList, first, last + 1, shift);

		fireTableRowsUpdated(first, last);
	}


	private static int gcd(int i, int j) {
		return (j == 0) ? i : gcd(j, i % j);
	}

	private void rotate(List<T> c, int a, int b, int shift) {
		int size = b - a;
		int r = size - shift;
		int g = gcd(size, r);
		for (int i = 0; i < g; i++) {
			int to = i;
			T tmp = c.get(a + to);
			for (int from = (to + r) % size; from != i; from = (to + r) % size) {
				c.set(a + to, c.get(a + from));
				to = from;
			}
			c.set(a + to, tmp);
		}
	}

}
