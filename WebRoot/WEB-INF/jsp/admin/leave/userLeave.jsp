<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-用户请假</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<link href="${path}/css/common/pager.css" type="text/css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css">
	<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		$(function(){
			/** 鼠标移动到每一个数据行时，行背景色变化 */
			$("tr[id^='tr']").hover(
					function(){ //mouseover
						$(this).css({"background-color" : "#ffffbf", "cursor" : "point"});
					},
					function(){ //mouseout
						$(this).css("background-color", "#fff");
					}
			);
			/** 异步加载假期类型 */
			$.ajax({
				url: "${path}/admin/leave/loadLeaveTypeAjax.jspx",
				type: "post",
				dataType: "json", //List<Map<String, Object>>
				success: function(data){
					$.each(data, function(){
						$("<option />").val(this.code).text(this.name)
							.attr("selected", this.code == '${leaveItem.leaveType.code}')
							.appendTo("#leaveType");
					});
				},
				error: function(){
					alert("假期类型加载失败");
				}
			});
			/** 添加请假单 */
			$("#addLeaveItem").click(function(){
				$("#dialogDiv").dialog({
					title: "填写请假单",
					width: "400",
					height: "300",
					closed: false,
					collapsible: true,
					minimizable: true,
					maximizable: true,
					modal: true,
					onClose: function(){
						/** 对话框关闭后，重新查询请假单列表 */
						window.location.href="${path }/admin/leave/selectUserLeave.jspx?pageModel.pageIndex=${pageModel.pageIndex}&leaveItem.leaveType.code=${leaveItem.leaveType.code}";
					}
				});
				$("#iframe").attr("src","${path}/admin/leave/showAddLeaveItem.jspx").show();
			});
			
		});
		/** 查看流程图 */
		var showProcessDiagramFn = function(piId){
			/** 显示流程图的对话窗口 */
			$("#dialogDiv").dialog({
				title : "请假-流程图", // 标题
				width : 580,  // 宽度
				height : 480, // 高度
				collapsible : true, // 可伸缩
				minimizable : true, // 最小化
				maximizable : true,  // 最大化
				modal : true // 模态窗口
			});
			$("#iframe").prop("src", "${path}/admin/workflow/showProcessDiagram.jspx?processInstanceId=" + piId).show();
		}
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
	<s:form theme="simple" id="selectUserLeave" name="selectUserLeave" action="/admin/leave/selectUserLeave.jspx" method="post">
		<table>
			<tr>
				<td><input type="button" value="添加" id="addLeaveItem">
					假期类型:
					<select name="leaveItem.leaveType.code" id="leaveType">
						<option value="0">==请选择==</option>
					</select>
					<input type="submit" value="查询"/>
				</td>
			</tr>
		</table>
	</s:form>

	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th>请假类型</th>
			<th>请假人</th>
			<th>请假原因</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>请假小时</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="leaveItems" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><s:property value="leaveType.name"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:property value="leaveCase"/></td>
					<td><s:date name="beginDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:date name="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="leaveHour"/></td>
					<td><s:if test="status == 0"><font>新建</font></s:if> 
						<s:elseif test="status == 1">
							<font color="blue">审核通过</font>
						</s:elseif>
						<s:else>
							<font color="red">不通过</font>
						</s:else>
					</td>
					<td>
						<s:if test="status == 0">
							<a href="javascript:;" onclick="showProcessDiagramFn('${procInstanceId}');">流程图</a>
						</s:if>
						<s:else>
							<a href="${path}/admin/workflow/selectHistoryTask.jspx?processInstanceId=${procInstanceId}">历史任务</a>
						</s:else>
							&nbsp;<a href="${path}/admin/leave/selectAuditResult.jspx?leaveAudit.leaveItem.id=${id}">审批意见</a>
					</td>
				</tr>
			</s:iterator>
			
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<jsoft:pager 
		pageIndex="${pageModel.pageIndex }" 
		pageSize="${pageModel.pageSize }" 
		recordCount="${pageModel.recordCount }" 
		submitUrl="${path }/admin/workflow/selectUserLeave.jspx?pageModel.pageIndex={0}"/>
	
	
	<!-- 添加的弹出对话框div -->
	<div id="dialogDiv"><iframe id="iframe" width="100%" height="100%" style="display: none" frameborder="0"></iframe></div>
	
</body>
</html>