package com.yj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.Video_categoryDao;
import com.yj.pojo.Video_category;

@Service
public class Video_categoryService {

	@Autowired
	public Video_categoryDao video_categoryDao;
	
	/**
	 * 获取所有直播分类列表
	 * @return
	 */
	public List<Video_category> getVideo_categoryList(){
		return video_categoryDao.selectAll_Video_category();
	}
	
	/**
	 * 更新指定直播类型的直播图片
	 * @param video_type_id
	 * @param img_url
	 * @return
	 */
	public int update_video_img_url(int video_type_id,String img_url){
		return video_categoryDao.update_video_img_url(video_type_id, img_url);
	}
	
	public Map<String,Video_category> getVideo_cateMap(){
		Map<String,Video_category> map=new HashMap<>();
		List<Video_category> list=video_categoryDao.selectAll_Video_category();
		for (Video_category cate : list) {
			map.put(cate.getVideo_type(), cate);
		}
		return map;
	}
	
	/**
	 * 更新直播分类图片Url到本地url
	 */
	public void updateAllcategoryURL(){
		List<Video_category> list=getVideo_categoryList();
		for (Video_category cate : list) {
			String url=cate.getVideo_type_img();
			String suffix=url.substring(url.lastIndexOf("."));
			url=StringUtils.getUUID()+suffix;
			update_video_img_url(cate.getVideo_type_id(), url);
		}
	}
}
