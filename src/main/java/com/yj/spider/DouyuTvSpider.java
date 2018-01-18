package com.yj.spider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_category;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_platform;
import com.yj.pojo.Video_source;

/**
 * 斗鱼tv 爬虫
 * @author Captain
 *
 */
@Component(value="douyuSpider")
public class DouyuTvSpider extends HtmlSpiderUtils{
	
	public static final Object signal = new Object();   //线程间通信变量  
	
	int threadCount=25;	//线程数量
	
	int waitThread=0;	//等待线程的数量
	
	int crawled_page=0;	//已爬取的页数
	
	int pagenum=120;
	
	private Map<String,Video_category> cate_map;
	
	private Video_platform video_platform;
	
	private Logger logger=LoggerFactory.getLogger(DouyuTvSpider.class);

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		String data_str=getTv_Video_source(live_lists_url, 1);
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		int total_page=json.getJSONObject("data").getIntValue("pgcnt");
		return total_page;
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		Map<String,Object> map=null;
		//斗鱼tv 当前页直播信息获取地址格式 https://www.douyu.com/gapi/rkc/directory/0_0/2
		live_lists_url=live_lists_url+"/"+pageno;
		//request params
		//null
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "text/plain, */*; q=0.01");
		requestHeadersMap.put("x-requested-with", "XMLHttpRequest");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, sdch, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh;q=0.8");
		requestHeadersMap.put("cookie", "TY_SESSION_ID=f666e5b1-1203-474e-96dd-47ba6da3eef6; dy_did=c3c9d81c2b977844c69a900300001501; smidV2=20180117104127493b2e9e675bded09eb2530e16af475700d3b0bdd77184ff0; _dys_lastPageCode=page_studio_normal,; acf_did=c3c9d81c2b977844c69a900300001501; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1516096205,1516154509; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1516159303");
		requestHeadersMap.put("referer", "https://www.douyu.com/directory/all");
		requestHeadersMap.put("x-tingyun-id", "OvAORhvqgtg;r=159313012");
		
		//获取当前直播页面中所有直播间信息的json字符串
		String live_dataStr=getRequestStr(live_lists_url, "GET", map,requestHeadersMap);
		return live_dataStr;
	}

	@Override
	public void parseVideo_items_JSONStr(String live_dataStr, List<Video_host> host_list,
			List<Video_source> source_list) {
		JSONObject json=JSON.parseObject(live_dataStr);
		JSONArray items=json.getJSONObject("data").getJSONArray("rl");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getString("c2name");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideo_type_id();
			Video_source source=new Video_source();
			source.setVideo_room_url(json.getString("url"));
			source.setVideo_img(json.getString("rs1"));
			source.setVideo_title(json.getString("rn"));
			source.setVideo_number(json.getIntValue("ol"));
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id(json.getIntValue("rid"));
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
//			if(json.getJSONObject("host_level_info")!=null){
//				host.setVideo_host_level(json.getJSONObject("host_level_info").getIntValue("c_lv"));
//			}
			host.setVideo_host_id(Long.parseLong(json.getString("uid")));
			host.setVideo_host_nickname(json.getString("nn"));
//			host.setVideo_host_avatar(json.getJSONObject("userinfo").getString("avatar"));
			host.setVideo_room_id(source.getVideo_id());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
	/**
	 * 爬虫 多线程 启动入口
	 */
	@Override
	public JSONObject getTv_Video_sourceBymulti_thread(String live_lists_url, int total_page) {
		JSONObject json=new JSONObject();
		List<Video_host> host_list=Collections.synchronizedList(new ArrayList<>());
		List<Video_source> source_list=Collections.synchronizedList(new ArrayList<>());
		json.put("host_list", host_list);
		json.put("source_list", source_list);
		
		cate_map=video_categoryService.getVideo_cateMap();
		video_platform=video_platformService.getVideo_platformByName("斗鱼tv");
		
		for(int i=1;i<=threadCount;i++){
			Thread t=new Thread(new Runnable(){
				@Override
				public void run() {
					int pageno=0;
					while(crawled_page<total_page){
						synchronized(signal){
							if(crawled_page<total_page){
								crawled_page++;
								pageno=crawled_page;
								logger.debug(Thread.currentThread()+"开始获取第"+crawled_page+"页的数据...");
							}else{
								break;
							}
						}
						String data_str=getTv_Video_source(live_lists_url, pageno);
						parseVideo_items_JSONStr(data_str, host_list, source_list);
					}
					synchronized(signal){
						waitThread++;
						try {
							signal.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			},"线程"+i);
			t.start();
		}
		while(waitThread<threadCount){
			//等待所有爬虫线程执行完后返回数据...
			logger.trace("还有"+(threadCount-waitThread)+"爬虫线程在运行...");
		}
		
		return json;
	}
	
}
