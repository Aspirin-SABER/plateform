package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

public interface OrderDao {

	void saveOrder(Orders order) throws SQLException;

	void saveOrderItem(OrderItem orderItem) throws SQLException;

	int finCountByuid(String uid)throws Exception;

	List<Orders> findMyOrderByPage(int startIndex, int pageSize, String uid)throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void update(Orders order) throws Exception;

}
