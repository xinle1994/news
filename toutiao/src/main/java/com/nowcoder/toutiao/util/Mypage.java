package com.nowcoder.toutiao.util;

import java.util.List;

public class Mypage<T> {

	//当前第几页
	private int pageNo;
	
	//当前的list
	private List<T> list;
	
	//每页显示多少条记录
	private int pageSize = 10;
	
	//共有多少条记录
	private long totalItemNumber;

	
	
	//需要交验   pageNo不一定是合法的值
	public int getPageNo() {
		
		if(pageNo < 0){
			pageNo = 1;
		}
		
		if(pageNo > getTotalPageNumber()){
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	
	//构造函数
	public Mypage(int pageNo) {
		super();
		this.pageNo = pageNo;
	}
	
	
	
	//获取总的页数
	public int getTotalPageNumber(){
		int totalPageNumber = (int) (totalItemNumber / pageSize);
		if(totalItemNumber % pageSize != 0){
			totalPageNumber++;
		}
		return totalPageNumber;
	}
	
	
	public boolean isHasNext(){
		
		if(getPageNo() < getTotalPageNumber()-1 ){
			return true;
		}
		
		return false;
	}
	
	public boolean isHasPrev(){
		if(getPageNo() > 0 ){
			return true;
		}
		
		return false;
	}
	
	
	//返回上一页
	public int getPrevPage(){
		if (isHasPrev()) {
			return getPageNo() -1;
		}else {
			return getPageNo();
		}
		
	}
	
	//返回下一页
	public int getNextPage(){
		if (isHasNext()) {
			return getPageNo() + 1;
		}else {
			return getPageNo();
		}
		
	}
	
	
	
}
