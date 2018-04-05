package com.yj.service;

import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.UserMapper;
import com.yj.pojo.User;

@Service
public class UserService {
	@Autowired
	public UserMapper userMapper;
	private String salt="1314";
	
	public User getUserByUserName(String username){
		return userMapper.selectByUsername(username);
	}
	
	public boolean validateUser(String username,String password){
		User user=getUserByUserName(username);
		if(user==null) return false;
		if(StringUtils.toMD5(password+salt).equals(user.getUserPassword())){
			return true;
		}
		return false;
	}
	
	public boolean addUser(String username,String password,String phone,String email){
		User user=new User();
		user.setUserName(username);
		user.setUserNickname(username);
		user.setUserResume("这个人很懒，什么都没有留下。");
		user.setUserVideoLevel(1);
		user.setUserHeadImg("default.jpg");
		user.setUserPassword(StringUtils.toMD5(password+salt));
		user.setUserPhone(phone);
		user.setUserEmail(email);
		int i=userMapper.insertSelective(user);
		if(i>0) return true;
		return false;
	}
	
	public boolean validateUsername(String username){
		User user=userMapper.selectByUsername(username);
		if(user!=null) return false;
		else return true;
	}
	
	public boolean validatePhone(String phone){
		User user=userMapper.selectByPhone(phone);
		if(user!=null) return false;
		else return true;
	}
	
	public boolean validateEmail(String email){
		User user=userMapper.selectByEmail(email);
		if(user!=null) return false;
		else return true;
	}
	
}
