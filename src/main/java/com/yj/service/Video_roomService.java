package com.yj.service;

import com.yj.dao.Video_roomDao;
import com.yj.pojo.Video_room;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Video_roomService {
	@Autowired
	public Video_roomDao video_roomDao;
	
	
	public List<Video_room> getVideoList(String searchWord,String platform,String type,int pageno,int pagesize){
		int start=(pageno-1)*pagesize;
		int offset=pagesize;
		Map<String,Object> params=new HashMap<>();
		params.put("searchWord", StringUtils.isEmpty(searchWord)?"":searchWord);
		params.put("platform", StringUtils.isEmpty(platform)?"":platform);
		params.put("type", StringUtils.isEmpty(type)?"":type);
		params.put("start", start);
		params.put("offset", offset);
		return video_roomDao.selectList(params);
	}
	
	public int getVideoCount(String searchWord,String platform,String type){
		Map<String,Object> params=new HashMap<>();
		params.put("searchWord", StringUtils.isEmpty(searchWord)?"":searchWord);
		params.put("platform", StringUtils.isEmpty(platform)?"":platform);
		params.put("type", StringUtils.isEmpty(type)?"":type);
		return video_roomDao.selectVideoCount(params);
	}
	
}
