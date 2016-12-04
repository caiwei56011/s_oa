<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!-- 访问index.jsp页面时，自动请求指定的:/admin/main.action -->
<s:action name="main" namespace="/admin" executeResult="true"></s:action>
