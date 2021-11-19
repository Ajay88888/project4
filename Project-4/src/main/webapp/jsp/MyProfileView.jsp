<%@page import="in.com.raysproject.ctl.MyProfileCtl" %>
<%@page import="in.com.raysproject.util.HTMLUtility" %>
<%@page import="in.com.raysproject.util.DataUtility" %>
<%@page import="in.com.raysproject.util.ServletUtility" %>
<%@page import="java.util.HashMap" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MY Profile</title>
<style>
.p1 {
	font-size: 18px;
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

</style>
<script>
	function phoneno() {
		$('#phone').keypress(function(e) {
			var a = [];
			var k = e.which;

			for (i = 48; i < 58; i++)
				a.push(i);

			if (!(a.indexOf(k) >= 0))
				e.preventDefault();
		});
	}
</script>
</head>
<body>
<form action="<%=ORSView.MY_PROFILE_CTL%>"method="post">
<%@include file="Header.jsp" %>
<script type="text/javascript" src="../jss/calendar.js"></script>
<jsp:useBean id="bean" class="in.com.raysproject.bean.UserBean" scope="request"></jsp:useBean>

<center>
  <h1>My Profile</h1>
  <h2>
     <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
     
  </h2>
  <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
  <input type="hidden" name="createdDateTime" value="<%=bean.getCreatedDatetime()%>">
  <input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDatetime()%>">
 
 <table>
 <tr>
    <th align="left" class="p1">LoginId<span style="color:red">*</span></th>
    <td><input type="text" name="login" placeholder="Enter LoginId" size="20"  value="<%=DataUtility.getStringData(bean.getLogin()) %>"readonly="readonly"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("login", request) %></font></td>
    </tr>
  <tr>
  <th align="left" class="p1">FirstName<span style="color:red">*</span></th>
    <td><input type="text" name="firstName" placeholder="Enter First Name" size="20"  value="<%=DataUtility.getStringData(bean.getFirstName()) %>"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("firstName", request) %></font></td>
    </tr>  
    <tr>
    <th align="left" class="p1">LastName<span style="color:red">*</span></th>
    <td><input type="text" name="lastName" placeholder="Enter Last Name" size="20"  value="<%=DataUtility.getStringData(bean.getLastName()) %>"></td>
  <td>  <font color="red"><%=ServletUtility.getErrorMessage("lastName", request) %></font></td>
    </tr>

        <tr>
		<th align="left" class="p1">Gender<span style="color: red;">*</span></th>
		<td><input type="text" name="gender"  size="20"
		 placeholder="Enter Gender" value="<%=DataUtility.getStringData(bean.getGender())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
        <tr>
          <th align="left" class="p1">MobileNo<span style="color:red">*</span></th>
    <td><input type="text" name="mobileNo" placeholder="Enter MobileNo" size="20"  value="<%=DataUtility.getStringData(bean.getMobileNo()) %>"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
    </tr>
    <tr>
      
      <th align="left" class="p1">Date Of Birth<span style="color:red">*</span></th>
    <td><input type="text" name="dob" readonly="readonly" size="20"  value="<%=DataUtility.getDateString(bean.getDob()) %>"></td>

  <!-- <a href="javascript:getCalendar(document.forms[0].dob);">
  <img alt="Calender" src="../img/cal.jpg" width="16" height="15" border="0">
  </a> -->
  
 <td>   <font color="red"><%=ServletUtility.getErrorMessage("dob", request) %></font></td>
    </tr>
    <h2>
    <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
    </h2>
    
    <tr>
      <th></th>
      <td colspan="2" align="center"><input type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>">&nbsp;
      <input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>">&nbsp;</td>
	</tr>
	
 </table>
 
 </center>
 </form>
 <%@ include file="Footer.jsp" %>
</body>
</html>