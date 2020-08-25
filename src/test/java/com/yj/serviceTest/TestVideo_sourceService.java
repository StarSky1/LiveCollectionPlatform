package com.yj.serviceTest;

import com.alibaba.fastjson.JSONObject;
import com.yj.service.Video_sourceService;
import com.yj.spider.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVideo_sourceService {
	@Autowired
	private Video_sourceService video_sourceService;
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
	@Qualifier(value="huyaSpider")
	public HuyaLiveSpider huyaLiveSpider;
	@Autowired
	@Qualifier(value="longzhuSpider")
	public LongzhuLiveSpider longzhuLiveSpider;
	@Autowired
	@Qualifier(value="quanminSpider")
	public QuanminTvSpider quanminTvSpider;
	@Autowired
	@Qualifier(value="biliLiveSpider")
	public BilibiliLiveSpider bilibiliLiveSpider;
	
	@Test
	public void testUpdateVideo_source(){
		String live_lists_url;
		int total_page;
		JSONObject json;
		HtmlSpiderUtils[] spiders={huyaLiveSpider,douyuTvSpider,bilibiliLiveSpider};
//		String url_list[]={//"https://www.panda.tv/live_lists",
//				"http://www.zhanqi.tv/api/static/v2.1/live/list/20","http://www.huya.com/cache.php",
//				"http://api.plu.cn/tga/streams","http://www.quanmin.tv/json/play/list"};
		String url_list[]={"http://www.huya.com/cache.php","https://www.douyu.com/gapi/rkc/directory/0_0","https://api.live.bilibili.com/room/v1/Area/getListByAreaID"};

		//将直播间表中现有直播间的直播状态全部修改为0  未开播
		video_sourceService.updateAllVideo_source_status(0);
		int i=0;
		for(HtmlSpiderUtils spider : spiders){
			//更新直播间
			live_lists_url=url_list[i];
			total_page=spider.getTv_videos_totalPage(live_lists_url);

			if(total_page>10){
				total_page=10;
			}
			json=spider.crawlData(live_lists_url, total_page);
			video_sourceService.updateVideo_source(json);
			i++;
		}
	}
	
	@Test
	public void testUpdateDouyuTVSource(){
		String live_lists_url;
		int total_page;
		JSONObject json;
		live_lists_url="https://www.douyu.com/gapi/rkc/directory/0_0";
		total_page=douyuTvSpider.getTv_videos_totalPage(live_lists_url);
		json=douyuTvSpider.getTv_Video_sourceBymulti_thread(live_lists_url, 10);
		video_sourceService.updateVideo_source(json);
	}
	
	@Test
	public void testUpdateHuyaLiveSource(){
		String live_lists_url;
		int total_page;
		JSONObject json;
		live_lists_url="http://www.huya.com/cache.php";
		total_page=huyaLiveSpider.getTv_videos_totalPage(live_lists_url);
		json=huyaLiveSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		video_sourceService.updateVideo_source(json);
	}

	@Test
	public void testUpdateBiliLiveSource(){
		String live_lists_url;
		int total_page;
		JSONObject json;
		live_lists_url="https://api.live.bilibili.com/room/v1/Area/getListByAreaID";
		total_page=bilibiliLiveSpider.getTv_videos_totalPage(live_lists_url);
		json=bilibiliLiveSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		video_sourceService.updateVideo_source(json);
	}
}
