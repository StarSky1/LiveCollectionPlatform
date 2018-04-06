package com.yj.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yj.service.UserService;

@Controller
@RequestMapping("/login/")
public class LoginController {
	@Autowired
	public UserService userService;
	
	@RequestMapping("showLogin.do")
	public ModelAndView showLogin(){
		ModelAndView mv=new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping("showRegister.do")
	public ModelAndView showRegister(){
		ModelAndView mv=new ModelAndView("register");
		return mv;
	}
	
	@RequestMapping("login.do")
	@ResponseBody
	public JSONObject login(HttpSession session,String username,String password,boolean remember){
		JSONObject json=new JSONObject();
		if(remember){
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			session.setAttribute("checked", remember);
		}
		boolean bl=userService.validateUser(username, password);
		if(bl){
			session.setAttribute("currentUser", userService.getUserByUserName(username));
			session.setAttribute("logined", true);
		} 
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("register.do")
	@ResponseBody
	public JSONObject register(String username,String password,String phone,String email){
		JSONObject json=new JSONObject();
		boolean bl=userService.addUser(username, password, phone, email);
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("validateUsername.do")
	@ResponseBody
	public JSONObject validateUsername(String username){
		JSONObject json=new JSONObject();
		boolean bl=userService.validateUsername(username);
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("validateEmail.do")
	@ResponseBody
	public JSONObject validateEmail(String email){
		JSONObject json=new JSONObject();
		boolean bl=userService.validateEmail(email);
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("validatePhone.do")
	@ResponseBody
	public JSONObject validatePhone(String phone){
		JSONObject json=new JSONObject();
		boolean bl=userService.validatePhone(phone);
		json.put("status", bl);
		return json;
	}
}
