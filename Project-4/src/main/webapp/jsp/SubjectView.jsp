<%@page import="in.com.raysproject.ctl.SubjectCtl"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Subject</title>

</head>
<body>
<%@include file="Header.jsp"%>
	<!-- <form action="SubjectCtl" method="post"> -->
	<form action="<%=ORSView.SUBJECT_CTL%>" method="POST">
		
		<jsp:useBean id="bean" class="in.com.raysproject.bean.SubjectBean"
			scope="request"></jsp:useBean>
		<center>
			<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 30px;">Update Subject</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 30px;">Add Subject</h1>
			<%
				}
			%>

			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>

			</h2>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>

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
				List l = (List) request.getAttribute("courseList");
			%>

			<table>
			<tr>
					<th align="left">Course Name<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
			
				<tr>
					<th align="left">Subject Name<span style="color: red;">*</span></th>
					<td><input type="text" name="subjectName" size="20"
						placeholder="Enter Subject"
						value="<%=DataUtility.getStringData(bean.getSubjectName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectName", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><textarea name="description"
							placeholder="Enter Description"
							style="width: 171px; height: 50px; resize: none;"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>

				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<th></th>
					<%-- <td colspan="2" align="right"><input type="submit"
						name="operation" style="padding: 3px;" 
						value="<%=SubjectCtl.OP_UPDATE%>" >&emsp; <input
						style="padding: 3px;"> &emsp; <input type="submit"
						name="operation" value="<%=SubjectCtl.OP_CANCEL%>"
						style="padding: 3px;">&emsp;&emsp;</td> --%>
						
						<td colspan="2" align="center"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=SubjectCtl.OP_UPDATE%>">&emsp; <input
						style="padding: 3px;" type="submit" name="operation"
						value="<%=SubjectCtl.OP_CANCEL%>"></td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<td colspan="2" align="right"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=SubjectCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit" name="operation" style="padding: 3px;"
						value="<%=SubjectCtl.OP_RESET%>">&emsp;&emsp;</td>
				</tr>
			<!-- </table> -->
			<%
				}
			%>
			</tr>
				
				</table>
		</center>
	</form>
  </body>
 <%@ include file="Footer.jsp" %> 
</html>