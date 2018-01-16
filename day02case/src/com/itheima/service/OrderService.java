package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;

public interface OrderService {

	void saveOrder(Orders order) throws SQLException;

	PageBean<Orders> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception;

	Orders findOrderByOid(String oid) throws Exception;

	void update(Orders order)throws Exception;

}
