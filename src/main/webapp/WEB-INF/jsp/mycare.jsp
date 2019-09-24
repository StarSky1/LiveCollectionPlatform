<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${currentUser.userNickname}的关注</title>
<link rel="icon" type="image/x-icon" href="../res/logo.png" />
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
   	overflow-x: hidden;
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
	.liveList {
		width: 768.66px;
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
				<li><a id="changePassword" href="showUpdatePwd.do"><i class="glyphicon glyphicon-wrench"></i> 修改密码</a></li>
				<li>
				<a class="checkin-btn" @click.prevent="signIn()" href="#" action="ajaxData"><i class="glyphicon glyphicon-ok-sign"></i> 签到</a></li>
				<li role="separator" class="divider"></li>
				<li><a @click.prevent="quit()" href="#"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
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
			<button type="button" class="btn btn-success btn-lg" @click.prevent="myprofile()"><i class="u_nav_icon1"></i>我的资料</button>
  			<!-- <button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon2"></i>站内信</button> -->
  			<button type="button" class="btn btn-success btn-lg" @click.prevent="mycare()"><i class="u_nav_icon2"></i>我的关注</button>
  			<!-- <button type="button" class="btn btn-success btn-lg"><i class="u_nav_icon2"></i>我的等级</button> -->
		</div>
    </div>
    <div class="user_myprofile col-md-8">
    	<div class="togglebtn">
            <span class="fl glyphicon glyphicon-user"></span>
            <p class="fl">我的关注</p>
        </div>
        <div class="container user-form">
        	<div id="liveList" class="liveList row">
		        <a  v-for="video in videos" :href="video.video_room_url" target="_blank" class="col-lg-6 col-md-6 col-sm-6 ellipsis">
		        <img :src="video.video_img" onerror="this.onerror=&quot;&quot;;this.src=&quot;../res/404.jpg&quot;">
		        <img :src="'../res/platform_img/'+video.video_platform_img" class="platform-icon">
		        <h5>&nbsp;{{ video.video_title }}</h5>
		        <div class="row">
		          <div class="col-sm-7">
		            <span class="glyphicon glyphicon-user"></span>&nbsp;{{ video.video_host_nickname }}
		          </div>
		          <div class="col-sm-5 text-right">
		            <img v-show="!cares[video.video_id]" :id="video.video_id+'_'" @click.prevent="addCare(video.video_id)" src="../res/heart-empty.png" class="heart-icon" >
		            <img v-show="cares[video.video_id]" :id="video.video_id+'__'" @click.prevent="cancelCare(video.video_id)" src="../res/heart.png" class="heart-icon" >
		            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;{{ video.video_number }}
		          </div>
		        </div>
		        </a>
		      </div>
        </div>
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
		videos: [],
	    cares: {}
	},
	computed: {
	    // 计算属性的 getter
	 },
	methods: {
		myprofile: function(){
			window.location.href="http://localhost:8080/liveplatform/user/showProfile.do";
		},
		mycare: function(){
			window.location.href="http://localhost:8080/liveplatform/user/showCare.do";
		},
		addCare: function(videoId){
	    	  if(vm1.logined){
	    		  $("#"+videoId+'_').hide();
	        	  $("#"+videoId+'__').show();
	        	  $.getJSON(getRootpath()+"/user/addVideoCare.do",{userId: "${currentUser.userId}",videoId: videoId},function(json){
	        		 if(!json.status){
	        			 swal("提示","添加关注失败","error");
	        		 } 
	        	  });
	    	  }else{
	    		  swal("提示","你还没有登录","info");
	    	  }
	      },
	      cancelCare: function(videoId){
	    	  $("#"+videoId+'_').show();
	    	  $("#"+videoId+'__').hide();
	    	  $.getJSON(getRootpath()+"/user/cancelVideoCare.do",{userId: "${currentUser.userId}",videoId: videoId},function(json){
	     		 if(!json.status){
	     			 swal("提示","取消关注失败","error");
	     		 } 
	     	  });
	      }
	}
});

if(vm1.logined){
	  $.getJSON(getRootpath()+"/user/carelist.do",{userId: "${currentUser.userId}"},function(json){
			vm.cares=json;
		});
}
$.getJSON(getRootpath()+"/user/careVideoList.do",{userId: "${currentUser.userId}"},function(json){
	vm.videos=json.videos;
});

</script>
</body>
</html>