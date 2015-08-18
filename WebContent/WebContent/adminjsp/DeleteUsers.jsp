<%--
	@author Shivam Gupta
 --%>
 
 
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">	
function validateUser()
{
	if(confirm("Are you sure to delete user_id: "+document.deleteuser.user_id.value+" ?"))
	{
		return true;
	}
	return false;
}

</script>
</head>
<body>
<%
 	String user_id= request.getParameter("val");
	String connectionparams ="jdbc:mysql://localhost:3306/debugcity";
    String uname="root";
	String psword="root"; 
 	Connection con=null;
 	Class.forName("com.mysql.jdbc.Driver"); 
	con = DriverManager.getConnection (connectionparams, uname, psword); 
	String sql = "select * from users";
	Statement stmt=(Statement) con.createStatement();
	ResultSet rs=stmt.executeQuery(sql); 
	while(rs.next())
	{
		if(rs.getString("user_id").equals(user_id))
		{
			out.println("<form action='../admin/DeleteUsers' method='post' name='deleteuser' onsubmit='return validateUser()'>");	
			out.println("<table border='2' color='#333'>");
			out.println("<tr>");
			out.println("<td><input type='text' value='"+rs.getString("user_id")+"' name='user_id' readonly='readonly' /></td>");
			out.println("<td>"+rs.getString("user_full_name")+"</td>");
			out.println("<td>"+rs.getString("user_mobile")+"</td>");
			out.println("<td>"+rs.getString("user_location")+"</td>");
			out.println("<td>"+rs.getString("user_auth")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<input type='submit' value='delete'>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		}
	}
%>
</body>
</html>