package com.yj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yj.dao.Video_hostDao;
import com.yj.pojo.Video_host;

@Service
public class Video_hostService {

	@Autowired
	public Video_hostDao video_hostDao;
	
	/**
	 * 添加主播信息到video_host表，如果主播已存在，则更新主播信息
	 * @param video_host
	 * @return
	 */
	public int insertVideo_host(Video_host video_host){
		Video_host previous_video_host=video_hostDao.selectHostByhost_nickname(video_host.getVideo_host_nickname());
		if(previous_video_host==null){
			int video_host_id=video_hostDao.selectMax_host_id()+1;
			video_host.setVideo_host_id(video_host_id);
			return video_hostDao.insertVideo_host(video_host);
		}else{
			video_host.setVideo_host_id(previous_video_host.getVideo_host_id());
			return video_hostDao.updateVideo_host(video_host);
		}
	}
}
