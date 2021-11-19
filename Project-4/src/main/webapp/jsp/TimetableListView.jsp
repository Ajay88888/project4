<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.com.raysproject.ctl.CollegeListCtl"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.bean.TimetableBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="in.com.raysproject.ctl.TimetableListCtl"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Timetable List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

/* .footer{
	position:relative;
	left:  0;
	bottom: 0;
	width: 100%;
	text-align:center;
} */
</style>
<script>

var d = new Date();
function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
}

$( function() {
	  $( "#datepicker" ).datepicker({
		  changeMonth : true,
		  changeYear : true,
		  yearRange : '1980:2030',
		  dateFormat: 'dd/MM/yyyy',
		//  minDate: 0,
		  beforeShowDay : disableSunday
		  
	  });
} );
</script>
<%@include file="Header.jsp" %>

	<center>
	<h1>Timetable List</h1>
	<center>
	<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request) %></font>
	</center>
	<center>
	<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request) %></font>
	</center>
	<jsp:useBean id="bean" class="in.com.raysproject.bean.TimetableBean" scope="request"></jsp:useBean>
	<form action=<%=ORSView.TIMETABLE_LIST_CTL %> method="post">
		<%
		List list1 = (List) request.getAttribute("courseList");
	    List list2=(List)request.getAttribute("subjectList");
		%>
		<%
		int pageNo       =  ServletUtility.getPageNo(request);
		int pageSize     =  ServletUtility.getPageSize(request);
		int index        =  ((pageNo-1)*pageSize) + 1 ;
		int nextPageSize =  DataUtility.getInt(request.getAttribute("nextListSize").toString());
		List list		 =  ServletUtility.getList(request);
		Iterator<TimetableBean> it=list.iterator();
		if(list.size()!=0){
		%>
		<table>
				<tr>
					<td><label class="p1">Course Name</label>
					<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), list1)%>&emsp;
					
					<label class="p1">Subject Name</label> 
					<%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), list2)%>&emsp;
					
					<label class="p1">Exam Date</label>
					 <input type="text" id="datepicker" name="examDate" readonly="readonly"
						placeholder="Enter Date"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>">&emsp; 
						
						<input type="submit" name="operation" style="padding: 5px;"
						value="<%=TimetableListCtl.OP_SEARCH%>"> 
					
						<input style="padding: 5px;" type="submit" name="operation"
						value="<%=TimetableListCtl.OP_RESET%>"></td>
				</tr>
				
			</table>
			<br>
			<table border="1px" width="100%">
				<tr>
					<th width="8%"><input type="checkbox" id="select_all"
						name="Select">  Select All</th>
					<th>S.NO</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Semester</th>
					<th>Exam Date</th>
					<th>Exam Time</th>
					<th>Edit</th>
				</tr>



				<%
					while (it.hasNext()) {
							TimetableBean bean1 = it.next();
				%>
				<tr>
					<td align="left"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean1.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean1.getCourseName()%></td>
					<td align="center"><%=bean1.getSubjectName()%></td>
					<td align="center"><%=bean1.getSemester()%></td>
					<td align="center"><%=DataUtility.getDateString(bean1.getExamDate())%></td>
					<td align="center"><%=bean1.getExamTime()%></td>
				<%-- 	<td align="center"><%=bean1.getDescription()%></td> --%>
					<td align="center"><a href="TimetableCtl?id=<%=bean1.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=TimetableListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=TimetableListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=TimetableListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=TimetableListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr>

				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=TimetableListCtl.OP_BACK%>">

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
