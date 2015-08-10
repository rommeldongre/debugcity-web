<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>getLocations</title>

<script type="text/javascript">
function getLocations()
{
	var retok=document.getElementById("returntoken").value;
	var token=document.getElementById("token").value;
	
	if(document.getElementById("token").value=="")
		token=0;
	
	xmlhttp=new XMLHttpRequest();
	var url = "service/GetLocations";
	
	var getLocationsData=new Object();
		
	getLocationsData["token"]=token;
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);

				document.getElementById("count").value=json.count;
				if(json.count!=0)
					document.getElementById("locality").value=json.locality;
				else
					document.getElementById("locality").value="";
				
				document.getElementById("returncode").value=json.returnCode;
				
				document.getElementById("returntoken").value=json.returnToken;
				
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
	xmlhttp.send(JSON.stringify(getLocationsData));	
}
</script>

</head>
<body>
	<h1>getLocations</h1>
	<table>
			<tr>
				<td>Token</td>
				<td><input type="text" id="token" name="token" value=""></td>
			</tr>
			<tr>
				<td colspan="2"><input type="Button" name="submit" id="submit"
					onclick="getLocations()" value="getLocations"></td>
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
				<td>Count</td>
				<td><input type="text" id="count" name="count"></td>
			</tr>
			<tr>
				<td>Locality[]</td>
				<td><input type="text" id="locality" name="locality"></td>
			</tr>
			<tr>
				<td>ReturnToken</td>
				<td><input type="text" id="returntoken" name="returntoken"></td>
			</tr>
		
		</table>
</body>
</html>
