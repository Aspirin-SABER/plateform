package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserService {

	void regist(User user) throws SQLException, Exception;

	User findByCode(String code) throws SQLException;

	void changeState(User user) throws SQLException;

	User login(String username, String password) throws SQLException;

}
