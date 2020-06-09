package com.yj.controller;

import com.alibaba.fastjson.JSONObject;
import com.yj.pojo.User;
import com.yj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

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
			User user=userService.getUserByUserName(username);
			//记录登录时间
			user.setLoginTime(new Date());
			session.setAttribute("currentUser", user);
			session.setAttribute("logined", true);
		} 
		json.put("status", bl);
		return json;
	}
	
	@RequestMapping("quit.do")
	@ResponseBody
	public JSONObject quit(HttpSession session){
		User curUser=(User) session.getAttribute("currentUser");
		session.removeAttribute("currentUser");
		session.removeAttribute("logined");
		session.removeAttribute("signed");
		User user=new User();
		user.setUserId(curUser.getUserId());
		user.setQuitTime(new Date());
		userService.updateUser(user);
		JSONObject json=new JSONObject();
		json.put("status", true);
		return json;
	}
	
	@RequestMapping("signIn.do")
	@ResponseBody
	public JSONObject signIn(HttpSession session){
		JSONObject json=new JSONObject();
		if(session.getAttribute("signed")!=null){
			json.put("status", false);
			return json;
		}
		User curUser=(User) session.getAttribute("currentUser");
		int level=curUser.getUserVideoLevel();
		curUser.setUserVideoLevel(level+1);
		userService.updateLevel(curUser.getUserId(), level+1);
		session.setAttribute("signed", true);
		json.put("status", true);
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
