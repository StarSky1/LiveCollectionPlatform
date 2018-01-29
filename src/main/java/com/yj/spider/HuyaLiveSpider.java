package com.yj.spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;

/**
 * 战旗tv 爬虫
 * @author Captain
 *
 */
@Component(value="huyaSpider")
public class HuyaLiveSpider extends HtmlSpiderUtils{
	
	public HuyaLiveSpider(){
		threadCount=25;	//线程数量
		waitThread=0;	//等待线程的数量
		crawled_page=0;	//已爬取的页数
		pagenum=120;
		platform="虎牙直播";
		logger=LoggerFactory.getLogger(HuyaLiveSpider.class);
	}

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
		//requestHeadersMap.put("Connection", "keep-alive");
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
			//如果直播间观看人数小于10，则不录入数据库
			if(source.getVideo_number()<10){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id("Huyalive_"+json.getString("privateHost"));
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
//			if(json.getString("hotsLevel")!=null){
//				host.setVideo_host_level(Integer.parseInt(json.getString("hotsLevel")));
//			}
			host.setVideo_host_id("Huyalive"+json.getString("uid"));
			host.setVideo_host_nickname(json.getString("nick"));
			host.setVideo_host_avatar(json.getString("avatar180"));
			host.setVideo_room_id(source.getVideo_id());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
}
