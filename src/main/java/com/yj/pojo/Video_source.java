package com.yj.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_source")
public class Video_source {
	
	private String videoTitle;
	private String videoImg;
	private int videoNumber;
	private int videoStationNum;
	private String videoUrl;
	private String videoRoomUrl;
	@Id
	private String videoId;
	private int videoPlatform;
	private int videoType;
	private int videoStatus;


	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoImg() {
		return videoImg;
	}

	public void setVideoImg(String videoImg) {
		this.videoImg = videoImg;
	}

	public int getVideoNumber() {
		return videoNumber;
	}

	public void setVideoNumber(int videoNumber) {
		this.videoNumber = videoNumber;
	}

	public int getVideoStationNum() {
		return videoStationNum;
	}

	public void setVideoStationNum(int videoStationNum) {
		this.videoStationNum = videoStationNum;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoRoomUrl() {
		return videoRoomUrl;
	}

	public void setVideoRoomUrl(String videoRoomUrl) {
		this.videoRoomUrl = videoRoomUrl;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public int getVideoPlatform() {
		return videoPlatform;
	}

	public void setVideoPlatform(int videoPlatform) {
		this.videoPlatform = videoPlatform;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}

	public int getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(int videoStatus) {
		this.videoStatus = videoStatus;
	}
}
