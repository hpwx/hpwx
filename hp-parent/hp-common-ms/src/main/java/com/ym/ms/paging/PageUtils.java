package com.ym.ms.paging;

import java.io.Serializable;
import java.util.List;

public class PageUtils<T> implements Serializable {
	//当前页数
	private int currPage;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//总记录数
	private long totalCount;
	//列表数据
	private List<?> list;
	//前端传来的页码
	private int page = 0;
	//前端传来的每页数据
	private int limit = 10;
	
	public PageUtils(List<?> list, long totalCount,int pageSize,int curPage) {
		super();
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = curPage == 0 ? 1: curPage;
		this.totalPage = (int)Math.ceil((double)(totalCount == 0 ? 1: totalCount) / pageSize);
	}
}
