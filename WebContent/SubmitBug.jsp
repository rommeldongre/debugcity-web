<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SubmitBug</title>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<script type="text/javascript">

canvasCtx = null;
imageFile = null;
url = null;
flag=0;

window.onload = function () {
	canvasCtx = document.getElementById("panel").getContext("2d");
	document.getElementById("pic").onchange = function(event) {
		
		flag=1;
		
		this.imageFile = event.target.files[0];
		
		var reader = new FileReader();
		reader.onload =  function(event) {
			var img = new Image();
			img.onload = function() {
				drawImage(img);
			}
			img.src = event.target.result;
		}
		reader.readAsDataURL(this.imageFile);
	}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = img.width;
		this.canvasCtx.canvas.height = img.height;
		this.canvasCtx.drawImage(img,0,0);
		url = canvasCtx.canvas.toDataURL("image/png");
		console.log(url);
		flag=1;
		setURL(url,flag);
	}
}

function setURL(url,flag)
{
	flag=flag;
	pic= url;
}

function submitBug(loc)
{
	var lat=document.getElementById("lat").value;
	var lng=document.getElementById("lng").value;
	var cat=document.getElementById("cat").value;
	var locality=loc;//document.getElementById("locality").value;
	var submitter=document.getElementById("submitter").value;
	var owner=document.getElementById("owner").value;
	var state=document.getElementById("state").value;
	
	var severity=document.getElementById("severity").value;

	var notes=document.getElementById("notes").value;
	
	var votes=document.getElementById("votes").value;

	var subBugData=new Object();
	
	subBugData["lat"]=lat;
	subBugData["lng"]=lng;
	subBugData["cat"]=cat;
	subBugData["locality"]=loc;
	
		
	if(flag==1)
		subBugData["pic"]=pic;
	else
		subBugData["pic"]="";
	/*
	if(document.getElementById("locality").value!="")
		subBugData["locality"]=locality;
	else
		subBugData["locality"]="Unknown";
	*/
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

function getLocation()
{
	if(document.getElementById("locality").value!="")
	{
		loc=document.getElementById("locality").value;
		submitBug(loc);
	}
	else
	{

		lat=document.getElementById("lat").value;
		lng=document.getElementById("lng").value;
		var latlng = new google.maps.LatLng(document.getElementById("lat").value,document.getElementById("lng").value);
		geocoder = new google.maps.Geocoder();
		var flag=0;
		var res=null;
		    geocoder.geocode({'latLng': latlng}, function(results, status) {
		        if (status == google.maps.GeocoderStatus.OK) {
		            if (results[0]) {
		                for (j = 0; j < results[0].address_components.length; j++) {
		                    if (results[0].address_components[j].types[0] == 'postal_code')
		                    {
		                    	//alert("Zip Code: " + results[0].address_components[j].short_name);
		                    	res=results[0].address_components[j].short_name;
		                    	flag=1;
		                    	break;
		                    }
		                }
		                if(flag==1)
		                {
		                	submitBug(res);
		               	}
		                else
			            {
		                	var loc="Unknown";
			          		submitBug(loc);
			            }
		            }
		        } else {
		            var loc="Unknown";
		          	submitBug(loc);
		        }
		    });
	}
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
			<td><input type="file" id="pic"><canvas id="panel"></canvas></td>
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
				onclick="getLocation()" value="Get BugId"></td>
		</tr>
	</table>
	<br/>
	<input type="text" id="bugId" name="bugId" readonly="readonly">

	<input type="text" id="returnCode" name="returnCode" readonly="readonly">

	<input type="text" id="errorString" name="errorString" >

</body>
</html>
