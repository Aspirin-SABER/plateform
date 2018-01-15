package com.itheima.service.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.alibaba.fastjson.JSON;
import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CateService;
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
		//String jsonStringCate = JSON.toJSONString(cate);
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

}
