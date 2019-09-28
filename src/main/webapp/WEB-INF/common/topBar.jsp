<%@ page  language="java" pageEncoding="UTF-8" %>

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
                <li v-if="showSearch">
                    <form class="navbar-form">
                        <div class="form-group">
                            <input type="text" v-model="searchWord" class="form-control" placeholder="搜索房间或主播">
                        </div>
                        <button type="submit" @click.prevent="search()" class="btn btn-default">搜索</button>
                    </form>
                </li>
                <li><a href="/">所有直播</a></li>
                <li><a href="/category/showCategory.do">所有分类</a></li>
                <li><a @click.prevent="myprofile()" href="#">我的主页</a></li>
                <li><a @click.prevent="mycare()" href="#">我的关注</a></li>
                <li v-if="logined">
                    <img class="userimg" src="/res/user_head_img/${currentUser.userHeadImg}">
                </li>
                <li v-if="logined">
                    <span class="user_dropdown glyphicon glyphicon-chevron-down dropdown-toggle" data-toggle="dropdown"></span>
                    <ul class="dropdown-menu">
                        <li><a href="/user/showProfile.do" target="_blank"><i class="glyphicon glyphicon-home"></i> 我的主页</a></li>
                        <li><a id="changePassword" href="/user/showUpdatePwd.do" ><i class="glyphicon glyphicon-wrench"></i> 修改密码</a></li>
                        <li>
                            <a class="checkin-btn" @click.prevent="signIn()" href="#" action="ajaxData"><i class="glyphicon glyphicon-ok-sign"></i> 签到</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a @click.prevent="quit()" href="#"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
                    </ul>
                </li>
                <li v-if="!logined"><a href="/login/showLogin.do">登录&nbsp;/&nbsp;</a></li>
                <li v-if="!logined" style="position: relative;left: -30px;"><a href="/login/showRegister.do">注册</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<script>
    var vm1=new Vue({
        el: "nav",
        data: {
            logined: false,
            searchWord: "",
            showSearch: false
        },
        methods: {
            search: function(){
                getVideolist(vm,page_vm,vm1.logined,1,vm1.searchWord,null,null);
            },
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
                    }
                });
            },
            quit: function(){
                $.getJSON(getRootpath()+"/login/quit.do",{},function(json){
                    if(!json.status){ swal("提示！", "退出登录失败", "error");  }
                    else{
                        swal("提示！", "退出成功", "success");
                        window.location.href='http://localhost:8080';
                    }
                });
            }
        }
    });

    vm1.logined=Boolean("${logined}");
    vm1.showSearch=Boolean("${showSearch}");
</script>
