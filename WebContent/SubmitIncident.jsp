<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SubmitIncident</title>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<script type="text/javascript" src="js/exif.js"></script>
<script type="text/javascript">

canvasCtx = null;
imageFile = null;
url = null;
flag=0;
latitude=null;
longitude=null;

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
		EXIF.getData(event.target.files[0], function() {
	        	latitude = EXIF.getTag(this, "GPSLatitude"),
	            longitude = EXIF.getTag(this, "GPSLongitude");
	        //alert(EXIF.pretty(this));
	    });
	}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = img.width;
		this.canvasCtx.canvas.height = img.height;
		this.canvasCtx.drawImage(img,0,0);
		url = canvasCtx.canvas.toDataURL("image/png");
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
	var cat=document.getElementById("cat").value;
	
	var subBugData=new Object();
	
	subBugData["cat"]=cat;	
	subBugData["lat"]=lat;	
	subBugData["lng"]=lng;	
	subBugData["locality"]=loc;	
		
	if(flag==1)
		subBugData["pic"]=pic;
	else
		subBugData["pic"]="";
	
	subBugData["submitter"]="";
	subBugData["owner"]="";
	subBugData["state"]="";
	subBugData["severity"]="";
	subBugData["notes"]="";
	subBugData["votes"]="";
		
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
	if(typeof latitude !=="undefined" && typeof longitude !=="undefined")
	{

		lat=latitude[0];
		if(latitude[1])
			lat=lat+"."+Math.round(latitude[1]);
		if(latitude[2])
			lat=lat+Math.round(latitude[2]);
		
		lng=longitude[0];
		if(longitude[1])
			lng=lng+"."+Math.round(longitude[1]);
		if(longitude[2])
			lng=lng+Math.round(longitude[2]);
		
		var latlng = new google.maps.LatLng(lat, lng);
		
		geocoder = new google.maps.Geocoder();
		var flag=0;
		var res=null;
		    geocoder.geocode({'latLng': latlng}, function(results, status) {
		        if (status == google.maps.GeocoderStatus.OK) {
		            if (results[0]) {
		                for (j = 0; j < results[0].address_components.length; j++) {
		                    if (results[0].address_components[j].types[0] == 'postal_code')
		                    {
		                    	alert("Zip Code: " + results[0].address_components[j].short_name);
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
	else
		alert("Invalid picture.");
}

</script>

</head>
<body>
	<h1>Submit Incident</h1>
	<table>
		<tr>
			<td>Cat</td>
			<td><input type="text" id="cat" name="cat"></td>
		</tr>
		<tr>
			<td>Pic</td>
			<td><input type="file" id="pic"><canvas id="panel"></canvas></td>
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
