<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-添加联系人</title>
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
		<script language="javascript" type="text/javascript" src="${path}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				
				/**  添加联系人，提交表单时进行前台校验 */
				$("#btn_submit").click(function(){
					// 对表单中所有字段做校验
					var name = $("#name");
					var phone = $("#phone");
					var email = $("#email");
					var qqNum = $("#qqNum");
					var birthday = $("#birthday");
					var msg = "";
					if ($.trim(name.val()) == ""){
						msg = "姓名不能为空!";
						name.focus();
					}else if ($.trim(phone.val()) == ""){
						msg = "手机号码不能为空!";
						phone.focus();
					}else if (!/^1[3|4|5|8]\d{9}$/.test(phone.val())){
						msg = "手机号码格式不正确!";
						phone.focus();
					}else if ($.trim(email.val()) == ""){
						msg = "邮箱不能为空!";
						email.focus();
					}else if (!/^\w+@\w{2,}\.\w{2,}$/.test(email.val())){
						msg = "邮箱格式不正确!";
						email.focus();
					}else if ($.trim(qqNum.val()) == ""){
						msg = "QQ号码不能为空!";
						qqNum.focus();
					}else if (!/^\d{5,11}$/.test(qqNum.val())){
						msg = "QQ号码长度必须在5-11之间!";
						qqNum.focus();
					}else if ($.trim(birthday.val()) == ""){
						msg = "生日不能为空!";
						qqNum.focus();
					}
					// 校验提示信息为空时,直接提交
					if (msg != ""){
						alert(msg);
					}else{
						$("#addContactForm").submit();
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
		
		<s:form id="addContactForm" name="addContactForm" action="/admin/addressbook/addContact.jspx" method="post" theme="simple">
			<!-- 防表单重复提交需要传入后台的参数 -->
			<s:token></s:token>
			<!-- 隐藏域保存联系人的联系组id -->
			<s:hidden name="contact.contactGroup.id"></s:hidden>
			<tr>
				<td>联系人姓名：</td>
				<td>
					<input type="text" name="contact.name" size="18" maxlength="20" value="" id="name"/>
				</td>
			</tr>
			
			<tr>
				<td>性别：</td>
				<td>
					<select name="contact.sex" id="addContactForm_contact_sex" style="width:50px">
					    <option value="1">男</option>
					    <option value="2">女</option>
					</select>
				</td>
			
			</tr>
			<tr>
				<td>手机号码：</td>
				<td>
					<input type="text" name="contact.phone" size="18" maxlength="11" value="" id="phone"/>
				</td>
			</tr>
			<tr>
				<td>邮箱 ：&nbsp;</td>
				<td>
					<input type="text" name="contact.email" size="18" maxlength="20" value="" id="email"/>
				</td>
			</tr>
			<tr>
				<td>QQ号码：&nbsp;</td>
				<td>
					<input type="text" name="contact.qqNum" size="18" maxlength="20" value="" id="qqNum"/>
				</td>
			</tr>
			<tr>
				<td>生日：&nbsp;</td>
				<td>
					<input type="text" class="Wdate" onClick="WdatePicker({readOnly:true,realDateFmt:'yyyy-MM-dd'})" name="contact.birthday" size="18" maxlength="20" id="birthday"/>
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
		</s:form>

	</table>
</body>
</html>	