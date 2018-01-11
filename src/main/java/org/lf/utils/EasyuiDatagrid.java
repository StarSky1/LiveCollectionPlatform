package org.lf.utils;

import java.util.List;

public class EasyuiDatagrid<T> {
	
	private int total;				//存放所有的记录数
	private List<T> rows;				//存放当前页的内容

	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	//构造方法，也是唯一的调用方法
	public EasyuiDatagrid(List<T> rows, int total) {
		this.total = total;
		this.rows = rows;
	}

	public EasyuiDatagrid() {
		super();
	}
}
