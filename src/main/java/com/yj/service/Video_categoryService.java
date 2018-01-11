package com.yj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yj.dao.Video_categoryDao;
import com.yj.pojo.Video_category;
import com.yj.spider.HtmlSpiderUtils;

@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class Video_categoryService {

	@Autowired
	public HtmlSpiderUtils htmlSpider;
	
	@Autowired
	public Video_categoryDao video_categoryDao;
	
	@Test
	public void getVideo_category_img(){
		List<Video_category> list=video_categoryDao.selectAll_Video_category();
		list=htmlSpider.getVideo_category_img("https://www.panda.tv/cate", list);
		for (Video_category cate : list) {
			System.out.println(cate.getVideo_type()+" : "+cate.getVideo_type_img());
			System.out.println("update video_img_url...");
			video_categoryDao.update_video_img_url(cate.getVideo_type_id(), cate.getVideo_type_img());
			htmlSpider.downloadImg_url(cate.getVideo_type_img(), cate.getVideo_type());
		}
	}
	
	public Map<String,Video_category> getVideo_cateMap(){
		Map<String,Video_category> map=new HashMap<>();
		List<Video_category> list=video_categoryDao.selectAll_Video_category();
		for (Video_category cate : list) {
			map.put(cate.getVideo_type(), cate);
		}
		return map;
	}
}
