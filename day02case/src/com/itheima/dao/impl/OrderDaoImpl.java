package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Orders order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params={
				order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()
		};
		qr.update(C3P0Utils.getConnection(), sql, params);
		
	}

	@Override
	public void saveOrderItem(OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		Object[] params={
				orderItem.getItemid(),orderItem.getCount(),
				orderItem.getSubtotal(),orderItem.getProduct().getPid(),
				orderItem.getOrders().getOid()
		};
		qr.update(C3P0Utils.getConnection(), sql, params);
	}
	/**
	 * 查询订单的总条数
	 */
	@Override
	public int finCountByuid(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select count(*) from orders where uid=?";
		return ((Long)(qr.query(sql, uid, new ScalarHandler()))).intValue();
	}

	@Override
	public List<Orders> findMyOrderByPage(int startIndex, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		//分页查询用户的所有订单
		String sql="select * from orders where uid=? limit ?,?";
		List<Orders> ordreList = qr.query(sql, new BeanListHandler<>(Orders.class),uid,startIndex,pageSize );
		//遍历订单,查询每个订单包含的订单项和商品信息
		for (Orders order : ordreList) {
			sql="select * from orderitem oi,product pro where oi.pid=pro.pid and oi.oid=?";
			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : listMap) {
				//map集合订单项和商品的详细信息
				//创建订单项对象
				OrderItem orderItem=new OrderItem();
				BeanUtils.populate(orderItem, map);
				//创建商品对象
				Product pro=new Product();
				BeanUtils.populate(pro, map);
				//把商品实体封装到订单实体中
				orderItem.setProduct(pro);
				//把订单实体封装到订到中
				order.getListItem().add(orderItem);
			}
					
			
		}
		return ordreList;
	}

}
