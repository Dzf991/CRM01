<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/jquery-3.6.0.js"></script>
	<script>

		$(function (){
			var $loginAct = $("[name='loginAct']");
			var $loginPwd = $("[name='loginPwd']");
			var $msg = $("#msg");
			var $login = $("#login")
			// if ($loginAct.val() === ""){
			// 	alert("用户名不能为空！")
			// }
			$loginAct.focus();
			$loginAct.blur(function (){
				 if ($loginAct.val() === ""){
					$msg.html("<font color='red'>请输入用户名!</font>")
					$loginAct.focus();
				}else {
					$msg.html("")
				 }
			})
			$loginPwd.blur(function (){
				if ($loginPwd.val() === ""){
					$msg.html("<font color='red'>请输入密码!</font>")
					$loginPwd.focus();
				}else {
					$msg.html("")

				}
			})
		})
		function checkLogin() {
			var $loginAct = $("[name='loginAct']");
			var $loginPwd = $("[name='loginPwd']");
			var $msg = $("#msg");
			var $login = $("#login")
			if ($loginPwd.val() !== "" && $loginAct !== ""){
				// alert("11111");
				$.ajax({
					url:"login",
					type:"post",
					data:{loginAct:$loginAct.val(),loginPwd:$loginPwd.val()},
					dataType:"json",
					success:function (resp){
						if (resp.succeed === "true"){
							window.location.href = "workbench/index.jsp";
						}else {
							$msg.html("<font color='red'>"+resp.msg+"</font>");
						}
					}
				})
			}else {
					alert("请输入用户名或密码！");
			}

		}

	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form class="form-horizontal" role="form" id="login">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" name="loginAct">
					</div>

					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" name="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg"></span>
						
					</div>
					<button type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;" onclick="checkLogin()">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>