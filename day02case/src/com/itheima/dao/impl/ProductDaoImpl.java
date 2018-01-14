package com.itheima.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.constant.Constant;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;

public class ProductDaoImpl implements ProductDao {
	/**
	 * is_hot 1 
	 * pflag 0 上架
	 * 首页显示9个
	 * 查询热门的商品信息
	 */
	@Override
	public List<Product> findHotList() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where is_hot=? and pflag=? limit ?";
		Object[] params={Constant.PRODUCT_IS_HOT,Constant.PRODUCT_PUSH_UP,Constant.INDEX_SHOW_COUNT};
		
		return qr.query(sql, params, new BeanListHandler<>(Product.class));
	}
	/**
	 * pflag 0上架
	 * 显示9个
	 * 查询最新的商品信息
	 */
	@Override
	public List<Product> findNewList() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where pflag=? order by pdate desc limit ?";
		Object[] params={Constant.PRODUCT_PUSH_UP,Constant.INDEX_SHOW_COUNT};
		
		return qr.query(sql, params, new BeanListHandler<>(Product.class));
	}
	/**
	 * 查找根据pid查询商品的详细信息(包含商品分类)
	 * 多表查询
	 */
	@Override
	public Product findByPid(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product pro,category cate where pro.cid=cate.cid and pro.pid=? ";
		List<Map<String,Object>> list=qr.query(sql, pid, new MapListHandler());
		Map<String, Object> map =list.get(0);
		//将查询结果封装到Product中
		Product product=new Product();
		BeanUtils.populate(product, map);
		// 将查询结果封装到category中
		Category category = new Category();
		BeanUtils.populate(category, map);
		product.setCategory(category);
		return product;
		/*for (Map<String, Object> map : list) {
			
		}*/
	}
	/**
	 * 根据id查询商品的总条数
	 */
	@Override
	public int findTotalPage(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select count(*) from product where cid=? ";
		return ((Long)qr.query(sql, new ScalarHandler(),cid)).intValue();
	}
	/**
	 * 分页展示分类商品信息
	 */
	@Override
	public List<Product> findProByPage(int startIndex, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where cid=? limit ?,? ";
		Object[] params={cid,startIndex,pageSize};
		return qr.query(sql, params, new BeanListHandler<>(Product.class));
	}

}
