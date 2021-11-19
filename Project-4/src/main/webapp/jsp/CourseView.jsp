<%@page import="in.com.raysproject.ctl.CourseCtl"  %>
<%@page import="in.com.raysproject.util.DataUtility" %>
<%@page import="java.util.HashMap"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility" %>
<%@page import="in.com.raysproject.ctl.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course View</title>

</head>
<body>
	<form action="<%=ORSView.COURSE_CTL%>" method="POST">
<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.com.raysproject.bean.CourseBean" scope="request"></jsp:useBean>
	
	<center>
	
	<input type="hidden" name="id" value="<%=bean.getId() %>">
	<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
	<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
	<input type="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime()) %>">
	<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime()) %>">
	
		<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 30px;">Update Course</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 30px;">Add Course</h1>
			<%
				}
			%>	
	<h2>
	<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
	
	</h2>
	<h2>
	<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
	</h2>
	
	<table>
	<tr>
	    <th align="left" >Course<span style="color:red;">*</span></th>
	    <td><input type="text" name="courseName" size="20" placeholder="Enter course "
		value="<%=DataUtility.getStringData(bean.getCourseName())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseName", request)%></font>
		</td>
	</tr>
	
	<tr>
		<th align="left" >Description<span style="color: red;">*</span></th>
		<td><textarea name="description"   placeholder="Enter Description" style="width: 177px;height:30px; resize: none;"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
	
	<tr>
					<th align="left">Duration<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("1 Year", "1 Year");
							map.put("2 Year", "2 Year");
							map.put("3 Year", "3 Year");
							map.put("4 Year", "4 Year");
							map.put("5 Year", "5 Year");
							
							String HtmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
						%> <%=HtmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%></font>
					</td>

				<%
						if (bean.getId() > 0) {
					%>
		</tr>
		
					<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" value="<%=CourseCtl.OP_UPDATE%>"
						style="padding: 3px;">&emsp; <input type="submit"
						name="operation" value="<%=CourseCtl.OP_CANCEL%>"
						style="padding: 3px;"></td>
				</tr>
				<%
					} else {
				%>
				<tr>	<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" value="<%=CourseCtl.OP_SAVE%>"
						style="padding: 3px;">&emsp; <input type="submit"
						name="operation" value="<%=CourseCtl.OP_RESET%>"
						style="padding: 3px;"></td>
				</tr>
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