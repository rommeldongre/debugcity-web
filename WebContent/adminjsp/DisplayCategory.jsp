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
<title>Display Category</title>
<script type="text/javascript">
function validateCategory()
{
	if(document.savecategory.cat_name.value == "")
	{
		alert("plz fill the cat_name");
		document.savecategory.cat_name.focus();
		return false;
	}

	return true;
	
}function loadXMLDoc()
{
	var xmlhttp;
	var k=document.getElementById("cat_name").value;
	var urls="../adminjsp/checkcategoryname.jsp?ver="+k;

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
	}</script>
</head>
<body>
	<table border='2'>
		<tr>
				<th>cat_name</th>
				<th>cat_desc</th>
				<th>cat_parent</th>
				<th>cat_child</th>
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
			<td align="center"><a href="../adminjsp/UpdateCategory.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Edit</a></td>
			<td align="center"><a href="../adminjsp/DeleteCategory.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Delete</a></td>
		<%}%>
		</tr>
	</table>
   <form name=savecategory action="../admin/SaveCategory" method="post" onsubmit="return validateCategory(this);">
   <table>
   <tr>
				<td>cat_name</td>
				<td><input type="text" id="cat_name" name="cat_name" onkeyup="loadXMLDoc();"></td>
			</tr>
			<tr>
				<td>cat_desc</td>
				<td><input type="text" name="cat_desc"></td>
			</tr>
			<tr>
				<td>cat_parent</td>
				<td><input type="text" name="cat_parent"></td>
			</tr>
			<tr>
				<td>cat_child</td>
				<td><input type="text" name="cat_child"></td>
			</tr>
   	
   			<tr><td colspan="2"><span id="err"></span></td></tr>
		
   </table>
   </form>
</body>
</html>
