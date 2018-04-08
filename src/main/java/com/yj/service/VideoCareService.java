package com.yj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.VideoCareMapper;
import com.yj.pojo.VideoCare;
import com.yj.pojo.Video_room;

@Service
public class VideoCareService {
	@Autowired
	public VideoCareMapper careMapper;
	
	public boolean addCare(VideoCare care){
		int i=careMapper.insertSelective(care);
		if(i>0) return true;
		return false;
	}
	
	public boolean cancelCare(VideoCare care){
		int i=careMapper.deleteByUserIdAndVideoId(care);
		if(i>0) return true;
		return false;
	}
	
	public List<VideoCare> getCarelist(int userId){
		return careMapper.selectByUserId(userId);
	}
	
	public List<Video_room> getVideoSourceByUserId(int userId){
		return careMapper.selectVideoSourceByUserId(userId);
	}
}
