<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-修改用户</title>
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
				$.post("${path}/admin/identity/loadDeptAndJob.jspx",function(data,status){
					if(status == "success"){
						/* {"depts": [{"id": 1, "name": "技术部"},{"id": 2, "name": "运营部"}],
						    "jobs" : [{"code": "0001", "name": "职员"},{"id": "0002", "name": "Java开发工程师"}]
						   }
						   后台：Map<String,List<Map<String,Object>>>
						*/
						//alert(data);
						$.each(data.depts, function(){
							$("<option />").val(this.id).text(this.name).attr("selected",this.id == ${user.dept.id})
								.appendTo("#deptSelect");
						});
						$.each(data.jobs, function(){
							$("<option />").val(this.code).text(this.name).attr("selected",this.code == ${user.job.code})
								.appendTo("#jobSelect");
						});
					}else if(status == "error"){
						alert("部门和职位数据加载失败");
					}
				},"json");
				
				
				/**  修改用户，提交表单时进行前台校验 */
				$("#btn_submit").click(function(){
					// 对表单中所有字段做校验
					var name = $("#name");
					var email = $("#email");
					var tel = $("#tel");
					var phone = $("#phone");
					var qqNum = $("#qqNum");
					var answer = $("#answer");
					var msg = "";
					
					if ($.trim(name.val()) == ""){
						msg = "姓名不能为空!";
						name.focus();
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
						$("#updateUserForm").submit();
					}
				});
			});
		</script>
	</head>
<body>
	<table align="center">
		
		<!-- 输出防表单重复提交的提示信息 -->
		<s:actionerror cssStyle="font-size: 12px; color: red;"/>
		<!-- 后台校验失败的提示信息 -->
		<s:fielderror cssStyle="font-size: 12px; color: red;"/>
		
		<s:form id="updateUserForm" name="updateUserForm" action="/admin/identity/updateUser.jspx" method="post" theme="simple">
			<!-- 防表单重复提交需要传入后台的参数 -->
			<s:token></s:token>
			<!-- 隐藏域保存user.userId参数 -->
			<s:hidden name="user.userId"></s:hidden>
			<tr><td colspan="4"></td></tr>
			<tr>
				<td>登&nbsp;录&nbsp;名：</td>
				<td>
					<!--disabled="true"会导致参数传入action失败,需要另外用一个隐藏域 -->
					<s:textfield value="%{user.userId}" size="18" id="userId" disabled="true" />
				</td>
				<td>用户姓名：</td>
				<td>
					<s:textfield name="user.name" size="18" maxlength="20" id="name" />
				</td>
			</tr>
			
			<tr>
				<td>性别：</td>
				<td>
					<s:select name="user.sex" list="#{1: '男', 2: '女'}"></s:select>
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
					<s:textfield name="user.email" size="18" maxlength="50" id="email" />
				</td>
			</tr>
			<tr>
				<td>电&nbsp;&nbsp;话：</td>
				<td>
					<s:textfield name="user.tel" size="18" id="tel" />
				</td>
				<td>手&nbsp;&nbsp;机：</td>
				<td>
					<s:textfield name="user.phone" size="18" maxlength="11" id="phone" />
				</td>
			</tr>
			<tr>
				<td>QQ号码：&nbsp;</td>
				<td>
					<s:textfield name="user.qqNum" size="18" maxlength="11" id="qqNum" />
				</td>
				<td>问&nbsp;&nbsp;题：</td>
				<td>
					<s:select name="user.question" list="#{0: '您的生日？', 1: '您父亲的姓名？', 2: '您的出生地？' ,3: '您母亲的名字？'}" id="updateUserForm_user_question"/>
				</td>
			</tr>
			<tr>
				<td>结&nbsp;&nbsp;果：</td>
				<td colspan="3">
					<s:textarea name="user.answer" cols="50" id="answer" />
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input value="提 交" type="button" id="btn_submit" />
					&nbsp;&nbsp;
					<input value="重 置" type="reset" />&nbsp;
					<!-- 修改成功后的提示信息 -->
					<font color="red">${tip}</font>
				</td>
			</tr>
		</s:form>




	</table>
</body>
</html>	