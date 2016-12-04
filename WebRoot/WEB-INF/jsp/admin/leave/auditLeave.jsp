<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-请假单审批管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
		$(function(){
			/** 为下面的数据行绑定onmouseover与onmouseout事件 */
			$("tr[id^='tr_']").hover(
				function(){ // onmouseover
					$(this).css({"cursor" : "pointer", "background-color" : "#FFFFBF"});
				},
				function(){ // onmouseout
					/** 获取当前行中的checkbox */
					var box = $(this).find("input[id^='box_']");
					if (!box.attr("checked")){
						$(this).css("background-color", "#FFFFFF");
					}
				}
			);
		
			
			/** 审核请假单操作 */
			$("#auditLeaveItem").click(function(){
				var boxs = $("input[id^=box_]:checked");
				switch (boxs.length){
					case 0:
						alert("请选择要审核的请假单！");
						break;
					case 1:
						$("#divDialog").dialog({
							title : "审核-请假单",
							width : 370, // 宽度
							height : 235, // 高度
							maximizable : false, // 最大化
							minimizable : false, // 最小化
							collapsible : true, // 可伸缩
							modal : true, // 模态窗口
							onClose : function(){
								window.location.href = "${path}/admin/leave/selectAuditLeave.jspx";
							}
						});
						
						var array = boxs.val().split(",");
						var leaveId = array[0];
						var taskId = array[1];
						$("#iframe").attr("src", "${path}/admin/leave/showAudit.jspx?leaveItem.id=" + leaveId + "&leaveItem.taskId=" + taskId).show();
						break;
					default:
						alert("审核请假单时，最多只能选择一项！");
						break;
				}
			});
			
			
		});
	</script>
</head>
<body>
	<table>
		<tr>
			<td>
				<input type="button" value="审批" id="auditLeaveItem"/>
				<s:if test="tip != null">
					<span style="color:red;">${tip}</span>
				</s:if>	
			</td>
		</tr>
	</table>
	<table class="listTable" width="100%" border="0" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th width="55px;">选择</th>
			<th>假期类型</th>
	        <th>请假原因</th>
	        <th>开始日期</th>
	        <th>结束日期</th>
	        <th>请假小时</th>
	        <th>创建日期</th>
	        <th>请假人</th>
		</tr>
		<tbody style="background-color: #ffffff;">
			<s:iterator value="leaveItems" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="radio" name="id" id="box_${stat.index}" value="${id},${taskId}"/></td>
					<td><s:property value="leaveType.name"/></td>
		            <td><s:property value="leaveCase"/></td>
		            <td><s:date name="beginDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		            <td><s:date name="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		            <td><s:property value="leaveHour"/></td>
		            <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		            <td><s:property value="creater.name"/></td>


					
				</tr>
			</s:iterator>
		</tbody>
	</table>
		
	<!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" frameborder="0" width="100%" height="100%" style="display:none;"></iframe>
	</div>	
</body>
</html>