package com.yj.spider;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;

/**
 * 全民tv 爬虫
 * @author Captain
 *
 */
@Component(value="quanminSpider")
public class QuanminTvSpider extends HtmlSpiderUtils{
	
	public QuanminTvSpider(){
		threadCount=25;	//线程数量
		waitThread=0;	//等待线程的数量
		crawled_page=0;	//已爬取的页数
		pagenum=120;
		platform="全民tv";
		logger=LoggerFactory.getLogger(QuanminTvSpider.class);
	}

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		String data_str=getTv_Video_source(live_lists_url, 1);
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		return json.getIntValue("pageCount");
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		//全民tv 当前页直播信息获取地址格式 http://www.quanmin.tv/json/play/list${pageIndex ? "_" + pageIndex : ""}.json
		live_lists_url=live_lists_url+"_"+pageno+".json";
		//request params
		//null
		//request headers
		//获取当前直播页面中所有直播间信息的json字符串
		String live_dataStr=getRequestStr(live_lists_url, "GET", null,null);
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
			String video_type=json.getString("category_name");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideo_type_id();
			Video_source source=new Video_source();
			source.setVideo_room_url(json.getString("uid"));
			source.setVideo_img(json.getString("thumb"));
			source.setVideo_title(json.getString("title"));
			source.setVideo_number(Integer.parseInt(json.getString("view")));
			//如果直播间观看人数小于1000，则不录入数据库
			if(source.getVideo_number()<1000){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id("Quanmintv_"+json.getString("uid"));
			source.setVideo_url(json.getString("stream"));
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
			if(json.getString("hotsLevel")!=null){
				host.setVideo_host_level(Integer.parseInt(json.getString("hotsLevel")));
			}
			host.setVideo_host_id("Quanmintv"+json.getString("id"));
			host.setVideo_host_nickname(json.getString("nick"));
			host.setVideo_host_avatar(json.getString("avatar"));
			host.setVideo_room_id(source.getVideo_id());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
}
