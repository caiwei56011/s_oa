<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-联系人管理</title>
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
			
			
			/** 添加联系人 */
			$("#addContact").click(function(){
				var contactGroupId = "${contact.contactGroup.id}";
				if(contactGroupId == ""){
					alert("请先选中一个联系组");
				}else{
					$("#dialogDiv").dialog({
						title: "添加联系人",
						width: "450",
						height: "280",
						//href: "${path}/admin/addressbook/showAddContact.jspx",
						closed: false,
						collapsible: true,
						minimizable: true,
						maximizable: true,
						modal: true,
						onClose: function(){
							/** 对话框关闭后，重新查询联系人列表 */
							window.location.href="${path }/admin/addressbook/selectContact.jspx?pageModel.pageIndex=${pageModel.pageIndex}&contact.contactGroup.id=${contact.contactGroup.id}";
						}
					});
					$("#iframe").attr("src","${path}/admin/addressbook/showAddContact.jspx?contact.contactGroup.id=${contact.contactGroup.id}").show();
				}
				
			});
			
			/** 修改联系人 */
			$("#updateContact").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请勾选一条记录");
				}else if(boxs.length == 1){
					$("#dialogDiv").dialog({
						title: "修改联系人",
						width: "450",
						height: "280",
						//href: "${path}/admin/addressbook/showUpdateContact.jspx",
						closed: false,
						collapsible: true,
						minimizable: true,
						maximizable: true,
						modal: true,
						onClose: function(){
							/** 对话框关闭后，重新查询联系人列表 */
							window.location.href="${path }/admin/addressbook/selectContact.jspx?pageModel.pageIndex=${pageModel.pageIndex}&contact.contactGroup.id=${contact.contactGroup.id}";
						}
					});
					$("#iframe").attr("src","${path}/admin/addressbook/showUpdateContact.jspx?contact.id=" + boxs.val()).show();
				}else{
					alert("只能勾选一条记录进行修改");
				}
			});
			/** 删除联系人 */
			$("#deleteContact").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请至少勾选一条记录");
				}else{
					if(confirm("确定删除吗？")){
						var contactIds = new Array();
						$.each(boxs, function(){
							contactIds.push($(this).val());
						});
						window.location.href="${path }/admin/addressbook/deleteContact.jspx?pageModel.pageIndex=${pageModel.pageIndex}&contact.contactGroup.id=${contact.contactGroup.id}&contactIds=" + contactIds.join(",");
					}
				}
			});
			/** 导出联系人到excel */
			$("#exportContact").click(function(){
				window.location.href="${path }/admin/addressbook/exportContact.jspx?contact.contactGroup.id=${contact.contactGroup.id}";
			});
			/** 从excel导入联系人 */
			$("#importContact").click(function(){
				/** 判断有没有选择联系组 */
				if ("${contact.contactGroup.id}" == ""){
					alert("请选择联系组！");
				}else{
					/** 判断有没有选择要上传的文件 xls xlsx */
					var excelFile = $("#excel").val();
					if ($.trim(excelFile) == ""){
						alert("请选择要导入的Excel文件！");
					}else if (!/[xls|xlsx]$/.test($.trim(excelFile))){
						alert("您选择的文件类型不正确！");
					}else{
						$("#excelForm").submit();
					}
				}
			});
		});
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
	<s:form theme="simple" id="excelForm" name="excelForm" action="/admin/addressbook/importContact.jspx" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td><input type="button" value="添加" id="addContact"/></td>
				<td><input type="button" value="修改" id="updateContact"/></td>
				<td><input type="button" value="删除" id="deleteContact"/></td>
				<td><input type="button" value="导出" id="exportContact"/></td>
				<td><input type="button" value="导入" id="importContact"/></td>
				<td><input type="file" name="excel" id="excel"/></td>
				<td>
					<!-- 隐藏域保存联系组id -->
					<s:hidden name="contact.contactGroup.id"></s:hidden>
					<!-- 删除成功后的提示信息 -->
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
			<th>手机号码</th>
			<th>邮箱</th>
			<th>QQ号码</th>
			<th>生日</th>
			<th>组名</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="contacts" status="st">
				<tr id="tr_${st.index}" class="listTr">
					<td><input type="checkbox" id="box_${st.index}" value="${id }"/></td>
					<td><s:property value="id"/></td>
					<td><s:property value="name"/></td>
					<td>${sex == 1 ? '男' : '女' }</td>
					<td><s:property value="phone"/></td>
					<td><s:property value="email"/></td>
					<td><s:property value="qqNum"/></td>
					<td><s:date name="birthday" format="yyyy-MM-dd"/></td>
					<td><s:property value="contactGroup.name"/></td>
				</tr>
			</s:iterator>	
			
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<jsoft:pager 
		pageIndex="${pageModel.pageIndex }" 
		pageSize="${pageModel.pageSize }" 
		recordCount="${pageModel.recordCount }" 
		submitUrl="${path }/admin/addressbook/selectContact.jspx?pageModel.pageIndex={0}&contact.contactGroup.id=${contact.contactGroup.id}"/>
	
	<!-- 添加、修改的弹出对话框div -->
	<div id="dialogDiv"><iframe id="iframe" width="100%" height="100%" style="display: none" frameborder="0"></iframe></div>
	
</body>
</html>