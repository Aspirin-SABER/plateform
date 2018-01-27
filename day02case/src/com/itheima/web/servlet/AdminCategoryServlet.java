package com.itheima.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.itheima.domain.Category;
import com.itheima.service.CateService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.base.BaseServlet;



public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public  String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> mapMsg=new HashMap<>();
		try {
			//获取参数
			String cid=request.getParameter("id");
			//调用Service层完成添加的业务逻辑
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			cateService.delete(cid);
			mapMsg.put("msg", "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapMsg.put("msg", "删除失败");
		}
		String jsonString = JSON.toJSONString(mapMsg);
		response.getWriter().print(jsonString);
		return null;
	}
	/**
	 * 根据Cid修改商品分类
	 */
	public  String updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> mapMsg=new HashMap<>();
		try {
			//获取参数
			String cid=request.getParameter("cid");
			String cname=request.getParameter("cname");
			System.out.println(cname);
			//调用Service层完成添加的业务逻辑
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			cateService.updateCategory(cid,cname);
			mapMsg.put("msg", "分类修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapMsg.put("msg", "分类修改失败");
		}
		String jsonString = JSON.toJSONString(mapMsg);
		response.getWriter().print(jsonString);
		return null;
	}
	/**
	 * 根据id查找商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String getBycId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			//调用Service层完成添加的业务逻辑
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			Category cate=cateService.getBycId(cid);
			String jsonString = JSON.toJSONString(cate);
			//System.out.println(jsonString);
			response.getWriter().print(jsonString);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> mapMsg=new HashMap<>();
		try {
			//获取参数
			String cname = request.getParameter("cname");
			//设置cid
			String cid=UUIDUtils.getId();
			//调用Service层完成添加的业务逻辑
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			cateService.addCategory(cid,cname);
			mapMsg.put("msg", "分类添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapMsg.put("msg", "分类添加失败");
		}
		String jsonString = JSON.toJSONString(mapMsg);
		response.getWriter().print(jsonString);
		return null;
	}
	/**
	 * 查询分类信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用servlet完成查询分类的业务逻辑
		try {
			CateService cateService=(CateService) BeanFactory.getBean("CategoryService");
			String category=cateService.findCategory();
			//System.out.println(category);
			response.getWriter().print(category);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	

}
