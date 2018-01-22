package com.itheima.web.servlet;

import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPageProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取参数
			int pageNumber = Integer.parseInt(request.getParameter("page"));
			int pageSize=Integer.parseInt(request.getParameter("rows"));
			//调用service层完成业务逻辑
			ProductService PService=(ProductService)BeanFactory.getBean("ProductService");
			PageBean<Product> pb=PService.findPageProduct(pageNumber,pageSize);
			//创建map集合用来接受响应数据
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("total", pb.getTotalCount());
			map.put("rows", pb.getData());
			String jsonString = JSON.toJSONString(map);
			response.getWriter().print(jsonString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	

}
