package com.itheima.domain;

import java.util.Date;

public class Product {
	private String pid;	//商品id
	private String pname;	//商品名称
	private Double market_price;	//市场价格
	
	private Double shop_price;		//商城价格
	private String pimage;	//商品图片
	private Date pdate;//商品上架日期.
	
	private int is_hot;//是否热门  1:热门 0:不热门
	private String pdesc;
	private int pflag;//是否下架  1:已下架 0:未下架
	
	//在数据库中表示当前商品属于那个分类
	//private String cid;
	//在java中表示当前商品属于那个分类
	private Category category;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public int getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public int getPflag() {
		return pflag;
	}

	public void setPflag(int pflag) {
		this.pflag = pflag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
