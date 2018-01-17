package com.yj.serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yj.service.Video_sourceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestVideo_sourceService {
	@Autowired
	private Video_sourceService Video_sourceService;
	
	@Test
	public void testGetPanda_video_source(){
		Video_sourceService.getPanda_video_source("https://www.panda.tv/all?pdt=1.r_26668.psbar-co.a1.3f5g2qe91vj","https://www.panda.tv/live_lists");
	}
	
	@Test
	public void testGetDouyu_video_source(){
		Video_sourceService.getDouyu_video_source("https://www.douyu.com/gapi/rkc/directory/0_0");
	}
	
	@Test
	public void testGetZhanqi_video_source(){
		Video_sourceService.getZhanqi_video_source("http://www.zhanqi.tv/api/static/v2.1/live/list/20");
	}
}
