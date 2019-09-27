package com.yj.dao;

import com.yj.pojo.Video_category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Video_categoryDao extends JpaRepository<Video_category,Integer> {
	
	@Query(nativeQuery = true,value = "update `video_category` set `video_type_img`=?2"
			+ " where `video_type_id`=?1")
	@Modifying
	void update_video_img_url(@Param("video_type_id")int video_type_id, @Param("img_url")String img_url);
	
	@Query("select c from Video_category c ")
	List<Video_category> selectAll_Video_category();
	
	@Query("select c from Video_category c where c.videoType=?1")
	Video_category selectVideo_type_idByVideo_type(String video_type);
}
