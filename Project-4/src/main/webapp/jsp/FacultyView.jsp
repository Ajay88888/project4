<%@page import="in.com.raysproject.ctl.FacultyCtl"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
		$("#datepicker" ).datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',
			dateFormat : 'mm/dd/yy',
			endDate : '-18y',
		});
	});

	/* $(function() {
		$("examdate").datepicker({
		beforeShowDay: DisableSunday ,
		changeMonth : true ,
		changeYear : true,
		yearRange : '1980:2030' ,
		dateFormat: 'dd-mm-yy',
		minDate: 0
		});
}); */

	
</script>
<form action=<%=ORSView.FACULTY_CTL%> method="POST">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.FacultyBean"
			scope="request"></jsp:useBean>
				<center>
		<div align="center">
			<%
				if (bean.getId() > 0) {
					System.out.println("id ....................."+bean.getId());
			%>

			<h1 style="font-size: 30px; margin-top: 0;">Update Faculty</h1>
			<%
				} else {
					System.out.println("id ............"+bean.getId());
			%>

			<h1 style="font-size: 30px; margin-top: 0">Add Faculty</h1>
			<%
				}
			%>
			<h2 style="margin-top: -10px;">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"><input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<%
				List ll = (List) request.getAttribute("collegeList");
				List lli = (List) request.getAttribute("courseList");
				List llist = (List) request.getAttribute("subjectList");
			%>
			<div>
	<table>
		<tr>
		   <th align="left" >First Name<span style="color: red;">*</span></th>
			  <td><input type="text" name="firstName" 
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red" > <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<th align="left" >Last Name<span style="color: red;">*</span></th>

					<td><input type="text" name="lastName" 
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>

				<tr>
					<th align="left" >Date Of Joining<span
						style="color: red;">*</span></th>
					<td><input type="text" readonly="readonly" name="dateOfJoining" 
						id="datepicker"  placeholder="Enter Joining Date"
						value="<%=DataUtility.getDateString(bean.getDateOfJoining())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dateOfJoining", request)%></font></td>

				</tr>
				<tr>
					<th align="left" >Qualification<span
						style="color: red;">*</span></th>
					<td><input type="text" name="qualification" 
						size="20" placeholder="Enter Qualification"
						value="<%=DataUtility.getStringData(bean.getQualification())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("qualification", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left" >Login Id<span
						style="color: red;">*</span></th>
					<td><input type="text" name="login" 
						size="20" placeholder="Enter your Login Id"
						value="<%=DataUtility.getStringData(bean.getLoginId())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<%-- 	<tr>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr> --%>
				<tr>
					<th align="left" >Mobile No<span style="color: red;">*</span></th>
					<td><input type="text" name="mobileNo"  maxlength="10" size="20" 
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
			
				 <tr>
					<th align="left" >Gender<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Female", "Female");
							map.put("Male", "Male");
							String HtmlList = HTMLUtility.getList("gender", String.valueOf(bean.getGender()), map);
						%><%=HtmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left" >College Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), ll)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
					<th align="left" >Course Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), lli)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr> 

				<tr>
					<th align="left" >Subject Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), llist)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
				</tr>
				
			<%-- 	<label >College Name</label><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), ll)%>&emsp;
				
				
				<tr><label >Course Name</label><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), lli)%>
				</tr> --%>
				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<td></td>
					<td colspan="1" align="center"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=FacultyCtl.OP_UPDATE%>"> &emsp;<input
						type="submit" name="operation" style="padding: 3px;"
						value="<%=FacultyCtl.OP_CANCEL%>"></td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<td></td>
					<td colspan="1" align="center"><input type="submit"
						name="operation" value="<%=FacultyCtl.OP_SAVE%>"
						style="padding: 3px;"> &emsp;<input type="submit"
						name="operation" value="<%=FacultyCtl.OP_RESET%>"
						style="padding: 3px;"></td>
				</tr>

				<%
					}
				%>
			</table>
			</div>
			</center>
	</form>
</body>
<%@ include file="Footer.jsp" %>
</html>