package com.yj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_room;
import com.yj.service.Video_roomService;

@Controller
@RequestMapping("/home/")
public class HomeController {
	@Autowired
	public Video_roomService video_roomService;
	
	final int pagesize=100;
	
	@RequestMapping("videolist.do")
	@ResponseBody
	public JSONObject getVideoList(int pageno){
		List<Video_room> list=video_roomService.getVideoList(null, null, pageno, pagesize);
		JSONArray videos=JSON.parseArray(JSON.toJSONString(list));
		int total=video_roomService.getVideoCount();
		JSONObject json=new JSONObject();
		json.put("videos", videos);
		json.put("total", total);
		json.put("pagesize", pagesize);
		return json;
	}
}
