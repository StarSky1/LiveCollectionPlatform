<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${currentUser.userNickname}的关注</title>
    <%@ include file="../common/head.jsp"%>
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
<%@ include file="../common/topBar.jsp"%>
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
			window.location.href="http://localhost:8080/user/showProfile.do";
		},
		mycare: function(){
			window.location.href="http://localhost:8080/user/showCare.do";
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

var logined=Boolean("${logined}");
if(logined){
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