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
</head>
<frameset cols="168,*" frameborder="yes" border="1" bordercolor="#95B8E7">
	<!-- 左边子页面 -->
	<frame src="${path }/admin/identity/popedomLeft.jspx?role.id=${role.id}" name="popedomLeftFrame" />
	<!-- 右边子页面：默认显示空白，必须先选中左边页面的模块 -->
	<frame src="" name="popedomRightFrame" />
</frameset>
</html>