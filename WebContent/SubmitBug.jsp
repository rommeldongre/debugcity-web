<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SubmitBug</title>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css"/>
	<style>
				* {
					    margin: 0;
					}
					html, body {
					    height: 100%;
					}
					.wrapper {
					    min-height: 100%;
					    height: auto !important;
					    height: 100%;
					    margin: 0 auto -30px; /* the bottom margin is the negative value of the footer's height */
					}
					.footer, .push {
					    height: 55px; /* .push must be the same height as .footer */
						font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    					font-size: 14px;
					}
		</style>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
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

</head><body style="background:">
	<div class="wrapper">
	<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header pull-left">
			<a class="navbar-brand" href="index.html"><b>DebugCityV1</b></a>
		</div>
		<div class="navbar-header pull-right">
			<ul class="nav navbar-nav pull-left">
				<li><a class="" href="admin.html">Admin&nbsp;</a></li>
			</ul>
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
          	</button>
         </div>
         <div class="navbar-collapse collapse">
         	
         	<div class="navbar-right">
         		<ul class="nav navbar-nav">
         			<li><a href="index.html">Home</a></li> 
         			<li><a href="spider.html">Spider Graph</a></li> 
         			<li><a href="DebugCityv1.20150723.apk">Download App</a></li> 
         		</ul>
         	</div>
        </div>
   </div>
</div>
<div class="container" style="margin-top:40px;">
  <h2>Submit Bug</h2>
  <form class="form-horizontal" role="form">
    <div class="form-group">
      <label class="control-label col-sm-2" for="lat">Lat</label>
      <div class="col-sm-10">
        <input input type="text" id="lat" name="lat" class="form-control" placeholder="Enter latitude">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="lng">Long</label>
      <div class="col-sm-10">
        <input input type="text" id="lng" name="lng" class="form-control" placeholder="Enter longitude">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="cat">Cat</label>
      <div class="col-sm-10">
        <input input type="text" id="cat" name="cat" class="form-control" placeholder="Enter category">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pic">Pic</label>
      <div class="col-sm-10">          
        <input type="file" class="form-control" type="file" id="pic"><canvas id="panel"></canvas>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="locality">Locality</label>
      <div class="col-sm-10">
        <input input type="text" id="locality" name="locality" class="form-control" placeholder="Enter locality">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="submitter">Submitter</label>
      <div class="col-sm-10">
        <input input type="text" id="submitter" name="submitter" class="form-control" placeholder="Enter submitter">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="owner">Owner</label>
      <div class="col-sm-10">
        <input input type="text" id="owner" name="owner" class="form-control" placeholder="Enter owner">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="state">State</label>
      <div class="col-sm-10">
        <input input type="text" id="state" name="state" class="form-control" placeholder="Enter state">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="severity">Severity</label>
      <div class="col-sm-10">
        <input input type="text" id="severity" name="severity" class="form-control" placeholder="Enter severity">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="notes">Notes</label>
      <div class="col-sm-10">
        <input input type="text" id="notes" name="notes" class="form-control" placeholder="Enter notes">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="votes">Votes</label>
      <div class="col-sm-10">
        <input input type="text" id="votes" name="votes" class="form-control" placeholder="Enter votes">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox"> Share on Facebook</label>
        </div>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
      		<input class="btn btn-default" type="Button" name="submit" id="submit"
				onclick="getLocation()" value="Get BugId">
	  </div>
    </div>
	<div class="form-group">
      <label class="control-label col-sm-2" for="bugId">Bug id</label>
      <div class="col-sm-10">
        <input input type="text" id="bugId" name="bugId" readonly="readonly" class="form-control" placeholder="Bug id">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="returnCode">Return code</label>
      <div class="col-sm-10">
        <input input type="text" id="returnCode" name="returnCode" readonly="readonly" class="form-control" placeholder="Return code">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="errorString">Error string</label>
      <div class="col-sm-10">
        <input input type="text" id="errorString" name="errorString" readonly="readonly" class="form-control" placeholder="Error string">
      </div>
    </div>
</div>
</div>
<div class="footer" style="background-color:#222; margin-top:30px;">	
	<center><font style="color:#9d9d9d;"><br>© Grey Labs LLP. All Rights Reserved.</center>	
</div>
</body>
</html>
</body>
</html>
