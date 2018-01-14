package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;

public class UserDaoImpl implements UserDao {
	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	@Override
	public void regist(User user) throws SQLException {
		
		QueryRunner qr = new QueryRunner();
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={
				user.getUid(),user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),
				user.getState(),user.getCode()
		};
		qr.update(C3P0Utils.getConnection(),sql,params);
		
	}
	/**
	 * 通过code查找用户
	 * @throws SQLException 
	 */
	@Override
	public User findByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from user where code=?";
		return qr.query(sql, code, new BeanHandler<>(User.class));
	}
	/**
	 * 修改用户的登录状态,删除激活码
	 * @throws SQLException 
	 */
	@Override
	public void changState(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="update user set state=?,code=? where uid=?";
		qr.update(sql, Constant.ACTIVE_IS_CODE,null,user.getUid());
		
	}
	@Override
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from user where username=? and password=?";
		return qr.query(sql, new BeanHandler<>(User.class),username,password);
	}

}
