package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

//因为需要跳转页面 所以不能使用restController
//如果后期返回值是json串.则在方法上添加@ResponseBody注解
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	
	/**
	 * 1.实现商品列表信息展现
	 * 2.页面取值: ${cartList}
	 */
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		
		//User user = (User)request.getAttribute("JT_USER");
		//Long userId = user.getId();
		//Long userId = 7L; //暂时写死
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = 		
		cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";//返回页面逻辑名称
	}
	
	/**
	 * 实现购物车数量修改
	 * 规定url参数中使用restFUL风格获取数据时
	 * 介绍参数是对象并且属性匹配,则可以使用对象接受
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateCatNum(Cart cart) {
		try {
			Long userId = UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.fail();
			
			}
	}
	
	//购物车删除
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	/**
	 * 新增购物车
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.insertCart(cart);
		
		return "redirect:/cart/show.html";
	}
	
	
	
	
	
	
}
