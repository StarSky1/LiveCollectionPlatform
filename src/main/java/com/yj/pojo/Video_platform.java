package com.yj.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_platform")
public class Video_platform {
	@Id
	private int videoPlatformId;
	private String videoPlatform;
	private String videoPlatformDomain;
	private String videoPlatformImg;

	public int getVideoPlatformId() {
		return videoPlatformId;
	}

	public void setVideoPlatformId(int videoPlatformId) {
		this.videoPlatformId = videoPlatformId;
	}

	public String getVideoPlatform() {
		return videoPlatform;
	}

	public void setVideoPlatform(String videoPlatform) {
		this.videoPlatform = videoPlatform;
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
}
