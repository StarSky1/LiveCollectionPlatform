package com.yj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.Video_platformDao;
import com.yj.pojo.Video_platform;

@Service
public class Video_platformService {
	@Autowired
	private Video_platformDao video_platformDao;
	
	/**
	 * 通过直播平台名称获取直播平台信息
	 * @param platform_name
	 * @return
	 */
	public Video_platform getVideo_platformByName(String platform_name){
		return video_platformDao.selectVideo_platformByName(platform_name);
	}
	
	public List<Video_platform> getVideo_platformList(){
		return video_platformDao.selectVideo_platformList();
	}
	
	/**
	 * 更新指定平台id的平台图片
	 * @param platform_id
	 * @param platform_img
	 * @return
	 */
	public int updateVideo_platformImg(int platform_id,String platform_img){
		return video_platformDao.updateVideo_platformImg(platform_id, platform_img);
	}
}
