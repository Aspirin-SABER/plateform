package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	private static JedisPool jedisPool = null;
	private static JedisPoolConfig config = null;
	static{
		// 设置连接池的配置信息
		config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(10);
		// 创建连接池对象
		jedisPool = new JedisPool(config,"127.0.0.1",6379);
	}
	// 获取jedis连接对象的方法
	public static Jedis getJedis(){
		return jedisPool.getResource();
	}
	
	// 释放jeids资源的方法
	public static void closeJedis(Jedis jedis){
		if(jedis!=null){
			jedis.close();
		}
	}
	
	public static void main(String[] args) {
		// 从连接池中获取jedis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		// 操作
		jedis.set("aaa", "哈哈");
		String str = jedis.get("aaa");
		System.out.println(str);
		// 释放资源
		JedisPoolUtils.closeJedis(jedis);
	}
	
}
