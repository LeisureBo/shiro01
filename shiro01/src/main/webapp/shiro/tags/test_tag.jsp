<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="bo" tagdir="/WEB-INF/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>shiro标签</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<shiro:guest>
    	欢迎游客访问，<a href="${pageContext.request.contextPath}/static/login.jsp">点击登录</a>
		<br />
	</shiro:guest>
	<shiro:user>
    	欢迎[<shiro:principal />]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a>
		<br />
	</shiro:user>

	<shiro:authenticated>
    	用户[<shiro:principal />]已身份验证通过<br />
	</shiro:authenticated>

	<shiro:notAuthenticated>
    	未身份验证（包括记住我）<br />
	</shiro:notAuthenticated>

	<shiro:hasRole name="admin">
    	用户[<shiro:principal />]拥有角色admin<br />
	</shiro:hasRole>

	<shiro:hasAnyRoles name="admin,user">
    	用户[<shiro:principal />]拥有角色admin或user<br />
	</shiro:hasAnyRoles>

	<shiro:lacksRole name="abc">
    	用户[<shiro:principal />]没有角色abc<br />
	</shiro:lacksRole>

	<shiro:hasPermission name="user:create">
    	用户[<shiro:principal />]拥有权限user:create<br />
	</shiro:hasPermission>
	<shiro:lacksPermission name="org:create">
    	用户[<shiro:principal />]没有权限org:create<br />
	</shiro:lacksPermission>

	<bo:hasAllRoles name="admin,user">
    	用户[<shiro:principal />]拥有角色admin和user<br />
	</bo:hasAllRoles>

	<bo:hasAllPermissions name="user:create,user:update">
    	用户[<shiro:principal />]拥有权限user:create和user:update<br />
	</bo:hasAllPermissions>

	<bo:hasAnyPermissions name="user:create,abc:update">
    	用户[<shiro:principal />]拥有权限user:create或abc:update<br />
	</bo:hasAnyPermissions>

</body>
</html>
