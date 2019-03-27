package com.ym.ms.paging;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.Page;

public class PageUtils<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  // 当前页数
  private int currPage;
  // 每页记录数
  private int pageSize;
  // 总页数
  private int totalPage;
  // 总记录数
  private long totalCount;
  // 列表数据
  private List<?> list;
  // 前端传来的页码
  private int page = 0;
  // 前端传来的每页数据
  private int limit = 10;

  public PageUtils() {

  }

  public PageUtils(List<?> list, long totalCount, int pageSize, int curPage) {
    super();
    this.list = list;
    this.totalCount = totalCount;
    this.pageSize = pageSize;
    this.currPage = curPage == 0 ? 1 : curPage;
    this.totalPage = (int) Math.ceil((double) (totalCount == 0 ? 1 : totalCount) / pageSize);
  }

  /**
   * 分页
   */
  public static <T> PageUtils<T> page(List<T> data) {
    PagerEO<T> pager = new PagerEO<T>();
    if (data instanceof Page) {
      Page<T> page = (Page<T>) data;
      pager.setPage(page.getPageNum());
      pager.setLimit(page.getPageSize());
      pager.setDataList(page.getResult());
      pager.setTotal(page.getTotal());
      pager.setPages(page.getPages());
    } else {
      pager.setPage(1);
      pager.setLimit(data.size());
      pager.setDataList(data);
      pager.setTotal(data.size());
    }
    PageUtils<T> res = new PageUtils<T>();
    res.list = data;
    res.totalCount = pager.getTotal();
    res.pageSize = pager.getLimit();
    res.currPage = pager.getPage();
    res.totalPage =
        (int) Math.ceil((double) (pager.getTotal() == 0 ? 1 : pager.getTotal()) / pager.getLimit());
    return res;
  }

  public int getCurrPage() {
    return currPage;
  }

  public void setCurrPage(int currPage) {
    this.currPage = currPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public List<?> getList() {
    return list;
  }

  public void setList(List<?> list) {
    this.list = list;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }


}
