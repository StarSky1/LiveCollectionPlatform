package com.yj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.Video_roomDao;
import com.yj.pojo.Video_room;

@Service
public class Video_roomService {
	@Autowired
	public Video_roomDao video_roomDao;
	
	
	public List<Video_room> getVideoList(String platform,String type,int pageno,int pagesize){
		int start=(pageno-1)*pagesize;
		int offset=pagesize;
		Map<String,Object> params=new HashMap<>();
		params.put("platform", StringUtils.isEmpty(platform)?null:platform);
		params.put("type", StringUtils.isEmpty(type)?null:type);
		params.put("start", start);
		params.put("offset", offset);
		return video_roomDao.selectList(params);
	}
	
	public int getVideoCount(String platform,String type){
		Map<String,Object> params=new HashMap<>();
		params.put("platform", StringUtils.isEmpty(platform)?null:platform);
		params.put("type", StringUtils.isEmpty(type)?null:type);
		return video_roomDao.selectVideoCount(params);
	}
	
}
