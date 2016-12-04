<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>OA办公管理系统-操作管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="pujin.ico" rel="shortcut icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${path}/css/common/global.css"/>
    <link rel="StyleSheet" type="text/css" href="${path }/js/dtree/dtree.css" />
	<script type="text/javascript" src="${path }/js/dtree/dtree.js"></script>
	<script type="text/javascript" src="${path }/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path }/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<style type="text/css">
		html,body{ height:100%;}
		a{color:#6a6f71; text-decoration:none;}
	</style>	
	<script type="text/javascript">
		$(function(){
			/** 创建树对象 */
			window.d = new dTree("d","${path}/js/dtree/");
			//d.add(id, pid, name, url, title, target, icon, iconOpen, open)
			/** 添加根节点 */
			d.add(-2, -1, "模块管理");
			d.add(0, -2, "全部","${path}/admin/identity/selectModule.jspx","全部","moduleRightFrame");
			/** 异步请求获取用于创建其它子节点的数据 */
			// data: [{"id": "0001","pid": "", "name": ""},{"id": "0001","pid": "", "name": ""},...]
			// 后台：List<Map<String,Object>>
			$.ajax({
				url: "${path}/admin/identity/loadModuleTreeAjax.jspx",
				type: "post",
				dataType: "json",
				async: true,
				success: function(data){
					//alert(data);
					$.each(data, function(){
						d.add(this.id, this.pid, this.name, "${path}/admin/identity/selectModule.jspx?parentCode=" + this.id, this.name, "moduleRightFrame");
					}); 
					/** 将树对象生成的html添加到body中 */
					$(document.body).html(d.toString());
				},
				error: function(){
					alert("加载操作树数据失败");
				}
			});
			
		});
	</script>
	
  </head>
  <body>
  </body>
</html>