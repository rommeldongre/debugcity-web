<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>getCategory</title>

<script type="text/javascript">
function getCategory()
{
	xmlhttp=new XMLHttpRequest();
	var url = "service/GetCategory";
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var json = JSON.parse(xmlhttp.responseText);
				
				//alert(json.categorylist);
				if(json.categorylist!="")
					document.getElementById("categorylist").value='{"'+json.categorylist+'"}';
				else
					document.getElementById("categorylist").value="no category found.";
				}
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send();	
}
</script>

</head>
<body>
	<h1>getCategory</h1>
	<table>
			<tr>
				<td>Categories</td>
				<td><input type="text" id="categorylist" name="categorylist"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="Button" name="submit" id="submit"
					onclick="getCategory()" value="getCategory"></td>
			</tr>		
		</table>
</body>
</html>
