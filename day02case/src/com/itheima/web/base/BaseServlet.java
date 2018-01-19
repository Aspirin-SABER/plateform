package com.itheima.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.utils.Privilege;

/**
 * 基本的servlet
 * 目的:将每个servlet中的if() else() 分离
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取请求的标识
			String methodName = request.getParameter("method");
			//2.获取字节码文件
			Class clazz = this.getClass();
			//3.判断方法是否存在
			//3.1获取字节码文件中所有公共的方法
			Method[] methods = clazz.getMethods();
			boolean flag=true;
			for (Method m : methods) {
				if(methodName.equals(m.getName())){
					flag=false;
					break;
				}
				
			}
			//System.out.println(flag);
			if(flag){
				//该方法不存在
				request.setAttribute("msg", "你访问的路径不存在,请核实后重新访问");
				request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
				return ;
			}
			//获取指定方法并执行
			Method method=clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//判断方法是否含有指定注解,如果包含判断用户是否登录
			if(method.isAnnotationPresent(Privilege.class)){
				// 判断用户是否登录
				Object user = request.getSession().getAttribute("user");
				if(user==null){
					request.setAttribute("msg", "权限不足啊,请登录去吧...");
					request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
					return;
				}
			}
			String path=(String)method.invoke(this,request,response);
			if(path!=null){
				request.getRequestDispatcher(path).forward(request, response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
