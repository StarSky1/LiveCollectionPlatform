package com.yj.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.lf.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.dao.Video_platformDao;
import com.yj.pojo.Video_category;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_platform;
import com.yj.pojo.Video_source;
import com.yj.service.Video_categoryService;

/** * @author  wenchen 
 * @date 创建时间：2017年8月15日 下午2:03:23 
 * @version 1.0 
 * @parameter */
@Component
public class HtmlSpiderUtils {
	
	@Autowired
	public Video_categoryService video_categoryService;
	
	@Autowired
	public Video_platformDao video_platformDao;
	
	
	/**
	 * 以特定方式向这个url发送请求，获得请求的字符串
	 * @param url
	 * @param method
	 * @return
	 */
	public static String getRequestStr(String url,String method,Map<String,?> map,Map<String,String> requestHeaderMap) {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		URL reuqestUrl = null;
		StringBuilder resultSb = new StringBuilder();
		if (map != null) {
			Set<String> set = map.keySet();
			StringBuilder queryParam = new StringBuilder("?");
			for (String s : set) {
				queryParam.append(s).append("=").append(map.get(s).toString()).append("&");
			}
			queryParam.deleteCharAt(queryParam.length()-1);
			url += queryParam.toString();
		}
		try {
			reuqestUrl = new URL(url);
			conn = (HttpURLConnection) reuqestUrl.openConnection();
			if (StringUtils.isEmpty(method)) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod(method);
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			// 设置请求属性
			if(requestHeaderMap!=null){
				Set<String> set=map.keySet();
				for (String s : set) {
					conn.setRequestProperty(s, requestHeaderMap.get(s));
				}
			}else{
				conn.setRequestProperty("accept", "*/*");  
	            conn.setRequestProperty("connection", "Keep-Alive"); 
			}
			conn.setUseCaches(false);//设置不要缓存
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400"); //user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400
            
            // 建立实际的连接  
            conn.connect();  
            // 定义 BufferedReader输入流来读取URL的响应  
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=reader.readLine())!=null){
				resultSb.append(line);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultSb.toString();
	}
	
	/**
	 * 将html页面中body里面的内容得到
	 * (去掉空格后的,同时也去掉了html注释)
	 * 去掉body中的script标签
	 * @param htmlStr
	 * @return
	 */
	public String getHtmlBodyStr(String htmlStr){
		
		htmlStr = htmlStr.replaceAll("\\s*", "");//去掉所有的空格
		String bodyPattern = "<body.*>.*</body>";
		Pattern p = Pattern.compile(bodyPattern);
		Matcher matcher = p.matcher(htmlStr);
		String body = null; 
		if(matcher.find()){
			body = matcher.group();
			body = body.replaceAll("<!--.+?-->", "");//去掉html注释
			body=body.replaceAll("<script.*?>.*?</script>", "");//去掉所有script标签
		}
		return body;
	}
	
	@Test
	public void testGetHtmlBodyStr(){
		Map<String,Object> map=new HashMap<>();
		map.put("pdt", new String("1.18.pheader-n.1.5gqdti53n1u")); //pdt=1.18.pheader-n.1.5gqdti53n1u
		String htmlStr=getRequestStr("https://www.panda.tv/all", "GET", null,null);
		String bodyStr=getHtmlBodyStr(htmlStr);
		System.out.println(bodyStr);
		
	}
	
	/**
	 * 获取直播分类图片url
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
	
	/**
	 * 下载直播分类图片
	 * @param img_url
	 * @param cate_name
	 */
	public void downloadImg_url(String img_url,String cate_name){
		HttpURLConnection conn = null;
		URL reuqestUrl = null;
		InputStream in=null;
		FileOutputStream out=null;
		//开始时间
		Date beginDate=new Date();
		System.out.println("开始下载图片 "+img_url);
		try {
			reuqestUrl = new URL(img_url);
			conn = (HttpURLConnection) reuqestUrl.openConnection();
			conn.setRequestMethod("GET");
			
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400"); //user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400
			in=conn.getInputStream();
			String suffix=img_url.substring(img_url.lastIndexOf("."));
			out=new FileOutputStream(new File("./src/main/webapp/res/cate_img/",cate_name+suffix));
			byte[] buf=new byte[1024];
			int len=0;
			while((len=in.read(buf, 0, buf.length))!=-1){
				out.write(buf, 0, len);
			}
			out.flush();
			Date endDate=new Date();
			long time=endDate.getTime()-beginDate.getTime();
			System.out.println("耗时："+ time/1000.0 + "s");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	/**
	 * 获取熊猫tv所有直播间信息
	 * @param video_platform_all_url
	 * @return
	 */
	public JSONArray getPanda_Video_source(String video_platform_all_url){
		JSONArray jsonArray=new JSONArray();
		String htmlStr=getRequestStr(video_platform_all_url, null, null,null);
		String bodyStr=getHtmlBodyStr(htmlStr);
		String regex="<liclass=\"video-list-item.*?\".*?>"
				+ "<ahref=\"(.*?)\".*?>"
				+ "<divclass=\"video-cover\">"
				+ "<imgclass=\"video-img.*?\""
				+ ".*?src=\"(.*?)\".*?>"
				+ ".*?<divclass=\"video-info\">"
				+ "<spanclass=\"video-title\"title=\"(.*?)\">.*?</span>"
				+ "<spanclass=\"video-nickname\"title=\"(.*?)\".*?>"
				+ "<iclass=.*?data-level=\"(.*?)\">"
				+ "</i>.*?</span>"
				+ "<spanclass=\"video-number\">(.*?)</span>"
				+ ".*?<iclass=\"video-station-num\">(.*?)</i>"
				+ ".*?<aclass=\"video-label-item.*?>(.*?)</a>.*?</li>";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(bodyStr);
		while(matcher.find()){
			String video_type=matcher.group(8);
			Map<String,Video_category> map=video_categoryService.getVideo_cateMap();
			if(!map.containsKey(video_type)){
				continue;
			}
			int video_type_id=map.get(video_type).getVideo_type_id();
			Video_source video_source=new Video_source();
			video_source.setVideo_room_url(matcher.group(1));
			video_source.setVideo_img(matcher.group(2));
			video_source.setVideo_title(matcher.group(3));
			video_source.setVideo_number(Integer.parseInt(matcher.group(6)));
			video_source.setVideo_station_num(Integer.parseInt(matcher.group(7)));
			video_source.setVideo_type(video_type_id);
			Video_platform video_platform=video_platformDao.selectVideo_platformByName("熊猫tv");
			video_source.setVideo_platform(video_platform.getVideo_platform_id());
			video_source.setVideo_id(video_platform.getVideo_platform_id()+"_"+video_source.getVideo_room_url());
			
			Video_host video_host=new Video_host();
			video_host.setVideo_host_level(Integer.parseInt(matcher.group(5)));
			video_host.setVideo_host_nickname(matcher.group(4));
			video_host.setVideo_room_id(video_source.getVideo_id());
			
			JSONObject json=new JSONObject();
			json.put("video_host", video_host);
			json.put("video_source", video_source);
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 获取熊猫tv全部直播页面总页数
	 * @param video_platform_all_url
	 * @return
	 */
	public int getPanda_videos_totalPage(String video_platform_all_url){
		int total_page=0;
		System.setProperty ( "webdriver.firefox.bin" , "E:/火狐浏览器/firefox.exe" );
		WebDriver webDriver=new FirefoxDriver();
		Navigation navigate=webDriver.navigate();
		navigate.to(video_platform_all_url);
		WebElement element=webDriver.findElement(By.id("pages-container"));
		WebElement div=element.findElement(By.className("page-component"));
		List<WebElement> list=div.findElements(By.tagName("a"));
		total_page=Integer.parseInt(list.get(list.size()-3).getText());
		return total_page;
	}
	
	/**
	 * 获取熊猫tv 所有正在直播的直播间和主播信息
	 * @param live_lists_url
	 * @param total_page
	 * @return
	 */
	public JSONArray getPanda_Video_source(String live_lists_url,int total_page){
		JSONArray jsonArray=new JSONArray();
		Map<String,Object> map=new HashMap<>();
		//request params
		map.put("status", 2);
		map.put("order", "person_num");
		map.put("pagenum", 120);
		map.put("_", "1515551847186");
		//request headers
		Map<String,String> requestHeadersMap=new HashMap<>();
		requestHeadersMap.put("accept", "application/json, text/javascript, */*; q=0.01");
		requestHeadersMap.put("x-requested-with", "XMLHttpRequest");
		for(int i=1;i<=total_page;i++){
			map.put("pageno", i);
			String live_dataStr=getRequestStr(live_lists_url, "GET", map,requestHeadersMap);
			JSONObject json=JSON.parseObject(live_dataStr);
			JSONArray items=json.getJSONObject("data").getJSONArray("items");
			for(int j=0;j<items.size();j++){
				json=items.getJSONObject(j);
				String video_type=json.getJSONObject("classification").getString("cname");
				Map<String,Video_category> cate_map=video_categoryService.getVideo_cateMap();
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
				Video_platform video_platform=video_platformDao.selectVideo_platformByName("熊猫tv");
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
				
				
				json=new JSONObject();
				json.put("video_host", host);
				json.put("video_source", source);
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	
}
