package com.yj.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_source;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 龙珠直播 爬虫
 * @author Captain
 *
 */
@Component(value="longzhuSpider")
public class LongzhuLiveSpider extends HtmlSpiderUtils{
	
	public LongzhuLiveSpider(){
		threadCount=10;	//线程数量
		waitThread=0;	//等待线程的数量
		crawled_page=0;	//已爬取的页数
		pagenum=18;
		platform="龙珠直播";
		logger=LoggerFactory.getLogger(LongzhuLiveSpider.class);
	}

//	/**
//	 * 使用selenium web自动化测试工具来控制浏览器滚动条滚动，以动态加载龙珠直播页面信息
//	 * @param live_lists_url
//	 * @return
//	 * @throws InterruptedException
//	 */
//	public JSONObject getTv_Video_source(String live_lists_url) throws InterruptedException {
//        //等待数据加载的时间
//        //为了防止服务器封锁，这里的时间要模拟人的行为，随机且不能太短
//        long waitLoadBaseTime = 3000;
//        int waitLoadRandomTime = 3000;
//        Random random = new Random(System.currentTimeMillis());
//
//        //火狐浏览器
//        System.setProperty ( "webdriver.firefox.bin" , "E:/火狐浏览器/firefox.exe" );
//        WebDriver driver = new FirefoxDriver();
//        //要抓取的网页
//        driver.get(live_lists_url);
//        //等待页面动态加载完毕
//        Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
//        List<WebElement> elements=driver.findElements(By.className("livecard-modal-username"));
//        
//        //要加载多少页数据
//        String lastNickname=elements.get(elements.size()-1).getText();
//        String nickname="";
//        while(!nickname.equals(lastNickname)){
//        	lastNickname=nickname;
//            //滚动加载下一页
//        	//使用js的scrollBy语句，相当于滚动条移动了5000像素  
//        	//创建一个javascript 执行实例  
//            JavascriptExecutor je = (JavascriptExecutor) driver; 
//            je.executeScript("window.scrollBy(0, 5000)"); 
//            //等待页面动态加载完毕
//            Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
//            elements=driver.findElements(By.className("livecard-modal-username"));
//            nickname=elements.get(elements.size()-1).getText();
//        }
//        //输出内容
//        //找到标题元素
//        elements = driver.findElements(By.className("title"));
//        int j=1;
//        for(int i=0;i<elements.size();i++) {
//            try {
//                WebElement element = elements.get(i).findElement(By.tagName("a"));
//                //输出标题
//                System.out.println((j++) + "、" + element.getText() + " " + element.getAttribute("href"));
//            }catch (Exception e){
//                System.out.println("ignore "+elements.get(i).getText()+" because "+e.getMessage());
//            }
//        }
//        //关闭浏览器
//        driver.close();
//		return null;
//	}

	@Override
	public int getTv_videos_totalPage(String live_lists_url) {
		String data_str=getTv_Video_source(live_lists_url, 0);
		data_str=data_str.replaceAll("^.*?\\(|\\)", "");
		JSONObject json=JSON.parseObject(data_str);
		//获取当前直播间总数量
		int total_live=json.getJSONObject("data").getIntValue("totalItems");
		pagenum=Integer.parseInt(json.getJSONObject("data").getString("limit"));
		int total_page=total_live%pagenum==0?total_live/pagenum:(total_live/pagenum+1);
		return total_page;
	}

	@Override
	public String getTv_Video_source(String live_lists_url, int pageno) {
		Map<String,Object> map=new HashMap<>();
		//虎牙直播 当前页直播信息获取地址格式 http://www.huya.com/cache.php?params&...
		//request params
		map.put("max-results", pagenum);
		map.put("start-index", (pageno-1)*pagenum);
		map.put("sort-by", "views");
		map.put("filter", 0);
		map.put("game", 0);
		map.put("callback", "_callbacks_._1319y17");
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "*/*");
		requestHeadersMap.put("accept-encoding", "gzip, deflate, sdch, br");
		requestHeadersMap.put("accept-language", "zh-CN,zh;q=0.8");
		requestHeadersMap.put("cookie", "pluguest=254B324D2CDAF9521D8B59FAEF7B150EB1A627500CB09D9B6DBC3DB0DDBFBC5929F814806AA3240495B972F54E8F27325E8598C3C73CBC1D");
		requestHeadersMap.put("referer", "http://longzhu.com/channels/all?from=rmlive");
		//requestHeadersMap.put("Connection", "keep-alive");
		requestHeadersMap.put("Host", "api.plu.cn");
		
		//获取当前直播页面中所有直播间信息的json字符串
		String live_dataStr=getRequestStr(live_lists_url, "GET", map,requestHeadersMap);
		return live_dataStr;
	}

	@Override
	public void parseVideo_items_JSONStr(String live_dataStr, List<Video_host> host_list,
			List<Video_source> source_list) {
		live_dataStr=live_dataStr.replaceAll("^.*?\\(|\\)", "");
		JSONObject json=null;
		try{
			json=JSON.parseObject(live_dataStr);
		}catch(JSONException e){
			logger.error("解析json字符串失败"+e.getMessage());
			return;
		}
		JSONArray items=json.getJSONObject("data").getJSONArray("items");
		
		for(int j=0;j<items.size();j++){
			json=items.getJSONObject(j);
			String video_type=json.getJSONArray("game").getJSONObject(0).getString("name");
			if(!cate_map.containsKey(video_type)){
				continue;
			}
			int video_type_id=cate_map.get(video_type).getVideoTypeId();
			JSONObject channel=json.getJSONObject("channel");
			Video_source source=new Video_source();
			source.setVideoRoomUrl(channel.getString("domain"));
			source.setVideoImg(json.getString("preview"));
			source.setVideoTitle(channel.getString("status"));
			source.setVideoNumber(Integer.parseInt(json.getString("viewers")));
			//如果直播间观看人数小于1000，则不录入数据库
			if(source.getVideoNumber()<1000){
				continue;
			}
			//source.setVideo_station_num(json.getJSONObject("ticket_rank_info").getInteger("score"));
			source.setVideoType(video_type_id);
			
			source.setVideoPlatform(video_platform.getVideoPlatformId());
			source.setVideoId("Longzhulive_"+channel.getString("domain"));
			source.setVideoStatus(1);
			
			Video_host host=new Video_host();
			if(json.getString("hotsLevel")!=null){
				host.setVideoHostLevel(json.getJSONObject("channel").getIntValue("grade"));
			}
			host.setVideoHostId("Longzhulive"+channel.getLongValue("id"));
			host.setVideoHostNickname(json.getJSONObject("channel").getString("name"));
			host.setVideoHostAvatar(json.getJSONObject("channel").getString("avatar"));
			host.setVideoRoomId(source.getVideoId());
			
			host_list.add(host);
			source_list.add(source);
		}
	}

	public JSONObject getTv_Video_sourceByCircle(String live_lists_url, int total_page) {
		JSONObject json=new JSONObject();
		List<Video_host> host_list=new ArrayList<>();
		List<Video_source> source_list=new ArrayList<>();
		cate_map=video_categoryService.getVideo_cateMap();
		video_platform=video_platformService.getVideo_platformByName(platform);
		
		for(int i=1;i<=total_page;i++){
			logger.info("龙珠爬虫开始获取第"+i+"页的数据...");
			String live_dataStr=getTv_Video_source(live_lists_url, i);
			parseVideo_items_JSONStr(live_dataStr, host_list, source_list);
		}
		json.put("host_list", host_list);
		json.put("source_list", source_list);
		return json;
	}
	
	/**
	 * 覆写父类的crawlData方法，使用循环来爬取数据，避免龙珠服务器没有回应时，多线程爬取死锁的问题
	 */
	@Override
	public JSONObject crawlData(String live_lists_url, int total_page) {
		// TODO Auto-generated method stub
		return getTv_Video_sourceByCircle(live_lists_url, total_page);
	}
	
	
	
	
	
	

	
}
