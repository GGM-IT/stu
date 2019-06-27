 package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//表示redis配置类

@SuppressWarnings("unused")
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
//	@Value("${jedis.host}")
//	private String host;
//	@Value("${jedis.port}")
//	private Integer port;
//	@Bean
//	public Jedis jedis() {
//		return new Jedis(host,port);
//	}
	
//   @Value("${redis.nodes}")
//   private String nodes;
//   @Bean
//   public  ShardedJedis getShards() {
//	   List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//	   //将nodes中的数据进行分组{ip:端口}
//	   String[] node = nodes.split(",");
//	   for (String nodeArgs : node) {
//		   String[] args = nodeArgs.split(":");
//		   String nodeIP = args[0];
//		   int nodePort = Integer.parseInt(args[1]);
//		   JedisShardInfo info = new JedisShardInfo(nodeIP, nodePort);
//	    shards.add(info);
//	   }
//	   return new ShardedJedis(shards);
//   }
	
	
	//哨兵
//	@Value("${redis.sentinels}")
//	private String jedisSentinelNodes;
//	@Value("${redis.sentinel.masterName}")
//	private String masterName;
//	
//	@Bean		
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(jedisSentinelNodes);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
//	
 
	
	//集群
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		//1按照 , 号拆分:拆分获取ip和端口
		Set<HostAndPort> nodes = new HashSet<>();
		//nodes.add(new HostAndPort("192.168.135.129",7000));
		
		 String[] node = redisNodes.split(",");
		   for (String nodeArgs : node) {
			   String[] args = nodeArgs.split(":");
			   String nodeIP = args[0];
			   int nodePort = Integer.parseInt(args[1]);
		   HostAndPort info = new HostAndPort(nodeIP, nodePort);
		  nodes.add(info);
		   }
		return new JedisCluster(nodes);
	
		  
		   }

}


