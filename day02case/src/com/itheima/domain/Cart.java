package com.itheima.domain;

import java.lang.annotation.Target;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *创建购物车 
 *封装数据处理简单的业务逻辑
 */
public class Cart {
	//map集合存放购物车项,key是商品的id
	private Map<String,CartItem> mapItem=new HashMap<String,CartItem>();
	//总金额
	private double total;
	public Map<String, CartItem> getMapItem() {
		return mapItem;
	}
	public void setMapItem(Map<String, CartItem> mapItem) {
		this.mapItem = mapItem;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	// 为了遍历方便,我们将存放购物项的map转为list
	public Collection<CartItem> getListItem(){
		return mapItem.values();
	}
	//把购物车项加入到购物车中
	public void addCartItemToCart(CartItem ci){
		//获取商品的id
		String pid = ci.getProduct().getPid();
		//判断集合是否包含该商品
		//如果有该商品则数量改变
		if(mapItem.containsKey(pid)){
			//获取集合中该商品
			CartItem cartItem = mapItem.get(pid);
			//集合中商品的数量加上添加商品的数量
			cartItem.setCount(cartItem.getCount()+ci.getCount());
		}else{
			//如果不包含
			mapItem.put(pid, ci);
		}
		total+=ci.getSubtotal();
	}
	//删除购物车中的商品
	public void remoteCartItemToCart(String pid){
		CartItem CartItem = mapItem.remove(pid);
		//改变总金额
		total-=CartItem.getSubtotal();
	}
	//清空购物车中的商品
	public void clearCart(){
		mapItem.clear();
		total=0.0;
	}
}
