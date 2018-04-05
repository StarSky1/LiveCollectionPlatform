package com.yj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yj.pojo.Video_source;

@Repository
public interface Video_sourceDao {
	
	
	public int insert_video_source(Video_source video_source);
	
	@Update("update `video-source` set `video-status`=#{arg0,jdbcType=INTEGER}")
	public int updateAllVideo_source_status(int video_status);
	
	public int replace_source_list(List<Video_source> source_list);
	
	public List<Video_source> selectList(Map<String,Object> params);
	
	@Select("select count(*) from `video-source`")
	public int selectVideoCount();
}
