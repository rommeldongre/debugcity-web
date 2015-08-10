<%--
	@author Shivam Gupta
 --%>
 
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Category</title>
<script type="text/javascript">

function validateCategory()
{
	if(confirm("Are you sure to update cat_name: "+document.editcategory.cat_name.value+" ?"))
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
			out.println("<form action='../admin/UpdateCategory' method='post' name='editcategory' onsubmit='return validateCategory()'>");	
			out.println("<table border='2' color='#333'>");
			out.println("<tr>");
			out.println("<td><input type='text' value='"+rs.getString("cat_name")+"' name='cat_name' readonly></td>");
			out.println("<td><input type='text' value='"+rs.getString("cat_desc")+"' name='cat_desc'></td>");
			out.println("<td><input type='text' value='"+rs.getString("cat_parent")+"' name='cat_parent'></td>");
			out.println("<td><input type='text' value='"+rs.getString("cat_child")+"' name='cat_child'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan='4'><input type='submit' value='update'></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		}
	}
%>
</body>
</html>