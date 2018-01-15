package com.itheima.domain;
/**
 * 创建购物车项
 * 
 * @author Aspirin
 *
 */
public class CartItem {
	//商品信息
	private Product product;
	//购买数量
	private int count;
	//小计
	private double subtotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return count*product.getShop_price();
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
}
