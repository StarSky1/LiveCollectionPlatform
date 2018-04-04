package com.yj.spider;

import com.alibaba.fastjson.JSONObject;
import com.yj.service.Video_sourceService;

public class CrawlVideoRunnable implements Runnable{
	private HtmlSpiderUtils spider;
	private String live_lists_url;
	private Video_sourceService video_sourceService;
	
	public CrawlVideoRunnable(HtmlSpiderUtils spider,String live_lists_url) {
		this.spider=spider;
		this.live_lists_url=live_lists_url;
	}
	@Override
	public void run() {
		int total_page=spider.getTv_videos_totalPage(live_lists_url);
		JSONObject json=spider.crawlData(live_lists_url, total_page);
		video_sourceService.updateVideo_source(json);
	}
	
}
