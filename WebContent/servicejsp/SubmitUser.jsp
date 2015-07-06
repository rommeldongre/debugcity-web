<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Testing JSON Service</title>

<script type="text/javascript">
function subUser()
{
	var fullname=document.getElementById("fullname").value;
	var mobile=document.getElementById("mobile").value;
	var location=document.getElementById("location").value;
	var auth=document.getElementById("auth").value;
	
	xmlhttp=new XMLHttpRequest();
	var url = "../service/SubUser";
	
	var subUserData=new Object();
		
	subUserData["fullname"]=fullname;
	subUserData["mobile"]=mobile;
	subUserData["location"]=location;
	subUserData["auth"]=auth;
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
				document.getElementById("userId").value=json.userId;		
	    }
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send(JSON.stringify(subUserData));	
}
</script>

</head>
<body>
	<h1>Submit User</h1>
	<table>
		<tr>
			<td>user_full_name</td>
			<td><input type="text" id="fullname" name="fullname"></td>
		</tr>
		<tr>
			<td>user_mobile</td>
			<td><input type="text" id="mobile" name="mobile"></td>
		</tr>
		<tr>
			<td>user_location</td>
			<td><input type="text" id="location" name="location"></td>
		</tr>
		<tr>
			<td>user_auth</td>
			<td><input type="password" id="auth" name="auth"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="Button" name="submit" id="submit" onclick="subUser()"  value="Get UserId"></td>
		</tr>
	</table>
	<br/>
	<input type="text" id="userId" name="userId" readonly="readonly">

</body>
</html>