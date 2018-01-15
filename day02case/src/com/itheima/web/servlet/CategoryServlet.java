package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CateService;
import com.itheima.service.impl.CateServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class CategroyServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
   //CateService cateService=new CateServiceImpl();
   
	/**
	 * 查询商品分类的信息
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public  String findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html;charset=utf-8");
		try {
			//调用service查询商品分类信息,完成业务逻辑
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			String categoryListJson = cateService.findCategory();
			//把商品信息放到request中
			response.getWriter().print(categoryListJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//查询商品分类信息失败
			request.setAttribute("msg","查询商品分类信息失败..." );
			return "/jsp/msg.jsp";
		}
		return null;

	}

	

}
