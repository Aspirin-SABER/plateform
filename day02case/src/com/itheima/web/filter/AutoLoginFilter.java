package com.itheima.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;
import com.itheima.service.impl.UserServiceImpl;

/**
 * Servlet Filter implementation class autologinFilter
 */
public class AutoLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			//向下转型
			HttpServletRequest req=(HttpServletRequest)request;
			HttpServletResponse resp=(HttpServletResponse)response;
			Object attribute = req.getSession().getAttribute("user");


 			if(attribute!=null){
				chain.doFilter(request, response);
				return;
			}
			//获取cookie
			Cookie cookie=getCookie(req, "usernameAndPassword");
			//如果Cookie不为空说明
			if(cookie!=null){
				String value = cookie.getValue();
				value = URLDecoder.decode(value, "utf-8");
				String username=value.split("-")[0];
				String password=value.split("-")[1];
				//System.out.println("调用一次数据库");
				User user = new UserServiceImpl().login(username, password);
				if(user!=null){
					req.getSession().setAttribute("user", user);
					}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
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
