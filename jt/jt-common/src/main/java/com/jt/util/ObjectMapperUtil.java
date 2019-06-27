package com.jt.util;

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

//编辑工具类实现对象对josn转化
public class ObjectMapperUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static String toJSON(Object target) {
		String json = null;
		try {
           json = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
		//将检查的异常转化为运行异常
			throw new RuntimeException();
		}
		return json;
	}

		public static <T> T toObject(String json,Class<T> targetClass) {
			T target = null;
			try {
	           target = MAPPER.readValue(json, targetClass);
			} catch (Exception e) {
			//将检查的异常转化为运行异常
				throw new RuntimeException();
			}
			return target;
		}
	}
