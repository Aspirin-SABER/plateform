package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
	/**
	 * 添加分类商品
	 */
	@Override
	public void addCategory(String cid, String cname) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="insert into category values(?,?)";
		qr.update(sql, cid,cname);
	}
	/**
	 * 
	 */
	@Override
	public Category getBycId(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from category where cid=?";
		return qr.query(sql, cid, new BeanHandler<>(Category.class));
	}
	
	@Override
	public void updateCategory(String cid, String cname) throws Exception {
		//调用dao完成分类商品的更新
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="update category set cname=?  where cid=?";
		qr.update(sql, cname,cid);
	}
	@Override
	public void delete(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="delete from category where cid=?";
		qr.update(sql,cid);
		
	}
}
