<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-流程部署</title>
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
				/** 为表单绑定提交事件 */
				$("#processDeployForm").submit(function(){
					var name = $("#name");
					var bpmn = $("#bpmn");
					var msg = "";
					if($.trim(name.val()) == ""){
						msg = "流程部署名称不能为空";
					}else if($.trim(bpmn.val()) == ""){
						msg = "流程定义文件不能为空";
					}
					if(msg != ""){
						alert(msg);
						return false;
					}
					return true;
				});
			});
		</script>
	</head>
<body>
	<br/>
	<table align="center" class="editTable" cellpadding="8" cellspacing="1">
		<s:actionerror/><s:fielderror/>
		<tbody style="background-color: #FFFFFF;">
			<s:form action="/admin/workflow/processDeploy.jspx" method="post" 
				id="processDeployForm" enctype="multipart/form-data"  theme="simple">
				<s:token></s:token>
				<tr>
					<td width="180px;">流程部署名称：</td>
					<td>
						<input type="text" name="name" size="25" id="name"/>
					</td>
					
				</tr>
				<tr>
					<td>请选择要上传的流程定义文件：</td>
					<td>
						<input type="file" name="bpmn" id="bpmn"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="部署"/>&nbsp;&nbsp;
						<input type="reset" value="重置">&nbsp;
						<font color="red">${tip }</font>						
					</td>
				</tr>
				
			</s:form>
		</tbody>
	</table>
</body>
</html>

	
	