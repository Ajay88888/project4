<%@page import="in.com.raysproject.ctl.StudentListCtl" %>
<%@page import="in.com.raysproject.util.ServletUtility" %>
<%@page import="in.com.raysproject.util.DataUtility" %>
<%@page import="in.com.raysproject.util.HTMLUtility" %>
<%@page import="in.com.raysproject.bean.StudentBean" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
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


</style>
</head>
<body>
	<%@include file="Header.jsp" %>
	<center>
	<h1 style="font-size:30px;">Student List</h1>
	<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.StudentBean"
			scope="request"></jsp:useBean>
	
	<form action="<%=ORSView.STUDENT_LIST_CTL%>"method="post">
	    <%
			List list1 = (List) request.getAttribute("collegeList");
		%>
		<%
		  int pageNo=ServletUtility.getPageNo(request);
		  int pageSize=ServletUtility.getPageSize(request);
		  int index=((pageNo-1)*pageSize)+1;
		  int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
		  
		  List list=ServletUtility.getList(request);
		  
		  Iterator<StudentBean> it=list.iterator();
		  if (list.size() != 0) {
		  
		  %>
		
		<table width="100%">
	<tr>
		<td align="center">
		<label class="p1"> First Name :</label> <input type="text" name="firstName" placeholder="Enter First Name"
		value="<%=ServletUtility.getParameter("firstName", request)%>">&emsp;
					
		<label class="p1">Email Id :</label> <input type="text" name="email" placeholder="Enter Email Id"
		value="<%=ServletUtility.getParameter("email", request)%>">&emsp;
		
		<label class="p1">College Name</label> <%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), list1)%>&emsp;
		
	   <%--  <label class="p1">College Name</label> &emsp;
	    <%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), list1)%>&emsp;&emsp; --%>
		
		
		<input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH%>" 
		style="padding: 5px;">&emsp;
		<input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET%>" 
		style="padding: 5px;"></td>
				
	</tr>
	
	</table>
	<br>
	
	<table border="1" width="100%">
		<tr>
		<th width="10%"><input type="checkbox" id="select_all" name="Select"> Select All</th>
		 <th>S.No.</th>
		 
		 
		 <th>First Name</th>
		 <th>Last Name</th>
		 <th>College Name</th>
		 <th>Date Of Birth</th>
		 <th>Mobile No.</th>
		 <th>Email ID</th>
		 <th>Edit</th>
		</tr>
		
		    
		  
			  
		  
		<% 	  while(it.hasNext()){
			  
			   bean=it.next();
		  
		  %>
		  <tr>
		  <td align="left"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean.getId()%>"></td>
		    <td align="center"><%=index++ %></td>
		    
		    
		    <td align="center"><%=bean.getFirstName() %></td>
		    <td align="center"><%=bean.getLastName() %></td>
		    <td align="center"><%=bean.getCollegeName() %></td>
		     <%
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String date = formatter.format(bean.getDob());
			%>
		    <%-- <td><%=bean.getDob() %></td> --%>
		    <td align="center"><%=date%>
		    <td align="center"><%=bean.getMobileNo() %></td>
		    <td align="center"><%=bean.getEmailId() %></td>
		      <td align="center"><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
		  </tr>
		  <%
		  	}
		  %>    
	</table>
	<table width="100%">
	  <tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=StudentListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=StudentListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=StudentListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"  style="padding: 5px;" 
					value="<%=StudentListCtl.OP_NEXT%>"<%=(nextPageSize != 0) ? "" : "disabled"%>> </td>
				</tr>
	</table>
		<%
				}
				if (list.size() == 0) {
			%>

			<input type="submit" name="operation"
				value="<%=StudentListCtl.OP_BACK%>">
			<%
				}
			%>
		
		
	<input type="hidden" name="pageNo" value="<%=pageNo %>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</center>
	<!-- <div class="footer">
<hr>
<center> 
  <h4>
  	<i><b>&#169;RAYS Technologies</b></i>
  	
  	</h4>
  </center>
</div> -->
</body>
<%@ include file="Footer.jsp" %>
</html>