<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.ctl.ORSView"%>
<%@page import="in.com.raysproject.ctl.FacultyCtl"%>
<%@page import="in.com.raysproject.bean.FacultyBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.com.raysproject.ctl.FacultyListCtl"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

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
	<%@include file="Header.jsp"%>
	<center>

		<h1 style="font-size: 30px;">Faculty List</h1>

		<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.FacultyBean"
			scope="request"></jsp:useBean>

		<%
		List list1 = (List) request.getAttribute("collegeList");
		List list2 = (List) request.getAttribute("courseList");
		List list3 = (List) request.getAttribute("subjectList");
	
		%>
		<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="POST">
			<%
				int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			List list = ServletUtility.getList(request);
			Iterator<FacultyBean> it = list.iterator();
			if (list.size() != 0) {
			%>
			<table>
				<tr>
					<td>
		<label class="p1">College Name</label> <%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), list1)%>&emsp;

		<label class="p1">Course Name</label> <%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), list2)%>&emsp;

	<label class="p1">Subject Name </label> <%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), list3)%>&emsp;
		
	<label class="p1">Login Id :</label> <input type="text" name="login" placeholder="Enter Login Id" value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
		
		
						<input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_SEARCH%>" style="padding: 3px;">&emsp;

						<input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_RESET%>" style="padding: 3px;"></td>
				</tr>
				<tr></tr>
				<tr></tr>

			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th><input type="checkbox" id="select_all" name="Select">Select All</th>
					<th>S.NO</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Qualification</th>
					<th> Mobile No</th>
					<th>Login Id</th>
					<th>Date Of Joining</th>
					<th>College Name</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Edit</th>

				</tr>

				<%
					while (it.hasNext()) {
					bean = it.next();
				%>
				<tr>
					<td align="left"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getQualification()%></td>
					<td align="center"><%=bean.getMobileNo()%></td>
					<td align="center"><%=bean.getLoginId()%></td>
					<%
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String date = formatter.format(bean.getDateOfJoining());
					%>
					<td align="center"><%=date%></td>
					<td align="center"><%=bean.getCollegeName()%></td>
					<td align="center"><%=bean.getCourseName()%></td>
					<td align="center"><%=bean.getSubjectName()%></td>
					<td align="center"><a href="FacultyCtl?id=<%=bean.getId()%>">Edit</a></td>


				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 3px;" value="<%=FacultyCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 3px;" value="<%=FacultyCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 3px;" value="<%=FacultyCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 3px;" value="<%=FacultyCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

			</table>
			<%
				}
			if (list.size() == 0) {
			%>

			<input type="submit" name="operation" style="padding: 3px;"
				value="<%=FacultyCtl.OP_BACK%>">
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">


		</form>
	</center>


</body>
<%@ include file="Footer.jsp" %>
</html>