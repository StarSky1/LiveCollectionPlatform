<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户注册</title>
	<%@ include file="head.jsp"%>
  <style type="text/css">
  	img {
  		width: 440px;
  		height: 660px;
  		margin-bottom: 20px;
  		margin-top: 20px;
  		margin-left: 200px;
  	}
  	body {
  		padding-top: 0px;
  	}
  	.login-wrapper {
  		margin-left: -200px;
    	margin-top: 30px;
  	}
  	.registerBtn {
  		width: 150px;
    	margin-left: 100px;
  	}
  	.login-title {
  		width: 100%;
	  	text-align: center;
	    margin-top: -5px;
	}
  </style>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">折叠导航开关按钮</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">YangTV</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse"  id="navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/">所有直播</a></li>
        <li><a href="/category/showCategory.do">所有分类</a></li>
        <li><a @click.prevent="myprofile()" href="#">我的主页</a></li>
        <li><a @click.prevent="mycare()" href="#">我的关注</a></li>
        <li><a href="showLogin.do">登录&nbsp;/&nbsp;</a></li>
        <li style="position: relative;left: -30px;"><a href="showRegister.do">注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="main-content container-fluid">
	<div class="col-md-8">
		<img alt="图片" src="../res/img1.jpg">
	</div>
	<div class="col-md-4 login-wrapper">
		<form class="well">
		  <h3 class="login-title">
		  	注册&nbsp;YangTV
		  </h3>
		  <div class="form-group">
		    <label for="InputUsername">用户名</label>
		    <input type="text" v-model="username" class="form-control" id="InputUsername" placeholder="请输入用户名">
		    <p v-show="usernameError" style="color: red;">{{ usernameMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">密码</label>
		    <input type="password" v-model="password" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="passwordError" style="color: red;">{{ passwordMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword1">确认密码</label>
		    <input type="password" v-model="password1" class="form-control" id="InputPassword1" placeholder="请确认密码">
		  	<p v-show="password1Error" style="color: red;">{{ password1Message }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPhone">手机号</label>
		    <input type="text" v-model="phone" class="form-control" id="InputPhone" placeholder="请输入手机号">
		    <p v-show="phoneError" style="color: red;">{{ phoneMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputEmail">邮箱</label>
		    <input type="email" v-model="email" class="form-control" id="InputEmail" placeholder="请输入邮箱地址">
		    <p v-show="emailError" style="color: red;">{{ emailMessage }}</p>
		  </div>
		  <div class="checkbox">
		    <label>
		      <input v-model="checked"  type="checkbox"> 我已经认真阅读并同意YangTV的 <a href="#">《使用协议》</a>。
		    </label>
		    <p v-show="!checked" style="color: red;">不同意使用协议，将不能注册。</p>
		  </div>
		  <p><a href="showLogin.do">返回登录</a></p>
		  <button type="submit"  @click.prevent="register()" class="btn btn-info registerBtn">注册</button>
		</form>
	</div>
</div>

<footer>
  Copyright &copy;&nbsp;2018 杨靖 All Rights Reserved
</footer>
<script type="text/javascript">
var vm1=new Vue({
	el: "nav",
	data: {
		logined: false
	},
	methods: {
		myprofile: function(){
			if(this.logined){
				window.location.href="http://localhost:8080/user/showProfile.do";
			}else{
				swal("提示","你还没有登录","info");
			}
		},
		mycare: function(){
			if(this.logined){
				window.location.href="http://localhost:8080/user/showCare.do";
			}else{
				swal("提示","你还没有登录","info");
			}
		}
	}
});
vm1.logined="${logined}";

	var vm=new Vue({
	    el: ".main-content",
	    data: {
	      checked: false,
	      username: "",
	      password: "",
	      password1: "",
	      phone: "",
	      email: "",
	      first: true,
	      usernameMessage: "",
	      passwordMessage: "",
	      password1Message: "",
	      phoneMessage: "",
	      emailMessage: ""
		},
		computed: {
		    // 计算属性的 getter
		    usernameError: function () {
		      // `this` 指向 vm 实例
		      if(this.first) return false;
		      if(this.username.trim()==""){
		    		this.usernameMessage="用户名不能为空";
		    		return true;
				}
		        if(this.username.length<6){
		        	this.usernameMessage="用户名不能小于6位";
		    		return true;
		        }
		        if(this.username.length>12){
		        	this.usernameMessage="用户名不能大于12位";
		    		return true;
		        }
		        var reg = new RegExp("^[a-zA-Z0-9_]+$"); 
		        if(!reg.test(this.username)){
		        	this.usernameMessage="用户名只能包含大写、小写、数字和下划线";
		    		return true;
		        }
		      	return false;
		    },
		    passwordError: function(){
		    	if(this.first) return false;
		    	if(this.password.trim()==""){
					this.passwordMessage="密码不能为空"
					return true;
				}
		    	if(this.password.length<6 || this.password.length>12){
		    		this.passwordMessage="密码长度只能是6到12位"
					return true;
		    	}
		    	return false;
		    },
		    password1Error: function(){
		    	if(this.first) return false;
		    	if(this.password1.trim()==""){
					this.password1Message="确认密码不能为空"
					return true;
				}
		    	if(this.password1!=this.password){
		    		this.password1Message="两次密码不一致"
					return true;
		    	}
		    	return false;
		    },
		    phoneError: function(){
		    	if(this.first) return false;
		    	if(this.phone.trim()==""){
					this.phoneMessage="手机号不能为空"
					return true;
				}
		    	if(this.phone.length!=11){
		    		this.phoneMessage="手机号长度只能是11位"
					return true;
		    	}
		    	return false;
		    },
		    emailError: function(){
		    	if(this.first) return false;
			      if(this.email.trim()==""){
			    		this.emailMessage="邮箱地址不能为空";
			    		return true;
					}
			        if(this.email.length>18){
			        	this.emailMessage="邮箱地址不能大于18位";
			    		return true;
			        }
			        var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); 
			        if(!reg.test(this.email)){
			        	this.emailMessage="邮箱名格式不正确";
			    		return true;
			        }
			      	return false;
		    }
		 },
		methods: {
			register: function(){
				this.first=false;
				if(!vm.checked) return;
				if(this.usernameError || this.passwordError || this.password1Error || this.phoneError || this.emailError) return;
				var flag=false;
				$.getJSON(getRootpath()+"/login/validateUsername.do",{username: this.username},function(json){
					if(!json.status){
						swal("提示！", "用户名已被使用", "error"); 
						flag=true;
						return;
					}
				});
				$.getJSON(getRootpath()+"/login/validatePhone.do",{phone: this.phone},function(json){
					if(!json.status){
						swal("提示！", "手机号已被使用", "error");
						flag=true;
						return;
					}
				});
				$.getJSON(getRootpath()+"/login/validateEmail.do",{email: this.email},function(json){
					if(!json.status){
						swal("提示！", "邮箱名已被使用", "error");
						flag=true;
						return;
					}
				});
				if(flag) return;
				$.getJSON(getRootpath()+"/login/register.do",{username: vm.username,password: vm.password,phone: vm.phone,email: vm.email},function(json){
					if(!json.status){ swal("提示！", "注册失败", "error"); }
				    else{
				    	swal("提示！", "注册成功", "success");
				    	window.location.href='http://localhost:8080/login/showLogin.do';
				    }
				 });
			}
		} 
	});
</script>
</body>
</html>