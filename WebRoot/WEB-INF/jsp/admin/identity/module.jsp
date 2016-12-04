<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-操作管理</title>
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
			
		
			
			/** 添加操作 */
			$("#addModule").click(function(){
				$("#dialogDiv").dialog({
					title: "添加操作",
					width: "350",
					height: "220",
					//href: "${path}/admin/identity/showAddModule.jspx",
					closed: false,
					collapsible: true,
					minimizable: true,
					maximizable: true,
					modal: true,
					onClose: function(){
						/** 对话框关闭后，重新查询操作列表 */
						window.location.href="${path }/admin/identity/selectModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
						/** 刷新左边操作树 */
						parent.moduleLeftFrame.location.reload();
					}
				});
				$("#iframe").attr("src","${path}/admin/identity/showAddModule.jspx?&parentCode=${parentCode}").show();
			});
			
			/** 修改操作 */
			$("#updateModule").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请勾选一条记录");
				}else if(boxs.length == 1){
					$("#dialogDiv").dialog({
						title: "修改操作",
						width: "350",
						height: "220",
						//href: "${path}/admin/identity/showUpdateModule.jspx",
						closed: false,
						collapsible: true,
						minimizable: true,
						maximizable: true,
						modal: true,
						onClose: function(){
							/** 对话框关闭后，重新查询操作列表 */
							window.location.href="${path }/admin/identity/selectModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
							/** 刷新左边操作树 */
							parent.moduleLeftFrame.location.reload();
						}
					});
					$("#iframe").attr("src","${path}/admin/identity/showUpdateModule.jspx?module.code=" + boxs.val()).show();
				}else{
					alert("只能勾选一条记录进行修改");
				}
			});
			/** 删除操作 */
			$("#deleteModule").click(function(){
				var boxs = $("input[id^='box_']:checked");
				if(boxs.length == 0){
					alert("请至少勾选一条记录");
				}else{
					if(confirm("确定删除吗？")){
						var moduleCodes = new Array();
						$.each(boxs, function(){
							moduleCodes.push($(this).val());
						});
						window.location.href="${path }/admin/identity/deleteModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}&moduleCodes=" + moduleCodes.join(",");
						/** 刷新左边操作树 */
						parent.moduleLeftFrame.location.reload();
					}
					
				}
			});
			
		});
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
		<table>
			<tr>
				<td><input type="button" value="添加" id="addModule"/></td>
				<td><input type="button" value="修改" id="updateModule"/></td>
				<td>
					<input type="button" value="删除" id="deleteModule"/>
					<!-- 删除成功后的提示信息 -->
					<font color="red">${tip}</font>
				</td>
			</tr>
		</table>

	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th><input type="checkbox" id="checkAll"/>全选</th>
			<th>编号</th>
			<th>名称</th>
			<th>备注</th>
			<th>链接</th>
			<th>操作</th>
			<th>创建日期</th>
			<th>创建人</th>
			<th>修改日期</th>
			<th>修改人</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="modules" status="st">
				<tr id="tr_${st.index}" class="listTr">
					<td><input type="checkbox" id="box_${st.index}" value="${code }"/></td>
					<td><s:property value="code"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="remark"/></td>
					<td><s:property value="url"/></td>
					<td><a href="${path }/admin/identity/selectModule.jspx?parentCode=${code}">查看下级</a></td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="modifier.name"/></td>
				</tr>
			</s:iterator>	
			
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<jsoft:pager 
		pageIndex="${pageModel.pageIndex }" 
		pageSize="${pageModel.pageSize }" 
		recordCount="${pageModel.recordCount }" 
		submitUrl="${path }/admin/identity/selectModule.jspx?pageModel.pageIndex={0}&parentCode=${parentCode}"/>

	
	<!-- 添加、修改的弹出对话框div -->
	<div id="dialogDiv"><iframe id="iframe" width="100%" height="100%" style="display: none" frameborder="0"></iframe></div>
	
</body>
</html>