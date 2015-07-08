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
<title>Delete Category</title>
<script type="text/javascript">
function validateCategory()
{
	if(confirm("Are you sure to delete cat_name: "+document.deletecategory.cat_name.value+" ?"))
	{
		return true;
	}
	return false;
}
</script>
</head>
<body>
<%
 	String cat_name= request.getParameter("val");
	String connectionparams ="jdbc:mysql://localhost:3306/debugcity";
	String uname="root";
	String psword="root"; 
 	Connection con=null;
 	Class.forName("com.mysql.jdbc.Driver"); 
	con = DriverManager.getConnection (connectionparams, uname, psword); 
	String sql = "select * from category";
	Statement stmt=(Statement) con.createStatement();
	ResultSet rs=stmt.executeQuery(sql); 
	while(rs.next())
	{
		if(rs.getString("cat_name").equals(cat_name))
		{
			out.println("<form action='../admin/DeleteCategory' method='post' name='deletecategory' onsubmit='return validateCategory()'>");	
			out.println("<table border='2' color='#333'>");
			out.println("<tr>");
			out.println("<td><input type='text' value='"+rs.getString("cat_name")+"' name='cat_name' readonly='readonly' /></td>");
			out.println("<td>"+rs.getString("cat_desc")+"</td>");
			out.println("<td>"+rs.getString("cat_parent")+"</td>");
			out.println("<td>"+rs.getString("cat_child")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan='4'><input type='submit' value='delete'></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		}
	}
%>
</body>
</html>