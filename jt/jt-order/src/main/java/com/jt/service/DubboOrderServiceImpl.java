package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class DubboOrderServiceImpl implements DubboOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	
	@Transactional
	@Override
	public String insertOrder(Order order) {
		//获取oederid
		String orderId = ""+order.getUserId()+System.currentTimeMillis();
		Date date = new Date();
		
		//入库订单
		order.setOrderId(orderId)
			.setStatus(1)
			.setCreated(date)
			.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单已入库");
		
		//入库订单物流
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("物流");
		
		//入库订单商品
		List<OrderItem> orderList = order.getOrderItems();
		for (OrderItem orderItem : orderList) {
			orderItem.setOrderId(orderId)
			.setCreated(date)
			.setUpdated(date);
			orderItemMapper.insert(orderItem);
			System.out.println("成功!!!!!!!!!");
		}
		return orderId;
	}


	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping shipping = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<OrderItem>();
		queryWrapper.eq("order_id", id);
		List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
		order.setOrderItems(list).setOrderShipping(shipping);
		return order;
	}
	
	
	
	
}
