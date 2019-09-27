package com.yj.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_category")
public class Video_category {
	@Id
	private int videoTypeId;
	private String videoType;
	private String videoTypeImg;

	public int getVideoTypeId() {
		return videoTypeId;
	}

	public void setVideoTypeId(int videoTypeId) {
		this.videoTypeId = videoTypeId;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getVideoTypeImg() {
		return videoTypeImg;
	}

	public void setVideoTypeImg(String videoTypeImg) {
		this.videoTypeImg = videoTypeImg;
	}
}
