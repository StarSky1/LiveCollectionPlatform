package com.yj.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.Video_category;
import com.yj.pojo.Video_platform;
import com.yj.service.Video_categoryService;
import com.yj.service.Video_platformService;

@Controller
@RequestMapping("/category/")
public class CategoryController {
	
	@Autowired
	public Video_categoryService categoryService;
	@Autowired
	public Video_platformService platformService;
	
	@RequestMapping("showCategory.do")
	public ModelAndView showCategory(){
		ModelAndView mv=new ModelAndView("videoCategory");
		return mv;
	}
	
	@RequestMapping("getCategory.do")
	@ResponseBody
	public JSONObject getCategory(){
		JSONObject json=new JSONObject();
		List<Video_platform> list=platformService.getVideo_platformList();
		List<Video_category> list1=categoryService.getVideo_categoryList();
		JSONArray platforms=JSON.parseArray(JSON.toJSONString(list));
		JSONArray categorys=JSON.parseArray(JSON.toJSONString(list1));
		json.put("platform_imgs", platforms);
		json.put("category_imgs", categorys);
		return json;
	}
	
	@RequestMapping("showVideos.do")
	public ModelAndView showVideos(String platform,String cateName) throws UnsupportedEncodingException{
		ModelAndView mv=new ModelAndView("videos");
		mv.addObject("cateName", cateName);
		mv.addObject("platform", platform);
		mv.addObject("showSearch",true);
		return mv;
	}
	
}
