<%--
	@author Shivam Gupta
 --%>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Admin</title>
</head>
<body>
<%
 String msg=request.getParameter("msg");
 
 if(msg!=null)
 	out.println(msg);
 %>
	<h1>DebugCity v1</h1>
	<dl>
		<dt><b>:::Database Schema:::</b></dt>
			<dd><br><a href="../admin/DisplayIncident">Incident</a></dd>
			<dd><a href="../admin/DisplayCategory">Category</a></dd>
			<dd><a href="../admin/DisplayUsers">Users</a></dd>
	</dl>
</body>
</html>