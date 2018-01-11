package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.yj.pojo.Video_category;

@Component
public interface Video_categoryDao {
	
	@Update("update `video-category` set `video-type-img`=#{img_url,jdbcType=VARCHAR}"
			+ " where `video-type-id`=#{video_type_id,jdbcType=INTEGER}")
	public int update_video_img_url(@Param("video_type_id")int video_type_id,@Param("img_url")String img_url);
	
	@Select("select * from `video-category`")
	@ResultMap(value={"video_category_ResultMap"})
	public List<Video_category> selectAll_Video_category();
	
	@Select("select * from `video-category` where `video-type`=#{video)type,jdbcType=VARCHAR}")
	@ResultMap(value={"video_category_ResultMap"})
	public Video_category selectVideo_type_idByVideo_type(String video_type);
}
