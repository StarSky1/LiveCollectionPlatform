<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>直播分类</title>
    <%@ include file="../common/head.jsp"%>
  <style type="text/css">
  	p {
  		text-align: center;
  		font-size: 18px;
  	}
  	img {
  		width: 100%;
  		height: 100%;
  	}
  	a:hover {
  		text-decoration: none;
  	}
  	ul {
	    list-style: none;
	}
	/* 页脚样式 */
	footer {
	  position: relative;
	  bottom: 0;
	  left: 0;
	  background-color: #3f3f3f;
	  text-align: center;
	  width: 100%;
	  color: #979797;
	  padding: 30px 0;
	  overflow: hidden;
	  line-height: 22px;
	}
  	.cc {
  		position: fixed;
  		top: 200px;
  		left: 0;
  		background-color: #191b2e;
  		color: #b2b4c4;
  	}
  	.cate_list {
  		width: 80%;
  		margin-left: 280px;
  		margin-right: 250px;
  	}
  	.cate_item {
  		margin-bottom: 20px;
  	}
  	.cate_item:hover .cate-title {
	    background-color: #1dd388;
	    color: #fff;
	}
  	.cate_title {
  		height: 39px;
	    font-size: 18px;
	    line-height: 36px;
	    color: #333;
	    text-align: center;
	    background: #FFFFFF;
	    -webkit-transition: all 250ms;
	    transition: all 250ms;
  	}

    .videoTypeImg {
        width: 286.594px;
        height: 397.313px;
    }
  	
  
  </style>
</head>
<body>
<%@ include file="../common/topBar.jsp"%>
<div class="main-content ">
		<div class="col-md-2 cc">
			<p>平台分类</p>
			<div class="row">
				<div class="col-md-6" v-for="platform in platform_imgs">
				<a :href="'showVideos.do?platform='+platform.video_platform">
					<img :src="'../res/platform_img/'+platform.video_platform_img"/>
					<p>{{ platform.video_platform }}</p>
				</a>
				</div>
			</div>
		</div>
		<div class="container cate_list">
			<ul class="row">
				<li class="col-md-3 cate_item" v-for="img in category_imgs">
				<a :href="'showVideos.do?cateName='+img.video_type">
					<img class="videoTypeImg" :src="'../res/cate_img/'+img.video_type_img"/>
					<div class="cate_title">{{ img.video_type }}</div>
				</a>
				</li>
			</ul>
		</div>
</div>

<footer>
  Copyright &copy;&nbsp;2018 杨靖 All Rights Reserved
</footer>
<script type="text/javascript">
var vm=new Vue({
    el: ".main-content",
    data: {
      platform_imgs: [],
      category_imgs: []
    }
});

$.getJSON(getRootpath()+"/category/getCategory.do",{},function(json){
    vm.platform_imgs=json.platform_imgs;
    vm.category_imgs=json.category_imgs;
 });
</script>

</body>
</html>