<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>getLocationVector</title>

<script type="text/javascript">
function getLocationVector()
{
	var location=document.getElementById("location").value;
	xmlhttp=new XMLHttpRequest();
	var url = "service/GetLocationVector";
	
	var getLocationVectorData=new Object();
		
	getLocationVectorData["location"]=location;
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
				document.getElementById("vector").value = JSON.stringify(json.locationVector);	
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
	xmlhttp.send(JSON.stringify(getLocationVectorData));	
}
</script>

</head>
<body>
	<h1>getLocationVector</h1>
	<table>
			<tr>
				<td>Location</td>
				<td><input type="text" id="location" name="location"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="Button" name="submit" id="submit"
					onclick="getLocationVector()" value="getLocationVector"></td>
			</tr>
			<tr>
				<td></td>	
				<td></td>
			</tr><tr>
				<td></td>	
				<td></td>
			</tr><tr>
				<td></td>	
				<td></td>
			</tr><tr>
				<td></td>	
				<td></td>
			</tr>
			<tr>
				<td>ReturnCode</td>
				<td><input type="text" id="returncode" name="returncode"></td>
			</tr>
			<tr>
				<td>ErrorString</td>
				<td><input type="text" id="errorstring" name="errorstring"></td>
			</tr>
			<tr>
				<td>Vector</td>
				<td><input type="text" id="vector" name="vector"></td>
			</tr>
		
		</table>
</body>
</html>
