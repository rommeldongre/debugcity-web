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
<title>Delete Incident</title>
<script type="text/javascript">
function validateIncident()
{
	if(confirm("Are you sure to delete incident_id: "+document.deleteincident.incident_id.value+" ?"))
	{
		return true;
	}
	return false;
}
</script>
</head>
<body>
<%
 	String incident_id= request.getParameter("val");
	String connectionparams ="jdbc:mysql://localhost:3306/debugcity";
	String uname="root";
	String psword="root"; 
 	Connection con=null;
 	Class.forName("com.mysql.jdbc.Driver"); 
	con = DriverManager.getConnection (connectionparams, uname, psword); 
	String sql = "select * from incident";
	Statement stmt=(Statement) con.createStatement();
	ResultSet rs=stmt.executeQuery(sql); 
	while(rs.next())
	{
		if(rs.getString("incident_id").equals(incident_id))
		{
				out.println("<form action='../admin/DeleteIncident' method='post' name='deleteincident' onsubmit='return validateIncident()'>");	
				out.println("<table border='2' color='#333'>");
				out.println("<tr>");
				out.println("<td><input type='text' value='"+rs.getString("incident_id")+"' name='incident_id' readonly='readonly' /></td>");
				//out.println("<td>"+rs.getString("incident_id")+"</td>");
				out.println("<td>"+rs.getString("incident_lat")+"</td>");
				out.println("<td>"+rs.getString("incident_long")+"</td>");
				out.println("<td>"+rs.getString("incident_category")+"</td>");
				out.println("<td><img src='");
				out.println(rs.getString("incident_picture"));
				out.println("' height='40' width='100'></td>");
				out.println("<td>"+rs.getString("incident_locality")+"</td>");
				out.println("<td>"+rs.getString("incident_submitter")+"</td>");
				out.println("<td>"+rs.getString("incident_owner")+"</td>");
				out.println("<td>"+rs.getString("incident_state")+"</td>");
				out.println("<td>"+rs.getString("incident_date_created")+"</td>");
				out.println("<td>"+rs.getString("incident_date_closed")+"</td>");
				out.println("<td>"+rs.getInt("incident_severity")+"</td>");
				out.println("<td>"+rs.getString("incident_notes")+"</td>");
				out.println("<td>"+rs.getInt("incident_votes")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td colspan='15'><input type='submit' value='delete'></td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</form>");
			}
	}
%>
</body>
</html>