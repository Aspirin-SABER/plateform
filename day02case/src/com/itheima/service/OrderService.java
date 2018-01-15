package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.Orders;

public interface OrderService {

	void saveOrder(Orders order) throws SQLException;

}
