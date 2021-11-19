<%@page import="in.com.raysproject.ctl.GetMarksheetCtl"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Marksheet View</title>

<!-- <style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

.footer{
	position:relative;
	left:  0;
	bottom: 0;
	width: 100%;
	text-align:center;
}
</style> -->

</head>
<body>
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.com.raysproject.bean.MarksheetBean"
		scope="request"></jsp:useBean>

	<center>
		<h1 style="font-size: 30px; margin-top: 0px">Get Marksheet</h1>
		
		<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">

			<input type="hidden" name="id" value="<%=bean.getId()%>">
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h2>
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>

			<!-- <table border=1> -->
			<table>
				<tr>
					<th><b> Roll No :</b></th>&emsp;
					<td><input type="text" name="rollNo" size="50"
						placeholder="Enter Roll No"
						value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=GetMarksheetCtl.OP_GO%>" size="40"> <font
						color="red" class="p3" style="position: fixed;"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
						</table>
						<%
							if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
						%>
					
			
			<table border="1" width="60%">
				<br>
				<tr>
					<th  colspan="2">RollNo</th>
					<td  align="center" colspan="3"><%=DataUtility.getStringData(bean.getRollNo())%></td>

				</tr>
				<tr>
					<th  colspan="2">Name</th>
					<td  align="center" colspan="3"><%=DataUtility.getStringData(bean.getName())%></td>
				</tr>
				<tr>
					<th colspan="1">Subject</th>
					<th colspan="1">Max Marks</th>
					<th colspan="1">Marks Obtain</th>
				</tr>

				<tr>
					<th>Physics</th>
					<td align="center">100</td>
					<td align="center"><%=DataUtility.getStringData(String.valueOf(bean.getPhysics()))%>
						<%
							float physics = bean.getPhysics();
						%></td>
				<!-- 	</td> -->
				</tr>
				<tr>
					<th>Chemistry</th>
					<td align="center">100</td>

					<td align="center"><%=DataUtility.getStringData(String.valueOf(bean.getChemistry()))%>
						<%
							float chemistry = bean.getChemistry();
						%></td>

				</tr>
				<tr>
					<th>Maths</th>
					<td align="center">100</td>

					<td align="center"><%=DataUtility.getStringData(String.valueOf(bean.getMaths()))%>
						<%
							float marks = bean.getMaths();
						%></td>

				</tr>
				<tr>
					<th  colspan="1">Total</th>
					<td align="center">300</td>


					<td  align="center" colspan="1">
						<%
							int total = ((bean.getMaths()) + (bean.getPhysics()) + (bean.getChemistry()));
						
						float percentage = (total * 100) / 300;
						
						%><%=total%></td>
				</tr>

				<tr>
					<th  colspan="2">Percentage</th>

					<td  align="center"><%=percentage%>%</td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
	</center>
<!-- 	<div class="footer">
<hr>
<center> 
  <h4>
  	<i><b>&#169;RAYS Technologies</b></i></div>
  	
  </h4>
  </center> -->
</body>
 <%@ include file="Footer.jsp" %>
</html>