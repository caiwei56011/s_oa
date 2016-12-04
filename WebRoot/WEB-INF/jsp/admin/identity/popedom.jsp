<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/pager-tags" prefix="jsoft"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-绑定操作</title>
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
			
			/** 返回按钮点击事件 */
			$("#backBtn").click(function(){
				//parent.window.location.href="${path}/admin/identity/selectRole.jspx";
				parent.window.history.go(-1); //这样跳转可以保证回到当时的页码
			});
			
			/**#################### 默认选中已绑定的操作 #############*/
			/** 获取后台传递的当前角色在当前模块已绑定的操作,并将字符串转换为json对象 */
			var operaCodes = $.parseJSON('${operateCodes}'); //
			//alert(operaCodes);
			/** 写法一： */
			/* boxs.each(function(){
				if(operaCodes.indexOf(this.value) != -1){
					$(this).prop("checked", true)
							.parent().parent().trigger("mouseover");
				}
			}); */
			/** 写法二： */
			boxs.each(function(){
				var box = $(this);
				for(var i = 0; i < operaCodes.length; i++){
					if(box.val() == operaCodes[i]){
						/** 选中并改变背景色 */
						box.prop("checked", true)
							.parent().parent().trigger("mouseover");
						break;
					}
				}
			});
			/** 所有行的复选框都选中时，全选复选框也选中 */
			var checkedboxs = $("input[id^='box']").filter(":checked");
			$("#checkAll").prop("checked", checkedboxs.length == boxs.length);
			
			
			/** 绑定操作按钮点击事件 */
			$("#bindModule").click(function(){
				var boxs = $("input[id^='box_']:checked");
				/** 定义数组保存选择的要绑定的操作code */
				var codes = new Array();
				$.each(boxs, function(){
					codes.push($(this).val());
				});
				window.location.href="${path }/admin/identity/bindModule.jspx?codes=" + codes.join(",") + "&moduleCode=${moduleCode}&role.id=${role.id}";
				parent.parent.parent.leftframe.location.reload();
			});
		});
	</script>
</head>
<body>
<!-- 工具按钮区 -->
	<s:form theme="simple" id="" name="" action="" method="post">
		<table>
			<tr>
				<td><input type="button" value="绑定操作" id="bindModule"/></td>
				<td><input type="button" value="返回" id="backBtn"></td>
				<td>当前角色：【<font color="red">${role.name}</font>】</td>
				<td>
					<!-- 绑定成功、失败的提示信息 -->
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
			<th>名称</th>
			<th>链接</th>
			<th>备注</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="modules" status="st">
				<tr id="tr_${st.index}" class="listTr">
					<td><input type="checkbox" id="box_${st.index}" value="${code }"/></td>
					<td><s:property value="code"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="url"/></td>
					<td><s:property value="remark"/></td>
					
				</tr>
			</s:iterator>	
			
		</tbody>
	</table>	
</body>
</html>