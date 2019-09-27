package com.yj.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_room")
public class Video_room {
	private String videoTitle;
	private String videoImg;
	private int videoNumber;
	private int videoStationNum;
	private String videoUrl;
	private String videoRoomUrl;
	@Id
	private String videoId;
	private String videoPlatform;
	private String videoType;
	private int videoStatus;
	private String videoHostNickname;
	private int videoHostLevel;
	private String videoHostAvatar;
	private String videoPlatformDomain;
	private String videoPlatformImg;
	private String videoTypeImg;

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

	public String getVideoPlatform() {
		return videoPlatform;
	}

	public void setVideoPlatform(String videoPlatform) {
		this.videoPlatform = videoPlatform;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public int getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(int videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getVideoHostNickname() {
		return videoHostNickname;
	}

	public void setVideoHostNickname(String videoHostNickname) {
		this.videoHostNickname = videoHostNickname;
	}

	public int getVideoHostLevel() {
		return videoHostLevel;
	}

	public void setVideoHostLevel(int videoHostLevel) {
		this.videoHostLevel = videoHostLevel;
	}

	public String getVideoHostAvatar() {
		return videoHostAvatar;
	}

	public void setVideoHostAvatar(String videoHostAvatar) {
		this.videoHostAvatar = videoHostAvatar;
	}

	public String getVideoPlatformDomain() {
		return videoPlatformDomain;
	}

	public void setVideoPlatformDomain(String videoPlatformDomain) {
		this.videoPlatformDomain = videoPlatformDomain;
	}

	public String getVideoPlatformImg() {
		return videoPlatformImg;
	}

	public void setVideoPlatformImg(String videoPlatformImg) {
		this.videoPlatformImg = videoPlatformImg;
	}

	public String getVideoTypeImg() {
		return videoTypeImg;
	}

	public void setVideoTypeImg(String videoTypeImg) {
		this.videoTypeImg = videoTypeImg;
	}
}
