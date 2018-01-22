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
 * 斗鱼tv 爬虫
 * @author Captain
 *
 */
@Component(value="douyuSpider")
public class DouyuTvSpider extends HtmlSpiderUtils{
	
	public DouyuTvSpider(){
		threadCount=25;	//线程数量
		waitThread=0;	//等待线程的数量
		crawled_page=0;	//已爬取的页数
		pagenum=120;
		platform="斗鱼tv";
		logger=LoggerFactory.getLogger(DouyuTvSpider.class);
	}
	
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
			//如果直播间观看人数小于10，则不录入数据库
			if(source.getVideo_number()<10){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id("Douyutv_"+json.getString("rid"));
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
//			if(json.getJSONObject("host_level_info")!=null){
//				host.setVideo_host_level(json.getJSONObject("host_level_info").getIntValue("c_lv"));
//			}
			host.setVideo_host_id("Douyutv"+json.getString("uid"));
			host.setVideo_host_nickname(json.getString("nn"));
//			host.setVideo_host_avatar(json.getJSONObject("userinfo").getString("avatar"));
			host.setVideo_room_id(source.getVideo_id());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
	
	
}
