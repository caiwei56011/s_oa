<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>办公管理系统-假期审批</title>
    <meta charset="UTF-8">
    <link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    		/** 点击同意或不同意按钮后，请求转发回此页面，弹出提示信息，刷新需要审批的请假单列表 */
    		if ("${tip}" != ""){
    			alert("${tip}");
    			/** 让父窗口重新加载 */
    			parent.window.location.reload();
    		}
    	
    		/** 为同意按钮绑定点击事件 */
    		$("#btn1").click(function(){
    			$("#status").val(1);
    			submitForm();
    		});
    		/** 为不同意按钮绑定点击事件 */
    		$("#btn2").click(function(){
    			$("#status").val(0);
    			submitForm();
    		});
    		
    		var submitForm = function(){
    			var remark = $("#remark");
    			if ($.trim(remark.val()) == ""){
    				alert("审批意见不能为空！");
    			}else{
    				$("#auditForm").submit();
    			}
    		};
    		
    	});
    </script>
</head>
<body>
	<form id="auditForm" action="${path}/admin/leave/audit.jspx" method="post">
		<input type="hidden" name="leaveAudit.leaveItem.id" value="${leaveItem.id}"/>
		<s:hidden name="leaveAudit.status" id="status"/>
		<s:hidden name="taskId" value="%{leaveItem.taskId}"/>
		<table>
			<tr>
		        <td>请假人：</td>
		        <td><font color="red">${leaveItem.creater.name }</font></td>
		    </tr>
		    <tr>
		        <td>请假原因：</td>
		        <td><font color="blue">${leaveItem.leaveCase}</font></td>
		    </tr>
		    <tr>
		        <td>审批意见：</td>
		        <td>
		        	<textarea rows="5" cols="35" id="remark" name="leaveAudit.remark"></textarea>
		        </td>
		    </tr>
		    <tr align="center">
		        <td colspan="2">
		            <input type="button" id="btn1" value="同意"/>
		            <input type="button" id="btn2" value="不同意"/>
		            &nbsp;<font color="red">${tip }</font>
		        </td>
		    </tr>
		</table>
	</form>
</body>
</html>