<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-用户请假</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="fkjava.org" />
		<meta name="Copyright" content="All Rights Reserved." />
		<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${path}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				/** 异步加载假期类型与流程定义 */
				$.ajax({
					url : "${path}/admin/leave/loadLeaveTypeAndProcessAjax.jspx",
					type : "post",
					dataType : "json",
					async : true,
					success : function(data){
						// data : {"" : [{},{}], "" : [{},{}]} : Map<String, List<>>
						// data : [[{},{}],[{},{}]] List<List<Map>>
						/** 填充假期类型 */
						$.each(data[0], function(){
							// this: {code=001, name=年假}
							$("<option/>").val(this.code).text(this.name).appendTo("#leaveTypeSelect");
						});
						/** 填充流程定义 */
						$.each(data[1], function(){
							// this: {id=001, name=年假}
							$("<option/>").val(this.id).text(this.name).appendTo("#processSelect");
						});
					},
					error : function(){
						alert("数据加载失败！");
					}
				});
			
				$("#btn_submit").on("click", function(){
					var beginDate = $("#beginDate");
					var endDate = $("#endDate");
					var leaveHour = $("#leaveHour");
					var leaveCase = $("#leaveCase");
					var msg = "";
					if ($.trim(beginDate.val()) == ""){
						msg = "开始时间不能为空！";
						beginDate.focus();
					}else if ($.trim(endDate.val()) == ""){
						msg = "结束时间不能为空！";
						endDate.focus();
					}else if (beginDate.val() > endDate.val()){
						msg = "开始时间不能大于结束时间！";
					}else if ($.trim(leaveHour.val()) == ""){
						msg = "请假小时数不能为空！";
						leaveHour.focus();
					}else if (!/^\d+\.?\d*$/.test($.trim(leaveHour.val()))){
						msg = "请假小时数格式不正确！";
						leaveHour.focus();
					}else if ($.trim(leaveCase.val()) == ""){
						msg = "请假原因不能为空！";
						leaveCase.focus();
					}
					
					if (msg != ""){
						alert(msg);
					}else{
						$("#addLeaveItemForm").submit();					
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
		
		<s:form id="addLeaveItemForm" name="addLeaveItemForm" action="/admin/leave/addLeaveItem.jspx" method="post" theme="simple">
			<!-- 防表单重复提交需要传入后台的参数 -->
			<s:token></s:token>
			<tr>
				<td>假期类型：</td>
				<td>
					<select id="leaveTypeSelect" name="leaveItem.leaveType.code"></select>
				</td>
			</tr>
			<tr>
				<td>开始时间：</td>
				<td>
					<s:textfield name="leaveItem.beginDate" size="18"  id="beginDate"
						cssClass="Wdate" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				</td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td>
					<s:textfield name="leaveItem.endDate" size="18" id="endDate"
						cssClass="Wdate" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				</td>
			</tr>
			<tr>
				<td>请假小时：</td>
				<td>
					<s:textfield name="leaveItem.leaveHour" size="18" id="leaveHour"/>
				</td>
			</tr>
			<tr>
				<td>选择流程：</td>
				<td>
					<select id="processSelect" name="leaveItem.procInstanceId"></select>
				</td>
			</tr>
			<tr>
				<td>请假原因：</td>
				<td>
					<s:textarea name="leaveItem.leaveCase" rows="5" cols="38" id="leaveCase"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input value="提 交" type="button" id="btn_submit" />
					&nbsp;
					<input value="重 置" type="reset" />&nbsp;<font color="red">${tip}</font>
				</td>
			</tr>
			
		</s:form>




	</table>
</body>
</html>	