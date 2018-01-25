<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="com.bo.shiro.session.mgt.OnlineSession" %>
<%@ page import="com.bo.shiro.session.dao.MySessionDAO" %>
<%@ page import="java.io.Serializable" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>HOME</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body>
	<shiro:guest>
    		欢迎游客访问，<a href="${pageContext.request.contextPath}/static/login.jsp">点击登录</a><br />
	</shiro:guest>
	<shiro:user>
    	欢迎[<shiro:principal />]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a><br />
		online_session：${requestScope.online_session}<br/>
	</shiro:user>

	<shiro:user>
		<%
			SecurityUtils.getSubject().getSession().setAttribute("key", 123);
			out.println(SecurityUtils.getSubject().getSession().getAttribute("key"));
		%>
		<br />
		<%
			MySessionDAO sessionDAO = new MySessionDAO();
			Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
			OnlineSession onlineSession = (OnlineSession) sessionDAO.readSession(sessionId);
			out.print(onlineSession.getStatus().getInfo());
		%>
	</shiro:user>
</body>
</html>
