package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
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
	/**
	 * 调用dao层,先查询订单的总条数
	 */
	@Override
	public PageBean<Orders> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception {
		//调用dao层,先查询订单的总条数
		OrderDao odao=(OrderDao)BeanFactory.getBean("OrderDao");
		int totalCount=odao.finCountByuid(uid);
		//创建PageBean
		PageBean<Orders> pb=new PageBean<>(pageNumber, pageSize, totalCount);
		//查询当前页所包含的数据
			//获取当前也得起始索引
			int startIndex = pb.getStartIndex();
			List<Orders> data=odao.findMyOrderByPage(startIndex,pageSize,uid);
		//调用dao层,先查询订单的总条数
			pb.setData(data);
			return pb;
	}
	/**
	 * 查询订单的详细信息
	 */
	@Override
	public Orders findOrderByOid(String oid) throws Exception {
		//调用dao层,先查询订单信息
		OrderDao odao=(OrderDao)BeanFactory.getBean("OrderDao");
		return odao.findOrderByOid(oid);
	}

}
