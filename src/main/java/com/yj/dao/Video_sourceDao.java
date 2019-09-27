package com.yj.dao;

import com.yj.pojo.Video_source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface Video_sourceDao extends JpaRepository<Video_source,String> {

	@Query("update Video_source v set v.videoStatus=?1")
	@Modifying
	void updateAllVideo_source_status(int video_status);


	
	@Query("select count(v) from Video_source v")
	int selectVideoCount();
}
