<%@page import="in.com.raysproject.ctl.BaseCtl"%>
<%@page import="in.com.raysproject.ctl.StudentCtl"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student View</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Datepicker - Display month &amp; year menus</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1960:2040'
		});
	});
</script>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
</style>
<!-- <script> 
  $(function(){
	  $("#datepicker").datepicker({
		  changeMonth  : true,
		  changeYear   :  true,
		  yearRange    :  1970:2030,
		  dateFormate  : 'dd/mm/yyyy',
		  endDate      :'-18y'
	  });
  }); -->


</script>
</head>
<body>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.StudentBean"
			scope="request"></jsp:useBean>
		<center>
			<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 30px;">Update Student</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 30px;">Add Student</h1>
			<%
				}
			%>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDateTime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDateTime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<%
				List li = (List) request.getAttribute("collegeList");
			%>
			<table>
				<tr>
					<th align="left" size="20">First Name <span style="color: red;">*</span></th>
					<td><input type="text" name="firstName" size="20" size="20"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left" size="20">Last Name <span style="color: red;">*</span></th>
					<td><input type="text" name="lastName" size="20" size="20"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left" size="20">Email<span style="color: red;">*</span></th>
					<td><input type="text" name="email" size="20" size="20"
						placeholder="Enter Email"
						value="<%=DataUtility.getStringData(bean.getEmailId())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left" size="20">MobileNo<span style="color: red;">*</span></th>
					<td><input type="text" name="mobileNo" size="20" size="20"
						placeholder="Enter MobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>
				</tr>
				<%-- <tr>
		  <th align="left" size="20">CollegeId<span style="color:red;">*</span></th>
		  <td><input type="text" name="collegeId" size="20" size="20" placeholder="CollegeID"
		  value="<%=DataUtility.getStringData(bean.getCollegeId())%>"></td>
		  <td style="position:fixed;"><font color="red"><%=ServletUtility.getErrorMessage("CollegeId",request) %></font>
		  </td></tr> --%>

				<tr>
					<th align="left" size="20">College <span style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), li)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>

				<tr>
					<th align="left" size="20">DateOfBirth<span
						style="color: red;">*</span></th>
					<td><input type="text" readonly="readonly" name="dob"
						id="datepicker" size="20" size="20"
						placeholder="Enter Date Of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<th></th>
					<td colspan="2" align="right"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=StudentCtl.OP_UPDATE%>">&emsp; &emsp;<input
						type="submit" style="padding: 3px;" name="operation"
						value="<%=StudentCtl.OP_CANCEL%>"> &emsp;&emsp;</td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<td colspan="2" align="right"><input type="submit"
						name="operation" value="<%=StudentCtl.OP_SAVE%>"
						style="padding: 3px;">&emsp;&emsp; <input type="submit"
						name="operation" value="<%=StudentCtl.OP_RESET%>"
						style="padding: 3px;"> &emsp;&emsp;</td>
				</tr>
				<%
					}
				%>
			</table>
			</center>
	</form>
	<%@ include file="Footer.jsp" %>
</body>
</html>