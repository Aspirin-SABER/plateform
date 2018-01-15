package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.service.OrderService;
import com.itheima.utils.C3P0Utils;

public class OrderServiceImpl implements OrderService {
	OrderDao odao=new OrderDaoImpl();
	@Override
	public void saveOrder(Orders order) throws SQLException {
		try {
			//手动开启事物
			C3P0Utils.startTransaction();
			//调用dao层保存订单信息
			odao.saveOrder(order);
			//调用dao层保存订单项信息
			for(OrderItem orderItem:order.getListItem()){
				odao.saveOrderItem(orderItem);
			}
			//提交事物
			C3P0Utils.commitAndClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//事物回滚
			C3P0Utils.rollbackAndClose();
			throw e;
		}
	}

}
