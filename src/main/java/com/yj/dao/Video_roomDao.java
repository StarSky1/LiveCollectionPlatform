package com.yj.dao;

import com.yj.pojo.Video_room;
import com.yj.repository.VideoRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Video_roomDao {
	@Autowired
	private VideoRoomRepository repository;

	public List<Video_room> selectList(Map<String,Object> params){
		String searchWord=(String)params.get("searchWord");
		String platform=(String)params.get("platform");
		String type=(String)params.get("type");
		int start=(Integer)params.get("start");
		int offset=(Integer)params.get("offset");
		return repository.getVideoRoomList(searchWord,platform,type,start,offset);
	}
	
	public int selectVideoCount(Map<String,Object> params){
		String searchWord=(String)params.get("searchWord");
		String platform=(String)params.get("platform");
		String type=(String)params.get("type");
		return repository.countVideoRoomList(searchWord,platform,type);
	}
}
