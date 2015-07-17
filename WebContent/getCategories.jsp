<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>getCategories</title>

<script type="text/javascript">
function getCategories()
{
	xmlhttp=new XMLHttpRequest();
	var url = "service/GetCategories";
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var json = JSON.parse(xmlhttp.responseText);

				document.getElementById("count").value=json.count;
				if(json.count!=0)
					document.getElementById("categories").value=json.categories;
				else
					document.getElementById("categories").value="";
				
				document.getElementById("returncode").value=json.returnCode;
				
				if(json.returnCode!=0)
				{
					document.getElementById("errorstring").value=json.errorString;		
				}
				else
					document.getElementById("errorstring").value="";
		}
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send();	
}
</script>

</head>
<body>
	<h1>getCategories</h1>
	<table>
			<tr>
				<td colspan="2"><input type="Button" name="submit" id="submit"
					onclick="getCategories()" value="getCategories"></td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
			</tr>	
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Categories</td>
				<td><input type="text" id="categories" name="categories"></td>
			</tr>
			<tr>
				<td>Count</td>
				<td><input type="text" id="count" name="count"></td>
			</tr>
			<tr>
				<td>ReturnCode</td>
				<td><input type="text" id="returncode" name="returncode"></td>
			</tr>
			<tr>
				<td>ErrorString</td>
				<td><input type="text" id="errorstring" name="errorstring"></td>
			</tr>
				
		</table>
</body>
</html>
