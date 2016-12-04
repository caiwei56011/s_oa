<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>文件表单异步上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${path }/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path }/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path }/js/malsup-form/jquery.form.js"></script>
	<script type="text/javascript">
	
    $(document).ready(function() {
    	/** 方式一：jquery v1.7- 
        $("#fileForm").on("submit", function(e) {
            e.preventDefault(); // 取消事件的默认行为，阻止同步提交
            $(this).ajaxSubmit({
            	url : "${path}/asyncUpload.jspx",
            	type: "post",
            	dataType: "text",
            	success: function(data){
            		$("<img />").attr("src","${path}" + data)
            			.width(100).height(100)
            			.appendTo("#imgs");
            	},
            	error: function(){
            		alert("数据加载失败");
            	}
            });
        });
    	*/
    	
    	/** 方式二： jquery v1.7+ */
        $("#fileForm").ajaxForm({
        	url : "${path}/asyncUpload.jspx",
        	type: "post",
        	dataType: "text",
        	success: function(data){
        		$("<img />").attr("src","${path}" + data)
        			.width(100).height(100)
        			.appendTo("#imgs");
        	},
        	error: function(){
        		alert("数据加载失败");
        	}
        });
    });
	</script>
	
	
  </head>
  <body>
  	<br/>
	<table align="center" class="editTable" cellpadding="8" cellspacing="1">
		<tbody style="background-color: #FFFFFF;">
			<%-- <s:form action="/asyncUpload.jspx" method="post" enctype="multipart/form-data" id="fileForm" theme="simple"> --%>
			<s:form id="fileForm"  method="post" enctype="multipart/form-data" theme="simple">
				<tr>
					<td width="65px;">文件说明：</td>
					<td>
						<input type="text" name="remark" size="18"/>
					</td>
				</tr>
				<tr>
					<td>请选择上传的文件：</td>
					<td>
						<input type="file" name="pic"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>
					</td>
				</tr>
			</s:form>
		</tbody>
	</table>
  	<div id="imgs">
  		<!-- <img src="${path }${imgUrl}">  -->
  	</div>
  </body>
</html>
