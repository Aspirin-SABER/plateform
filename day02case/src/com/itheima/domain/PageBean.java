package com.itheima.domain;

import java.util.List;
/**
 * 实体的作用:
 *		数据封装   和  简单的业务处理
 * @param <T>
 */
public class PageBean<T> {

	private List<T> data;  //当前页的数据信息
	private int pageNumber; //当前页数
	private int pageSize; //每页展示条数
	private int totalCount; //总条数
	// 总页数,只需要计算出来,给前台提供服务即可
	private int totalPage; //总页数
	
	// 通过对该实体的封装 和 所需数据的分析
	// pageSize pageNumber totalCount
	public PageBean(int pageNumber, int pageSize, int totalCount) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	
	
	// 获取当前页的起始索引
	public int getStartIndex(){
		return (pageNumber-1)*pageSize; 
	}
	
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	// 更改获取的方法
	public int getTotalPage() {
		// 计算总页数
		// 总页数 = 总条数/每页展示条数;
		double ceil = Math.ceil(totalCount*1.0/pageSize);
		return (int)ceil;
	}
	
	
}

