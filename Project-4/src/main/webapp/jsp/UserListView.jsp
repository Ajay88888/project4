<%@page import="in.com.raysproject.ctl.UserListCtl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.com.raysproject.util.ServletUtility"%>
<%@page import="in.com.raysproject.util.HTMLUtility"%>
<%@page import="in.com.raysproject.model.RoleModel"%>
<%@page import="in.com.raysproject.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserListView</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<base>
<style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

.p2 {
	padding: 5px;
	margin: 3px;
}

/* .footer {
	position: relative;
	left: 0;
	bottom: 0;
	width: 100%;
	text-align: center;
} */
</style>
</head>
<body>

	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean1" class="in.com.raysproject.bean.UserBean"
		scope="request"></jsp:useBean>
	<center>

		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
			<h1 style="font-size: 30px;">User List</h1>
			<center>
				<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
			</center>
			<center>
				<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
			</center>
			<jsp:useBean id="bean" class="in.com.raysproject.bean.UserBean"
				scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("roleList");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				RoleBean rbean1 = new RoleBean();
				RoleModel rmodel = new RoleModel();

				List list = ServletUtility.getList(request);
				System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" + list);
				Iterator<UserBean> it = list.iterator();
				if (list.size() != 0) {
			%>


			<table width=100%>
				<tr>
					<td align="center"><label class="p1">First Name </label> <input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">

						&emsp;<label class="p1">Login Id</label> <input type="text"
						name="login" placeholder="Enter LoginId"
						value="<%=ServletUtility.getParameter("login", request)%>">

						&emsp;<label class="p1">Role </label><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), list1)%>
						&emsp; <input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>" style="padding: 5px;">&emsp;
						<input type="submit" name="operation"
						value="<%=UserListCtl.OP_RESET%>" style="padding: 5px;"></td>
					</td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th width="10%"><input type="checkbox" id="select_all"
						name="Select"> Select All</th>
					<th>S.No</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>Role</th>
					<th>DOB</th>
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
							System.out.print("RoleIdddddddddddddddddddddddd"+bean.getRoleId());
							RoleBean rbean = rmodel.findbypk(bean.getRoleId());
							System.out.print("roleNameeeeeeeeeeeeeeeeeeee"+rbean.getName());
				%>

				<tr>
					<td><input type="checkbox" name="ids" class="checkbox"
						value="<%=bean.getId()%>"
						<%if (bean.getRoleId() == RoleBean.ADMIN) {%> <%="disabled"%>
						<%}%>></td>

					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getLogin()%></td>
					<td align="center"><%=bean.getGender()%></td>
					<td align="center"><%=rbean.getName()%></td>
 					<%
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String date = formatter.format(bean.getDob());
								/* 					System.out.println("aaaaaaaaaaaaaaaaaaaa"+bean.getFirstName()+" "+bean.getLastName()+" "+bean.getLogin()+" "+bean.getGender()+" "+rbean.getName());
								 */
					%>
					<td align="center"><%=date%></td>
					<td align="center"><a href="UserCtl?id=<%=bean.getId()%>"
						<%if (bean.getRoleId() == RoleBean.ADMIN) {%>
						onclick="return false;" <%}%>>Edit</a></td>
				</tr>
				<%
					}
				%>

			</table>

			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 3px;" value="<%=UserListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						style="padding: 3px;" class="button1" style="padding: 5px;"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 3px;" value="<%=UserListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 3px;" value="<%=UserListCtl.OP_NEXT%>"
						<%=(nextPageSize == 0) ? "" : "disabled"%>></td>


				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>

			<input type="submit" name="operation" style="padding: 3px;"
				value="<%=UserListCtl.OP_BACK%>">
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>

	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>