<%--
	@author Shivam Gupta
 --%>
 
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dispaly Incident</title>
<script type="text/javascript">
function validateIncident()
{
	if(document.saveincident.incident_id.value == "")
	{
		alert("plz fill the incident_id");
		document.saveincident.incident_id.focus();
		return false;
	}
	

	return true;
	
}
function loadXMLDoc()
{
var xmlhttp;
var k=document.getElementById("incident_id").value;
var urls="../adminjsp/checkincidentid.jsp?ver="+k;

if (window.XMLHttpRequest)
 {
 xmlhttp=new XMLHttpRequest();
 }
else
 {
 xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
 }
xmlhttp.onreadystatechange=function()
 {
 if (xmlhttp.readyState==4)
   {
       document.getElementById("err").innerHTML=xmlhttp.responseText;
    }
 }
xmlhttp.open("GET",urls,true);
xmlhttp.send();
}
</script>
</head>
<body>
	<table border='2'>
		<tr>
				<th>incident_id</th>
				<th>incident_lat</th>
				<th>incident_long</th>
				<th>incident_category</th>
				<th>incident_picture</th>
				<th>incident_locality</th>
				<th>incident_submitter</th>
				<th>incident_owner</th>
				<th>incident_state</th>
				<th>incident_date_created</th>
				<th>incident_date_closed</th>
				<th>incident_severity</th>
				<th>incident_notes</th>
				<th>incident_votes</th>
				<th colspan='2'>Action</th>
		</tr>
		<%Iterator itr;%>
		<%List data=(List) request.getAttribute("UserData");
		for(itr = data.iterator(); itr.hasNext();){
		%>
		
		<tr>
			<% String s= (String)itr.next(); %>
			<td align="center"><%=s%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			
			<td align="center"><a href="../adminjsp/UpdateIncident.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Edit</a></td>
			<td align="center"><a href="../adminjsp/DeleteIncident.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Delete</a></td>
		<%}%>
		</tr>
	</table>
   <form name="saveincident" method="post" action="../admin/SaveIncident" onsubmit="return validateIncident(this);">
  <table>
   
   			<tr>
  				<td>incident_id</td>
  				<td><input type="text" name="incident_id" id="incident_id" onkeyup="loadXMLDoc();"></td>
      		</tr>
  		 	<tr>
				<td>incident_lat</td>
				<td><input type="text" name="incident_lat"></td>
			</tr>
			<tr>
				<td>incident_long</td>
				<td><input type="text" name="incident_long"></td>
			</tr>
			<tr>
				<td>incident_category</td>
				<td><input type="text" name="incident_category"></td>
			</tr>
			<tr>
				<td>incident_picture</td>
				<td><input type="file" name="incident_picture"  ></td>
			</tr>
			<tr>
				<td>incident_locality</td>
				<td><input type="text" name="incident_locality"></td>
			</tr>
			<tr>
				<td>incident_submitter</td>
				<td><input type="text" name="incident_submitter"></td>
			</tr>
			<tr>
				<td>incident_owner</td>
				<td><input type="text" name="incident_owner"></td>
			</tr>
			<tr>
				<td>incident_state</td>
				<td><input type="text" name="incident_state"></td>
			</tr>
			<tr>
				<td>incident_date_created</td>
				<td><input type="text" placeholder="yyyy-mm-dd hh:mm:ss" name="incident_date_created"></td>
			</tr>
			<tr>
				<td>incident_date_closed</td>
				<td><input type="text" placeholder="yyyy-mm-dd hh:mm:ss" name="incident_date_closed"></td>
			</tr>
			<tr>
				<td>incident_severity</td>
				<td><input type="text" placeholder="enter the number" name="incident_severity"></td>
			</tr>
			<tr>
				<td>incident_notes</td>
				<td><input type="text" name="incident_notes"></td>
			</tr>
			<tr>
				<td>incident_votes</td>
				<td><input type="text" placeholder="enter the number" name="incident_votes"></td>
			</tr>			
   		<tr><td colspan="2"><span id="err"></span></td></tr>
		
   </table>
   </form>
</body>
</html>
