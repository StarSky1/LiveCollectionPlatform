package com.yj.service;

import com.yj.dao.VideoCareMapper;
import com.yj.pojo.VideoCare;
import com.yj.pojo.Video_room;
import com.yj.repository.VideoRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yj.logger.Log4JConfigure.LOGERROR;
import static com.yj.logger.Log4JConfigure.LOGINFO;

@Service
public class VideoCareService {
	@Autowired
	public VideoCareMapper careMapper;
	@Autowired
	public VideoRoomRepository videoRoomRepository;
	
	public boolean addCare(VideoCare care){
		try {
			careMapper.save(care);
			return true;
		}catch (Exception e){
			LOGINFO.error("addCare error",e);
			LOGERROR.error("addCare error",e);
		}
		return false;
	}
	
	public boolean cancelCare(VideoCare care){
		try {
			careMapper.delete(care);
			return true;
		}catch (Exception e){
			LOGINFO.error("cancelCare error",e);
			LOGERROR.error("cancelCare error",e);
		}
		return false;
	}
	
	public List<VideoCare> getCarelist(int userId){
		return careMapper.selectByUserId(userId);
	}
	
	public List<Video_room> getVideoSourceByUserId(int userId){
		return videoRoomRepository.selectVideoSourceByUserId(userId);
	}
}
