package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

public interface OrderDao {

	void saveOrder(Orders order) throws SQLException;

	void saveOrderItem(OrderItem orderItem) throws SQLException;

}
