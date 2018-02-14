<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>首页</title>
</head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.css">
  <link rel="stylesheet" href="./css/main.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  <script type="text/javascript" src="./js/jQuery1.12.4.js"></script>
  <script type="text/javascript" src="./bootstrap-3.3.7-dist/js/bootstrap.js"></script>
  <script type="text/javascript" src="./js/vue.js"></script>
  <script type="text/javascript" src="./js/main.js"></script>
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
        <li><a href="#">所有直播</a></li>
        <li><a href="#">所有分类</a></li>
        <li><a href="#">上传直播源</a></li>
        <li><a href="#">我的主页</a></li>
        <li><a href="#">登录/注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="main-content container-fluid">
    <div class="row  suggestion">
      <!-- 推荐直播间 -->
      <div class="col-md-3  col-sm-6 col-xs-6 suggestion_list">
        <ul>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
          <li><a href="#"><img src="https://i.h2.pdim.gs/cb9bab1140b9cff9afbcbfc643560d1c.webp" alt="" class="suggested_LiveRoom"></a></li>
        </ul>
      </div>
      <div class="col-md-7 col-sm-6 col-xs-6 well suggested_video">
          <video  src="./video/b.mp4"  controls="controls">
            您的浏览器不支持 video 标签。
          </video>
      </div>
      <div class="col-md-1"></div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-offset-3 col-sm-6 col-xs-6 well">
        <!-- 轮播图片 -->
        <div id="carousel-pictures" class="carousel slide" data-ride="carousel">
          <!-- 图片指示器 -->
          <ol class="carousel-indicators">
            <li data-target="#carousel-pictures" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-pictures" data-slide-to="1"></li>
            <li data-target="#carousel-pictures" data-slide-to="2"></li>
          </ol>

          <!-- 幻灯片的wrapper -->
          <div class="carousel-inner" role="listbox">
            <div class="item active">
              <img src="./assets/1.jpg" class="carousel-img" alt="图片一">
              <div class="carousel-caption">
                <h3>图片一</h3>
              </div>
            </div>
            <div class="item">
              <img src="./assets/2.jpg" class="carousel-img" alt="图片二">
              <div class="carousel-caption">
                <h3>图片二</h3>
              </div>
            </div>
            <div class="item">
              <img src="./assets/3.jpg" class="carousel-img" alt="图片三">
              <div class="carousel-caption">
                <h3>图片三</h3>
              </div>
            </div>
          </div>

          <!-- Controls -->
          <a class="left carousel-control" href="#carousel-pictures" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">上一张</span>
          </a>
          <a class="right carousel-control" href="#carousel-pictures" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">下一张</span>
          </a>
        </div>
      </div>
    </div>
    <div class="row liveList">
      <a  v-for="video in videos" :href="video.video_room_url" target="_blank" class="col-lg-3 col-md-4 col-sm-6">
        <img :src="video.video_img" onerror="this.onerror=&quot;&quot;;this.src=&quot;./res/404.jpg&quot;">
        <img :src="video.video_platform_img" class="platform-icon">
        <h5>&nbsp;{{ video.video_title }}</h5>
        <div class="row">
          <div class="col-sm-7">
            <span class="glyphicon glyphicon-user"></span>&nbsp;{{ video.video_host_nickname }}
          </div>
          <div class="col-sm-5 text-right">
            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;{{ video.video_number }}
          </div>
        </div>
      </a>
    </div>
    <div class="row">
      <nav aria-label="Page navigation">
        <ul class="pagination pagination-lg">
          <li class="disabled">
            <a href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li v-for="page in pages">
            <a href="#" class="pagebtn" @click.prevent="turnPage">{{ page }}</a>
          </li>
          <li>
            <a href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
</div>



<!-- <div class="opacity"></div>
<div class="login">123</div> -->
<footer>
  Copyright &copy;&nbsp;2018 杨靖 All Rights Reserved
</footer>
<script>
  var vm=new Vue({
    el: ".main-content",
    data: {
      videos: [],
      total: 0
    },
    computed: {
      // 计算属性的 getter
      pages: function () {
        // `this` 指向 vm 实例
        var pages=[];
        var total=this.total%100!=0?(parseInt(this.total/100)+1):this.total/100;
        for(var i=1;i<=total;i++){
          pages.push(i);
        }
        return pages;
      }
    },
    methods: {
      turnPage: function(event){
        var aDom=event.target;
        var li=aDom.parentElement;
        var pageno=aDom.innerText;
        if(parseInt(pageno)==1){
          $(li.previousSibling).removeClass('disabled');
        }else if(parseInt(pageno)==this.pages.length){
          li.nextSibling.setAttribute('class', 'disabled');
        }
        $(".pagebtn").each(function(){
          $(this).removeClass("active");
        });
        aDom.setAttribute('class', 'active');
        $.getJSON(getRootpath()+"/home/videolist.do",{pageno: pageno,pagesize: 100},function(json){
           vm.videos=json.videos;
           vm.total=json.total;
        });
      }
    }
  });
  $.getJSON(getRootpath()+"/home/videolist.do",{pageno: 1,pagesize: 100},function(json){
   vm.videos=json.videos;
   vm.total=json.total;
});
</script>
<!-- <script>
  var opacity=document.getElementsByClassName("opacity")[0];
  opacity.style.width=document.body.clientWidth+"px";
  opacity.style.height=document.body.clientHeight+"px";
</script> -->
</body>
</html>
