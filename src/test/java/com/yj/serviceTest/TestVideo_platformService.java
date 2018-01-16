package com.yj.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yj.pojo.Video_platform;
import com.yj.service.Video_platformService;
import com.yj.spider.HtmlSpiderUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestVideo_platformService {
	@Autowired
	private Video_platformService video_platformService;
	@Autowired
	@Qualifier("htmlSpider")
	public HtmlSpiderUtils htmlSpider;
	
	@Test
	public void getVideo_platformImg(){
		List<Video_platform> list=video_platformService.getVideo_platformList();
		for (Video_platform platform : list) {
			String platform_name=platform.getVideo_platform();
			String img_url=platform.getVideo_platform_img();
			System.out.println(platform_name+" 平台logo图片地址："+img_url);
			htmlSpider.downloadImg_url(img_url, platform_name, "./src/main/webapp/res/platform_img/");
			String suffix=img_url.substring(img_url.lastIndexOf("."));
			img_url="res/platform_img/"+platform_name+suffix;
			System.out.println("update "+platform_name+" platform img to "+img_url);
			video_platformService.updateVideo_platformImg(platform.getVideo_platform_id(), img_url);
		}
	}
}
