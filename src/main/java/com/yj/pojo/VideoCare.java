package com.yj.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_care")
public class VideoCare {
    @Id
    private Integer id;

    @Column(name = "def_userId")
    private Integer userid;

    @Column(name = "def_videoId")
    private String videoid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid == null ? null : videoid.trim();
    }
}