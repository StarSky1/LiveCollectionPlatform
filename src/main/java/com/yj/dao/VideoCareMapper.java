package com.yj.dao;

import com.yj.pojo.VideoCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoCareMapper extends JpaRepository<VideoCare,Integer> {
    
    @Query(value = "select v from VideoCare v where v.userid=?1")
    List<VideoCare> selectByUserId(int userId);

}