<%@page import="in.com.raysproject.ctl.MarksheetMeritListCtl" %>
<%@page import="in.com.raysproject.util.ServletUtility" %>
<%@page import="in.com.raysproject.bean.MarksheetBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet Merit List View</title>
<style>
.p1 {
	font-size: 18px;
}
</style>

</head>
<body>
	<%@include file="Header.jsp" %>
	<center>
		<h1>Marksheet Merit List</h1>
		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL %>"method="post">
		<br>
		<table border="2" width="100%">
		<tr>
		  <th>S.NO</th>
		  <th>RollNo</th>
		  <th>Name</th>
		  <th>Physics</th>
		  <th>Chemistry</th>
		  <th>Maths</th>
		  <th>Total</th>
		  <th>Percentage(%)</th>
		 </tr>
		 <tr>
		   <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request) %></font></td>
		   </tr>
		   
		   <%
		   int pageNo=ServletUtility.getPageNo(request);
		   int pageSize=ServletUtility.getPageSize(request);
		   int index=((pageNo-1)*pageSize)+1;
		   
		   List list=ServletUtility.getList(request);
		   Iterator<MarksheetBean> it=list.iterator();
		   
		   while(it.hasNext()){
			   MarksheetBean bean=it.next();
		   
		   %>
		   <tr>
		   
		     <td align="center"><%=index++ %></td>
		     <td align="center"><%=bean.getRollNo() %></td>
		     <td align="center"><%=bean.getName() %></td>
		     <td align="center"><%=bean.getPhysics() %></td>
		     <td align="center"><%=bean.getChemistry() %></td>
		     <td align="center"><%=bean.getMaths() %></td>
		     <td align="center"><%
							int total = (bean.getChemistry() + bean.getPhysics() + bean.getMaths());
						%><%=total%></td>
			<td align="center">
						<%
							float percentage = ((total * 100) / 300);
						%> <%=percentage%></td>
		   </tr>
		   
		   <%
		     }
		   %>
		</table>
		<table>
			<tr>
			  <td align="right"><input type="submit" name="operation" value="<%=MarksheetMeritListCtl.OP_BACK %>"></td>
			</tr>
		 </table>
		 <input type="hidden" name="pageNo" value="<%=pageNo %>">
		 <input type="hidden" name="pageSize" value="<%=pageSize%>">
		 
		</form>
	</center>
	<!-- <div class="footer">
<hr>
<center> 
  <h4>
  	<i><b>&#169;RAYS Technologies</b></i></div>
  	
  </h4> -->
 <%@ include file="Footer.jsp" %>  
</body>

</html>