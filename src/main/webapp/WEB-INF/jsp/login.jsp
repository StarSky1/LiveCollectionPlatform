<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>登录YangTV</title>
    <%@ include file="../common/head.jsp"%>
  <style type="text/css">
  	img {
  		width: 440px;
  		height: 520px;
  		margin-bottom: 20px;
  		margin-top: 20px;
  		margin-left: 200px;
  	}
  	body {
  		padding-top: 0px;
  	}
  	.login-wrapper {
  		margin-left: -200px;
    	margin-top: 150px;
  	}
  	.loginBtn {
  		width: 150px;
    	margin-left: 100px;
  	}
  	.login-title {
  		width: 100%;
	  	text-align: center;
	    margin-top: -5px;
	}
	  .main-content {
		  margin-top: 50px;
	  }
  </style>
</head>
<body>
<%@ include file="../common/topBar.jsp"%>
<div class="main-content container-fluid">
	<div class="col-md-8">
		<img alt="图片" src="../res/img.jpg">
	</div>
	<div class="col-md-4 login-wrapper">
		<form class="well">
		  <h3 class="login-title">
		  	登录&nbsp;YangTV
		  </h3>
		  <div class="form-group">
		    <label for="InputEmail">用户名</label>
		    <input type="text" v-model="username" class="form-control" id="InputEmail" placeholder="请输入用户名">
		    <p v-show="usernameError" style="color: red;">{{ usernameMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">密码</label>
		    <input type="password" v-model="password" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="passwordError" style="color: red;">{{ passwordMessage }}</p>
		  </div>
		  <div class="checkbox">
		    <label>
		      <input v-model="checked"  type="checkbox"> 记住我
		    </label>
		  </div>
		  <button type="submit"  @click.prevent="login()" class="btn btn-info loginBtn">登录</button>
		</form>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>

<script type="text/javascript">
	var vm=new Vue({
	    el: ".main-content",
	    data: {
	      checked: false,
	      username: "",
	      password: "",
	      first: true,
	      usernameMessage: "",
	      passwordMessage: ""
		},
		computed: {
		    // 计算属性的 getter
		    usernameError: function () {
		      // `this` 指向 vm 实例
		      if(this.first) return false;
		      if(this.username.trim()==""){
		    		this.usernameMessage="用户名不能为空";
		    		return true;
				}
		        if(this.username.length<6){
		        	this.usernameMessage="用户名不能小于6位";
		    		return true;
		        }
		        if(this.username.length>12){
		        	this.usernameMessage="用户名不能大于12位";
		    		return true;
		        }
		        var reg = new RegExp("^[a-zA-Z0-9_]+$"); 
		        if(!reg.test(this.username)){
		        	this.usernameMessage="用户名只能包含大写、小写、数字和下划线";
		    		return true;
		        }
		      	return false;
		    },
		    passwordError: function(){
		    	if(this.first) return false;
		    	if(this.password.trim()==""){
					this.passwordMessage="密码不能为空"
					return true;
				}
		    	if(this.password.length<6 || this.password.length>12){
		    		this.passwordMessage="密码长度只能是6到12位"
					return true;
		    	}
		    	return false;
		    }
		 },
		methods: {
			login: function(){
				this.first=false;
				if(this.usernameError || this.passwordError) return;
				if(vm.checked==''){
				    vm.checked=false;
                }
				$.getJSON(getRootpath()+"/login/login.do",{username: vm.username,password: vm.password,remember: vm.checked},function(json){
				    if(!json.status){ swal("提示！", "用户名或密码错误", "error");  }
				    else{
				    	window.location.href='/';
				    }
				 });
			}
		} 
	});
	vm.username="${username}";
	vm.password="${password}";
	vm.checked="${checked}";
</script>
</body>
</html>