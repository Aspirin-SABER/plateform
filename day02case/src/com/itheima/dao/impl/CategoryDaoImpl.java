package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findCategory() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	

}
