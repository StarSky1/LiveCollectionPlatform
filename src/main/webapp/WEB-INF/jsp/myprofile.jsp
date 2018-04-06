<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${currentUser.userNickname}的主页</title>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="../bootstrap-3.3.7-dist/simple-sidebar.css">
  <link rel="stylesheet" href="../css/bootstrap-datetimepicker.css">
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
  <script type="text/javascript" src="../js/bootstrap-datetimepicker.js"></script>
  <script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
  <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  <style>
   .main-content {
   	background-color: #F6F6F6;
   	margin-bottom: 20px;
   	height: 800px;
   	border-radius: 2%;
   }
  	.u_header {
	    height: 50px;
	    margin-bottom: 20px;
	    border-bottom: 2px #ebebeb solid;
	}
	.u_header h1 {
	    width: 167px;
	    height: 50px;
	    padding-top: 10px;
	    font-size: 30px;
	    color: #555;
	    border-bottom: 5px #1DD388 solid;
	    text-align: center;
	}
	.u_header .guild-entry {
	    float: right;
	    margin-top: 18px;
	    font-size: 0;
	}
	.u_nav {
	    width: 200px;
	    float: left;
	    /* overflow: hidden; */
	}
	ul, li {
	    list-style-type: none;
	}
	.u_nav .btn {
		width: 170px;
		margin-bottom: 10px;
		background-color: #1DD388;
	}
	.user_myprofile .togglebtn {
	    margin-bottom: 34px;
	    width: 100%;
	    background:#CCE4F7;
	}
	.user_myprofile .togglebtn span {
	    display: block;
	    float: left;
	    margin: 10px 0;
	    height: 20px;
	    width: 20px;
	    margin-left: 20px;
	}
	.user_myprofile .togglebtn p {
	    height: 40px;
	    line-height: 40px;
	    font-size: 16px;
	    margin-left: 10px;
	    color: #555555;
	}
	.user_img {
		width: 150px;
	    height: 150px;
	    margin-top: -20px;
	}
	.col-md-10 .row .col-md-4 p {
		margin-bottom: 20px;
	}
	.edit-row{
		margin-top: 80px;
	}
	.edit-row .btn-info {
		margin-left: 50px;
    	margin-top: -50px;
	}
	.edit .btn-warning,.edit .btn-info {
		margin-left: 10px;
    	margin-top: -50px;
	}
	.fileInput{
		margin-top: 30px;
	}
	.user-form .form-control {
		display: inline;
		width: 200px;
	}
  </style>
</head>
<body>
  <nav class="navbar navbar-default navbar-fixed-top">
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
        <li>
          <form class="navbar-form">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="搜索游戏或主播">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
          </form>
        </li>
        <li><a href="#">所有直播</a></li>
        <li><a href="category/showCategory.do">所有分类</a></li>
        <li><a href="#">上传直播源</a></li>
        <li><a href="#">我的主页</a></li>
        <li v-if="logined">
        	<img class="userimg" src="../res/user_head_img/${currentUser.userHeadImg}">
        </li>
        <li v-if="logined">
        	<span class="user_dropdown glyphicon glyphicon-chevron-down dropdown-toggle" data-toggle="dropdown"></span>
			  <ul class="dropdown-menu">
				<li><a href="user/showProfile.do" target="_blank"><i class="glyphicon glyphicon-home"></i> 我的主页</a></li>
				<li><a id="changePassword" href="/user/changePassword" target="dialog" rel="changePassword" mask="true" height="380" width="500"><i class="glyphicon glyphicon-wrench"></i> 修改密码</a></li>
				<li>
				<a class="checkin-btn" href="/my/checkin" action="ajaxData"><i class="glyphicon glyphicon-ok-sign"></i> 签到</a></li>
				<li role="separator" class="divider"></li>
				<li><a href="/logout?refer=/"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
			  </ul>
        </li>
        <li v-if="!logined"><a href="/liveplatform/login/showLogin.do">登录&nbsp;/&nbsp;</a></li>
        <li v-if="!logined" style="position: relative;left: -30px;"><a href="#">注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="main-content container">
	<div class="u_header">
	    <h1 class="fl">个人中心</h1>
	    <div class="guild-entry"></div>
	</div>
	<div class="row">
	<div class="u_nav col-md-4">
		<div class="btn-group-vertical" role="group">
			<button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon1"></i>我的资料</button>
  			<button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon2"></i>站内信</button>
  			<button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon2"></i>我的关注</button>
  			<button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon2"></i>我的等级</button>
		</div>
    </div>
    <div class="user_myprofile col-md-8">
    	<form method="post" enctype="multipart/form-data">
    	<div class="togglebtn">
            <span class="fl glyphicon glyphicon-user"></span>
            <p class="fl">我的资料</p>
        </div>
        <div class="container user-form">
        	<div class="row">
        		<div class="col-md-2">
        			<img class="user_img" alt="" src="../res/user_head_img/default.jpg">
        			<input v-if="isEdit" type="file" class="fileInput" id="InputFile">
        			<div class="row edit-row" :class="{edit: isEdit}">
						<button type="button" v-if="!isEdit" @click.prevent="isEdit=true" class="btn btn-info">编辑信息</button>
						<button type="button" v-if="isEdit" @click.prevent="submit()" class="btn btn-info">确定编辑</button>
						<button type="button" v-if="isEdit" @click.prevent="isEdit=false" class="btn btn-warning">取消</button>
        			</div>
        		</div>
        		<div class="col-md-10">
        			<div class="row">
        				<div class="col-md-4">
        					<p><span>用户名：</span><span>${currentUser.userName}</span></p>
        				</div>
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>昵称：</span><input type="text" v-model="nickname" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>昵称：</span><span>${currentUser.userNickname}</span></p>
        					<p v-show="nicknameError" style="color: red;">{{ nicknameMessage }}</p>
        				</div>
        			</div>
        			<div class="row">
        				<div class="col-md-4">
        					<p><span>等级：</span><span>${currentUser.userVideoLevel}</span></p>
        				</div>
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>性别：</span><input type="text" v-model="sex" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>性别：</span><span>${currentUser.userSex}</span></p>
        					<p v-show="sexError" style="color: red;">{{ sexMessage }}</p>
        				</div>
        			</div>
        			<div class="row">
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>真实姓名：</span><input type="text" v-model="authenticName" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>真实姓名：</span><span>${currentUser.userAuthenticName}</span></p>
        					<p v-show="authenticNameError" style="color: red;">{{ authenticNameMessage }}</p>
        				</div>
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>出生日期：</span><input type="text" v-model="birthday" class="form-control" id="datetimepicker" data-date-format="yyyy-mm-dd" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>出生日期：</span><span>${currentUser.userBirthday}</span></p>
        					<p v-if="isEdit" v-show="birthdayError" style="color: red;">{{ birthdayMessage }}</p>
        				</div>
        			</div>
        			<div class="row">
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>手机号：</span><input type="text" v-model="phone" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>手机号：</span><span>${currentUser.userPhone}</span></p>
        					<p v-show="phoneError" style="color: red;">{{ phoneMessage }}</p>
        				</div>
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>邮箱地址：</span><input type="email" v-model="email" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>邮箱地址：</span><span>${currentUser.userEmail}</span>	</p>	
        					<p v-show="emailError" style="color: red;">{{ emailMessage }}</p>
        				</div>
        			</div>
        			<div class="row">
        				<div class="col-md-4">
        					<p><span>当前登录时间：</span><span>${currentUser.loginTime}</span></p>
        				</div>
        				<div class="col-md-4">
        					<p><span>上次离开时间：</span><span>${currentUser.quitTime}</span></p>	
        				</div>
        			</div>
        			<div class="row">
        				<div class="col-md-4">
        					<p v-if="isEdit"><span>简介：</span><input type="text" v-model="resume" class="form-control" placeholder="写点东西"></p>
        					<p v-if="!isEdit"><span>简介：</span><span>${currentUser.userResume}</span></p>
        					<p v-show="resumeError" style="color: red;">{{ resumeMessage }}</p>
        				</div>
        			</div>
        		</div>
        	</div>
        </div>
        </form>
     </div>
     </div> 
</div>
<footer>
  Copyright &copy;&nbsp;2018 杨靖 All Rights Reserved
</footer>
<script>
var vm1=new Vue({
	el: "nav",
	data: {
		logined: false
	}
});
vm1.logined="${logined}";

var vm=new Vue({
	el: ".main-content",
	data: {
		isEdit: false,
		first: true,
		nickname: "",
		sex: "",
		authenticName: "",
		birthday: "",
		phone: "",
		email: "",
		resume: "",
		nicknameMessage: "",
		authenticNameMessage: "",
		birthdayMessage: "",
		resumeMessage: "",
		sexMessage: "",
		emailMessage: "",
		phoneMessage: ""
	},
	computed: {
	    // 计算属性的 getter
	    nicknameError: function () {
	      // `this` 指向 vm 实例
	      if(this.first) return false;
	      if(this.nickname.trim()==""){
	    		this.nicknameMessage="昵称不能为空";
	    		return true;
			}
	        if(this.nickname.length<3){
	        	this.nicknameMessage="昵称不能小于3位";
	    		return true;
	        }
	        if(this.nickname.length>12){
	        	this.nicknameMessage="昵称不能大于12位";
	    		return true;
	        }
	        var reg = new RegExp("^[a-zA-Z0-9_]+$"); 
	        if(!reg.test(this.nickname)){
	        	this.nicknameMessage="昵称只能包含大写、小写、数字和下划线";
	    		return true;
	        }
	      	return false;
	    },
	    authenticNameError: function(){
	    	if(this.first) return false;
	    	if(this.authenticName.length>4){
	    		this.authenticNameMessage="真实姓名不能超过4位。"
	    		return true;
	    	}
	    	return false;
	    },
	    sexError: function(){
	    	if(this.first) return false;
	    	if(this.sex.trim()=="") return false;
	    	if(this.sex.trim()!="男" && this.sex.trim()!="女"){
	    		this.sexMessage="性别只能是男或女。"
	    		return true;
	    	}
	    	return false;
	    },
	    birthdayError: function(){
	    	if(this.first) return false;
	    	if(this.birthday.trim()==""){
	    		this.birthdayMessage="出生日期不能为空";
	    		return true;
	    	}
	    	var reg = new RegExp("^[0-9]{4}-[0-9]{2}-[0-9]{1,2}$");
	    	if(!reg.test(this.birthday)){
	    		this.birthdayMessage="日期格式必须是xxxx-xx-xx"
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
	    },
	    resumeError: function(){
	    	if(this.first) return false;
	    	if(this.resume.length>150){
	    		this.resumeMessage="简介最多150字。";
	    		return true;
	    	}
	    	return false;
	    }
	 },
	methods: {
		submit: function(){
			this.first=false;
			if(this.nicknameError || this.authenticError || this.sexError || this.birthdayError || this.phoneError || this.emailError || this.resumeError) return;
			var fileD=document.querySelector("#InputFile");
			var formData=new FormData();
			formData.append("userId","${currentUser.userId}");
			formData.append("file",fileD.files[0]);
			formData.append("nickname",vm.nickname);
			formData.append("authenticName",vm.authenticName);
			formData.append("phone",vm.phone);
			formData.append("email",vm.email);
			formData.append("resume",vm.resume);
			formData.append("sex",vm.sex);
			formData.append("birthday",vm.birthday);
			 $.ajax({
				   url:  getRootpath()+"/user/editUserInfo.do",
				   type:'post',
				   data:formData,
				   processData : false,  //必须false才会避开jQuery对 formdata 的默认处理 
				   contentType : false,  //必须false才会自动加上正确的Content-Type
				   dataType:'json',
				   success:function(json){
					   if(json.error){
						   swal("提示！", json.error, "error");
					   }else{
						   if(!json.status){ swal("提示！", "修改失败", "error"); }
						    else{
						    	swal({title: "提示！",text: "修改成功",type: "success"});
						    	window.location.href='http://localhost:8080/liveplatform/user/showProfile.do';
						    }
					   }
					   
				   }
			 });
		}
	}
});

vm.nickname="${currentUser.userNickname}";
vm.sex="${currentUser.userSex}";
vm.authenticName="${currentUser.userAuthenticName}";
vm.birthday="${currentUser.userBirthday}";
vm.phone="${currentUser.userPhone}";
vm.email="${currentUser.userEmail}";
vm.resume="${currentUser.userResume}";
</script>
</body>
</html>