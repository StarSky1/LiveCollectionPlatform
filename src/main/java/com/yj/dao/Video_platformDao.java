package com.yj.dao;

import com.yj.pojo.Video_platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Video_platformDao extends JpaRepository<Video_platform,Integer>{

	@Query(value = "select v from Video_platform v where v.videoPlatform=?1")
	Video_platform selectVideo_platformByName(String video_platform);
	
	@Query("select p from Video_platform p")
	List<Video_platform> selectVideo_platformList();
	
	@Query("update Video_platform p set p.videoPlatformImg=?2"
			+ "where p.videoPlatformId=?1")
	@Modifying
	void updateVideo_platformImg(int video_platform_id,String platform_img);
}
