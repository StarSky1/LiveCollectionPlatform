package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yj.pojo.Video_platform;

@Repository
public interface Video_platformDao {

	@Select("select * from `video-platform` where `video-platform`=#{video_platform,jdbcType=VARCHAR}")
	@ResultMap(value={"video_platform_ResultMap"})
	public Video_platform selectVideo_platformByName(String video_platform);
	
	@Select("select * from `video-platform`")
	@ResultMap(value={"video_platform_ResultMap"})
	public List<Video_platform> selectVideo_platformList();
	
	@Update("update `video-platform` set `video-platform-img`=#{arg1,jdbcType=VARCHAR} "
			+ "where `video-platform-id`=#{arg0,jdbcType=INTEGER}")
	public int updateVideo_platformImg(int video_platform_id,String platform_img);
}
