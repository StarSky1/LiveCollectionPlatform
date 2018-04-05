<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>直播列表</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="../bootstrap-3.3.7-dist/simple-sidebar.css">
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
        <li><a href="#">上传直播源</a></li>
        <li><a href="#">我的主页</a></li>
        <li><a href="#">登录/注册</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div class="main-header">
	<i class="header-line"></i>
	<h3 class="header-title-firstname">${platform}</h3>
	<h3 class="header-title-secondname">${cateName}</h3>
</div>
<div class="main-content container-fluid">
      <div id="liveList" class="liveList row">
        <a  v-for="video in videos" :href="video.video_room_url" target="_blank" class="col-lg-3 col-md-4 col-sm-6 ellipsis ">
        <img :src="video.video_img" onerror="this.onerror=&quot;&quot;;this.src=&quot;./res/404.jpg&quot;">
        <img :src="'../res/platform_img/'+video.video_platform_img" class="platform-icon">
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
      <nav aria-label="Page navigation" class="col-md-6 col-md-offset-2 Page">
        <ul class="pagination pagination-lg">
          <li class="disabled prePage">
            <a href="#" aria-label="Previous" @click.prevent="prePage">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li v-for="item in pages" :class="{active : item.isActive}">
            <a v-if="item.display==undefined" href="#" class="pagebtn"  @click.prevent="turnPage">{{ item.pageno }}</a>
            <a v-else>···</a>
          </li>
          <li class="nextPage">
            <a href="#liveList" aria-label="Next" @click.prevent="nextPage">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
      <form class="form-inline col-md-3">
          <div class="form-group">
            <label for="InputPage">第</label>
            <input type="text" class="form-control" id="InputPage" placeholder="几">
            <label>页</label>
          </div>
          <button type="submit" class="btn btn-success" @click.prevent="turnPage">go</button>
      </form>
    </div>
</div>



<!-- <div class="opacity"></div>
<div class="login">123</div> -->
<footer>
  Copyright &copy;&nbsp;2018 杨靖 All Rights Reserved
</footer>
<script>
  var start,end;
  var vm=new Vue({
    el: ".main-content",
    data: {
      videos: [],
      total: 0,
      pagesize: 0,
      pageno: 1,
    },
    computed: {
      // 计算属性的 getter
      pages: function () {
        // `this` 指向 vm 实例
        var pages=[];
        var total_page=Math.ceil(this.total / this.pagesize);
        var show_size=10;
        if(total_page<show_size){
          start=1;
          end=total_page;
        }else{
           start=1;
           end=10;
        }
        if(total_page>show_size && this.pageno>=end){
          start=Math.floor(this.pageno/10)*10-1;
          end=start+show_size+1;
          pages.push({pageno: 1});   //始终显示第一页
          pages.push({display: false}); //在start前显示省略号
        }
        for(var i=start;i<=end;i++){
          var obj={};
          obj["pageno"]=i;
          obj["isActive"]=false;
          if(i==this.pageno){
            obj["isActive"]=true;
          }
          pages.push(obj);
        }
        if(total_page>show_size && end<total_page-2){
          pages.splice(end,0,{display: false});
          for(var i=total_page-2;i<total_page;i++){
            pages.push({pageno: i});
          }
          pages.push({pageno: total_page});  //始终显示最后一页
        }
        if(total_page>show_size && end>=total_page-2 && end<total_page){
          for(var i=end+1;i<=total_page;i++){
            pages.push({pageno: i});
          }
        }
        if(end>=total_page){
          pages.splice(total_page-start+3,end-total_page);
        }
        return pages;
      }
    },
    methods: {
      turnPage: function(event){
        if(event.target.tagName.toLowerCase()=="button"){
          var pageno=parseInt($("#InputPage").val());
        }else{
          var pageno=parseInt(event.target.innerText);
        }
        var total_page=Math.ceil(this.total / this.pagesize);
        if(pageno>total_page || pageno<1){
          return;
        }
        this.pageno=pageno;
        if(this.pageno==1){
          $(".prePage").addClass("disabled");
          $(".nextPage").removeClass("disabled");
        }else if(this.pageno==total_page){
          $(".nextPage").addClass("disabled");
          $(".prePage").removeClass("disabled");
        }else{
          $(".prePage").removeClass("disabled");
          $(".nextPage").removeClass("disabled");
        }
        getVideolist(vm,this.pageno,null,null);
      },
      prePage: function(event){
        var current=this.pageno-1;
        if(current==1){
          $(".prePage").addClass("disabled");
        }
        $(".nextPage").removeClass("disabled");
        this.pageno=current;
        getVideolist(this,current,null,null);
      },
      nextPage: function(event){
        var current=this.pageno+1;
        var total_page=Math.ceil(this.total / this.pagesize);
        if(this.pageno==total_page){
          $(".nextPage").addClass("disabled");
        }
        $(".prePage").removeClass("disabled");
        this.pageno=current;
        getVideolist(this,current,null,null);
      }
    }
  });
  var cateName="${cateName}";
  var platform="${platform}";
  getVideolist(vm,1,cateName,platform);
</script>
<!-- <script>
  var opacity=document.getElementsByClassName("opacity")[0];
  opacity.style.width=document.body.clientWidth+"px";
  opacity.style.height=document.body.clientHeight+"px";
</script> -->
</body>
</html>
