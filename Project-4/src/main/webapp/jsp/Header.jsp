<%@page import="in.com.raysproject.bean.RoleBean"%>
<%@page import="in.com.raysproject.ctl.LoginCtl"%>
<%@page import="in.com.raysproject.bean.UserBean"%>
<%@page import="in.com.raysproject.ctl.ORSView"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Display month &amp; year menus</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true, 
      format: 'mm/dd/yyyy', 
      yearRange : '1970:2030'
    });
  } );
  </script>



<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script> -->
  

</head>
<%
    UserBean userBean = (UserBean) session.getAttribute("user");

    boolean userLoggedIn = userBean != null;

    String welcomeMsg = "Hi, ";

    if (userLoggedIn) {
        String role = (String) session.getAttribute("role");
        welcomeMsg += userBean.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
%>
		

<table width="100%" border="0">
    <tr>
        <td width="90%" ><a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> |
            <%
            if (userLoggedIn) {
        %> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>

            <%
                } else {
            %> <a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a> <%
     }
 %></td>
        <td rowspan="2">
            
   		<center>         
            
             <h1 align="right">   <img src="<%=ORSView.APP_CONTEXT%>/image/rays.png" width="250"
                    height="70"></h1>
            </center>
        </td> 

    </tr>
    
    <tr>
        <td >
            <h3>
                <%=welcomeMsg%></h3>
        </td>
    </tr>
    

    <%
        if (userLoggedIn) {
    %>

    <tr>
        <td colspan="2" >
        
        <a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a> |
            <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet Merit
                List</b>
        </a> | <a href="<%=ORSView.MY_PROFILE_CTL%>"><b>MyProfile</b></a> | <a
            href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change Password</b></a>    
            
            <%
            if (userBean.getRoleId() == RoleBean.ADMIN) {
        %>| <a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a> | 
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a> |
           <a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a> | 
           <a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a> | 
           <a href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a> | 
           <a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a> | 
          <a  href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a> | 
          <a  href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a> | 
           <a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a> | 
           <a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a> |
			<a	href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a> | 
			<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a> |
          <a  href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a> | 
         <a   href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a> | 
          <a  href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a> | 
          <a  href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a>| 
			<a	href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a>| 
			<a	href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a> | 
			<a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><b>Java Doc</b></a> |  <%
     }

 %></td>
    
    </tr>
    <%
        }
    %>
</table>
<hr>
</html>