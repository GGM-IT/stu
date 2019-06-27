package com.jt.util;

import com.jt.pojo.User;

//ThreadLocal是线程安全的
public class UserThreadLocal {
	/**
	 * 如何存取多个数据 map集合
	 * ThreadLocal<Map<k,v>>
	 */
	private static ThreadLocal<User> thread =
			new ThreadLocal<>(); 
	public static void set(User user) {
	
		thread.set(user);
	 
	}
	public static User get() {
		
		return thread.get();
	}
	
	//使用ThreadLocal切记关闭 防止内存泄露
	public static void remove() {
		thread.remove();
	}
	}
