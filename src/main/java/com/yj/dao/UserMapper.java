package com.yj.dao;

import com.yj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMapper extends JpaRepository<User,Integer> {

    @Query(value = "select u from User u where u.userId=:userId")
    User selectByPrimaryKey(@Param("userId") Integer userId);

    @Query(value = "select u from User u where u.userName=:username")
    User selectByUsername(@Param("username") String username);

    @Query(value = "select u from User u where u.userPhone=:phone")
    User selectByPhone(@Param("phone") String phone);

    @Query(value = "select u from User u where u.userEmail=:email")
    User selectByEmail(@Param("email") String email);

}