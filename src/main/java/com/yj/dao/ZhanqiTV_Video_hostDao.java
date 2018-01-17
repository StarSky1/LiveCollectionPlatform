package com.yj.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.yj.pojo.Video_host;

@Repository
public interface ZhanqiTV_Video_hostDao {

	public int insertVideo_host(Video_host video_host);
	
	@Select("select * from `zhanqitv-video-host` where `video-host-nickname`=#{host_nickname,jdbcType=VARCHAR}")
	@ResultMap(value={"video_host_ResultMap"})
	public Video_host selectHostByhost_nickname(String host_nickname);
	
	public int updateVideo_host(Video_host video_host);
	
	public int replace_host_list(List<Video_host> host_list);
	
}
