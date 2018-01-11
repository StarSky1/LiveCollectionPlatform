package org.lf.utils;

public class PageNavigator {
	private int pageSize;
	private int pageNumber;

	private int start; // 起始行
	private int offset; // 偏移量

	private void init() {
		offset = pageSize;

		if (pageNumber == 1) {
			start = 0;
		} else {
			start = (pageNumber - 1) * pageSize;
		}
	}

	public PageNavigator(int pageSize, int pageNumber) {
		super();
		if (pageNumber < 1) {
			throw new IllegalArgumentException("页号不能小于1");
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("每页显示记录数不能小于1");
		}

		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		init();
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getStart() {
		return start;
	}

	public int getOffset() {
		return offset;
	}

}
