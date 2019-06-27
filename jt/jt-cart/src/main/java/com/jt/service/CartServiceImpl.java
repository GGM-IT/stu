package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.service.DubboCartService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
@Service
public class CartServiceImpl implements DubboCartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}
    @Transactional
	@Override
	public void updateCartNum(Cart cart) {
		Cart tempCart = new Cart();
		tempCart.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		updateWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		
		cartMapper.update(tempCart, updateWrapper);
	}
    @Transactional
	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>(cart);
		
		cartMapper.delete(queryWrapper);
	
	}
    
    /**
     * 新增业务
     * 1.用户第一次新增可以直接入库
     * 2.用户不是第一次入库,应该只做数据的修改
     */
    @Transactional
	@Override
	public void insertCart(Cart cart) {
    	QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("user_id", cart.getUserId())
    				.eq("item_id", cart.getItemId());
		Cart cartDB = cartMapper.selectOne(queryWrapper);
		if(cartDB == null) {
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);
			
		}else {
			//表示用户多次添加购物车,只做数量的修改
			int num = cart.getNum() + cartDB.getNum();
			cartDB.setNum(num).setUpdated(new Date());
			cartMapper.updateById(cartDB);
		}
	}

}
