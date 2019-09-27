package com.yj.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="video_host")
public class Video_host {
	@Id
	private String videoHostId;
	private String videoHostNickname;
	private int videoHostLevel=1;
	private String videoHostAvatar="null";
	private String videoRoomId;


	public String getVideoHostId() {
		return videoHostId;
	}

	public void setVideoHostId(String videoHostId) {
		this.videoHostId = videoHostId;
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

	public String getVideoRoomId() {
		return videoRoomId;
	}

	public void setVideoRoomId(String videoRoomId) {
		this.videoRoomId = videoRoomId;
	}
}
