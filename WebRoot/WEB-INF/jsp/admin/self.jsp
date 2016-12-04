<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				/** 异步加载部门与职位 */
				$.ajax({
					type : "post",
					url : "${path}/admin/identity/loadDeptAndJobAjax.jspx",
					dataType : "json",
					async : true,
					success : function(data){
						var deptId = "${session_user.dept.id}";
						$.each(data.depts, function(){
							$("<option/>").text(this.name)
										  .val(this.id)
										  .attr("selected", this.id == deptId)
										  .appendTo("#deptSelect");
						});
						var jobId = "${session_user.job.code}";
						$.each(data.jobs, function(){
							$("<option/>").text(this.name)
										  .val(this.code)
										  .attr("selected", this.code == jobId)
										  .appendTo("#jobSelect");
						});
					},
					error : function(){
						alert("网络加载失败！");
					}
				});
				
				/** 还原性别 */
				// select --> option
				/**$("#sex").find("option").each(function(){
					if (this.value == "${session_user.sex}"){
						$(this).attr("selected", true);
					}
				});*/
				//$("#sex > option[value=${session_user.sex}]").attr("selected", true);
				$("#sex").val("${session_user.sex}");
				/** 还原密保问题 */
				$("#question").val("${session_user.question}");
				
				
				/** 提交表单函数 */
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
					}else if (!/^\w+@\w{2,}.\w{2,}$/.test(email.val())){
						msg = "邮箱格式不正确!";
						email.focus();
					}else if ($.trim(tel.val()) == ""){
						msg += "电话号码不能为空!";
						tel.focus();
					}else if (!/^0\d{2,3}-?\d{7,8}$/.test(tel.val())){
						msg = "电话号码格式不正确!";
						tel.focus();
					}else if ($.trim(phone.val()) == ""){
						msg += "手机号码不能为空!";
						phone.focus();
					}else if (!/^1[3|5|8]\d{9}$/.test(phone.val())){
						msg = "手机号码格式不正确!";
						phone.focus();
					}else if ($.trim(qqNum.val()) == ""){
						msg = "QQ号码不能为空!";
						qqNum.focus();
					}else if (!/^\d{5,11}$/.test(qqNum.val())){
						msg = "QQ号码长度必须在5-11之间!";
						qqNum.focus();
					}else if ($.trim(answer.val()) == ""){
						msg = "密保问题不能为空!";
						answer.focus();
					}
					// 直接提交
					if (msg != ""){
						alert(msg);
					}else{
						$("#updateSelfForm").submit();
					}
				});
			});
			/** 当前登录用户信息修改成功，刷新head.jsp */
			if ("${tip}" == "修改成功"){
				// 刷新head.jsp
				// parent --> tab.jsp
				// parent.parent --> main.jsp
				parent.parent.topframe.location.reload();
			}
			
		
		</script>
	</head>
<body>
	<br/>
	<table align="center" class="editTable" cellpadding="8" cellspacing="1">
		<!-- 输防表单重复提交的提示信息 -->
		<s:actionerror cssStyle="font-size: 12px; color: red;"/>
		<!-- 后台校验失败的提示信息 -->
		<s:fielderror cssStyle="font-size: 12px; color: red;"/>
		<tbody style="background-color: #FFFFFF;">
			<s:form action="/admin/updateSelf.jspx" method="post" id="updateSelfForm" theme="simple">
				<s:token></s:token>
				<tr>
					<td>
						登&nbsp;录&nbsp;名：
						<input type="text" value="${session_user.userId}" disabled="disabled" size="18"/>
					</td>
					<td width="75%">
						用户姓名：
						<input type="text" id="name" name="user.name" value="${session_user.name}" size="18" maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td>
						性&nbsp;&nbsp;&nbsp;&nbsp;别：
						<s:select name="user.sex" id="sex" list="#{1:'男',2:'女'}"/>
					</td>
					<td>
						部&nbsp;&nbsp;&nbsp;&nbsp;门：
						<select id="deptSelect" name="user.dept.id" disabled="disabled"></select>
					</td>
				</tr>
	
				<tr>
					<td>
						职&nbsp;&nbsp;&nbsp;&nbsp;位：
						<select id="jobSelect" name="user.job.code" disabled="disabled"></select>
					</td>
					<td>
						邮&nbsp;&nbsp;&nbsp;&nbsp;箱：
						<input type="text" id="email" name="user.email" size="18" value="${session_user.email}"
							maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td>
						电&nbsp;&nbsp;&nbsp;&nbsp;话：
						<input type="text"  id="tel" name="user.tel" size="18" value="${session_user.tel}"/>
					</td>
					<td>
						手&nbsp;&nbsp;&nbsp;&nbsp;机：
						<input type="text" id="phone" name="user.phone" size="18" maxlength="11" value="${session_user.phone}"/>
					</td>
				</tr>
				<tr>
					<td>
						Q&nbsp;Q号&nbsp;码：
						<input type="text" id="qqNum" name="user.qqNum" size="18" maxlength="20" value="${session_user.qqNum}"/>
					</td>
					<td>
						问&nbsp;&nbsp;&nbsp;&nbsp;题：
						<s:select name="user.question"
							list="#{0:'您的生日？',1:'您父亲的姓名？',2:'您的出生地？',3:'您母亲的名字？'}"  id="question"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						结&nbsp;&nbsp;&nbsp;&nbsp;果：
						<input type="text" id="answer" name="user.answer" size="60" maxlength="50" value="${session_user.answer}"/>
					</td>
				</tr>
				<tr class="editFooterTr">
					<td colspan="2" align="left">
						<input value="提 交" type="button" id="btn_submit" />
						&nbsp;<input value="重 置" type="reset" />
						&nbsp;<span style="color:red;">${tip}</span>
					</td>
				</tr>
			</s:form>
		</tbody>
	</table>
	<img src="${path}/barcode.jspx" align="right" style="padding-right: 10px;"/>
</body>
</html>

	
	