package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.Privilege;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    OrderService oService=new OrderServiceImpl();
    /**
	 * 支付的回调函数
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String p1_MerId = request.getParameter("p1_MerId");
			String r0_Cmd = request.getParameter("r0_Cmd");
			String r1_Code = request.getParameter("r1_Code");
			String r2_TrxId = request.getParameter("r2_TrxId");
			String r3_Amt = request.getParameter("r3_Amt");
			String r4_Cur = request.getParameter("r4_Cur");
			String r5_Pid = request.getParameter("r5_Pid");
			String r6_Order = request.getParameter("r6_Order");
			String r7_Uid = request.getParameter("r7_Uid");
			String r8_MP = request.getParameter("r8_MP");
			String r9_BType = request.getParameter("r9_BType");
			String rb_BankId = request.getParameter("rb_BankId");
			String ro_BankOrderId = request.getParameter("ro_BankOrderId");
			String rp_PayDate = request.getParameter("rp_PayDate");
			String rq_CardNo = request.getParameter("rq_CardNo");
			String ru_Trxtime = request.getParameter("ru_Trxtime");
			// 身份校验 --- 判断是不是支付公司通知你
			String hmac = request.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");
			// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
					r8_MP, r9_BType, keyValue);
			if (isValid) {
				// 响应数据有效
				if (r9_BType.equals("1")) {
					// 浏览器重定向
					System.out.println("111");
					request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				} else if (r9_BType.equals("2")) {
					// 服务器点对点 --- 支付公司通知你
					System.out.println("付款成功！222");
					// 修改订单状态 为已付款
					// 回复支付公司
					response.getWriter().print("success");
				}
				//修改订单状态
				OrderService oService=(OrderService)BeanFactory.getBean("OrderService");
				// 根据订单id查询订单信息
				Orders order = oService.findOrderByOid(r6_Order);
				//设置订单状态
				order.setState(Constant.ORDER_WEIFAHUO);
				//跟新订单状态
				oService.update(order);
			} else {
				// 数据无效
				System.out.println("数据被篡改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/msg.jsp";
	}
    /**
     * 在线支付
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public  String  pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			//1.获取请求携带的参数 订单oid 收货人 收货地址 联系方式 银行标识
			String oid = request.getParameter("oid");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String pd_FrpId = request.getParameter("pd_FrpId");
			String telephone = request.getParameter("telephone");
			//根据oid查找该订单详细信息
			OrderService oService=(OrderService)BeanFactory.getBean("OrderService");
			Orders order=oService.findOrderByOid(oid);
			//更新订单
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			//2.将订单的基本信息更新到数据库
			oService.update(order);
			//3.拼接第三所需要的参数  (数据加密)
			//拼接第三方需要的参数
			// 组织发送支付公司需要哪些数据
			String p0_Cmd = "Buy";
			// ResourceBundle 是jdk的一个读取properties文件的工具类
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01"; // 支付金额
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);

			
			//发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			//4.重定向到第三方平台
			response.sendRedirect(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    	
    }
    /**
     * 修改订单状态
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Privilege
    public  String  findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			//获取订单号
			String oid = request.getParameter("oid");
			//调用Service查询该订单的详细信息
			OrderService oService=(OrderService)BeanFactory.getBean("OrderService");
			Orders order=oService.findOrderByOid(oid);
			request.setAttribute("order", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详细信息失败....");
			return "/jsp/msg.jsp";
		}
    	
    	
    }
    /**
     * 查询我的订单分页显示
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
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
				request.setAttribute("msg", "权限不足,请登录...");
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
