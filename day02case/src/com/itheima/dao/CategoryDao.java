package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Category;

public interface CategoryDao {

	List<Category> findCategory() throws Exception;

	void addCategory(String cid, String cname)throws Exception;

	Category getBycId(String cid)throws Exception;

	void updateCategory(String cid, String cname)throws Exception;

	void delete(String cid)throws Exception;


}
