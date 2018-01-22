package com.yj.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yj.service.Video_sourceService;
import com.yj.spider.DouyuTvSpider;
import com.yj.spider.HuyaLiveSpider;
import com.yj.spider.LongzhuLiveSpider;
import com.yj.spider.PandaTvSpider;
import com.yj.spider.ZhanqiTvSpider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
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
	
	@Test
	public void testUpdateVideo_source(){
		String live_lists_url;
		int total_page;
		JSONObject json;
		//更新熊猫tv直播间
//		live_lists_url="https://www.panda.tv/live_lists";
//		total_page=pandaSpider.getTv_videos_totalPage(live_lists_url);
//		JSONObject json=pandaSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
//		video_sourceService.updateVideo_source(json);
		//更新斗鱼tv直播间
		live_lists_url="https://www.douyu.com/gapi/rkc/directory/0_0";
		total_page=douyuTvSpider.getTv_videos_totalPage(live_lists_url);
		json=douyuTvSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
		video_sourceService.updateVideo_source(json);
		//更新战旗tv直播间
//		live_lists_url="http://www.zhanqi.tv/api/static/v2.1/live/list/20";
//		total_page=zhanqiTvSpider.getTv_videos_totalPage(live_lists_url);
//		json=zhanqiTvSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
//		video_sourceService.updateVideo_source(json);
		//更新虎牙tv直播间
//		live_lists_url="http://www.huya.com/cache.php";
//		total_page=huyaLiveSpider.getTv_videos_totalPage(live_lists_url);
//		json=huyaLiveSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
//		video_sourceService.updateVideo_source(json);
		//更新龙珠tv直播间
//		live_lists_url="http://api.plu.cn/tga/streams";
//		total_page=longzhuLiveSpider.getTv_videos_totalPage(live_lists_url);
//		json=longzhuLiveSpider.getTv_Video_sourceBymulti_thread(live_lists_url, total_page);
//		video_sourceService.updateVideo_source(json);
	}
}
