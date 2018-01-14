package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.commons.beanutils.BeanUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	UserService uservice= new UserServiceImpl();
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String  loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie=getCookie(request, "usernameAndPassword");
		if(cookie!=null){
			//说明是自动登录的
			Cookie cookie1 = new Cookie("usernameAndPassword","143456");
			cookie1.setPath(request.getContextPath()+"/");
			cookie1.setMaxAge(0);
			response.addCookie(cookie1);
		}

		//手动删除session
		request.getSession().invalidate();
		//重定向到index.jsp页面
		response.sendRedirect(request.getContextPath());
		return null;
	}	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public  String  login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取参数
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String sessionCode = request.getParameter("sessionCode");
			//System.out.println(sessionCode);
			String code=(String) request.getSession().getAttribute("sessionCode");
			request.getSession().removeAttribute("sessionCode");
			//验证码为空
			if(sessionCode==null||sessionCode.trim().length()==0||code==null){
				request.setAttribute("msg", "请输入验证码");
				//request.setAttribute("username", username);
				return "/jsp/login.jsp";
			}
			if(!sessionCode.equalsIgnoreCase(code)){
				request.setAttribute("msg", "验证码不正确,请重新输入");
				//request.setAttribute("username", username);
				return "/jsp/login.jsp";
			}
			//调用uservice完成业务逻辑
			User user=uservice.login(username,password);
			if(user!=null){
				if(user.getState()==Constant.ACTIVE_NOT_CODE){
					request.setAttribute("msg", "请先激活");
					//request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
					return "/jsp/msg.jsp";
				}
				//添加自动登录逻辑
				//获取自动登录的复选框
				String autologin = request.getParameter("autologin");
				if(autologin!=null&&autologin.trim().length()>0&&"ok".equals(autologin)){
					//如果复选框勾选
					String usernameAndPassword=username+"-"+password;
					usernameAndPassword =  URLEncoder.encode(usernameAndPassword, "utf-8");
					//创建新的Cookie
					Cookie cookie=new Cookie("usernameAndPassword", usernameAndPassword);
					//设置路径和Cookie持久化
					cookie.setPath(request.getContextPath()+"/");
					cookie.setMaxAge(7*24*3600);
					//放回到浏览器中
					response.addCookie(cookie);
				}
				//登录成功,保存登录状态
				request.getSession().setAttribute("user", user);
				//request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
				return "/jsp/index.jsp" ;
				
			}else{
				request.setAttribute("msg", "登录失败,账号密码不匹配");
				//request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				return "/jsp/login.jsp";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "登录失败,账号密码不匹配");
			//request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			return "/jsp/login.jsp";
		}
		
	}
		
	/**
	 * 激活账户
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public  String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String code = request.getParameter("code");
			User user=uservice.findByCode(code);
			if(user==null){
				request.setAttribute("msg", "激活失败");
				//request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
				return "/jsp/msg.jsp";
			}else{
				uservice.changeState(user);
				request.setAttribute("msg", "激活成功");

				//设置一个定时刷新
				response.setHeader("refresh", "3;url='http://localhost"+request.getContextPath()+"'");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			request.setAttribute("msg", "激活失败");
		}
		//request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
		return "/jsp/msg.jsp";
		
		
	}

	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public  String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取全部参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			if(user!=null){
				uservice.regist(user);
			}
			request.setAttribute("msg", "注册成功,请进一步激活");
			//request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return "/jsp/msg.jsp";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "注册失败");
			//request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return "/jsp/msg.jsp";
		} 
		
	}
	/**
	 * 封装一个获取Cookie的方法
	 */
	private Cookie getCookie(HttpServletRequest request,String cookieName){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookieName.equals(cookie.getName())){
					return cookie;
				}
			}
		}
		return null;
	}


}
