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
<title>Display Users</title>

  
 <script type="text/javascript">
 function validateUser()
 {
 	if(document.saveusers.user_id.value == "")
 	{
 		alert("plz fill the user_id");
 		document.saveusers.user_id.focus();
 		return false;
 	}
 	

 	return true;
 	
 }
function loadXMLDoc()
{
var xmlhttp;
var k=document.getElementById("user_id").value;
var urls="../adminjsp/checkuserid.jsp?ver="+k;

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
				<th>user_id</th>
				<th>user_full_name</th>
				<th>user_mobile</th>
				<th>user_location</th>
				<th>user_auth</th>
				<th colspan='2'>Action</th>
		</tr>
		<%Iterator itr;%>
		<%List data=(List) request.getAttribute("UserData");
		for(itr = data.iterator(); itr.hasNext();){
		%>
		
		<tr>
			<% String s= (String)itr.next(); %>
			<td align="center"><%= s%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><a href="../adminjsp/UpdateUsers.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Edit</a></td>
			<td align="center"><a href="../adminjsp/DeleteUsers.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Delete</a></td>
		<%}%>
		</tr>
	</table>
  <form name="saveusers" method="post" action="../admin/SaveUsers" onsubmit="return validateUser(this);">
        <table><tr><td>
    	user_id</td><td><input type="text" name="user_id" id="user_id" onkeyup="loadXMLDoc();">
      	</td>
      	</tr>
        <tr>
				<td>user_full_name</td>
				<td><input type="text" name="user_full_name"></td>
			</tr><tr><td colspan="2"></td></tr>
       	<tr>
		<tr>
				<td>user_mobile</td>
				<td><input type="text" name="user_mobile"></td>
			</tr>
			<tr>
				<td>user_location</td>
				<td><input type="text" name="user_location"></td>
			</tr>
			<tr>
				<td>user_auth</td>
				<td><input type="text" name="user_auth"></td>
			</tr>
			<tr><td colspan="2"><span id="err"></span></td></tr>
		</table>
   
</body>
</html>
