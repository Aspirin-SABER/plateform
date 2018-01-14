package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    ProductService pService=new ProductServiceImpl();
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findProByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			//获取参数
			String cid = request.getParameter("cid");
			int pageNumber=1;
			try {
				 pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
				
			} catch (Exception e) {
				pageNumber=1;
			}
			//定义每页展示的条数
			int pageSize=12;
			PageBean<Product> pb=pService.findProByPage(pageNumber,pageSize,cid);
			request.setAttribute("pb", pb);
			request.setAttribute("cid", cid);
			
			return "/jsp/product_list.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "展示分类商品信息错误");
			return "/jsp/msg.jsp";
		}
    }
    /**
     * 展示商品的详细信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			//获取参数
			String pid = request.getParameter("pid");
			//根据pid查询商品的详细信息
			Product  product=pService.findByPid(pid);
			request.setAttribute("pro", product);
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//获取失败时
			request.setAttribute("mag", "获取商品西详细信息失败");
			return "/jsp/msg.jsp";
		}
    	
    }
	/**
	 * 从数据库中查询最新商品和热门商品在首页显示
	 */
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//查询最新商品的信息
			List<Product> newList=pService.findNewList();
			//查询热门商品
			List<Product> hotList=pService.findHotList();
			//将结果封装到request中
			request.setAttribute("newList", newList);
			request.setAttribute("hotList", hotList);
			return "/jsp/index.jsp";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "查询信息失败");
			return "/jsp/msg.jsp";
		}
		
	}

	
}
