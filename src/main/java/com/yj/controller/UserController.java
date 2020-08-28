package com.yj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.User;
import com.yj.pojo.VideoCare;
import com.yj.pojo.Video_room;
import com.yj.service.UserService;
import com.yj.service.VideoCareService;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	public UserService userService;
	@Autowired
	public VideoCareService videoCareService;

	@RequestMapping("showProfile.do")
	public ModelAndView showProfile(HttpSession session){
		ModelAndView mv=new ModelAndView("myprofile");
		return mv;
	}
	
	@RequestMapping("showCare.do")
	public ModelAndView showCare(){
		ModelAndView mv=new ModelAndView("mycare");
		return mv;
	}
	
	@RequestMapping("careVideoList.do")
	@ResponseBody
	public JSONObject getVideoList(int userId) throws UnsupportedEncodingException{
		List<Video_room> list=videoCareService.getVideoSourceByUserId(userId);
		JSONArray videos=JSON.parseArray(JSON.toJSONString(list));
		JSONObject json=new JSONObject();
		json.put("videos", videos);
		return json;
	}
	
	@RequestMapping("editUserInfo.do")
	@ResponseBody
	public JSONObject editUserInfo(int userId,HttpSession session,@RequestParam(required=false,name="file")MultipartFile file,String nickname,String sex,String authenticName,String birthday,String phone,String email,String resume){
		JSONObject json=new JSONObject();
		User user=(User) session.getAttribute("currentUser");
		if(file!=null && !file.isEmpty()){
			String filename=file.getOriginalFilename();
			String suffix=filename.substring(filename.lastIndexOf("."));
			if(!suffix.equals(".jpg") && !suffix.equals(".png")){
				json.put("error", "请上传jpg或png格式图片。");
				return json;
			}
			if(file.getSize()>3*1024*1024){
				json.put("error", "图片大小不能大于3M。");
				return json;
			}
			filename=userService.uploadUserImg(userId,file, suffix, session);
			if(filename.equals("error")){
				json.put("error", "上传图片出错。");
				return json;
			}
			user.setUserHeadImg(filename);
		}
		user.setUserId(userId);
		user.setUserNickname(nickname);
		user.setUserBirthday(birthday);
		user.setUserSex(StringUtils.isEmpty(sex)?null:sex);
		user.setUserPhone(phone);
		user.setUserAuthenticName(StringUtils.isEmpty(authenticName)?null:authenticName);
		user.setUserEmail(email);
		user.setUserResume(StringUtils.isEmpty(resume)?null:resume);
		boolean bl=userService.updateUser(user);
		session.setAttribute("currentUser", userService.getUserById(userId));
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("showUpdatePwd.do")
	public ModelAndView showUpdatePwd(){
		ModelAndView mv=new ModelAndView("updatePwd");
		return mv;
	}
	
	@RequestMapping("updatePwd.do")
	@ResponseBody
	public JSONObject updatePwd(HttpSession session,String originalPwd,String password){
		User curUser=(User) session.getAttribute("currentUser");
		boolean bl=userService.validateUser(curUser.getUserName(), originalPwd);
		JSONObject json=new JSONObject();
		if(!bl){
			json.put("item", "原密码错误");
		}else{
			bl=userService.updatePassword(curUser.getUserId(), password);
			if(bl) session.setAttribute("password", password);
			json.put("status", bl);
		}
		return json;
	}
	
	@RequestMapping("addVideoCare.do")
	@ResponseBody
	public JSONObject addVideoCare(int userId,String videoId){
		JSONObject json=new JSONObject();
		VideoCare care=new VideoCare();
		care.setUserid(userId);
		care.setVideoid(videoId);
		boolean bl=videoCareService.addCare(care);
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("cancelVideoCare.do")
	@ResponseBody
	public JSONObject cancelVideoCare(int userId,String videoId){
		JSONObject json=new JSONObject();
		VideoCare care=new VideoCare();
		care.setUserid(userId);
		care.setVideoid(videoId);
		boolean bl=videoCareService.cancelCare(care);
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("carelist.do")
	@ResponseBody
	public JSONObject carelist(int userId){
		JSONObject json=new JSONObject();
		List<VideoCare> list=videoCareService.getCarelist(userId);
		for (VideoCare care : list) {
			json.put(care.getVideoid(), true);
		}
		return json;
	}
}
