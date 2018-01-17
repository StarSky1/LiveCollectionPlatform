package com.yj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yj.dao.DouyuTV_Video_sourceDao;
import com.yj.dao.Video_sourceDao;
import com.yj.dao.ZhanqiTV_Video_sourceDao;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;
import com.yj.spider.DouyuTvSpider;
import com.yj.spider.PandaTvSpider;
import com.yj.spider.ZhanqiTvSpider;

@Service
public class Video_sourceService {

	@Autowired
	@Qualifier(value="pandaSpider")
	public PandaTvSpider pandaSpider;
	@Autowired
	@Qualifier(value="douyuSpider")
	public DouyuTvSpider douyuTvSpider;
	@Autowired
	@Qualifier(value="zhanqiSpider")
	public ZhanqiTvSpider zhanqiTvSpider;
	@Autowired
	public Video_sourceDao video_sourceDao;
	@Autowired
	public DouyuTV_Video_sourceDao douyuTV_Video_sourceDao;
	@Autowired
	public ZhanqiTV_Video_sourceDao zhanqiTV_Video_sourceDao;
	@Autowired
	public Video_hostService video_hostService;
	
	public void getPanda_video_source(String video_platform_all_url,String live_lists_url){
		int total_page=pandaSpider.getTv_videos_totalPage(live_lists_url);
		JSONObject json=pandaSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		if(json==null || json.size()==0){
			System.out.println("无数据录入");
			return;
		}
		//将直播间表中现有直播间的直播状态全部修改为0  未开播
		video_sourceDao.updateAllVideo_source_status(0);
		@SuppressWarnings("unchecked")
		List<Video_host> host_list=(List<Video_host>) json.get("host_list");
		@SuppressWarnings("unchecked")
		List<Video_source> source_list=(List<Video_source>) json.get("source_list");
		
		//使用replace into语句批量更新直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		//批量添加直播间信息
		video_sourceDao.replace_source_list(source_list);
		//批量添加主播信息
		video_hostService.replace_host_list(host_list);
		
	}
	
	public void getDouyu_video_source(String live_lists_url){
		int total_page=douyuTvSpider.getTv_videos_totalPage(live_lists_url);
		JSONObject json=douyuTvSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		if(json==null || json.size()==0){
			System.out.println("无数据录入");
			return;
		}
		//将直播间表中现有直播间的直播状态全部修改为0  未开播
		douyuTV_Video_sourceDao.updateAllVideo_source_status(0);
		@SuppressWarnings("unchecked")
		List<Video_host> host_list=(List<Video_host>) json.get("host_list");
		@SuppressWarnings("unchecked")
		List<Video_source> source_list=(List<Video_source>) json.get("source_list");
		
		//使用replace into语句批量更新直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		//批量添加直播间信息
		douyuTV_Video_sourceDao.replace_source_list(source_list);
		//批量添加主播信息
		video_hostService.replace_DouyuTV_host_list(host_list);
	}
	
	public void getZhanqi_video_source(String live_lists_url){
		int total_page=zhanqiTvSpider.getTv_videos_totalPage(live_lists_url);
		JSONObject json=zhanqiTvSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		if(json==null || json.size()==0){
			System.out.println("无数据录入");
			return;
		}
		//将直播间表中现有直播间的直播状态全部修改为0  未开播
		zhanqiTV_Video_sourceDao.updateAllVideo_source_status(0);
		@SuppressWarnings("unchecked")
		List<Video_host> host_list=(List<Video_host>) json.get("host_list");
		@SuppressWarnings("unchecked")
		List<Video_source> source_list=(List<Video_source>) json.get("source_list");
		
		//使用replace into语句批量更新直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		//批量添加直播间信息
		zhanqiTV_Video_sourceDao.replace_source_list(source_list);
		//批量添加主播信息
		video_hostService.replace_ZhanqiTV_host_list(host_list);
	}
	

	
	
}
