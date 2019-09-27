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
@Component(value="zhanqiSpider")
public class ZhanqiTvSpider extends HtmlSpiderUtils{
	
	public ZhanqiTvSpider(){
		threadCount=25;	//线程数量
		waitThread=0;	//等待线程的数量
		crawled_page=0;	//已爬取的页数
		pagenum=120;
		platform="战旗tv";
		logger=LoggerFactory.getLogger(ZhanqiTvSpider.class);
	}

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		String data_str=getTv_Video_source(live_lists_url, 1);
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		int total_live=json.getJSONObject("data").getIntValue("cnt");
		int total_page=total_live%pagenum==0?total_live/pagenum:(total_live/pagenum+1);
		return total_page;
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		Map<String,Object> map=null;
		//战旗tv 当前页直播信息获取地址格式 http://www.zhanqi.tv/api/static/v2.1/live/list/20/1.json
		live_lists_url=live_lists_url+"/"+pageno+".json";
		//request params
		//null
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "application/json, text/javascript, */*; q=0.01");
		requestHeadersMap.put("x-requested-with", "XMLHttpRequest");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, sdch, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh;q=0.8");
		requestHeadersMap.put("cookie", "PHPSESSID=rf9dp3k087ogc8sp28ltih8f02; gid=1872174635; cookie_ip=%2C3396240088; myLocationCacheKey=%u6E56%u5317%u6B66%u6C49%u6E56%u5317%u7B2C%u4E8C%u5E08%u8303%u5B66%u9662; myIpCacheKey=202.197.149.141; myIspCacheKey=%u6559%u80B2%u7F51; myContryCacheKey=%u4E2D%u56FD; Hm_lvt_299cfc89fdba155674e083d478408f29=1516170631; Hm_lpvt_299cfc89fdba155674e083d478408f29=1516171628");
		requestHeadersMap.put("referer", "http://www.zhanqi.tv/lives");
		//requestHeadersMap.put("Connection", "keep-alive");
		requestHeadersMap.put("Host", "www.zhanqi.tv");
		
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
		JSONArray items=json.getJSONObject("data").getJSONArray("rooms");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getString("gameName");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideoTypeId();
			Video_source source=new Video_source();
			source.setVideoRoomUrl(json.getString("url"));
			source.setVideoImg(json.getString("bpic"));
			source.setVideoTitle(json.getString("title"));
			source.setVideoNumber(Integer.parseInt(json.getString("online")));
			//如果直播间观看人数小于1000，则不录入数据库
			if(source.getVideoNumber()<1000){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideoType(video_type_id);
			
			source.setVideoPlatform(video_platform.getVideoPlatformId());
			source.setVideoId("Zhanqitv_"+json.getString("code"));
			source.setVideoStatus(1);
			
			Video_host host=new Video_host();
			if(json.getString("hotsLevel")!=null){
				host.setVideoHostLevel(Integer.parseInt(json.getString("hotsLevel")));
			}
			host.setVideoHostId("Zhanqitv"+json.getString("uid"));
			host.setVideoHostNickname(json.getString("nickname"));
//			host.setVideo_host_avatar(json.getJSONObject("userinfo").getString("avatar"));
			host.setVideoRoomId(source.getVideoId());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
}
