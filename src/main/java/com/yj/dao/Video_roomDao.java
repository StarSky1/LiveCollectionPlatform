package com.yj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yj.pojo.Video_room;

@Repository
public interface Video_roomDao {
	
	public List<Video_room> selectList(Map<String,Object> params);
	
	public int selectVideoCount(Map<String,Object> params);
}
