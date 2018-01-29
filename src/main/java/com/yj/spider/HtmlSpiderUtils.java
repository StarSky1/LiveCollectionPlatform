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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_category;
import com.yj.pojo.Video_host;
import com.yj.pojo.Video_platform;
import com.yj.pojo.Video_source;
import com.yj.service.Video_categoryService;
import com.yj.service.Video_platformService;

/** * @author  wenchen 
 * @date 创建时间：2017年8月15日 下午2:03:23 
 * @version 1.0 
 * @parameter */
@Component(value="htmlSpider")
public class HtmlSpiderUtils {
	
	@Autowired
	public Video_categoryService video_categoryService;
	
	@Autowired
	public Video_platformService video_platformService;
	
	public Map<String,Video_category> cate_map;
	
	public Video_platform video_platform;
	
	String platform;
	
	static int connectionTimeout = 1000;// 连接超时时间,毫秒  
    static int soTimeout = 30000;// 读取数据超时时间，毫秒  
    /** HttpClient对象 */  
    static CloseableHttpClient httpclient = HttpClients.  
                custom().disableAutomaticRetries().build();  
    /*** 超时设置 ****/  
    static RequestConfig requestConfig = RequestConfig.custom()  
                                                    .setSocketTimeout(soTimeout)  
                                                    .setConnectTimeout(connectionTimeout)  
                                                    .build();//设置请求和传输超时时间  
	
	public static final Object signal = new Object();   //线程间通信变量  
	
	int threadCount=25;	//线程数量
	
	int waitThread=0;	//等待线程的数量
	
	int crawled_page=0;	//已爬取的页数
	
	int pagenum=120;
	
	public Logger logger;
	
//	/**
//	 * 以特定方式向这个url发送请求，获得请求的字符串
//	 * @param url
//	 * @param method
//	 * @return
//	 */
//	public static String getRequestStr(String url,String method,Map<String,?> map,Map<String,String> requestHeaderMap) {
//		HttpURLConnection conn = null;
//		BufferedReader reader = null;
//		URL reuqestUrl = null;
//		StringBuilder resultSb = new StringBuilder();
//		if (map != null) {
//			Set<String> set = map.keySet();
//			StringBuilder queryParam = new StringBuilder("?");
//			for (String s : set) {
//				queryParam.append(s).append("=").append(map.get(s).toString()).append("&");
//			}
//			queryParam.deleteCharAt(queryParam.length()-1);
//			url += queryParam.toString();
//		}
//		try {
//			reuqestUrl = new URL(url);
//			conn = (HttpURLConnection) reuqestUrl.openConnection();
//			if (StringUtils.isEmpty(method)) {
//				conn.setRequestMethod("GET");
//			} else {
//				conn.setRequestMethod(method);
//			}
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			// 设置请求属性
//			if(requestHeaderMap!=null){
//				Set<String> set=requestHeaderMap.keySet();
//				for (String s : set) {
//					conn.setRequestProperty(s, requestHeaderMap.get(s));
//				}
//			}else{
//				conn.setRequestProperty("accept", "*/*");  
//	            conn.setRequestProperty("connection", "Keep-Alive"); 
//			}
//			conn.setUseCaches(false);//设置不要缓存
//			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400"); //user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400
//            
//            // 建立实际的连接  
//            conn.connect();
//            long startTime=System.currentTimeMillis();
//            long endTime=System.currentTimeMillis();
//            while(conn.getInputStream().available()==0 && (endTime-startTime)<5000){
//            	endTime=System.currentTimeMillis();
//            }
//            if((endTime-startTime)>5000){
//            	System.out.println("连接服务器失败");
//            }
//            String content_encoding=conn.getHeaderField("content-encoding");
//            if(content_encoding!=null && content_encoding.toLowerCase().equals("gzip")){
//            	// 定义 BufferedReader输入流来读取URL的响应  
//            	InputStreamReader in=new InputStreamReader(new GZIPInputStream(conn.getInputStream()));
//    			reader = new BufferedReader(in);
//            }else{
//            	// 定义 BufferedReader输入流来读取URL的响应  
//    			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            }
//			String line;
//			while((line=reader.readLine())!=null){
//				resultSb.append(line);
//			}
//			
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (conn != null) {
//				conn.disconnect();
//			}
//		}
//		return resultSb.toString();
//	}
	
	/**
	 * 以特定方式向这个url发送请求，获得请求的字符串
	 * 使用httpclient发送http连接
	 * @param url
	 * @param method
	 * @return
	 */
    public String getRequestStr(String url, String method, Map<String,?> map, Map<String,String> requestHeaderMap) {  
    	BufferedReader reader = null;
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
        HttpGet httpget = new HttpGet(url);  
        httpget.setConfig(requestConfig);
        // 设置请求属性
		if(requestHeaderMap!=null){
			Set<String> set=requestHeaderMap.keySet();
			for (String s : set) {
				httpget.setHeader(s, requestHeaderMap.get(s));
			}
		}else{
			httpget.setHeader("accept", "*/*");  
		}
		httpget.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400"); //user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400
        CloseableHttpResponse response=null;  
        try {  
            response = httpclient.execute(httpget);  
            HttpEntity entity = response.getEntity();  
            int iGetResultCode = response.getStatusLine().getStatusCode();  
            if (iGetResultCode >= 200 && iGetResultCode < 303) {  
                if(entity.getContentEncoding()!=null && entity.getContentEncoding().getValue().toLowerCase().equals("gzip")){
                	// 定义 BufferedReader输入流来读取URL的响应  
                	InputStreamReader in=new InputStreamReader(new GZIPInputStream(entity.getContent()));
        			reader = new BufferedReader(in);
                }else{
                	// 定义 BufferedReader输入流来读取URL的响应  
        			reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                }
      			String line;
      			while((line=reader.readLine())!=null){
      				resultSb.append(line);
      			}  
            } else if (iGetResultCode >= 400 && iGetResultCode < 500) {  
            	resultSb.append("请求的目标地址不存在：" + iGetResultCode);  
            } else {  
            	resultSb.append("请求错误：" + iGetResultCode);  
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
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				};
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
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 下载图片到本地
	 * @param img_url
	 * @param cate_name
	 */
	public void downloadImg_url(String img_url,String cate_name,String save_dir){
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
			out=new FileOutputStream(new File(save_dir,cate_name+suffix));
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
	 * 获取直播平台 全部直播页面总页数
	 * @param video_platform_all_url
	 * @return
	 */
	public int getTv_videos_totalPage(String live_lists_url){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 获取直播平台 正在直播页面中指定页号的直播间和主播信息
	 * 返回值是json字符串
	 * @param live_lists_url
	 * @param total_page
	 * @return
	 */
	public String getTv_Video_source(String live_lists_url,int pageno){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 解析Json字符串，生成直播间和主播对象信息
	 * @param jsonStr
	 */
	public void parseVideo_items_JSONStr(String live_dataStr,List<Video_host> host_list,List<Video_source> source_list){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 开启多个线程，并行获取1-total_page页的直播平台 所有正在直播的直播间和主播信息
	 * @param live_lists_url
	 * @param total_page
	 * @return
	 */
	public JSONObject getTv_Video_sourceBymulti_thread(String live_lists_url, int total_page) {
		JSONObject json=new JSONObject();
		List<Video_host> host_list=Collections.synchronizedList(new ArrayList<>());
		List<Video_source> source_list=Collections.synchronizedList(new ArrayList<>());
		json.put("host_list", host_list);
		json.put("source_list", source_list);
		
		cate_map=video_categoryService.getVideo_cateMap();
		video_platform=video_platformService.getVideo_platformByName(platform);
		
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
		while(waitThread<threadCount){
			//等待所有爬虫线程执行完后返回数据...
			logger.trace("还有"+(threadCount-waitThread)+"爬虫线程在运行...");
		}
		
		return json;
	}
	
//	/**
//	 * 获取熊猫tv所有直播间信息
//	 * @param video_platform_all_url
//	 * @return
//	 */
//	public JSONArray getPanda_Video_source(String video_platform_all_url){
//		JSONArray jsonArray=new JSONArray();
//		String htmlStr=getRequestStr(video_platform_all_url, null, null,null);
//		String bodyStr=getHtmlBodyStr(htmlStr);
//		String regex="<liclass=\"video-list-item.*?\".*?>"
//				+ "<ahref=\"(.*?)\".*?>"
//				+ "<divclass=\"video-cover\">"
//				+ "<imgclass=\"video-img.*?\""
//				+ ".*?src=\"(.*?)\".*?>"
//				+ ".*?<divclass=\"video-info\">"
//				+ "<spanclass=\"video-title\"title=\"(.*?)\">.*?</span>"
//				+ "<spanclass=\"video-nickname\"title=\"(.*?)\".*?>"
//				+ "<iclass=.*?data-level=\"(.*?)\">"
//				+ "</i>.*?</span>"
//				+ "<spanclass=\"video-number\">(.*?)</span>"
//				+ ".*?<iclass=\"video-station-num\">(.*?)</i>"
//				+ ".*?<aclass=\"video-label-item.*?>(.*?)</a>.*?</li>";
//		Pattern pattern=Pattern.compile(regex);
//		Matcher matcher=pattern.matcher(bodyStr);
//		while(matcher.find()){
//			String video_type=matcher.group(8);
//			Map<String,Video_category> map=video_categoryService.getVideo_cateMap();
//			if(!map.containsKey(video_type)){
//				continue;
//			}
//			int video_type_id=map.get(video_type).getVideo_type_id();
//			Video_source video_source=new Video_source();
//			video_source.setVideo_room_url(matcher.group(1));
//			video_source.setVideo_img(matcher.group(2));
//			video_source.setVideo_title(matcher.group(3));
//			video_source.setVideo_number(Integer.parseInt(matcher.group(6)));
//			video_source.setVideo_station_num(Integer.parseInt(matcher.group(7)));
//			video_source.setVideo_type(video_type_id);
//			Video_platform video_platform=video_platformDao.selectVideo_platformByName("熊猫tv");
//			video_source.setVideo_platform(video_platform.getVideo_platform_id());
//			video_source.setVideo_id(video_platform.getVideo_platform_id()+"_"+video_source.getVideo_room_url());
//			
//			Video_host video_host=new Video_host();
//			video_host.setVideo_host_level(Integer.parseInt(matcher.group(5)));
//			video_host.setVideo_host_nickname(matcher.group(4));
//			video_host.setVideo_room_id(video_source.getVideo_id());
//			
//			JSONObject json=new JSONObject();
//			json.put("video_host", video_host);
//			json.put("video_source", video_source);
//			jsonArray.add(json);
//		}
//		return jsonArray;
//	}
	
	
}
