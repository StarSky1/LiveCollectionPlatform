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
 * bilibili live 爬虫
 * @author Captain
 *
 */
@Component(value="biliLiveSpider")
public class BilibiliLiveSpider extends HtmlSpiderUtils{

	public BilibiliLiveSpider(){
		platform="bilibili直播";
		logger=LoggerFactory.getLogger(BilibiliLiveSpider.class);
	}

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		return 10;
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		Map<String,Object> map=new HashMap<>();
		//bilibili直播 当前页直播信息获取地址格式 https://api.live.bilibili.com/room/v1/Area/getListByAreaID?areaId=0rem&sort=dynamic&page=1&pageSize=60
		//request params
		map.put("pageSize", 60);
		map.put("page", pageno);
		map.put("areaId","0rem");
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept","application/json, text/plain, */*");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6");
		requestHeadersMap.put("origin", "https://live.bilibili.com");
		requestHeadersMap.put("referer", "https://live.bilibili.com/all?visit_id=7z34a5f5zt80");

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
		JSONArray items=json.getJSONArray("data");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getString("area_v2_name");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideoTypeId();
			Video_source source=new Video_source();
			source.setVideoRoomUrl(json.getString("roomid"));
			source.setVideoImg(json.getString("cover"));
			source.setVideoTitle(json.getString("title"));
			source.setVideoNumber(Integer.parseInt(json.getString("online")));
			//如果直播间观看人数小于1000，则不录入数据库
			if(source.getVideoNumber()<1000 || source.getVideoImg().length()>255){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideoType(video_type_id);
			
			source.setVideoPlatform(video_platform.getVideoPlatformId());
			source.setVideoId("Bililive_"+json.getString("roomid"));
			source.setVideoStatus(1);
			
			Video_host host=new Video_host();
//			if(json.getString("hotsLevel")!=null){
//				host.setVideo_host_level(Integer.parseInt(json.getString("hotsLevel")));
//			}
			host.setVideoHostId("Bililive_"+json.getString("uid"));
			host.setVideoHostNickname(json.getString("uname"));
			host.setVideoHostAvatar(json.getString("face"));
			host.setVideoRoomId(source.getVideoId());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
}
