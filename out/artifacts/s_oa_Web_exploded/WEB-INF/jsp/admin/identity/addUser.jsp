<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-添加用户</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="fkjava.org" />
		<meta name="Copyright" content="All Rights Reserved." />
		<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				/** 异步加载表单中的部门和职位数据 */
				$.post("${path}/admin/identity/loadDeptAndJobAjax.jspx",function(data,status){
					if(status == "success"){
						/* {"depts": [{"id": 1, "name": "技术部"},{"id": 2, "name": "运营部"}],
						    "jobs" : [{"code": "0001", "name": "职员"},{"id": "0002", "name": "Java开发工程师"}]
						   }
						   后台：Map<String,List<Map<String,Object>>>
						*/
						//alert(data);
						$.each(data.depts, function(){
							$("<option />").val(this.id).text(this.name).appendTo("#deptSelect");
						});
						$.each(data.jobs, function(){
							$("<option />").val(this.code).text(this.name).appendTo("#jobSelect");
						});
					}else if(status == "error"){
						alert("部门和职位数据加载失败");
					}
				},"json");
				
				/** 异步请求校验登录名是否在数据库中已存在 */
				var isExists = false;
				$("#userId").blur(function(){
					$.post("${path}/admin/identity/loadUserIdAjax.jspx",{"userId": $("#userId").val()},
							function(data,status){
								if(status == "success"){
									//alert(data);
									if(data !=  null && data != ""){
										alert("登录名已存在");
										/** 点击提交按钮时如果还未修改登录名，再提示一次 */
										isExists = true;
									}
								}else if(status == "error"){
									alert("加载登录名数据失败");
								}
					},"json");
				});
				/** 登录名文本框获取焦点时，将isExists重置为false */
				$("#userId").focus(function(){
					isExists = false;
				});
				
				/**  添加用户，提交表单时进行前台校验 */
				$("#btn_submit").click(function(){
					// 对表单中所有字段做校验
					var userId = $("#userId");
					var name = $("#name");
					var passWord = $("#passWord");
					var repwd = $("#repwd");
					var email = $("#email");
					var tel = $("#tel");
					var phone = $("#phone");
					var qqNum = $("#qqNum");
					var answer = $("#answer");
					var msg = "";
					if ($.trim(userId.val()) == ""){
						msg = "用户登录名不能为空!";
						userId.focus();
					}else if (!/^\w{5,20}$/.test(userId.val())){
						msg = "用户登录名长度必须在5-20之间!";
						userId.focus();
					}else if (isExists){
						msg = "用户登录名已存在!";
					}else if ($.trim(name.val()) == ""){
						msg = "姓名不能为空!";
						name.focus();
					}else if ($.trim(passWord.val()) == ""){
						msg = "密码不能为空!";
						passWord.focus();
					}else if (!/^\w{6,20}$/.test(passWord.val())){
						msg = "密码长度必须为6-20之间!";
						passWord.focus();
					}else if (repwd.val() != passWord.val()){
						msg = "两次输入的密码不一致!";
						repwd.focus();
					}else if ($.trim(email.val()) == ""){
						msg = "邮箱不能为空!";
						email.focus();
					}else if (!/^\w+@\w{2,}\.\w{2,}$/.test(email.val())){
						msg = "邮箱格式不正确!";
						email.focus();
					}else if ($.trim(tel.val()) == ""){
						msg = "电话号码不能为空!";
						tel.focus();
					}else if (!/^0\d{2,3}-?\d{7,8}$/.test(tel.val())){
						msg = "电话号码格式不正确!";
						tel.focus();
					}else if ($.trim(phone.val()) == ""){
						msg = "手机号码不能为空!";
						phone.focus();
					}else if (!/^1[3|4|5|8]\d{9}$/.test(phone.val())){
						msg = "手机号码格式不正确!";
						phone.focus();
					}else if ($.trim(qqNum.val()) == ""){
						msg = "QQ号码不能为空!";
						qqNum.focus();
					}else if (!/^\d{5,12}$/.test(qqNum.val())){
						msg = "QQ号码长度必须在5-12之间!";
						qqNum.focus();
					}else if ($.trim(answer.val()) == ""){
						msg = "密保问题不能为空!";
						answer.focus();
					}
					// 校验提示信息为空时,直接提交
					if (msg != ""){
						alert(msg);
					}else{
						$("#addUserForm").submit();
					}
				});
			});
		</script>
	</head>
<body>
	<table align="center">
		
		<!-- 输防表单重复提交的提示信息 -->
		<s:actionerror cssStyle="font-size: 12px; color: red;"/>
		<!-- 后台校验失败的提示信息 -->
		<s:fielderror cssStyle="font-size: 12px; color: red;"/>
		
		<form id="addUserForm" name="addUserForm" action="${path}/admin/identity/addUser.jspx" method="post">
			<!-- 防表单重复提交需要传入后台的参数 -->
			<s:token></s:token>
			
			<tr><td colspan="4"></td></tr>
			<tr>
				<td>登&nbsp;录&nbsp;名：</td>
				<td>
					<input type="text" name="user.userId" size="18" value="" id="userId"/>
				</td>
				<td>用户姓名：</td>
				<td>
					<input type="text" name="user.name" size="18" maxlength="20" value="" id="name"/>
				</td>
			</tr>
			<tr>
				<td>用户密码：</td>
				<td>
					<input type="password" name="user.passWord" size="18" id="passWord"/>
				</td>
				<td>重输密码：</td>
				<td>
					<input type="password" name="repwd" size="18" id="repwd"/>
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<select name="user.sex" id="addUserForm_user_sex">
					    <option value="1">男</option>
					    <option value="2">女</option>
					</select>
				</td>
				<td>部&nbsp;&nbsp;门：</td>
				<td>
					<select id="deptSelect" name="user.dept.id"></select>
				</td>
			</tr>

			<tr>
				<td>职&nbsp;&nbsp;位：</td>
				<td>
					<select id="jobSelect" name="user.job.code"></select>
				</td>
				<td>邮&nbsp;&nbsp;箱：</td>
				<td>
					<input type="text" name="user.email" size="18" maxlength="50" value="" id="email"/>
				</td>
			</tr>
			<tr>
				<td>电&nbsp;&nbsp;话：</td>
				<td>
					<input type="text" name="user.tel" size="18" value="" id="tel"/>
				</td>
				<td>手&nbsp;&nbsp;机：</td>
				<td>
					<input type="text" name="user.phone" size="18" maxlength="11" value="" id="phone"/>
				</td>
			</tr>
			<tr>
				<td>QQ号码：&nbsp;</td>
				<td>
					<input type="text" name="user.qqNum" size="18" maxlength="20" value="" id="qqNum"/>
				</td>
				<td>问&nbsp;&nbsp;题：</td>
				<td>
					<select name="user.question" id="addUserForm_user_question">
    <option value="0">您的生日？</option>
    <option value="1">您父亲的姓名？</option>
    <option value="2">您的出生地？</option>
    <option value="3">您母亲的名字？</option>


</select>


				</td>
			</tr>
			<tr>
				<td>结&nbsp;&nbsp;果：</td>
				<td colspan="3">
					<input type="text" name="user.answer" size="48" maxlength="50" value="" id="answer"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input value="提 交" type="button" id="btn_submit" />
					&nbsp;&nbsp;
					<input value="重 置" type="reset" />&nbsp;
					<!-- 添加成功后的提示信息 -->
					<font color="red">${tip}</font>
				</td>
			</tr>
		</form>




	</table>
</body>
</html>	