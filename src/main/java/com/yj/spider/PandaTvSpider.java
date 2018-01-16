package com.yj.spider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_category;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_platform;
import com.yj.pojo.Video_source;

/**
 * 熊猫tv 爬虫
 * @author Captain
 *
 */
@Component(value="pandaSpider")
public class PandaTvSpider extends HtmlSpiderUtils{

	public static final Object signal = new Object();   //线程间通信变量  
	
	int threadCount=10;	//线程数量
	
	int waitThread=0;	//等待线程的数量
	
	int crawled_page=0;	//已爬取的页数
	
	int pagenum=120;
	
	private Map<String,Video_category> cate_map;
	
	private Video_platform video_platform;
	
	/**
	 * 获取熊猫tv 直播分类图片url
	 * @param url
	 * @param list
	 * @return
	 */
	public List<Video_category> getVideo_category_img(String url,List<Video_category> list){
		String htmlStr=getRequestStr(url, null, null,null);
		String bodyStr=getHtmlBodyStr(htmlStr);
		Map<String,Video_category> map=new HashMap<>();
		for (Video_category cate : list) {
			map.put(cate.getVideo_type(), cate);
		}
		Pattern pattern=Pattern.compile("<aclass=\"video-list-item-wrap\"[^>]*?>.*?<imgsrc=\"(.*?)\"[^>]*?>.*?<divclass=\"cate-title\">"
				+ "(.*?)</div></a>");
		Matcher matcher=pattern.matcher(bodyStr);
		while(matcher.find()){
			String cate_name=matcher.group(2).trim();
			if(map.containsKey(cate_name)){
				map.get(cate_name).setVideo_type_img(matcher.group(1));
			}
		}
		return list;
	}
	
//	/**
//	 * 获取熊猫tv全部直播页面总页数
//	 * @param video_platform_all_url
//	 * @return
//	 */
//	public int getTv_videos_totalPage(String video_platform_all_url){
//		int total_page=0;
//		System.setProperty ( "webdriver.firefox.bin" , "E:/火狐浏览器/firefox.exe" );
//		WebDriver webDriver=new FirefoxDriver();
//		Navigation navigate=webDriver.navigate();
//		navigate.to(video_platform_all_url);
//		WebElement element=webDriver.findElement(By.id("pages-container"));
//		WebElement div=element.findElement(By.className("page-component"));
//		List<WebElement> list=div.findElements(By.tagName("a"));
//		total_page=Integer.parseInt(list.get(list.size()-3).getText());
//		return total_page;
//	}
	
	/**
	 * 获取熊猫tv全部直播页面总页数
	 * @param live_lists_url
	 * @return
	 */
	public int getTv_videos_totalPage(String live_lists_url){
		String data_str=getTv_Video_source(live_lists_url, 1);
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		int data_total=json.getJSONObject("data").getIntValue("total");
		int total_page=data_total%pagenum==0?data_total/pagenum:(data_total/pagenum+1);
		return total_page;
	}
	
	/**
	 * 获取熊猫tv 正在直播页面中指定页号的直播间和主播信息
	 * @param live_lists_url
	 * @param total_page
	 * @return
	 */
	public String getTv_Video_source(String live_lists_url,int pageno){
		Map<String,Object> map=new HashMap<>();
		//request params
		map.put("status", 2);
		map.put("order", "person_num");
		map.put("pagenum", pagenum);
		map.put("_", "1515551847186");
		map.put("pageno", pageno);
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "application/json, text/javascript, */*; q=0.01");
		requestHeadersMap.put("x-requested-with", "XMLHttpRequest");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, sdch, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh;q=0.8");
		requestHeadersMap.put("cookie", "__guid=96554777.2648944975772524000.1501862232102.666; _uab_collina=150607002895625586773494; smid=bee10a7d-fa4d-472e-bff4-86d957707c86; pdftv1=fe374|160c9d24169|2daa|ca6e82d8|14; R=r%3D36899590%26u%3DCnaqnGi36899590%26n%3Dnaablrq_zna%26le%3D%26m%3DZGHjAmR0ZQx3AQp%3D%26im%3DnUE0pPHmDFHlEvHlEzx2YaOxnJ0hM3ZyZxMvAmp3BJAwAwLjMGR0ZGqxLGRkMGZlMzZjA2LlAwOzMP5jozp%3D%26p%3D%26i%3D; M=t%3D1515218898%26v%3D1.0%26mt%3D1515218898%26s%3D8daeff458d39c06c8e8387e32d0b0258; pdft=20171014200614cbe62ca9188877887e0c1f37bc4da476000a265399b9f417; I=r%3D36899590%26t%3Dd97d32fdcf0ba279fa1d203038e80116; webp=1; _umdata=55F3A8BFC9C50DDA758664CC93A838EDD5A971655D10D9E5FC2D18C03EDC34E234204B6550F9F8B1CD43AD3E795C914CBEA0FBFA2A2FB339224E7B05648E127D; GED_PLAYLIST_ACTIVITY=W3sidSI6ImZuU1QiLCJ0c2wiOjE1MTU2NTA2ODksIm52IjoxLCJ1cHQiOjE1MTU2NDM2NDEsImx0IjoxNTE1NjUwNjg5fV0.; smidV9=20171014200614cbe62ca9188877887e0c1f37bc4da476000a265399b9f417; monitor_count=33; Hm_lvt_204071a8b1d0b2a04c782c44b88eb996=1515220313,1515325866,1515468839,1515643644; Hm_lpvt_204071a8b1d0b2a04c782c44b88eb996=1515657075");
		requestHeadersMap.put("referer", "https://www.panda.tv/all?pdt=1.24.psbar-en.a0.7ns5gda8vbk");
		
		//获取当前直播页面中所有直播间信息的json字符串
		String live_dataStr=getRequestStr(live_lists_url, "GET", map,requestHeadersMap);
		return live_dataStr;
		
	}
	
	/**
	 * 解析Json字符串，生成直播间和主播对象信息
	 * @param jsonStr
	 */
	public void parseVideo_items_JSONStr(String live_dataStr,List<Video_host> host_list,List<Video_source> source_list){
		JSONObject json=JSON.parseObject(live_dataStr);
		JSONArray items=json.getJSONObject("data").getJSONArray("items");
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getJSONObject("classification").getString("cname");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideo_type_id();
			Video_source source=new Video_source();
			source.setVideo_room_url(json.getString("id"));
			source.setVideo_img(json.getJSONObject("pictures").getString("img"));
			source.setVideo_title(json.getString("name"));
			source.setVideo_number(Integer.parseInt(json.getString("person_num")));
			source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideo_type(video_type_id);
			
			source.setVideo_platform(video_platform.getVideo_platform_id());
			source.setVideo_id("PandaTv_"+source.getVideo_room_url());
			source.setVideo_status(1);
			
			Video_host host=new Video_host();
			if(json.getJSONObject("host_level_info")!=null){
				host.setVideo_host_level(json.getJSONObject("host_level_info").getIntValue("c_lv"));
			}
			host.setVideo_host_id("PandaTv"+json.getJSONObject("userinfo").getIntValue("rid"));
			host.setVideo_host_nickname(json.getJSONObject("userinfo").getString("nickName"));
			host.setVideo_host_avatar(json.getJSONObject("userinfo").getString("avatar"));
			host.setVideo_room_id(source.getVideo_id());
			
			host_list.add(host);
			source_list.add(source);
		}
	}
	
	/**
	 * 开启10个线程，并行获取1-total_page页的熊猫tv 所有正在直播的直播间和主播信息
	 * @param live_lists_url
	 * @param total_page
	 * @return
	 */
	public JSONObject getTv_Video_sourceBymulti_thread(String live_lists_url,int total_page){
		JSONObject json=new JSONObject();
		List<Video_host> host_list=Collections.synchronizedList(new ArrayList<>());
		List<Video_source> source_list=Collections.synchronizedList(new ArrayList<>());
		json.put("host_list", host_list);
		json.put("source_list", source_list);
		
		cate_map=video_categoryService.getVideo_cateMap();
		video_platform=video_platformService.getVideo_platformByName("熊猫tv");
		
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
								System.out.println(Thread.currentThread()+"开始获取第"+crawled_page+"页的数据...");
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
			System.out.println("还有"+(threadCount-waitThread)+"爬虫线程在执行...");
		};
		return json;
	}
}
