package com.itheima.service;

import java.util.List;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public interface ProductService {

	List<Product> findNewList() throws Exception;

	List<Product> findHotList() throws Exception;

	Product findByPid(String pid) throws Exception;

	PageBean<Product> findProByPage(int pageNumber, int pageSize, String cid)throws Exception;

	PageBean<Product> findPageProduct(int pageNumber, int pageSize) throws Exception;

}
