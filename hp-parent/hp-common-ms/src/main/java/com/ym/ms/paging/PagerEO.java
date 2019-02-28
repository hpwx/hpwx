package com.ym.ms.paging;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * @author 郁如义
 * 分页封装
 * @param <T> 返回的实体对象
 */
public class PagerEO<T> implements Serializable {

	//返回的数据集
	private List<T> dataList;
	
	//当前页码，默认为1
	private int page = 1;
	
	//当前每页记录数（easyui）
	private int rows = 10;
	
	//当前每页记录数（jqgrid)
	//private int limit = 10;
	
	//总条数
	private long total;
	//总页数
	private int pages;
	
	public static <T> PagerEO<T> setPageResult(List<T> data){
		PagerEO<T> res = new PagerEO<T>();
		if(data instanceof Page) {
			Page<T> page = (Page<T>) data;
			res.setPage(page.getPageNum());
			res.setRows(page.getPageSize());
			res.setDataList(page.getResult());
			res.setTotal(page.getTotal());
			res.setPages(page.getPages());
		}else {
			res.setPage(1);
			res.setRows(data.size());
			res.setDataList(data);
			res.setTotal(data.size());
		}
		return res;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

//	public int getLimit() {
//		return limit;
//	}
//
//	public void setLimit(int limit) {
//		this.limit = limit;
//	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	
	
	
}
