<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SubmitBug</title>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<script type="text/javascript">
function submitBug()
{
	var lat=document.getElementById("lat").value;
	var lng=document.getElementById("lng").value;
	var cat=document.getElementById("cat").value;
	var pic=document.getElementById("pic").value;
	var locality=document.getElementById("locality").value;
	var submitter=document.getElementById("submitter").value;
	var owner=document.getElementById("owner").value;
	var state=document.getElementById("state").value;
	//var datecreated=document.getElementById("datecreated").value;
	//var dateclosed=document.getElementById("dateclosed").value;
	//var severity=document.getElementById("severity").value;
	
	var severity=document.getElementById("severity").value;

	var notes=document.getElementById("notes").value;
	//var votes=document.getElementById("votes").value;
	
	var votes=document.getElementById("votes").value;

	var subBugData=new Object();
	
	subBugData["lat"]=lat;
	subBugData["lng"]=lng;
	subBugData["cat"]=cat;
	
	if(document.getElementById("pic").value!="");
		subBugData["pic"]=pic;
		
	if(document.getElementById("locality").value!="")
		subBugData["locality"]=locality;
	
	if(document.getElementById("submitter").value!="");
		subBugData["submitter"]=submitter;
	
	if(document.getElementById("owner").value!="");	
		subBugData["owner"]=owner;

	if(document.getElementById("state").value!="");	
		subBugData["state"]=state;
	
	if(document.getElementById("severity").value!="");	
		subBugData["severity"]=severity;
	
	if(document.getElementById("notes").value!="");
		subBugData["notes"]=notes;
		
	if(document.getElementById("votes").value!="");	
		subBugData["votes"]=votes;
	
	xmlhttp=new XMLHttpRequest();
	var url = "service/SubmitBug";

	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
				if(json.bugId!=null)
				{	
					document.getElementById("bugId").value=json.bugId;
				}
				else
					document.getElementById("bugId").value="error";
				
				document.getElementById("returnCode").value=json.returnCode;
				if(json.returnCode!=0)
				{
					document.getElementById("errorString").value=json.errorString;		
				}
				else
					document.getElementById("errorString").value="bug successfully submitted.";
		}
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send(JSON.stringify(subBugData));	
}
</script>

</head>
<body>
	<h1>Submit Bug</h1>
	<table>
		<tr>
			<td>Lat</td>
			<td><input type="text" id="lat" name="lat"></td>
		</tr>
		<tr>
			<td>Long</td>
			<td><input type="text" id="lng" name="lng"></td>
		</tr>
		<tr>
			<td>Cat</td>
			<td><input type="text" id="cat" name="cat"></td>
		</tr>
		<tr>
			<td>Pic</td>
			<td><input type="text" id="pic" name="pic"></td>
		</tr>
		<tr>
			<td>Locality</td>
			<td><input type="text" id="locality" name="locality"></td>
		</tr>
		<tr>
			<td>Submitter</td>
			<td><input type="text" id="submitter" name="submitter"></td>
		</tr>
		<tr>
			<td>Owner</td>
			<td><input type="text" id="owner" name="owner"></td>
		</tr>
		<tr>
			<td>State</td>
			<td><input type="text" id="state" name="state"></td>
		</tr>
		<!-- <tr>
			<td>Date Created</td>
			<td><input type="text" id="datecreated" name="datecreated"></td>
		</tr>
		<tr>
			<td>Date Closed</td>
			<td><input type="text" id="dateclosed" name="dateclosed"></td>
		</tr>-->
		<tr>
			<td>Severity</td>
			<td><input type="text" id="severity" name="severity"></td>
		</tr>
		<tr>
			<td>Notes</td>
			<td><input type="text" id="notes" name="notes"></td>
		</tr>
		<tr>
			<td>Votes</td>
			<td><input type="text" id="votes" name="votes"></td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="Button" name="submit" id="submit"
				onclick="submitBug()" value="Get BugId"></td>
		</tr>
	</table>
	<br/>
	<input type="text" id="bugId" name="bugId" readonly="readonly">

	<input type="text" id="returnCode" name="returnCode" readonly="readonly">

	<input type="text" id="errorString" name="errorString" >

</body>
</html>
