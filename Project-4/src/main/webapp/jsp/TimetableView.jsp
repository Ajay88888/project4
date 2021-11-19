<%@page import="in.com.raysproject.ctl.TimetableCtl"%>
<%@page import="in.com.raysproject.ctl.SubjectCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time table View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script>
var d = new Date();
function disableSunday(d){
	  var day = d.getDay();
	  if(day == 0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
}
/* 
$( function() {
	  $( "#datepicker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'1980:2030',
		  dateFormat:'dd/mm/yyyy',
		  minDate:0,
		  beforeShowDay : disableSunday
		  
	  });
} ); */

$(function() {
		$("examdate").datepicker({
		beforeShowDay: DisableSunday ,
		changeMonth : true ,
		changeYear : true,
		yearRange : '1980:2030' ,
		dateFormat: 'dd-mm-yy',
		minDate: 0
		});
});
</script>
</head>
<body>
	<form action="<%=ORSView.TIMETABLE_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.TimetableBean"
			scope="request"></jsp:useBean>
		<center>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<%
				List l = (List) request.getAttribute("courseList");
			List li = (List) request.getAttribute("subjectList");
			%>


			<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 30px;">Update Timetable</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 30px;">Add Timetable</h1>
			<%
				}
			%>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<table>
				<tr>
					<th align="left">Course<span style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l)%>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("CourseId", request)%></font></td>


				</tr>
				<tr>
					<th align="left">Subject<span style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), li)%>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("SubjectId", request)%></font></td>


				</tr>
				<tr>
					<th align="left">Semester<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
						map.put("1", "1");
						map.put("2", "2");
						map.put("3", "3");
						map.put("4", "4");
						map.put("5", "5");
						map.put("6", "6");
						map.put("7", "7");
						map.put("8", "8");
						map.put("9", "9");
						map.put("10", "10");
						String htmlList = HTMLUtility.getList("semesterId", bean.getSemester(), map);
						%><%=htmlList%>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("SemesterId", request)%></font></td>

				</tr>
				<tr>
					<th align="left">Exam Date<span style="color: red;">*</span></th>
					<td><input type="text" name="examDate" id="datepicker"
						size="20" readonly="readonly" placeholder="Enter Exam Date"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("ExamDate", request)%></font></td>

				</tr>
				<tr>
					<th align="left">Exam Time<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map1 = new HashMap();
						map1.put("08:00 AM - 11:00 AM", "08:00 AM - 11:00 AM");
						map1.put("12:00PM - 3:00PM", "12:00PM - 3:00PM");
						map1.put("3:00PM - 6:00PM", "3:00PM - 6:00PM");
						String examList = HTMLUtility.getList("examId", String.valueOf(bean.getExamTime()), map1);
						%><%=examList%>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("ExamId", request)%></font></td>
				</tr>
				</tr>
				<tr>
					</td>
				</tr>
				<%
					if (bean.getId() > 0) {
				%>

				<tr>
					<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=TimetableCtl.OP_UPDATE%>">&emsp; <input
						style="padding: 3px;" type="submit" name="operation"
						value="<%=TimetableCtl.OP_CANCEL%>"></td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" style="padding: 3px;"
						value="<%=TimetableCtl.OP_SAVE%>">&emsp; <input
						type="submit" name="operation" value="<%=TimetableCtl.OP_RESET%>"
						style="padding: 3px;"></td>
				</tr>
				<%
					}
				%>

			</table>
		</center>
	</form>
</body>
<%@ include file="Footer.jsp" %>
</html>

