package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {
	ProductDao pdao=new ProductDaoImpl();
	@Override
	public List<Product> findNewList() throws Exception {
		//调用pdao查询最新的商品
		return pdao.findNewList();
	}
	@Override
	public List<Product> findHotList() throws Exception {
		// TODO Auto-generated method stub
		return pdao.findHotList();
	}
	/**
	 * 根据pid查询商品的详细信息(包含商品分类)
	 */
	@Override
	public Product findByPid(String pid) throws Exception {
		
		return pdao.findByPid(pid);
	}
	/**
	 * 展示分类商品的信息调用pdao
	 */
	@Override
	public PageBean<Product> findProByPage(int pageNumber, int pageSize, String cid) throws Exception {
		//查询商品的总条数
		int totalPage=pdao.findTotalPage(cid);
		//创建pageBean
		PageBean<Product> pb=new PageBean<>(pageNumber, pageSize, totalPage);
		//起始页的索引
		int startIndex = pb.getStartIndex();
		List<Product> list=pdao.findProByPage(startIndex,pageSize,cid);
		pb.setData(list);
		return pb;
	}



}
