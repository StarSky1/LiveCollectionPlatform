package com.yj.spider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
 * 战旗tv 爬虫
 * @author Captain
 *
 */
@Component(value="huyaSpider")
public class HuyaLiveSpider extends HtmlSpiderUtils{
	public static final Object signal = new Object();   //线程间通信变量  
	
	int threadCount=30;	//线程数量
	
	int waitThread=0;	//等待线程的数量
	
	int crawled_page=0;	//已爬取的页数
	
	int pagenum=120;
	
	private Map<String,Video_category> cate_map;
	
	private Video_platform video_platform;
	
	private Logger logger=LoggerFactory.getLogger(HuyaLiveSpider.class);

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		String data_str=getTv_Video_source(live_lists_url, 1);
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		int total_page=json.getJSONObject("data").getIntValue("totalPage");
		return total_page;
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		Map<String,Object> map=new HashMap<>();
		//虎牙直播 当前页直播信息获取地址格式 http://www.huya.com/cache.php?params&...
		//request params
		map.put("m", "LiveList");
		map.put("do", "getLiveListByPage");
		map.put("tagAll", 0);
		map.put("page", pageno);
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "application/json, text/javascript, */*; q=0.01");
		requestHeadersMap.put("x-requested-with", "XMLHttpRequest");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, sdch, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh;q=0.8");
		requestHeadersMap.put("cookie", "__yamid_tt1=0.7154031259899476; __yamid_new=C7D6BF7F37200001F8514D5081F01767; PHPSESSID=i380cn4nrd5arlgmbh1i4cnq34; SoundValue=0.50; isInLiveRoom=; udb_passdata=1; __yasmid=0.7154031259899476; _yasids=__rootsid%3DC7D7078589C00001AF168D601D30F880; Hm_lvt_51700b6c722f5bb4cf39906a596ea41f=1516178656,1516187088,1516254179; Hm_lpvt_51700b6c722f5bb4cf39906a596ea41f=1516254181");
		requestHeadersMap.put("referer", "http://www.huya.com/l");
		requestHeadersMap.put("Connection", "keep-alive");
		requestHeadersMap.put("Host", "www.huya.com");
		
		//获取当前直播页面中所有直播间信息的json字符串
		String live_dataStr=getRequestStr(live_lists_url, "GET", map,requestHeadersMap);
		return live_dataStr;
	}

	@Override
	public void parseVideo_items_JSONStr(String live_dataStr, List<Video_host> host_list,
			List<Video_source> source_list) {
		JSONObject json=JSON.parseObject(live_dataStr);
		JSONArray items=json.getJSONObject("data").getJSONArray("datas");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getString("gameFullName");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideo_type_id();
			Video_source source=new Video_source();
			source.setVideo_room_url(json.getString("privateHost"));
			source.setVideo_img(json.getString("screenshot"));
			source.setVideo_title(json.getString("introduction"));
			source.setVideo_number(Integer.parseInt(json.getString("totalCount")));
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id(Long.parseLong(json.getString("liveChannel")));
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
//			if(json.getString("hotsLevel")!=null){
//				host.setVideo_host_level(Integer.parseInt(json.getString("hotsLevel")));
//			}
			host.setVideo_host_id(Long.parseLong(json.getString("uid")));
			host.setVideo_host_nickname(json.getString("nick"));
			host.setVideo_host_avatar(json.getString("avatar180"));
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
		video_platform=video_platformService.getVideo_platformByName("战旗tv");
		
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
		try {
			System.setOut(new PrintStream(new File("./log.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(waitThread<threadCount){
			//等待所有爬虫线程执行完后返回数据...
//			synchronized(signal){
//				if(waitThread<threadCount){
//					try {
//						signal.wait();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}else{
//					break;
//				}
//			}
			logger.trace("还有"+(threadCount-waitThread)+"爬虫线程在运行...");
		}
		
		return json;
	}
}
