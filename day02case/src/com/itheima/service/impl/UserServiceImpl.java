package com.itheima.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.print.attribute.standard.RequestingUserName;

import org.apache.catalina.connector.Request;

import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.MailUtils;
import com.itheima.utils.UUIDUtils;

public class UserServiceImpl implements UserService {
	UserDao udao=new UserDaoImpl();
	/**
	 * 处理注册的业务逻辑
	 * @throws SQLException 
	 * @throws MessagingException 
	 */
	@Override
	public void regist(User user) throws Exception {
		
		try {
			C3P0Utils.startTransaction();
			//0.手动开启事务
			//1.生成用户id
			user.setUid(UUIDUtils.getId());
			//2.生成一个状态
			user.setState(Constant.ACTIVE_NOT_CODE);
			//3.生成一个激活码
			String code=UUIDUtils.getCode();
			user.setCode(code);
			//4.调用udao完成用户注册
			udao.regist(user);
			//5.给用户发送邮件
			String emailMsg="<a href='http://localhost/day01case/user?method=active&code="+code+"'>激活账户</a>";
			MailUtils.sendMail(user.getEmail(), "激活邮件", emailMsg);
			//6.提交事务
			C3P0Utils.commitAndClose();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			C3P0Utils.rollbackAndClose();
			throw e;	
		}
		
	}
	/**
	 * 通过编码查找用户
	 * @throws SQLException 
	 */
	@Override
	public User findByCode(String code) throws SQLException {
		return udao.findByCode(code);
		
	}
	/**
	 * 修改用户的状态并清除code
	 * 只激活一次
	 * @throws SQLException 
	 */
	@Override
	public void changeState(User user) throws SQLException {
		udao.changState(user);
		
	}
	/**
	 * 根据用户名和密码查找用户
	 * @throws SQLException 
	 */
	@Override
	public User login(String username, String password) throws SQLException {
		return udao.login(username,password);
	}

}
