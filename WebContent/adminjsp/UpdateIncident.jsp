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
<title>Edit Incident</title>
<script type="text/javascript">
canvasCtx = null;
imageFile = null;
url = null;
flag=0;

window.onload = function () {
	canvasCtx = document.getElementById("panel").getContext("2d");
	document.getElementById("pic").onchange = function(event) {
		
		flag=1;
		
		this.imageFile = event.target.files[0];
		
		var reader = new FileReader();
		reader.onload =  function(event) {
			var img = new Image();
			img.onload = function() {
				drawImage(img);
			}
			img.src = event.target.result;
		}
		reader.readAsDataURL(this.imageFile);
	}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = img.width;
		this.canvasCtx.canvas.height = img.height;
		this.canvasCtx.drawImage(img,0,0);
		url = canvasCtx.canvas.toDataURL("image/png");
		//console.log(url);
		flag=1;
		setURL(url,flag);
	}
}

function setURL(url,flag)
{
	flag=flag;
	document.getElementById("hidden").value= url;
}
function validateIncident()
{
	if(confirm("Are you sure to update incident_id: "+document.editincident.incident_id.value+" ?"))
	{
		return true;
	}
	return false;
}
</script>
</head>
<body>
<%
	String msg=request.getParameter("msg");
	if(msg!=null)
		out.println(msg);
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
			out.println("<form action='../admin/UpdateIncident' method='post' enctype='multipart/form-data' name='editincident' onsubmit='return validateIncident()'>");	
			out.println("<table border='3' color='#333'>");
			out.println("<tr>");
			out.println("<td><input type='text' value='"+rs.getString("incident_id")+"' name='incident_id' readonly></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_lat")+"' name='incident_lat' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_long")+"' name='incident_long' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_category")+"' name='incident_category' ></td>");
			out.println("<td><img src='");
			out.println(rs.getString("incident_picture"));
			out.println("' height='40' width='100'>");
			out.println("<input type='file' id='pic' name='incident_picture'  ><canvas id='panel'></canvas></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_locality")+"' name='incident_locality' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_submitter")+"' name='incident_submitter' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_owner")+"' name='incident_owner' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_state")+"' name='incident_state' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_date_created")+"' name='incident_date_created' placeholder='yyyy-mm-dd hh:mm:ss'></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_date_closed")+"' name='incident_date_closed' placeholder='yyyy-mm-dd hh:mm:ss' ></td>");
			out.println("<td><input type='text' value='"+rs.getInt("incident_severity")+"' name='incident_severity'placeholder='enter the number' ></td>");
			out.println("<td><input type='text' value='"+rs.getString("incident_notes")+"' name='incident_notes' ></td>");
			out.println("<td><input type='text' value='"+rs.getInt("incident_votes")+"' name='incident_votes' placeholder='enter the number'></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan='14'><input type='submit' value='update'></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<input type='hidden' id='hidden' name='hidden'>");
			out.println("</form>");
		}
	}
%>
</body>
</html>