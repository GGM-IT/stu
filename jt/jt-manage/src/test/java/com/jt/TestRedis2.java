package com.jt;

import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.135.129",6379);
		jedis.hset("user1", "id", "200");
		jedis.hset("user1", "name", "tom");
		String hget = jedis.hget("user1", "name");
		System.out.println(hget);
		
		Map<String, String> hget1 = jedis.hgetAll("user1");
		System.out.println(hget1);
	}
	//编辑list集合
	@Test
	public void list() {
		Jedis jedis = new Jedis("192.168.135.129",6379);
		jedis.lpush("list", "1","2","3","4");
		System.out.println(jedis.lpop("list"));
	}
	
	
	//redis事务控制
	@Test
	public void TestTx() {
		Jedis jedis = new Jedis("192.168.135.129",6379);
		Transaction multi = jedis.multi();//开启事务
		try {
			multi.set("ww", "wwww");
			multi.set("bb",null);
			multi.exec();
		} catch (Exception e) {
			multi.discard();
		}
	}
}
