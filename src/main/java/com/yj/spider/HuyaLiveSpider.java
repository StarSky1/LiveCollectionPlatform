package com.yj.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战旗tv 爬虫
 * @author Captain
 *
 */
@Component(value="huyaSpider")
public class HuyaLiveSpider extends HtmlSpiderUtils{
	
	public HuyaLiveSpider(){
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
		JSONObject json=null;
		try{
			json=JSON.parseObject(live_dataStr);
		}catch(JSONException e){
			logger.error("解析json字符串失败"+e.getMessage());
			return;
		}
		JSONArray items=json.getJSONObject("data").getJSONArray("datas");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getString("gameFullName");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideoTypeId();
			Video_source source=new Video_source();
			source.setVideoRoomUrl(json.getString("privateHost"));
			source.setVideoImg(json.getString("screenshot"));
			source.setVideoTitle(json.getString("introduction"));
			source.setVideoNumber(Integer.parseInt(json.getString("totalCount")));
			//如果直播间观看人数小于1000，则不录入数据库
			if(source.getVideoNumber()<1000 || source.getVideoImg().length()>255){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideoType(video_type_id);
			
			source.setVideoPlatform(video_platform.getVideoPlatformId());
			source.setVideoId("Huyalive_"+json.getString("privateHost"));
			source.setVideoStatus(1);
			
			Video_host host=new Video_host();
//			if(json.getString("hotsLevel")!=null){
//				host.setVideo_host_level(Integer.parseInt(json.getString("hotsLevel")));
//			}
			host.setVideoHostId("Huyalive"+json.getString("uid"));
			host.setVideoHostNickname(json.getString("nick"));
			host.setVideoHostAvatar(json.getString("avatar180"));
			host.setVideoRoomId(source.getVideoId());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
}
