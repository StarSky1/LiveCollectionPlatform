package com.yj.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yj.pojo.Video_category;
import com.yj.service.Video_categoryService;
import com.yj.spider.HtmlSpiderUtils;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVideo_categoryService {
	@Autowired
	private Video_categoryService video_categoryService;
	@Autowired
	@Qualifier("htmlSpider")
	public HtmlSpiderUtils htmlSpider;
	
	@Test
	public void testUpdateURL(){
		video_categoryService.updateAllcategoryURL();
	}
	
//	@Test
//	public void getVideo_category_img(){
//		List<Video_category> list=video_categoryService.getVideo_categoryList();
//		list=htmlSpider.getVideo_category_img("https://www.panda.tv/cate", list);
//		for (Video_category cate : list) {
//			System.out.println(cate.getVideo_type()+" : "+cate.getVideo_type_img());
//			System.out.println("update video_img_url...");
//			video_categoryService.update_video_img_url(cate.getVideo_type_id(), cate.getVideo_type_img());
//			htmlSpider.downloadImg_url(cate.getVideo_type_img(), cate.getVideo_type(),"./src/main/webapp/res/cate_img/");
//		}
//	}
//	
//	@Test
//	public void downloadVideo_category_img(){
//		List<Video_category> list=video_categoryService.getVideo_categoryList();
//		for (Video_category cate : list) {
//			System.out.println("开始下载 "+cate.getVideo_type()+" 图片");
//			htmlSpider.downloadImg_url(cate.getVideo_type_img(), cate.getVideo_type(), "./src/main/webapp/res/cate_img/");
//		}
//	}
}
