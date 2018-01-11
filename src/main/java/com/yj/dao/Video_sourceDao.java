package com.yj.dao;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Component;

import com.yj.pojo.Video_source;

@Component
public interface Video_sourceDao {
	
	
	public int insert_video_source(Video_source video_source);
	
	@Delete("delete from `video-source`")
	public int deleteAllVideo_source();
	

}
