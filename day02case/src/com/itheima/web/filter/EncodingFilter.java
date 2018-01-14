package com.itheima.web.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 过滤请求
		final HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		resp.setContentType("text/html;charset=utf-8");
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)){
			// 使用动态代理对方法增强
			ServletRequest proxy = (ServletRequest)Proxy.newProxyInstance(
					EncodingFilter.class.getClassLoader(), 
					req.getClass().getInterfaces(), 
					new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							// 对需要增强的方法进行增强
							if("getParameter".equals(method.getName())){
								// 获取乱码的参数信息
								String value = (String)method.invoke(req, args);
								// 转码
								return new String(value.getBytes("iso8859-1"),"utf-8");
							}
							// 对不需要增强的方法调用原来的逻辑
							return method.invoke(req, args);
						}
					});
			// 放行 
			chain.doFilter(proxy, resp);
		}else if("post".equalsIgnoreCase(method)){
			req.setCharacterEncoding("utf-8");
			// 放行 
			chain.doFilter(req, resp);
		}else{
			// 放行 
			chain.doFilter(req, resp);
		}
		// 过滤响应
	}

	@Override
	public void destroy() {
		
	}

}
