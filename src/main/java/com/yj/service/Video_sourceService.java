package com.yj.service;

import java.util.Date;

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
		//删除直播间表所有数据，重新导入直播间数据，已获取最新直播信息
		video_sourceDao.deleteAllVideo_source();
		Date beginDate1=new Date();
		Date beginDate=null;
		Date endDate=null;
		long time=0;
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json=jsonArray.getJSONObject(i);
			Video_host host=(Video_host) json.get("video_host");
			Video_source source=(Video_source) json.get("video_source");
			source.setVideo_id(i);
			beginDate=new Date();
			System.out.println("开始录入\""+host.getVideo_host_nickname()+
					"\"主播和\""+source.getVideo_title()+"\"直播间数据...");
			//添加主播信息
			video_hostService.insertVideo_host(host);
			source.setVideo_host_id(host.getVideo_host_id());
			//添加直播间信息
			video_sourceDao.insert_video_source(source);
			endDate=new Date();
			time=endDate.getTime()-beginDate.getTime();
			System.out.println("录入成功，耗时："+time/1000.0+"s");
		}
		endDate=new Date();
		time=endDate.getTime()-beginDate1.getTime();
		System.out.println("总耗时："+time/1000.0+"s");
	}
	
	@Test
	public void testGetPanda_video_source(){
		getPanda_video_source("https://www.panda.tv/all","https://www.panda.tv/live_lists");
	}
	
	
}
