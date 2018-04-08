package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.yj.pojo.VideoCare;
import com.yj.pojo.Video_room;

public interface VideoCareMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByUserIdAndVideoId(VideoCare care);

    int insert(VideoCare record);

    int insertSelective(VideoCare record);

    VideoCare selectByPrimaryKey(Integer id);
    
    @Select("select * from `video-care` where userId=#{userId,jdbcType=INTEGER}")
    @ResultMap("BaseResultMap")
    List<VideoCare> selectByUserId(int userId);
    
    List<Video_room> selectVideoSourceByUserId(int userId);

    int updateByPrimaryKeySelective(VideoCare record);

    int updateByPrimaryKey(VideoCare record);
}