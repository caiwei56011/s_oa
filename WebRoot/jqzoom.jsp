<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>jqzoom图片放大镜</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${path }/js/jqzoom_ev-2.3.1/css/jquery.jqzoom.css">
	<script type="text/javascript" src="${path }/js/jquery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path }/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path }/js/jqzoom_ev-2.3.1/jquery.jqzoom-core-pack.js"></script>
   	<script type="text/javascript">
	   	$(document).ready(function() {
	   		$("#bigImg").jqzoom({
	   		 	zoomType: "standard", // 指定放大镜的类型 'standard', 'drag', 'innerzoom', 'reverse'
	            lens:true, // 是否产生蒙版
	            preloadImages: false, // 是否预加载图片
	            title : true, // 是否需要标题
	            alwaysOn: false, // 是否总是显示
	            zoomWidth: 200, // 右边div的宽度
         		zoomHeight: 200, // 右边div的高度
         		xOffset:0, // 指定右边div离左边的距离
         		yOffset:0, // 指定右边div离上面的距离
         		position:"right" // 指div放的方向(left、right、bottom)
	   	  	});
	   		
	   	});
   	</script>
   
   </head>  
  <body>
 	 <a id="bigImg" href="${path }/images/jqzoom_img.jpg" title="testTitle" >
 	 	<img src="${path }/images/jqzoom_img.jpg" width="100px" height="100px">
 	 </a>
  	
  </body>
</html>

