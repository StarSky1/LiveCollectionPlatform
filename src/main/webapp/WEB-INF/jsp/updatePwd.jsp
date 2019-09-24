<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改密码</title>
<link rel="icon" type="image/x-icon" href="../res/logo.png" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.css">
  <link rel="stylesheet" href="../css/main.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  <script type="text/javascript" src="../js/jQuery1.12.4.js"></script>
  <script type="text/javascript" src="../bootstrap-3.3.7-dist/js/bootstrap.js"></script>
  <script type="text/javascript" src="../js/vue.js"></script>
  <script type="text/javascript" src="../js/main.js"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <style type="text/css">
  	.backImg {
  		width: 440px;
  		height: 520px;
  		margin-bottom: 20px;
  		margin-top: 20px;
  		margin-left: 200px;
  	}
  	body {
  		padding-top: 0px;
  	}
  	.login-wrapper {
  		margin-left: -200px;
    	margin-top: 150px;
  	}
  	.loginBtn {
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
        <li v-if="logined">
        	<img class="userimg" src="../res/user_head_img/${currentUser.userHeadImg}">
        </li>
        <li v-if="logined">
        	<span class="user_dropdown glyphicon glyphicon-chevron-down dropdown-toggle" data-toggle="dropdown"></span>
			  <ul class="dropdown-menu">
				<li><a href="showProfile.do" target="_blank"><i class="glyphicon glyphicon-home"></i> 我的主页</a></li>
				<li><a id="changePassword" href="showUpdatePwd.do" ><i class="glyphicon glyphicon-wrench"></i> 修改密码</a></li>
				<li>
				<a class="checkin-btn" @click.prevent="signIn()" href="#" action="ajaxData"><i class="glyphicon glyphicon-ok-sign"></i> 签到</a></li>
				<li role="separator" class="divider"></li>
				<li><a @click.prevent="quit()" href="#"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
			  </ul>
        </li>
        <li v-if="!logined"><a href="/liveplatform/login/showLogin.do">登录&nbsp;/&nbsp;</a></li>
        <li v-if="!logined" style="position: relative;left: -30px;"><a href="/liveplatform/login/showRegister.do">注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="main-content container-fluid">
	<div class="col-md-8">
		<img class="backImg" alt="图片" src="../res/img.jpg">
	</div>
	<div class="col-md-4 login-wrapper">
		<form class="well">
		  <h3 class="login-title">
		  	修改密码&nbsp;
		  </h3>
		  <div class="form-group">
		    <label for="InputEmail">用户名</label>
		    <p>${currentUser.userName}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">原密码</label>
		    <input type="password" v-model="originalPwd" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="originalPwdError" style="color: red;">{{ originalPwdMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">新密码</label>
		    <input type="password" v-model="password" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="passwordError" style="color: red;">{{ passwordMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword1">确认新密码</label>
		    <input type="password" v-model="password1" class="form-control" id="InputPassword1" placeholder="请确认密码">
		  	<p v-show="password1Error" style="color: red;">{{ password1Message }}</p>
		  </div>
		  <button type="submit"  @click.prevent="update()" class="btn btn-info loginBtn">确认修改</button>
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
		},
		signIn: function(){
			$.getJSON(getRootpath()+"/login/signIn.do",{},function(json){
			    if(!json.status){ swal("提示！", "今日已经签到", "error");  }
			    else{
			    	swal("提示！", "签到成功", "success");
			    	window.location.href='http://localhost:8080/liveplatform/user/showProfile.do';
			    }
		   });
		},
		quit: function(){
	    	  $.getJSON(getRootpath()+"/login/quit.do",{},function(json){
				    if(!json.status){ swal("提示！", "退出登录失败", "error");  }
				    else{
				    	swal("提示！", "退出成功", "success");
				    	window.location.href='http://localhost:8080/liveplatform';
				    }
			   });
	      }
	}
});
vm1.logined="${logined}";

	var vm=new Vue({
	    el: ".main-content",
	    data: {
	      checked: false,
	      originalPwd: "",
	      password: "",
	      password1: "",
	      first: true,
	      originalPwdMessage: "",
	      passwordMessage: "",
	      password1Message: ""
		},
		computed: {
		    // 计算属性的 getter
		    originalPwdError: function(){
		    	if(this.first) return false;
		    	if(this.originalPwd.trim()==""){
					this.originalPwdMessage="密码不能为空"
					return true;
				}
		    	if(this.originalPwd.length<6 || this.originalPwd.length>12){
		    		this.originalPwdMessage="密码长度只能是6到12位"
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
		    }
		 },
		methods: {
			update: function(){
				this.first=false;
				if(this.originalPwdError || this.passwordError || this.password1Error) return;
				$.getJSON(getRootpath()+"/user/updatePwd.do",{originalPwd: vm.originalPwd,password: vm.password},function(json){
				    if(json.item){
				    	swal("提示！", json.item, "error");
				    }else{
				    	if(!json.status){ swal("提示！", "修改密码错误", "error");  }
					    else{
					    	swal("提示！", "修改密码成功", "success");
					    }
				    }
					
				 });
			}
		} 
	});
</script>
</body>
</html>