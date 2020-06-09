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
  		margin-left: auto;
  		margin-right: auto;
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
        max-width: 280px;
        height: 350px;
        /*display: block;*/
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
					<a :href="'showVideos.do?platform='+platform.videoPlatform">
						<img :src="'../res/platform_img/'+platform.videoPlatformImg"/>
						<p>{{ platform.videoPlatform }}</p>
					</a>
				</div>
			</div>
		</div>
		<div class="col-md-10 col-md-offset-2">
				<ul class="row cate_list">
					<li class="col-md-3 cate_item" v-for="img in category_imgs">
						<a :href="'showVideos.do?cateName='+img.videoType">
							<img  class="videoTypeImg" :src="'../res/cate_img/'+img.videoTypeImg"/>
							<div  class="cate_title">{{ img.videoType }}</div>
						</a>
					</li>
				</ul>
		</div>
</div>

<%@ include file="../common/footer.jsp"%>

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