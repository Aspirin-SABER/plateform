package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.alibaba.fastjson.JSON;
import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CateService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CateServiceImpl implements CateService {
	CategoryDao catedao=new CategoryDaoImpl();
	@Override
	/**
	 * 调用catedao查询商品信息
	 */
	public String  findCategory() throws Exception {
		//调用catedao查询商品信息
		//List<Category> cate=catedao.findCategory();
		//把cate转换成json
		//String categoryListJson = JSON.toJSONString(cate);
		//a.查询redis
		Jedis jedis = JedisPoolUtils.getJedis();
		//从Redis中获取数据
		String categoryListJson=jedis.get(Constant.CATEGORY_LIST_JSON);
		if(categoryListJson==null){
			//redis中没有分类的数据信息
			//b.查询mysql
			CategoryDao dao = new CategoryDaoImpl();
			List<Category> list = catedao.findCategory();
			categoryListJson = JSON.toJSONString(list);
			//c.将查询结果存放到redis中
			jedis.set(Constant.CATEGORY_LIST_JSON, categoryListJson);
			System.out.println("mysql中查询.....");
		}
		JedisPoolUtils.closeJedis(jedis);
		return categoryListJson;
		
	}
	/**
	 * 添加分类商品的业务逻辑
	 */
	@Override
	public void addCategory(String cid, String cname) throws Exception {
		//调用dao层添加分类商品
		CategoryDao dao=(CategoryDao)BeanFactory.getBean("CategoryDao");
		dao.addCategory(cid,cname);
		//获取Redis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del(Constant.CATEGORY_LIST_JSON);
	}
	/**
	 * 根据cid查找商品分类
	 */
	@Override
	public Category getBycId(String cid) throws Exception {
		//调用dao层添加分类商品
		CategoryDao dao=(CategoryDao)BeanFactory.getBean("CategoryDao");
		return dao.getBycId(cid);
	}
	/**
	 * 更新分类商品
	 */
	@Override
	public void updateCategory(String cid, String cname) throws Exception {
		//调用dao层添加分类商品
		CategoryDao dao=(CategoryDao)BeanFactory.getBean("CategoryDao");
		dao.updateCategory(cid,cname);
		//获取Jedis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del(Constant.CATEGORY_LIST_JSON);
		
		
	}
	@Override
	public void delete(String cid) throws Exception {
		//调用dao层删除商品分类信息
		CategoryDao dao=(CategoryDao)BeanFactory.getBean("CategoryDao");
		dao.delete(cid);
		//获取Jedis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del(Constant.CATEGORY_LIST_JSON);
		
	}
	

}
