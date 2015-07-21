<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SearchBug</title>

<script type="text/javascript">
function SearchBug()
{
	var id=document.getElementById("id").value;
	var lat=document.getElementById("lat").value;
	var lng=document.getElementById("lng").value;
	var cat=document.getElementById("cat").value;
	var locality=document.getElementById("locality").value;
	var token=document.getElementById("token").value;
	
	if(document.getElementById("id").value=="")
		id=0;
	else
		id=id;
	
	if(document.getElementById("token").value=="")
		token=0;
	else
		token=token;
	
	xmlhttp=new XMLHttpRequest();
	var url = "service/SearchBug";
	
	var SearchBugData=new Object();
		
	SearchBugData["id"]=id;
	SearchBugData["lat"]=lat;
	SearchBugData["lng"]=lng;
	SearchBugData["cat"]=cat;
	SearchBugData["locality"]=locality;
	SearchBugData["token"]=token;
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
				

				document.getElementById("returncode").value=json.returnCode;
				
				if(json.returnCode==0)
				{
					//document.getElementById("id").value=json.id;
					document.getElementById("lat").value=json.slat;
					document.getElementById("lng").value=json.slng;
					document.getElementById("cat").value=json.scat;
					document.getElementById("locality").value=json.slocality;
					
					document.getElementById("rid").value=json.id;
					document.getElementById("rlat").value=json.lat;
					document.getElementById("rlng").value=json.lng;
					document.getElementById("rcat").value=json.cat;
					document.getElementById("rpic").src=json.pic;
					document.getElementById("rlocality").value=json.locality;
					document.getElementById("rsubmitter").value=json.submitter;
					document.getElementById("rowner").value=json.owner;
					document.getElementById("rstate").value=json.state;
					document.getElementById("rdatecreated").value=json.datecreated;
					document.getElementById("rdateclosed").value=json.dateclosed;
					document.getElementById("rseverity").value=json.severity;
					document.getElementById("rnotes").value=json.notes;
					document.getElementById("rvotes").value=json.votes;
					
				}
				
				if(document.getElementById("id").value!="")
					document.getElementById("returntoken").value="";
				else
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
	xmlhttp.send(JSON.stringify(SearchBugData));	
}
</script>

</head>
<body>
	<h1>SearchBug</h1>
	<table>
			<tr>
				<td>Id</td>
				<td><input type="text" id="id" name="id"></td>
			</tr>
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
			<tr>
				<td>Date Created</td>
				<td><input type="text" id="datecreated" name="datecreated"></td>
			</tr>
			<tr>
				<td>Date Closed</td>
				<td><input type="text" id="dateclosed" name="dateclosed"></td>
			</tr>
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
				<td>Token</td>
				<td><input type="text" id="token" name="token"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="Button" name="submit" id="submit" onclick="SearchBug()" value="SearchBug"></td>
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
				<td>Id</td>
				<td><input type="text" id="rid" name="rid"></td>
			</tr>
			<tr>
				<td>Lat</td>
				<td><input type="text" id="rlat" name="rlat"></td>
			</tr>
			<tr>
				<td>Long</td>
				<td><input type="text" id="rlng" name="rlng"></td>
			</tr>
			<tr>
				<td>Cat</td>
				<td><input type="text" id="rcat" name="rcat"></td>
			</tr>
			<tr>
				<td>Pic</td>
				<td><img id="rpic" name="rpic" height="40" width="100"></td>
			</tr>
			<tr>
				<td>Locality</td>
				<td><input type="text" id="rlocality" name="rlocality"></td>
			</tr>
			<tr>
				<td>Submitter</td>
				<td><input type="text" id="rsubmitter" name="rsubmitter"></td>
			</tr>
			<tr>
				<td>Owner</td>
				<td><input type="text" id="rowner" name="rowner"></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type="text" id="rstate" name="rstate"></td>
			</tr>
			<tr>
				<td>Date Created</td>
				<td><input type="text" id="rdatecreated" name="rdatecreated"></td>
			</tr>
			<tr>
				<td>Date Closed</td>
				<td><input type="text" id="rdateclosed" name="rdateclosed"></td>
			</tr>
			<tr>
				<td>Severity</td>
				<td><input type="text" id="rseverity" name="rseverity"></td>
			</tr>
			<tr>
				<td>Notes</td>
				<td><input type="text" id="rnotes" name="rnotes"></td>
			</tr>
			<tr>
				<td>Votes</td>
				<td><input type="text" id="rvotes" name="rvotes"></td>
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
				<td>ReturnToken</td>
				<td><input type="text" id="returntoken" name="returntoken"></td>
			</tr>
		
		</table>
</body>
</html>
