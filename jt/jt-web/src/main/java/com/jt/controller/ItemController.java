package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
public class ItemController {
	/**
	 * 根据商品的id查询后台服务器数据
	 * 业务步骤
	 * 
	 */
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/items/{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model) {
		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item",item);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		model.addAttribute("itemDesc",itemDesc);
		
		
		return "item";
	}
}
