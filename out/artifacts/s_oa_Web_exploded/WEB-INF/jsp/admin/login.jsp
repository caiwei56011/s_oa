<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>办公管理系统-登录页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${path }/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path }/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
	<style type="text/css">
		body{ 
			background-repeat: repeat; 
			background-positon: 100%, 100%; 
		} 
		li{
			margin-left:20px;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			/** 让登陆页面总是显示在最外层 */
			if(parent != window){
				parent.location = window.location;
			}
			
			/** 已登录用户在地址栏直接访问登录页面时，跳转到主页面，要求重新登录前必须先安全退出 */
			var loginUser = "${session_user}";
			if(loginUser != ""){
				window.location.href="${path}/admin/main.jspx";
			}
			
			/** 点击看不清按钮，修改验证码图片的src*/
			$("#see").click(function(){
				$("#vimg").attr("src","${path }/verify.action?random=" + Math.random());
			});
			/** 鼠标移动到验证码图片时变小手状，点击时切换验证码*/
			$("#vimg").mouseover(function(){
				$(this).css("cursor","pointer");
			}).click(function(){
				$("#see").trigger("click");
			});
			/** 点击登录按钮时进行表单的前台校验 */
			$("#login_id").click(function(){
				/** 用户名 */
				var userId = $("#userId");
				/** 密码 */
				var password = $("#password");
				/** 验证码 */
				var vcode = $("#vcode");
				/** 提示信息 */
				var msg = "";
				if($.trim(userId.val()) == ""){
					msg = "用户名不能为空";
					userId.focus();
				}else if(!/^\w{5,20}$/.test($.trim(userId.val()))){
					msg = "用户名格式不正确";
					userId.focus();
				}else if($.trim(password.val()) == ""){
					msg = "密码不能为空";
					password.focus();
				}else if(!/^\w{6,20}$/.test($.trim(password.val()))){
					msg = "密码格式不正确";
					password.focus();
				}else if($.trim(vcode.val()) == ""){
					msg = "验证码不能为空";
					vcode.focus();
				}else if(!/^[a-zA-Z0-9]{4}$/.test($.trim(vcode.val()))){
					msg = "验证码格式不正确";
					vcode.focus();
				}
				/** 判断提示信息是否为空，不为空则弹出提示，为空则异步提交表单进行登录*/
				if(msg != ""){
					alert(msg);
				}else{
					/** 将表单中的input元素name和value序列化为get格式字符串*/
					var params = $("#loginForm").serialize();
					//alert(params);
					/** ajax异步提交请求 */
					$.ajax({
						url: "${path}/admin/loginAjax.jspx",
						type: "post",
						data: params,
						dataType: "json",
						async: true,
						success: function(data){
							//alert(data); //{"tip":"登陆成功","status":0}
							if(data.status === 0){
								/** 如果登陆成功，则跳转到main.jspx*/
								window.location.href="${path}/admin/main.jspx";
							}else{
								/** 如果登陆失败,则提示用户，并切换验证码 */
								alert(data.tip);
								$("#see").trigger("click");
							}
							
						},
						error: function(){
							alert("数据加载失败");
						}
					});
				}
			});
			
			/** 用户按回车键时触发登录按钮 */
			$(document).keydown(function(event){
				if(event.keyCode == 13){
					$("#login_id").trigger("click");
				}
			});
			
			/** 找回密码按钮点击 */
			$("#findpwd").click(function(){
				$("#dialogDiv").dialog({
					title: "找回密码",
					width: 360,
					height: 260,
					collapsible: true,
					minimizable: true,
					maximizable: true,
					modal: true,
				});
				$("#iframe").attr("src","${path}/admin/showFindPwd.jspx").show();
			});
		});
		
	</script>
	
	
</head>
<body background="${path}/images/login/9.png">
	<div align="center" style="height:100%">
		<form id="loginForm">
			<table border="0" cellpadding="0" cellspacing="0" style="margin-top:120px;">
				<tr>
					<td colspan="2" width="447" height="104" style="background-image: url('${path}/images/login/1_01.jpg');"></td>
				</tr>
				<tr>
					<td width="92" height="200" style="background-image: url('${path}/images/login/1_02.gif');">&nbsp;</td>
					<td width="355" height="200" style="background-image: url('${path}/images/login/1_03.gif');">
						<table style="font-size:13px;margin:0 0 0 25px;" >
							<tr align="left">
								<td>用户名：</td>
								<td colspan="2">
									<input type="text" name="userId" style="width: 122px;" id="userId"/>
								</td>
								
							</tr>
							<tr align="left">
								<td>密&nbsp;&nbsp;码：</td>
								<td>
									<input type="password" name="password" style="width: 122px;" id="password"/>
								</td>
								<td>
									<a href="javascript:void(0)" id="findpwd" style="font-size:12px;color:white;">忘记密码?</a>
								</td>
							</tr>
						   <tr align="left">
								<td>验证码：</td>
								<td>
									<div style="float:left;">
										<input type="text" name="vcode" size="4" maxlength="4" id="vcode"/>
									</div>
									<div style="float:left;padding:0 0 0 5px;">
										<img src="${path }/verify.action" width="60" height="22" title="验证码" id="vimg"/>
									</div>
								</td>
								<td align="left">
									<a href="javascript:void(0);" id="see" style="font-size:12px;color:white;">看不清楚</a>
								</td>
						   </tr>
						   <tr align="left">
						   		<td>&nbsp;</td>
								<td colspan="2"><input type="checkbox" name="key" value="1" id="key"/>记住用户</td>
						   </tr>
						 
						  <tr align="center">
							<td colspan="3">
								<input type="button" value="登 录" width="67" height="22" id="login_id"/>&nbsp;
								<input type="reset" value="重 置"/>
							</td>
						  </tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" width="447" height="34" style="background-image: url('${path}/images/login/1_04.gif');"></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 找回密码弹出div -->
	<div id="dialogDiv" style="overflow: hidden;">
		<iframe id="iframe" width="100%" height="100%" style="display:none;" frameborder="0" ></iframe>
	</div>
</body>
</html>