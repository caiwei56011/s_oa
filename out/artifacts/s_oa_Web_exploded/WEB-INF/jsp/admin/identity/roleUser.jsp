<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-角色绑定管理</title>
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
			
			/** 全选或全不选 */
			$("#checkAll").click(function(){
				/** 每个数据行的复选框也全选或全不选 */
				$("input[id^='box']").prop("checked",$(this).prop("checked")); 
				/** 每个数据行的 复选框如果选中则改变背景色*/
				$("tr[id^='tr']").trigger(this.checked ? "mouseover" : "mouseout"); //this是DOM对象
				
			});
			
			/** 当每行的复选框都选中时，全选按钮也选中 */
			/** 写法一: */
			/*
			$("input[id^='box']").click(function(){
				var isAllChecked = true;
				$.each($("input[id^='box']"),function(){
					if($(this).prop("checked") == false){
						isAllChecked = false;
					}
				});
				$("#checkAll").prop("checked",isAllChecked);
			});
			*/
			/** 写法二: */
			var boxs = $("input[id^='box']");
			boxs.click(function(){
				$("#checkAll").prop("checked", boxs.filter(":checked").length == boxs.length);
			});
			
			/** 鼠标移动到每一个数据行时，行背景色变化 */
			$("tr[id^='tr']").hover(
					function(){ //mouseover
						$(this).css({"background-color" : "#ffffbf", "cursor" : "point"});
					},
					function(){ //mouseout
						var box = $(this).find("input[id^='box']");
						if(box.prop("checked") == false){
							$(this).css("background-color", "#fff");
						}
					}
			);
			
			
			
			/** 绑定用户按钮点击事件 */
			$("#bindUser").click(function(){
				$("#dialogDiv").dialog({
					title: "绑定用户",
					width: "800",
					height: "400",
					//href: "${path}/admin/identity/showAddUser.jspx",
					closed: false,
					collapsible: true,
					minimizable: true,
					maximizable: true,
					modal: true,
					onClose: function(){
						/** 对话框关闭后，重新查询角色已绑定的用户列表 */
						window.location.href="${path }/admin/identity/selectRoleUser.jspx?pageModel.pageIndex=&{pageModel.pageIndex}&role.id=${role.id}";
					}
				});
				$("#iframe").attr("src","${path}/admin/identity/showBindUser.jspx?role.id=${role.id}").show();
			});
			
			/** 解除用户按钮点击事件 */
			$("#unBindUser").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请至少勾选一条记录");
				}else{
					if(confirm("您确定要解除吗？")){
						var userIds = new Array();
						$.each(boxs, function(){
							userIds.push($(this).val());
						});
						window.location.href="${path }/admin/identity/unBindUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&role.id=${role.id}&userIds=" + userIds.join(",");
					}
				}
			});
			/** 返回按钮点击事件 */
			$("#backBtn").click(function(){
				//window.location.href="${path }/admin/identity/selectRole.jspx";
				window.history.back(); //这样跳转可以保证回到当时的页码
			});
			
		});
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
	<s:form theme="simple" id="" name="" action="" method="post">
		<table>
			<tr>
				<td><input type="button" value="绑定用户" id="bindUser"/></td>
				<td><input type="button" value="解除用户" id="unBindUser"/></td>
				<td><input type="button" value="返回" id="backBtn"></td>
				<td>当前角色：【<font color="red">${role.name}</font>】</td>
				<td>
					<!-- 解除用户成功、失败的提示信息 -->
					<font color="red">${tip}</font>
				</td>
			</tr>
		</table>
	</s:form>

	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th><input type="checkbox" id="checkAll"/>全选</th>
			<th>编号</th>
			<th>姓名</th>
			<th>性别</th>
			<th>部门</th>
			<th>职位</th>
			<th>手机号码</th>
			<th>邮箱</th>
			<th>状态</th>
			<th>创建日期</th>
			<th>创建人</th>
			<th>审核日期</th>
			<th>审核人</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="users" status="st">
				<tr id="tr_${st.index}" class="listTr">
					<td><input type="checkbox" id="box_${st.index}" value="${userId }"/></td>
					<td><s:property value="userId"/></td>
					<td><s:property value="name"/></td>
					<td>${sex == 1 ? '男' : '女' }</td>
					<td><s:property value="dept.name"/></td>
					<td><s:property value="job.name"/></td>
					<td><s:property value="phone"/></td>
					<td><s:property value="email"/></td>
					<td>
						<!-- 状态	0新建,1审核,2不通过审核,3冻结  -->
						<s:if test="status == 0"><font style="color: red;">新建</font></s:if>
						<s:elseif test="status == 1">审核</s:elseif>
						<s:elseif test="status == 2">不通过</s:elseif>
						<s:else>冻结</s:else>
					</td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:date name="checkDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="checker.name"/></td>
				</tr>
			</s:iterator>	
			
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<jsoft:pager 
		pageIndex="${pageModel.pageIndex }" 
		pageSize="${pageModel.pageSize }" 
		recordCount="${pageModel.recordCount }" 
		submitUrl="${path }/admin/identity/selectRoleUser.jspx?pageModel.pageIndex={0}&role.id=${role.id }"/>
	
	<!-- 绑定用户的弹出对话框div -->
	<div id="dialogDiv"><iframe id="iframe" width="100%" height="100%" style="display: none" frameborder="0"></iframe></div>
	
</body>
</html>