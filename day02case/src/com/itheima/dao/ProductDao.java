package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Product;

public interface ProductDao {

	List<Product> findHotList() throws Exception;

	List<Product> findNewList() throws Exception;

	Product findByPid(String pid) throws Exception;

	int findTotalPage(String cid) throws Exception;

	List<Product> findProByPage(int startIndex, int pageSize, String cid) throws Exception;

	int findTotal()throws Exception;

	List<Product> findPageProduct(int startIndex, int pageSize)throws Exception;

	void addProduct(Product pro)throws Exception;

}
