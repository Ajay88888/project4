<%@page import="in.com.raysproject.ctl.UserRegistrationCtl"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',
			dateFormat : 'dd/mm/yy',
			endDate : '-18y',
				maxDate:0
		});
	});
</script>
</head>
<body>
    <form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
      
        <jsp:useBean id="bean" class="in.com.raysproject.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1 style="font-size: 30px;">User Registration</h1>

            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table>

                <tr>
                    <th align="left" class="p1">First Name<span style="color:red;">*</span></th>
                    <td><input type="text" name="firstName" size="20"  class="p2" placeholder="please enter name" 
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
						<td style="position: fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left" class="p1">Last Name<span style="color:red;">*</span></th>
                    <td><input type="text" name="lastName" size="20" class="p2" placeholder="please  enter last name"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
						<td style="position: fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left" class="p1">LoginId<span style="color:red;">*</span></th>
                    <td><input type="text" name="login" size="20"  class="p2"
                        placeholder="Must be Email ID"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
						<td style="position: fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                <tr>
					<th align="left" class="p1">MobileNo<span style="color:red;">*</span></th>
					<td><input type="text" name="mobile" size="20"  maxlength="10" class="p2" placeholder="please enter mobile no"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
						<td style="position: fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
                <tr>
                    <th align="left" class="p1">Password<span style="color:red;">*</span></th>
                    <td><input type="password" name="password" size="20" class="p2" placeholder="please enter password"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
						<td style="position: fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr>
                    <th align="left" class="p1">Confirm Password<span style="color:red;">*</span></th>
                    <td><input type="password" name="confirmPassword" size="20" class="p2" placeholder="re-enter password"
                        value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
						<td style="position: fixed;"><font
                        color="red"> <%=ServletUtility
                    .getErrorMessage("confirmPassword", request)%></font></td>
                </tr>
                <tr>
                    <th align="left" class="p1">Gender <span style="color:red;">*</span></th>
                    <td>
                        <% 
                            HashMap map = new HashMap();
                        	/* map.put("","---------select----------"); */
                            map.put("Male", "Male");
                            map.put("Female", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                    map);
                        %> <%=htmlList%></td>
						<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>

                    </td>
                </tr>

                  <tr>
                    <th align="left" class="p1">Date Of Birth<span style="color:red;">*</span> </th>
                    <td><input type="text"  readonly="readonly" name="dob" id="datepicker"  size="20" class="p2"
                        placeholder="dd/MM/yyyy"
                        value="<%=DataUtility.getDateString(bean.getDob())%>"> </td>
						<td style="position: fixed;" >
                   <font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr> 
                
         <%-- <tr> 
           <th align="left" class ="p1">Date Of Birth</th>
     <td><input type="text" name="dob" class="form-control" id="datepicker" size="20" class="p2"
     value="<%=DataUtility.getDateString(bean.getDob())%>" placeholder="yyyy/MM/dd">
     <a htef="javascript:getCalendar(document.forms[0].dob);"></a>&nbsp;
    </td></tr> --%>
                  
                
                
                <tr> 
                    <th></th>
                    <td colspan="2" align="center">
                         <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>">
                    &nbsp;
                        &nbsp; <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>
            </center>
    </form>
    
   <%@ include file="Footer.jsp"%>
</body>
</html>