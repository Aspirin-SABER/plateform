package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.constant.Constant;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    OrderService oService=new OrderServiceImpl();  
    public  String  findMyOrderByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			//获取当前的页数
			int  pageNumber=1;
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				pageNumber=1;
			}
			//设置pageSize
			int pageSize=3;
			//获取用户uid
			HttpSession session = request.getSession();
			User user=(User)session.getAttribute("user");
			String uid = user.getUid();
			//调用Service层完成我的订单查询
			//通过工程解耦合实现
			OrderService oService=(OrderService)BeanFactory.getBean("OrderService");
			PageBean<Orders> pb=oService.findMyOrderByPage(pageNumber,pageSize,uid);
			request.setAttribute("pb", pb);
			return "/jsp/order_list.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "查询我的订单商品失败...");
			return "/jsp/msg.jsp";
		}
    }
   
	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String  saveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.0判断用户是否登录
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			if(user==null){
				request.setAttribute("msg", "全选不足,请登录...");
				return "/jsp/msg.jsp";
			}
			//创建订单对象
			Orders order=new Orders();
			//1.设置oid
			order.setOid(UUIDUtils.getId());
			//设置订单生成的时间
			order.setOrdertime(new Date());
			//获取购物车对象
			Cart cart = (Cart)session.getAttribute("cart");
			//获取购物车中的总金额,放入到订单中
			order.setTotal(cart.getTotal());
			//设置订单状态
			order.setState(Constant.ORDER_WEIZHIFU);
			//设置所属用户
			order.setUser(user);
			//设置订单项
			Collection<CartItem> list = cart.getListItem();
			//获取购物车中的所有购物项
			for (CartItem cartItem : list) {
				//将购物项转化为订单项
				OrderItem orderItem=new OrderItem();
				//设置订单项id
				orderItem.setItemid(UUIDUtils.getId());
				//设置数量
				orderItem.setCount(cartItem.getCount());
				//设置小计
				orderItem.setSubtotal(cartItem.getSubtotal());
				//设置所属订单
				orderItem.setOrders(order);
				//包含的商品
				orderItem.setProduct(cartItem.getProduct());
				List<OrderItem> listItem = order.getListItem();
				listItem.add(orderItem);
			}
			//调用sercice层完成订单的创建
			oService.saveOrder(order);
			//请求转发到订单详情界面
			request.setAttribute("order", order);
			return "/jsp/order_info.jsp"; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "生成订单失败....");
			return "/jsp/msg.jsp";
		}
		
	}

	

}
