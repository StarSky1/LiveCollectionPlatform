package com.yj.service;

import com.alibaba.fastjson.JSONObject;
import com.yj.dao.Video_sourceDao;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Video_sourceService {
	@Autowired
	public Video_sourceDao video_sourceDao;
	@Autowired
	public Video_hostService video_hostService;
	
	/**
	 * status为0表示未开播，1表示正在直播
	 * @param status
	 */
	public void updateAllVideo_source_status(int status){
		video_sourceDao.updateAllVideo_source_status(status);
	}
	
	public void updateVideo_source(JSONObject json){
		if(json==null || json.size()==0){
			System.out.println("无数据录入");
			return;
		}
		@SuppressWarnings("unchecked")
		List<Video_host> host_list=(List<Video_host>) json.get("host_list");
		@SuppressWarnings("unchecked")
		List<Video_source> source_list=(List<Video_source>) json.get("source_list");
		
		//使用replace into语句批量更新直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		//批量添加直播间信息
		video_sourceDao.saveAll(source_list);
		//批量添加主播信息
		video_hostService.replace_host_list(host_list);
	}
	
	public int getVideoCount(){
		return video_sourceDao.selectVideoCount();
	}
	
}
