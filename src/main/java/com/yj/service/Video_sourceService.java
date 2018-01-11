package com.yj.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.dao.Video_sourceDao;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;
import com.yj.spider.HtmlSpiderUtils;

@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class Video_sourceService {

	@Autowired
	public HtmlSpiderUtils htmlSpider;
	@Autowired
	public Video_sourceDao video_sourceDao;
	@Autowired
	public Video_hostService video_hostService; 
	
	public void getPanda_video_source(String video_platform_all_url,String live_lists_url){
		int total_page=htmlSpider.getPanda_videos_totalPage(video_platform_all_url);
		JSONArray jsonArray=htmlSpider.getPanda_Video_source(live_lists_url,total_page);
		if(jsonArray==null || jsonArray.size()==0){
			System.out.println("无数据录入");
			return;
		}
		//将直播间表中现有直播间的直播状态全部修改为0  未开播
		video_sourceDao.updateAllVideo_source_status(0);
		List<Video_host> host_list=new ArrayList<>();
		List<Video_source> source_list=new ArrayList<>();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json=jsonArray.getJSONObject(i);
			Video_host host=(Video_host) json.get("video_host");
			Video_source source=(Video_source) json.get("video_source");
			host_list.add(host);
			source_list.add(source);
		}
		//使用replace into语句批量更新直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		//批量添加直播间信息
		video_sourceDao.replace_source_list(source_list);
		//批量添加主播信息
		video_hostService.replace_host_list(host_list);
		
	}
	
	@Test
	public void testGetPanda_video_source(){
		getPanda_video_source("https://www.panda.tv/all?pdt=1.r_26668.psbar-co.a1.3f5g2qe91vj","https://www.panda.tv/live_lists");
	}
	
	
}
