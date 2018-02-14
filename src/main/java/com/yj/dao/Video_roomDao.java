package com.yj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.yj.pojo.Video_room;

@Repository
public interface Video_roomDao {
	
	public List<Video_room> selectList(Map<String,Object> params);
	
	@Select("select count(*) from `video-room`")
	public int selectVideoCount();
}
