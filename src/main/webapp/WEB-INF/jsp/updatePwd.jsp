<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改密码</title>
	<%@ include file="../common/head.jsp"%>
  <style type="text/css">
  	.backImg {
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
  </style>
</head>
<body>
<%@ include file="../common/topBar.jsp"%>
<div class="main-content container-fluid">
	<div class="col-md-8">
		<img class="backImg" alt="图片" src="../res/img.jpg">
	</div>
	<div class="col-md-4 login-wrapper">
		<form class="well">
		  <h3 class="login-title">
		  	修改密码&nbsp;
		  </h3>
		  <div class="form-group">
		    <label for="InputEmail">用户名</label>
		    <p>${currentUser.userName}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">原密码</label>
		    <input type="password" v-model="originalPwd" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="originalPwdError" style="color: red;">{{ originalPwdMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword">新密码</label>
		    <input type="password" v-model="password" class="form-control" id="InputPassword" placeholder="请输入密码">
		  	<p v-show="passwordError" style="color: red;">{{ passwordMessage }}</p>
		  </div>
		  <div class="form-group">
		    <label for="InputPassword1">确认新密码</label>
		    <input type="password" v-model="password1" class="form-control" id="InputPassword1" placeholder="请确认密码">
		  	<p v-show="password1Error" style="color: red;">{{ password1Message }}</p>
		  </div>
		  <button type="submit"  @click.prevent="update()" class="btn btn-info loginBtn">确认修改</button>
		</form>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>

<script type="text/javascript">
	var vm=new Vue({
	    el: ".main-content",
	    data: {
	      checked: false,
	      originalPwd: "",
	      password: "",
	      password1: "",
	      first: true,
	      originalPwdMessage: "",
	      passwordMessage: "",
	      password1Message: ""
		},
		computed: {
		    // 计算属性的 getter
		    originalPwdError: function(){
		    	if(this.first) return false;
		    	if(this.originalPwd.trim()==""){
					this.originalPwdMessage="密码不能为空"
					return true;
				}
		    	if(this.originalPwd.length<6 || this.originalPwd.length>12){
		    		this.originalPwdMessage="密码长度只能是6到12位"
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
		    },
		    password1Error: function(){
		    	if(this.first) return false;
		    	if(this.password1.trim()==""){
					this.password1Message="确认密码不能为空"
					return true;
				}
		    	if(this.password1!=this.password){
		    		this.password1Message="两次密码不一致"
					return true;
		    	}
		    	return false;
		    }
		 },
		methods: {
			update: function(){
				this.first=false;
				if(this.originalPwdError || this.passwordError || this.password1Error) return;
				$.getJSON(getRootpath()+"/user/updatePwd.do",{originalPwd: vm.originalPwd,password: vm.password},function(json){
				    if(json.item){
				    	swal("提示！", json.item, "error");
				    }else{
				    	if(!json.status){ swal("提示！", "修改密码错误", "error");  }
					    else{
					    	swal("提示！", "修改密码成功", "success");
					    }
				    }
					
				 });
			}
		} 
	});
</script>
</body>
</html>