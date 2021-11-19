<%@page import="in.com.raysproject.ctl.CollegeCtl"  %>
<%@page import="in.com.raysproject.util.DataUtility" %>
<%@page import="in.com.raysproject.util.ServletUtility" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add College</title>
</head>
<script>        
           function phoneno(){          
            $('#phone').keypress(function(e) {
                var a = [];
                var k = e.which;

                for (i = 48; i < 58; i++)
                    a.push(i);

                if (!(a.indexOf(k)>=0))
                    e.preventDefault();
            });
        }
       </script>


</head>
<body>
	
<form action="CollegeCtl" method="post">
<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.com.raysproject.bean.CollegeBean" scope="request"></jsp:useBean>
	
	<center>

	 <%
	  	if(bean.getId()>0){
	  	
	 %>
	 <h1 style="font-size:30px;">Update College</h1>
	 <%
	    } else {
	 %>
	 <h1 style="font-size:30px;">Add College</h1>
	 <%
	    }
	 %>
	
	
	
	<h2>
	<font color="green" ><%=ServletUtility.getSuccessMessage(request) %></font>
	
	</h2>
	<h2>
	<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
	
	</h2>
	<center>
	
	<input type="hidden" name="id" value="<%=bean.getId() %>">
	<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
	<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
	<input type="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime()) %>">
	<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime()) %>">
	
	</center>
 <table>
 <tr>
   <th align="left" >Name<span style="color:red">*</span></th>
   <td><input type="text" name="name"  size="20" placeholder="Enter Name" 
   			value="<%=DataUtility.getStringData(bean.getName()) %>"></td>
<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name",request) %></font></td>
   </tr>
   <tr>
     <th align="left" >Address<span style="color:red">*</span></th>
     <td> <input type="text" name="address"  size="20" placeholder="Enter Address" 
     		value="<%=DataUtility.getStringData(bean.getAddress()) %>"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request) %></font></td>
     </tr>
     <tr>
     <th align="left" >State<span style="color:red">*</span></th>
     <td> <input type="text" name="state"  size="20" placeholder="Enter State" 
     		value="<%=DataUtility.getStringData(bean.getState()) %>"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state", request) %></font></td>
     </tr>
     <tr>
     <th align="left" >City<span style="color:red">*</span></th>
     <td> <input type="text" name="city"  size="20" placeholder="Enter City" 
     		value="<%=DataUtility.getStringData(bean.getCity()) %>"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("city", request) %></font></td>
     </tr>
     <tr>
     <th align="left" >PhoneNo<span style="color:red">*</span></th>
     <td> <input type="text" name="phoneNo"  size="20" placeholder="Enter PhoneNo" 
     		value="<%=DataUtility.getStringData(bean.getPhoneNo()) %>"></td>
     <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request) %></font></td>
     </tr>
     <%
       if(bean.getId()>0){
       
       %>
     <tr>
       <th></th>
       <td colspan="2" align="right"><input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>" style="padding: 3px;">
       &emsp; <input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>" style="padding: 3px;">&emsp;&emsp;</td>
       </tr>
       
       <%
       }else{
       %>
       <tr><td colspan="2" align="right">
       <input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>" style="padding: 3px;">&emsp;
       <input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>" style="padding: 3px;">&emsp;&emsp;
       </td>
       </tr>
       <% } %>
 </table>	
	</center>
</form>

<%@ include file="Footer.jsp" %>
</body>
</html>
