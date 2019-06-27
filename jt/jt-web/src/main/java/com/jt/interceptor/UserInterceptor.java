package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

@Component //将拦截器交给spring容器管理
public class UserInterceptor implements HandlerInterceptor{
   
	@Autowired
	private JedisCluster jedisCluster;
	          
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1.获取cookie信息
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		
		//2.判断token是否有效
		if(!StringUtils.isEmpty(token)) {
		String userJSON = jedisCluster.get(token);
		if(!StringUtils.isEmpty(userJSON)) {
			//redis中有数据
				User user = ObjectMapperUtil.toObject(userJSON,User.class);
				//request.setAttribute("JT_USER", user);
				//request.getSession().setAttribute("JT_USER", user);//用户每次请求都将数据保存到session中.切记用完销毁
			UserThreadLocal.set(user);
				return true;
			}
		}
		//3.重定向到用户的登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
}
