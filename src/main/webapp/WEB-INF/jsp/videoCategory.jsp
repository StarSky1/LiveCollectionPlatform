<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>直播分类</title>
<meta charset="utf-8">
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
  <style type="text/css">
  	p {
  		text-align: center;
  		font-size: 18px;
  	}
  	img {
  		width: 100%;
  		height: 100%
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
  		margin-left: 250px;
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
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li>
          <form class="navbar-form">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="搜索游戏或主播">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
          </form>
        </li>
       <li><a href="/liveplatform">所有直播</a></li>
        <li><a href="category/showCategory.do">所有分类</a></li>
        <li><a href="#">我的主页</a></li>
        <li><a href="/liveplatform/login/showLogin.do">登录&nbsp;/&nbsp;</a></li>
        <li style="position: relative;left: -30px;"><a href="/liveplatform/login/showRegister.do">注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
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
					<img :src="'../res/cate_img/'+img.video_type_img"/>
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