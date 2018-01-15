package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ProductService pService=new ProductServiceImpl(); 
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取cart对象
		Cart cart=this.getCart(request);
		cart.clearCart();
		return "/jsp/cart.jsp";
				
	}
	/**
	 * 删除购物车中的商品
	 */
	public String remoteCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		//获取cart对象
		Cart cart=this.getCart(request);
		cart.remoteCartItemToCart(pid);
		return "/jsp/cart.jsp";
	}
	/**
	 * 将商品添加到购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		try {
			//1.获取id,根据id查询商品
			String pid = request.getParameter("pid");
			Product product=pService.findByPid(pid);
			//2.获取购买数量
			int count =Integer.parseInt(request.getParameter("count"));
			//3.创建购物车项,进行封装
			CartItem ci=new CartItem();
			ci.setProduct(product);
			ci.setCount(count);
			//获取cart对象
			Cart cart=this.getCart(request);
			cart.addCartItemToCart(ci);
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mag", "添加购物车失败...");
			return "/jsp/msg.jsp";
		}
	}
	//获取cart对象
	private Cart getCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		Cart cart=(Cart) session.getAttribute("cart");
		if(cart==null){
			cart =new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	

}
