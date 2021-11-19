<%@page import="in.com.raysproject.ctl.MarksheetListCtl" %>
<%@page import="in.com.raysproject.ctl.BaseCtl" %>
<%@page import="in.com.raysproject.util.ServletUtility" %>
<%@page import="in.com.raysproject.util.HTMLUtility" %>
<%@page import="in.com.raysproject.util.DataUtility" %>
<%@page import="in.com.raysproject.bean.MarksheetBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List View</title>
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
	<h1 style="font-size: 30px;">Marksheet List</h1>
		<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
		<jsp:useBean id="bean" class="in.com.raysproject.bean.MarksheetBean"
			scope="request"></jsp:useBean>
			
		<%
		 List list1=(List)request.getAttribute("rollNoList"); 
		%>
	<form action="<%=ORSView.MARKSHEET_LIST_CTL %>"method="post">
	<%
	     int pageNo=ServletUtility.getPageNo(request);
	     int pageSize=ServletUtility.getPageSize(request);
	     int index=((pageNo-1)*pageSize)+1;
	     int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
	     
	     List list=ServletUtility.getList(request);
	     Iterator<MarksheetBean> it=list.iterator();
	     if (list.size() != 0) {
     %>
	
	<table width="100%">
	
		<tr>
		  <td align="center"><label class="p1">Roll No : </label> <%=HTMLUtility.getList("rollNo", String.valueOf(bean.getRollNo()), list1)%>&emsp;
		  <label class="p1"> Student Name :</label> <input type="text"  name="name" placeholder="Enter Student Name" value="<%=ServletUtility.getParameter("name", request)%>">&emsp;
		  <input type="submit" name="operation" style="padding: 3px;" value="<%=MarksheetListCtl.OP_SEARCH%>">
		  <input type="submit" name="operation"	style="padding: 3px;" value="<%=MarksheetListCtl.OP_RESET%>" ></td>
		  </tr>
	</table><br>
	<table border="1" width="100%">
	<tr>
	   <th width="10%"><input type="checkbox" id="select_all" name="Select"> Select All</th>
	   <th>S.NO</th>
	   <th>Roll No</th>
	   <th>Name</th>
	   <th>Physics</th>
	   <th>Chemistry</th>
	   <th>Maths</th>
	   <th>Edit</th>
	   </tr>
	   <tr> 
	     <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></td>
	     </tr>
	     
	    <% 
	     while(it.hasNext()){
	    	 
	     bean=it.next();
	     
	     %>
	     <tr>
	       <td align="center"><input type="checkbox" name="ids" class="checkbox" value="<%=bean.getId()%>"></td>
	       <td align="center"><%=index++%></td>
	       <td align="center"><%=bean.getRollNo() %></td>
	   		<td align="center"><%=bean.getName() %></td>
	   		<td align="center"><%=bean.getPhysics() %></td>
	   		<td align="center"><%=bean.getChemistry() %></td>
	   		<td align="center"><%=bean.getMaths() %></td>
			<td align="center"><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
			</tr>
			
			<%
	    	 }
			%>	   
	</table>
	<table width="100%">
	<tr>
	  <td><input type="submit" name="operation"  style="padding: 3px;" value="<%=MarksheetListCtl.OP_PREVIOUS%>"<%=pageNo > 1 ? "" : "disabled"%>></td>
	  <td><input type="submit" name="operation"  style="padding: 3px;" value="<%=MarksheetListCtl.OP_NEW%>">  </td>
	 <td><input type="submit" name="operation"  style="padding: 3px;" value="<%=MarksheetListCtl.OP_DELETE%>"> </td>
	 <td><input type="submit" name="operation" style="padding: 3px;" value="<%=MarksheetListCtl.OP_BACK %>"></td>
	 <td align="right"><input type="submit" name="operation"  style="padding: 3px;" value="<%=MarksheetListCtl.OP_NEXT%>"<%=(nextPageSize != 0) ? "" : "disabled"%>> </td>
	  </tr>
	</table>
		<%
	     	}
				if (list.size() == 0) {
			%>
			<br><br>
			<input type="submit" name="operation"
				value="<%=MarksheetListCtl.OP_BACK%>">
			<%
				}
			%>
		
	
	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">
	
	</form>
	
	</center>
	<!-- <div class="footer">
<hr>
<center> 
  <h4>
  	<i><b>&#169;RAYS Technologies</b></i></div>
  	
  </h4> -->
  </center>
 <%@ include file="Footer.jsp" %>
</body>
</html>