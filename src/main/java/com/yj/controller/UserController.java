package com.yj.controller;

import javax.servlet.http.HttpSession;

import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.User;
import com.yj.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	public UserService userService;

	@RequestMapping("showProfile.do")
	public ModelAndView showProfile(HttpSession session){
		ModelAndView mv=new ModelAndView("myprofile");
		return mv;
	}
	
	@RequestMapping("editUserInfo.do")
	@ResponseBody
	public JSONObject editUserInfo(int userId,HttpSession session,@RequestParam("file")MultipartFile file,String nickname,String sex,String authenticName,String birthday,String phone,String email,String resume){
		JSONObject json=new JSONObject();
		User user=new User();
		if(!file.isEmpty()){
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
		json.put("status", bl);
		return json;
	}
}
