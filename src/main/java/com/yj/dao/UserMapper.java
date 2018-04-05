package com.yj.dao;

import com.yj.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);
    
    User selectByUsername(String username);
    
    User selectByPhone(String phone);
    
    User selectByEmail(String email);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}