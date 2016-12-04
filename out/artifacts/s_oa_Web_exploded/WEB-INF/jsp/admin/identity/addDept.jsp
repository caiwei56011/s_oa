<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-添加部门</title>
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
				/**  添加部门，提交表单时进行前台校验 */
				$("#btn_submit").click(function(){
					// 对表单中所有字段做校验
					var name = $("#name");
					var msg = "";
					if ($.trim(name.val()) == ""){
						msg = "部门名称不能为空!";
						name.focus();
					}else if (name.val().length > 50){
						msg = "部门名称长度必须在50以内!";
						name.focus();
					}
					// 校验提示信息为空时,直接提交
					if (msg != ""){
						alert(msg);
					}else{
						$("#addDeptForm").submit();
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
		
		<form id="addDeptForm" name="addDeptForm" action="${path}/admin/identity/addDept.jspx" method="post">
			<!-- 防表单重复提交需要传入后台的参数 -->
			<s:token></s:token>
			
			<tr>
				<td>部门名称：</td>
				<td>
					<input type="text" name="dept.name" size="30" maxlength="50" id="name"/>
				</td>
			</tr>
			
			<tr>
				<td>部门备注：</td>
				<td>
					<textarea rows="5" cols="30" name="dept.remark"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
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