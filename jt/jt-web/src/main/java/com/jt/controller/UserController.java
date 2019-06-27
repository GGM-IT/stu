package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.remoting.exchange.Response;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;
   



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private JedisCluster jedisCluster;
	
	
	//导入dubbo的用户接口
	@Reference(timeout = 3000,check = false)
	 private DubboUserService userService;
	
//	@Autowired
//	private UserService userService;

	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	//使用dubbo形式实现业务调用
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
           e.printStackTrace();
           return SysResult.fail();
		}
	}
	
	//实现用户的登录
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {
		try {
			//调用sso系统秘钥
			String token = userService.findUserByUP(user);
			//判断数据是否正确
			//cookie中的key固定值JT_TICKET
			if (!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET",token);
				/**
				 * cookie生命周期
				 *    cookie.setMaxAge(0);立即删除
				 *    cookie.setMaxAge(值>0)能够存活多久 单位/秒
				 *    cookie.setMaxAge(-1) 当回话结束后删除
				 * cookie.setPath("/abc");权限
				 *   www.jt.com/abc/a.html
				 *   www.jt.com/b.html
				 *   可以获取abc下面的a.html 获取不到b.html
				 *   
				 *   Response
				 */
				cookie.setMaxAge(7*24*3600);
				cookie.setDomain("jt.com");//实现数据的共享
				cookie.setPath("/");//权限
				response.addCookie(cookie);
				return SysResult.ok();
			
			}
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		return SysResult.fail();
	}
	/**
	 * 实现用户退出登录
	 * 删除redis request对象~~~cookie中~~~JT_TICKET
	 * 删除cookie
	 */
	//实现用户登出操作
	@RequestMapping("/logout")
    public 	String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if(cookies.length !=0) {
			
		
		String tooken = null;
			for (Cookie cookie : cookies) {
				if("JT_TICKET".equals(cookie.getName())){
					tooken = cookie.getValue();
					break;
		}
			}
			//判断cookie是否有值  删除redis cookie
			if(!StringUtils.isEmpty(tooken)) {
				jedisCluster.del(tooken);
				
				Cookie cookie = new Cookie("JT_TICKET","");
			      cookie.setMaxAge(0);
			      
			      cookie.setDomain("jt.com");
			      cookie.setPath("/");
			     
			      response.addCookie(cookie);
			}
		}
		//当用户登出时,页面重定向首页
		return "redirect:/";
	}
	
	
}
