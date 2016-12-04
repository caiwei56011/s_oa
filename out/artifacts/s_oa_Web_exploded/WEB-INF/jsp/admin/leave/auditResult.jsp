<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-审批意见</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${path}/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript">
		$(function(){
			/** 为下面的数据行绑定onmouseover与onmouseout事件 */
			$("tr[id^='tr_']").hover(
				function(){ // onmouseover
					$(this).css({"cursor" : "pointer", "background-color" : "#FFFFBF"});
				},
				function(){ // onmouseout
					$(this).css("background-color", "#FFFFFF");
				}
			);
		});
	</script>
</head>
<body>
	<table><tr><td>
		    <input type="button" value="返回" onclick="history.go(-1);"/>
	</td></tr></table>
	
	<table class="listTable" width="100%" border="0" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
	        <th>编号</th>
	        <th>审批人</th>
	        <th>职位</th>
	        <th>是否通过</th>
	        <th>审批意见</th>
	        <th>审批时间</th>
		</tr>
		<tbody style="background-color: #ffffff;">
		    <s:iterator value="leaveAudits" status="stat">
			      <tr id="tr_${stat.index}" class="listTr">
		            <td><s:property value="id"/></td>
		            <td><s:property value="checker.name"/></td>
		            <td><s:property value="checker.job.name"/></td>
		            <td>${ status == 1 ? '同意' : '不同意' }</td>
		            <td><s:property value="remark"/></td>
		            <td><s:date name="checkDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		        </tr>	
		   </s:iterator>
	   </tbody>
	</table>
</body>
</html>