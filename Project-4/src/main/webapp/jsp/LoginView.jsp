 <%@page import="in.com.raysproject.ctl.LoginCtl"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="in.com.raysproject.ctl.UserCtl"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<style >
	.p1 {
	font-size : 18px;
	}
</style>
</head>
<form action ="<%=ORSView.LOGIN_CTL%>"method="post">
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.com.raysproject.bean.UserBean" scope="request"></jsp:useBean>

	<input type ="hidden" name="id" value="<%=bean.getId()%>">
	<input type ="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
	<input type ="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
	<input type ="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
	<input type ="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

	<center>
	<h1 style="font-size: 40px;">Login</h1>
	</center>
	
	<center>
		<table>
				<tr>
					<td></td>
				</tr>
				<tr>
		
					<H2>
						<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
						</font>
					</H2>

					<H2>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
						</font>
					</H2>
					<%
						String uri = (String) request.getAttribute("uri");
					%>
					
					
					
	
			<th align="left" class="p1">LoginId<span style="color:red">*</span></th>
			<td><input type ="text" name="login" size="20" size=30 placeholder="Enter LoginID"
			value="<%=DataUtility.getStringData(bean.getLogin())%>"><td>
			<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request)%></font></td>
			</tr>
			<tr>
			  <th align="left" class="p1">Password<span style="color:red">*</span></th>
			  <td><input type="password" name="password"  size="20" size=30 
			  placeholder="Enter Password" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
			  <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password",request)%></font></td>
			  </tr>
			  <tr>
			   <th></th>
			   <td colspan="2" align="center"><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN %>"style="padding: 5px;">&nbsp;
			   <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>"style="padding: 5px;">&nbsp;</td>
			   </tr>
			   <tr><th></th>
			   <td align="center"><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"style="color: black; font-size: 15px;"><b>Forget my password</b></a>&nbsp;</td>
			   </tr>
			   </table>
			   
			   <input type="hidden" name="uri" value="<%=uri%>">
			   
			   </form>
	</center>
	
</body>
 <%@ include file="Footer.jsp" %>
</html> 