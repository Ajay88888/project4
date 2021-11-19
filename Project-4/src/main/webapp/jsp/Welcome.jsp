<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@page import="in.com.raysproject.ctl.ORSView"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>

<body>

	<form action="<%=ORSView.WELCOME_CTL%>">
		<br>
	<%@include file="Header.jsp"%>
	<h1 align="center">
		<br>
		<br> <font size ="10px" color ="Green">Welcome to ORS </font>
		</h1>
		
		<%
		UserBean beanUserBean = (UserBean) session.getAttribute("user");
		if(beanUserBean != null) {
			if(beanUserBean.getRoleId() == RoleBean.STUDENT) {
		%>
		<%-- <h2 align = "center" > 
		<a herf =<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your Marksheet</a>
		</h2> --%>
		<% 
			}
		}
		%>
	</form>
</body>
 <%@ include file="Footer.jsp" %>
</html>