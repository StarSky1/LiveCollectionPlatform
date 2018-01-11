package com.yj.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.yj.pojo.Video_platform;

@Component
public interface Video_platformDao {

	@Select("select * from `video-platform` where `video-platform`=#{video_platform,jdbcType=VARCHAR}")
	@ResultMap(value={"video_platform_ResultMap"})
	public Video_platform selectVideo_platformByName(String video_platform);
}
