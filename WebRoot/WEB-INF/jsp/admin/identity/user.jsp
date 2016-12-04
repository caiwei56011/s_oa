<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-用户管理</title>
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
			/** 异步请求获取部门下拉列表数据 */
			$.ajax({
				url: "${path}/admin/identity/loadDeptByIdAndNameAjax.jspx",
				type: "post",
				dataType: "json",
				success: function(data){
					//data: [{"id": 1, "name": "技术部"}, {"id": 2, "name": "市场部"}]
					//后台类型：List<Map<String,Object>>
					//alert(data);
					/** 获取上一次点击查询时传入后台的user.dept.id */
					var deptId = ${user.dept.id == null ? 0 : user.dept.id};
					$.each(data,function(){
						$("<option />")
							.val(this.id)
							.text(this.name)
							.attr("selected", this.id == deptId) //回显，默认选中
							.appendTo("#deptSelect");
					});
				},
				error: function(){
					alert("部门数据加载失败");					
				}
			});
			
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
			
			/** #######姓名搜索条件文本框输入内容时联想begin####### */
			/** 定义变量缓存上一次用户输入的数据 */
			var name = "";
			$("#user_name").keyup(function(){
				/** 判断文本框输入值不为空字符串时 */
				if($.trim($(this).val()) != ""){
					/** 判断用户输入的值与上一次是不是一样 */
					if (name != $.trim(this.value)){
						/** 异步请求获取数据 */
						$.ajax({
							url : "${path}/admin/identity/loadUserNameAjax.jspx",
							type : "post",
							data : {"userName": $.trim($(this).val())},
							dataType : "json",
							success : function(data){
								//['','',''] >>> List<String>
								//console.log(data);
								/** 先清空div中之前的数据 */
								$("#nameDiv").empty();
								if(data.length > 0){
									/** 将数据添加到联想div中 */
									$.each(data, function(){
										$("<div />").text(this).appendTo("#nameDiv");
									});
									/** 设置联想div样式、位置 */
									$("#nameDiv").removeAttr("style");
									$("#nameDiv").width($("#user_name").width() + 1)
										.css({"border" : "1px solid gray", "position": "absolute", "background-color": "#FFF"})
										.offset({left : $("#user_name").offset().left, top : $("#user_name").offset().top + $("#user_name").outerHeight()})
										.slideDown(300);
									/** 为联想div中的子div绑定mouseover和mouseout事件和点击事件 */
									$("#nameDiv").children().hover(
											function(){
												$("#nameDiv > div").removeAttr("style");
												$(this).css({"background-color": "#ffffbf", "cursor": "pointer"});
												$("#user_name").val($(this).text());
											},
											function(){
												$(this).removeAttr("style");
											}
									).click(function(){
										$("#user_name").val($(this).text());
										$("#nameDiv").slideUp(300);
									});
								}
							},
							error : function(){
								alert("联想数据加载失败");
							}
						});
					}
					/** 缓存用户输入的文本 */
					name = $.trim(this.value);
				}else{
					/** 关闭nameDiv隐藏 */
					$("#nameDiv").slideUp(300);
					/** 还原为空字符串 */
					name = "";
				}
					
			});
			/** 为document绑定点击事件 */
			$(document).click(function(){
				$("#nameDiv").slideUp(300);
			});
			
			/** 为document绑定onkeydown事件 */
			$(document).keydown(function(event){
				if (event.keyCode === 38){ // 向上
					/** 获取当前的div(有style的属性div) */
					var currentDiv = $("#nameDiv > div[style]").removeAttr("style"); // 删除样式
					/** 代表有一个div上面有style */
					if (currentDiv.length == 1 && currentDiv.prev().length == 1){
						/** 获取相邻的上一个div */
						var prevDiv = currentDiv.prev();
						/** 为prevDiv添加style */
						prevDiv.css("background-color","#ffffbf");
						/** 为name赋值，为了防止发送异步请求 */
						name = prevDiv.text();
						/** 把prevDiv中文本放到上面的文本框中 */
						$("#user_name").val(prevDiv.text());
					}
					/** 代表没有一个div上面有style */
					if (currentDiv.length == 0 || currentDiv.prev().length == 0){
						/** 获取最后一个div */
						var lastDiv = $("#nameDiv > div:last");
						/** 为lastDiv添加style */
						lastDiv.css("background-color","#ffffbf");
						/** 为name赋值，为了防止发送异步请求 */
						name = lastDiv.text();
						/** 把lastDiv中文本放到上面的文本框中 */
						$("#user_name").val(lastDiv.text());
					}
				}
				if (event.keyCode === 40){ // 向下
					/** 获取当前的div(有style的属性div) */
					var currentDiv = $("#nameDiv > div[style]").removeAttr("style"); // 删除样式
					/** 代表有一个div上面有style */
					if (currentDiv.length == 1 && currentDiv.next().length == 1){
						/** 获取相邻的下一个div */
						var nextDiv = currentDiv.next();
						/** 为nextDiv添加style */
						nextDiv.css("background-color","#ffffbf");
						/** 为name赋值，为了防止发送异步请求 */
						name = nextDiv.text();
						/** 把nextDiv中文本放到上面的文本框中 */
						$("#user_name").val(nextDiv.text());
					}
					/** 代表没有一个div上面有style */
					if (currentDiv.length == 0 || currentDiv.next().length == 0){
						/** 获取第一个div */
						var firstDiv = $("#nameDiv > div:first");
						/** 为firstDiv添加style */
						firstDiv.css("background-color","#ffffbf");
						/** 为name赋值，为了防止发送异步请求 */
						name = firstDiv.text();
						/** 把firstDiv中文本放到上面的文本框中 */
						$("#user_name").val(firstDiv.text());
					}
				}
			});	
			/** #######姓名搜索条件文本框输入内容时联想end####### */
			
			/** 添加用户 */
			$("#addUser").click(function(){
				$("#dialogDiv").dialog({
					title: "添加用户",
					width: "500",
					height: "300",
					//href: "${path}/admin/identity/showAddUser.jspx",
					closed: false,
					collapsible: true,
					minimizable: true,
					maximizable: true,
					modal: true,
					onClose: function(){
						/** 对话框关闭后，重新查询用户列表 */
						window.location.href="${path }/admin/identity/selectUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id }";
					}
				});
				$("#iframe").attr("src","${path}/admin/identity/showAddUser.jspx").show();
			});
			
			/** 修改用户 */
			$("#updateUser").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请勾选一条记录");
				}else if(boxs.length == 1){
					$("#dialogDiv").dialog({
						title: "修改用户",
						width: "500",
						height: "300",
						//href: "${path}/admin/identity/showUpdateUser.jspx",
						closed: false,
						collapsible: true,
						minimizable: true,
						maximizable: true,
						modal: true,
						onClose: function(){
							/** 对话框关闭后，重新查询用户列表 */
							window.location.href="${path }/admin/identity/selectUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id }";
						}
					});
					$("#iframe").attr("src","${path}/admin/identity/showUpdateUser.jspx?user.userId=" + boxs.val()).show();
				}else{
					alert("只能勾选一条记录进行修改");
				}
			});
			/** 删除用户 */
			$("#deleteUser").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请至少勾选一条记录");
				}else{
					if(confirm("确定删除吗？")){
						var userIds = new Array();
						$.each(boxs, function(){
							userIds.push($(this).val());
						});
						window.location.href="${path }/admin/identity/deleteUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id }&userIds=" + userIds.join(",");
					}
				}
			});
			/** 审核用户 */
			$("#checkUser").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请至少勾选一条记录");
				}else{
					var userIds = new Array();
					$.each(boxs, function(){
						userIds.push($(this).val());
					});
					window.location.href="${path }/admin/identity/checkUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id }&userIds=" + userIds.join(",") + "&user.status=" + $("#status").val();
				}
			});
			/** 根据当前登录用户的权限，隐藏无操作权限的按钮 */
			var userPopedom = "${user_module_popedom}";
			//alert(userPopedom);
			/** 定义数组保存操作按钮id */
			var btnIdArr = ["addUser", "updateUser", "deleteUser", "checkUser"];
			$(btnIdArr).each(function(){
				if(userPopedom.indexOf(this) == -1){
					$("#" + this).hide();
					if(this == "checkUser"){
						$("#td_" + this).hide();
					}
				}
			});
		});
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
	<s:form theme="simple" id="selectUser" name="selectUser" action="/admin/identity/selectUser.jspx" method="post">
		<table>
			<tr>
				<td><input type="button" value="添加" id="addUser"/></td>
				<td><input type="button" value="修改" id="updateUser"/></td>
				<td><input type="button" value="删除" id="deleteUser"/></td>
				<td id="td_checkUser">状态：
					<select name="user.status" id="status">
					    <option value="1">审核</option>
					    <option value="2">不通过</option>
					    <option value="3">冻结</option>
					</select>
				</td>
				<td><input type="button" value="审核" id="checkUser"/></td>
				<td>姓名：<s:textfield name="user.name" size="12"  id="user_name"/></td>
				<td>手机号码：<s:textfield name="user.phone" size="12"  id="user_phone"/></td>
				<td>部门：
					<select name="user.dept.id" id="deptSelect">
						<option value="0">==请选择==</option>
					</select>
				</td>
				<td>
					<input type="submit" value="查询"/>&nbsp;
					<!-- 删除、审核成功后的提示信息 -->
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
		submitUrl="${path }/admin/identity/selectUser.jspx?pageModel.pageIndex={0}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id }"/>
	
	<!-- 姓名搜索文本框联想弹出的div -->
	<div id="nameDiv" style="display:none;"></div>
	
	<!-- 添加、修改的弹出对话框div -->
	<div id="dialogDiv"><iframe id="iframe" width="100%" height="100%" style="display: none" frameborder="0"></iframe></div>
	
</body>
</html>