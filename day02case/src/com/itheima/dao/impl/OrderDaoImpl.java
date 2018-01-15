package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
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

}
