<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>直播列表</title>
  <%@ include file="../common/head.jsp"%>
</head>
<body>
  <%@ include file="../common/topBar.jsp"%>
<div class="main-header">
	<i class="header-line"></i>
	<h3 class="header-title-firstname">${platform}</h3>
	<h3 class="header-title-secondname">${cateName}</h3>
</div>
<div class="main-content container-fluid">
      <div id="liveList" class="liveList row">
        <a  v-for="video in videos" :href="video.videoRoomUrl" target="_blank" class="col-lg-3 col-md-4 col-sm-6 ellipsis ">
        <img :src="video.videoImg" onerror="this.onerror=&quot;&quot;;this.src=&quot;./res/404.jpg&quot;">
        <img :src="'../res/platform_img/'+video.videoPlatformImg" class="platform-icon">
        <h5>&nbsp;{{ video.videoTitle }}</h5>
        <div class="row">
          <div class="col-sm-7">
            <span class="glyphicon glyphicon-user"></span>&nbsp;{{ video.videoHostNickname }}
          </div>
          <div class="col-sm-5 text-right">
          	<img v-show="!cares[video.videoId]" :id="video.videoId+'_'" @click.prevent="addCare(video.videoId)" src="../res/heart-empty.png" class="heart-icon" >
            <img v-show="cares[video.videoId]" :id="video.videoId+'__'" @click.prevent="cancelCare(video.videoId)" src="../res/heart.png" class="heart-icon" >
            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;{{ formatNumber(video.videoNumber) }}
          </div>
        </div>
        </a>
      </div>
</div>

  <div class="container-fluid">
    <div class="row">
      <%@ include file="../common/pagenation.jsp"%>
    </div>
  </div>



<!-- <div class="opacity"></div>
<div class="login">123</div> -->

  <%@ include file="../common/footer.jsp"%>

<script>
    var cateName="${cateName}";
    var platform="${platform}";
  var vm=new Vue({
    el: ".main-content",
    data: {
      videos: [],
      cares: {},
      cateName: cateName,
      platform: platform
    },
    computed: {
      // 计算属性的 getter

    },
    methods: {
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
        },
        formatNumber: function(videoNumber){
          let nums=videoNumber/10000;
          return nums.toFixed(0)>0?nums.toFixed(2)+'万':(videoNumber/1000).toFixed(2)+'千';
        }
    }
  });

  var logined=Boolean("${logined}");
  getVideolist(vm,page_vm,logined,1,vm1.searchWord,cateName,platform);
</script>

</body>
</html>
