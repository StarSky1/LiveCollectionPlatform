package com.yj.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.yj.pojo.Video_host;


public interface Video_hostDao extends JpaRepository<Video_host,String> {
	
	@Query(value = "select h from Video_host h where h.videoHostNickname=?1")
	Video_host selectHostByhost_nickname(String host_nickname);

	
}
