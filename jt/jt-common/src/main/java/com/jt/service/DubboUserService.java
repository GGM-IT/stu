package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {
     
	//实现业务新增
	void saveUser(User user);

	String findUserByUP(User user);

}
