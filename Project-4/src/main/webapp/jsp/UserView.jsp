<%@page import="in.com.raysproject.ctl.BaseCtl"%>
<%@page import="in.com.raysproject.ctl.UserCtl"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User view</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Datepicker - Display month &amp; year menus</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>

.footer {
	position: Relative;
	left: 0;
	bottom: 0;
	width: 100%;
	text-align: center;
}
</style>

<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',

		});
	});
</script>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<style>
.footer {
	position: relative;
	left: 0;
	bottom: 0;
	width: 100%;
	text-align: center;
}
</style>

</head>
<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.UserBean"
			scope="request"></jsp:useBean>

		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


		<center>
			<%
				if (bean.getId() > 0) {
			%>

			<h1 style="font-size: 30px; margin-top: 0;">Update User</h1>
			<%
				} else {
			%>

			<h1 style="font-size: 30px; margin-top: 0">Add User</h1>
			<%
				}
			%>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>

			<%
				List li = (List) request.getAttribute("rolelist");
			%>
			<table>
				<tr>
					<th align="left">First Name<span style="color: red;">*</span></th>
					<td><input type="text" name="firstName" size="20"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("FirstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name<span style="color: red;">*</span></th>
					<td><input type="text" name="lastName" size="20"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Login Id<span style="color: red;">*</span></th>
					<td><input type="text" name="login" size="20"
						placeholder="Enter Login ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Password<span style="color: red;">*</span></th>
					<td><input type="password" name="password" size="20"
						placeholder="Enter password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Confirm Password<span style="color: red;">*</span></th>
					<td><input type="password" name="confirmPassword" size="20"
						placeholder="Enter Confirm password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">MobileNo<span style="color: red;">*</span></th>
					<td><input type="text" name="mobileNo" size="20" maxlength="10"
						placeholder="Enter Mobile No"
					value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Gender<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Role :<span style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), li)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Date Of Birth<span style="color: red;">*</span></th>
					<td><input type="text"  name="dob"
						id="datepicker" size="20" placeholder="Enter Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> </a></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>

				</tr>
				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<th></th>
					<td colspan="2" align="right"><input type="submit"
						name="operation" value="<%=UserCtl.OP_UPDATE%>">&emsp; <input
							type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>">&emsp;&emsp;</td>
				</tr>
				<%
					} else {
				%>
				<tr>

					<td colspan="2" align="right"><input type="submit"
						name="operation" 
						value="<%=UserCtl.OP_SAVE%>">&emsp;
						<input type="submit" name="operation"
						value="<%=UserCtl.OP_RESET%>">&emsp;&emsp;</td>
				</tr>
				<%
					}
				%>
			</table>
	</form>
	</center>
	 
</body>
<%@ include file="Footer.jsp" %>
</html>